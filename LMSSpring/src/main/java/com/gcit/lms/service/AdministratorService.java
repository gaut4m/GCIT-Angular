package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LoansDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Branch;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Loans;
import com.gcit.lms.domain.Publisher;

public class AdministratorService {
	
	@Autowired
	BasicDataSource ds;
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	BranchDAO bhdao;
	
	@Autowired
	BorrowerDAO udao;
	
	@Autowired
	BCopiesDAO bcdao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	LoansDAO ldao;
	
	
	
	
	public int createService(Object obj) throws Exception
	{
		
		if( obj instanceof Author)
			return createAuthor((Author)obj);
		else if	(obj instanceof Genre)	
			return createGenre((Genre)obj);
		else if(obj instanceof Publisher)
			return createPublisher((Publisher)obj);
		else if(obj instanceof Borrower)
			return createBorrower((Borrower)obj);
		else if(obj instanceof Branch)
			return createBranch((Branch)obj);
		else
			return 0;
			
	}
	
	public int updateService(Object obj) throws Exception
	{
		
		if( obj instanceof Author)
			return updateAuthor((Author)obj);
		else if	(obj instanceof Genre)	
			return updateGenre((Genre)obj);
		else if(obj instanceof Publisher)
			return updatePublisher((Publisher)obj);
		else if(obj instanceof Borrower)
			return updateBorrower((Borrower)obj);
		else if(obj instanceof Branch)
			return updateBranch((Branch)obj);
		else
			return 0;
			
	}
	
	

	

	

	public List<?> getService(String str,String searchString, int startNo,int endCnt ) throws Exception
	{
		switch(str)
		{
		case "pubs":
			return (List<?>) getPublisher(searchString,startNo,endCnt);
		case "authors":
			return (List<?>) getAuthor(searchString,startNo,endCnt);
		case "books":
			return (List<?>) getBook(searchString,startNo,endCnt);
		case "users":
			return (List<?>)getBorrower(startNo,endCnt);
		case "genres":
			return (List<?>)getGenres(searchString,startNo,endCnt);
		case "branch":
			return (List<?>)getBranch(searchString,startNo,endCnt);
		
		
		default:
			break;
		}
		return null;
	}
	
	private List<?> getBook(String searchString, int startNo, int endCnt) throws ClassNotFoundException, SQLException {
		List<Book> books = bdao.getAllBooks();
		
		for(Book b : books) {
			b.setAuthors(adao.getAuthorByBookId(b.getBookId()));
			b.setGenres(gdao.getGenreByBookId(b.getBookId()));
			b.setPublisher(pdao.getPublisher(b.getPublisher().getPublisherId()));
		}
		return books;
	}

	public List<?> getService(String str ) throws Exception
	{
		switch(str)
		{
		case "pubs":
			return (List<?>) getPublisher();
		case "authors":
			return (List<?>) getAuthor();
		case "books":
			return (List<?>) getBook();
		case "users":
			return (List<?>)getBorrower();
		case "genres":
			return (List<?>)getGenres();
		case "branch":
			return (List<?>)getBranch();
		
		
		default:
			break;
		}
		return null;
	}

	private List<?> getBranch() {
		 
		return null;
	}

	private List<?> getGenres() throws ClassNotFoundException, SQLException {
		List<Genre> genres = gdao.getAllGenres("",-1,-1);
		
		for(Genre g:genres)
		{
			List<Book> books = (List<Book>)bdao.getBookByGenreId(g.getGenreId());
		
			g.setBooks(books);
		}
		

		
		return genres;
	}

	private List<?> getBorrower() throws ClassNotFoundException, SQLException {
		List<Borrower> users = udao.getAllBorrowers();
		return users;
	}

	private List<?> getPublisher() throws ClassNotFoundException, SQLException {
		List<Publisher> pubs = pdao.getAllPublishers("",-1,-1);
		return pubs;
	}

	private List<?> getAuthor() throws ClassNotFoundException, SQLException {
		List<Author> authors = adao.getAllAuthors("",-1,-1);
		
		return authors;
	}

	public int getCount(String string, String searchString) throws ClassNotFoundException, SQLException {
		 
		switch(string)
		{
		case "pubs":
			return  publisherCount(searchString);
		case "authors":
			return authorCount(searchString);
		//case "books":
		//	return  getBook();
		case "users":
			return borrowerCount(searchString);
		case "genres":
			return genreCount(searchString);
		case "branch":
			return branchCount(searchString);
		
		
		default:
			break;
		}
		return 0;
	}

	public Object getServiceById(String str, int id) throws Exception
	{
	
		switch(str)
		{
		
		case "pubById":
			return getPublisher(id);
		case "cardNo":
			return getBorrower(id);
		case "book":
			return getBook(id);
		case "branch":
			return getBranch(id);
		
		default:
			break;
		}
		return null;
	}

	private Object getBranch(int id) throws ClassNotFoundException, SQLException {
		 
		return bhdao.getBranch(id);
	}

	@Transactional
	public List<Loans> getLoans(int cardNo) throws Exception {
		
			return ldao.getLoans(cardNo);
		
	}

	
	public int deleteService(String str, int id) throws Exception {
		 
		switch(str)
		{
		
		case "pub":
			return deletePublisher(id);
		case "author":
			return deleteAuthor(id);
		case "cardNo":
				return deleteBorrower(id);
		case "branch":
			return deleteBranch(id);
		case "genre":
			return deleteGenre(id);
				
		default:
			break;
		}
		
		return 0;
	}

	@Transactional
	public int createAuthor(Author author) throws Exception {
		//Boolean flag = validateAuthor(author)
		int i=0;
			
				if(author.getAuthorName()!=null && author.getAuthorName() !=""){
				if(author.getAuthorName().length() > 45){
					throw new Exception("Author Name cannot be more than 45 chars");
				}else{
					i=adao.createAuthor(author);
	
					}
				}

				
			
			return i;
	}
	
	@Transactional
	public int extendDate(int bookId, int branchId, int cardNo) throws Exception {
		 
		int i=0;
		
		i= ldao.extendLoan(bookId,branchId,cardNo);
				
			
		return i;
	}
	
	@Transactional
	private int updateGenre(Genre obj) throws Exception {
		 
		int i=0;
		
			if(obj.getGenreName() !=null && obj.getGenreName() !="" ){
			if(obj.getGenreName().length() > 20){
				throw new Exception("Genre Name cannot be more than 20 chars");
			}else{
				gdao.updateGenre(obj);
				i=1;
			}
			
			}
			else
				throw new Exception("Genre Name cannot be null");
				
			
			
		return i;
	}
		
	
	@Transactional
	private int updatePublisher(Publisher obj)throws Exception {
		 
		int i=0;
		if(obj.getPublisherName() !=null){
			if(obj.getPublisherName().length() > 20){
				throw new Exception("Publisher Name cannot be more than 20 chars");
			}else{
				pdao.updatePublisher(obj);
				i=1;
			}
			
			}
			
		return i;
	}
	
	@Transactional
	private int updateBranch(Branch obj) throws Exception {
		 
		int i=0;
		
			if(obj.getBranchName() !=null && obj.getBranchName() !=""){
			if(obj.getBranchName().length() > 20){
				throw new Exception("Branch Name cannot be more than 20 chars");
			}else{
				bhdao.updateBranch(obj);
				i=1;
			}
			
			}
			else
				throw new Exception("Branch Name and Address cannot be null");
				
				
		return i;
	}

	@Transactional
	private int updateAuthor(Author author) throws Exception {
		 
		int i=0;
			if(author.getAuthorName()!=null && author.getAuthorName() !=""){
			if(author.getAuthorName().length() > 45){
				throw new Exception("Author Name cannot be more than 45 chars");
			}else{
				adao.updateAuthor(author);
				i=1;
				}
			}
			
		return i;
	}
	
	@Transactional
	private int updateBorrower(Borrower obj) throws Exception{
		 
		int i=0;
			if(obj.getName().length() > 20){
				throw new Exception("Borrower Name cannot be more than 20 chars");
			}else{
				udao.updateBorrower(obj);
				i=1;
			}
			
			
			
		return i;
	}

	@Transactional
	private int branchCount(String searchString) throws ClassNotFoundException, SQLException {
		 
		//	return bhdao.getCount(searchString);
		
		return 0;
	}

	@Transactional
	private int genreCount(String searchString) throws ClassNotFoundException, SQLException {
		 
		
			//	return gdao.getCount(searchString);
		return 0;
	}
	
	@Transactional
	private int borrowerCount(String searchString) throws SQLException, ClassNotFoundException {
		 
	
		//		return udao.getCount(searchString);
		
		return 0;
	}

	@Transactional
	private int publisherCount(String searchString) throws ClassNotFoundException, SQLException {
		 
		
				return pdao.getCount(searchString);
		
	}

	@Transactional
	private int authorCount(String searchString) throws ClassNotFoundException, SQLException {
		 
		
				return adao.getCount(searchString);
		
	}

	@Transactional
	private List<?> getBook() throws ClassNotFoundException, SQLException {
		 
			List<Book> books = bdao.getAllBooks();
		
			
			
			return books;
	}

	@Transactional
	private Object getBook(int bookId) throws ClassNotFoundException, SQLException {
		 
		Book book =  bdao.getBook(bookId);
		
		book.setAuthors(adao.getAuthorByBookId(book.getBookId()));
		book.setGenres(gdao.getGenreByBookId(book.getBookId()));
		book.setPublisher(pdao.getPublisher(book.getPublisher().getPublisherId()));
			
			return book;
			
			
		
	}

	@Transactional
	private List<?> getBranch(String searchString, int pageNo, int pageSize) throws Exception {
		 
			return bhdao.getAllBranchs();
	}

	@Transactional
	private List<?> getGenres(String searchString, int startNo, int endCnt) throws Exception {
		 
		
		
			return gdao.getAllGenres(searchString,startNo,endCnt);
			
			
		
	}

	@Transactional
	private List<?> getBorrower(int startNo, int endCnt) throws Exception {
		 
		
		
			return udao.getAllBorrowers();
			
	}

	@Transactional
	private Object getBorrower(int cardNo) throws Exception {
		 
		
		
			return udao.getBorrower(cardNo);
		
	}

	
	private Object getPublisher(int id) {
		 
		
		Publisher pub =new Publisher();
		try {
			pub= pdao.getPublisherById(id);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		return pub;
	}

	@Transactional
	private List<?> getPublisher(String searchString, int pageNo, int pageSize) throws Exception {
		 
		
		
		List<Publisher> pubs = pdao.getAllPublishers(searchString,pageNo, pageSize);
		
		for(Publisher p:pubs)
		{
			p.setBooks(bdao.getBookByPublisher(p.getPublisherId()));
		}
		return pubs;
	}
	
	private List<?> getAuthor(String searchString, int pageNo, int pageSize) throws Exception {
		 
		
			List<Author> authors = adao.getAllAuthors(searchString,pageNo, pageSize);
			for(Author a:authors)
			{
				a.setBooks(bdao.getBookByAuthorId(a.getAuthorId()));
			}
			
			return authors;
		
	}

	@Transactional
	private int deleteGenre(int genreId) throws Exception {
		 
	return gdao.deleteGenre(genreId);
	}

	@Transactional
	private int deleteBranch(int branchId) throws Exception {
		 
		
			return bhdao.deleteBranch(branchId);
	}

	@Transactional
	private int deleteBorrower(int cardNo) throws Exception {
		 
		
			return udao.deleteBorrower(cardNo);
			
	}
	
	@Transactional
	private int deleteAuthor(int authorId)throws Exception{
		return adao.deleteAuthor(authorId);
	}

	@Transactional
	private int deletePublisher(int publisherId)throws Exception {
		 
		return pdao.deletePublisher(publisherId);
			
	}

	@Transactional
	private int createBranch(Branch obj) throws Exception {
		 
		int i=0;
			if(obj.getBranchName() !=null && obj.getBranchName() !="" && obj.getBranchAddress() != null && obj.getBranchAddress() != ""){
			if(obj.getBranchName().length() > 20){
				throw new Exception("Branch Name cannot be more than 20 chars");
			}else{
				bhdao.createBranch(obj);
				i=1;
			}
			
			}
			else
				throw new Exception("Branch Name and Address cannot be null");
				
				
		return i;
	}

	@Transactional
	private int createBorrower(Borrower obj) throws Exception {
		 
		int i=0;
		
			if(obj.getName() !=null){
			if(obj.getName().length() > 20){
				throw new Exception("Borrower Name cannot be more than 20 chars");
			}else{
				udao.createBorrower(obj);
				i=1;
			}
			
			}
			return i;
			
	}

	@Transactional
	private int createPublisher(Publisher obj) throws Exception {
		 
		int i=0;
		
			if(obj.getPublisherName() !=null && obj.getPublisherName() !=""){
			if(obj.getPublisherName().length() > 20){
				throw new Exception("Publisher Name cannot be more than 20 chars");
			}else{
				i=pdao.createPublisher(obj);
				
			}
			
			}
			else
				throw new Exception("Publisher Name is empty");
			
		return i;
	}
	
	
	@Transactional
	private int createGenre(Genre genre) throws Exception {
		 
		int i=0;
		
			if(genre.getGenreName() !=null && genre.getGenreName() != ""){
			if(genre.getGenreName().length() > 20){
				throw new Exception("Genre Name cannot be more than 20 chars");
			}else{
				i=gdao.createGenre(genre);
				
				}
			}
			else
				throw new Exception("Genre Name cannot be null");
			
		return i;
	}

	@Transactional
	public int createBook(List<Integer> authorIds, List<Author> newAuthors, List<Integer> genreIds,
			List<Genre> newGenres, Publisher pub, String title) throws Exception {
		
		for(int i=0;i<newGenres.size();i++)
		{
			
			genreIds.add(createGenre(newGenres.get(i)));
		}
		for(int i=0;i<newAuthors.size();i++)
		{
			authorIds.add(createAuthor(newAuthors.get(i)));
		}
		
		
		
		int bookId= bdao.createBook(title,pub);
		
		
		
		if(bookId>0)
		{
		for (int authorId : authorIds) {
			bdao.addBookAuthors(authorId,bookId);	
			
		}
		for (int genreId : genreIds) {
			bdao.addBookGenres(genreId, bookId);
		}
		}
		
		List<Branch> branch = new ArrayList<Branch>();
		
			branch = (List<Branch>)getService("branch","",-1,-1);
			
			for(Branch b:branch)
			{
				addBookCopy(b.getBranchId(),bookId,0);
			}
				
		return bookId;
	}
	
	public int addBookCopy(int branchId,int bookId,int noOfCopies)
	{
		return bhdao.addBookCopy(branchId,bookId,noOfCopies);
	}

	

	
}
