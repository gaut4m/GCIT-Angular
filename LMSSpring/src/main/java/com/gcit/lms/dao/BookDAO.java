/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.Publisher;

/**
 * @book Gautham
 *
 */
@SuppressWarnings("unchecked")
public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{

	@Autowired
	PublisherDAO pdao;
	@Autowired
	AuthorDAO adao;
	@Autowired
	GenreDAO gdao;
	@Autowired
	BasicDataSource ds;
	
	public int createBook(final String title, final Publisher pub) throws ClassNotFoundException,
			SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		PreparedStatementCreator psc=null;
		if(pub.getPublisherId()!=0){
			final String query = "insert into tbl_book (title, pubId) values(?, ?)";
			psc = new PreparedStatementCreator() {           

	            @Override
	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	               PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	                ps.setString(1, title);
	                ps.setInt(2, pub.getPublisherId());
	                
	                return ps;
	            }
	        };}
		else
		{
			final String query = "insert into tbl_book (title) values(?)";
		psc = new PreparedStatementCreator() {           

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
               PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, title);
             return ps;
            }
        };}
		template.update(psc,keyHolder);
		
		return keyHolder.getKey().intValue();
		

	}
	
	public void addBookAuthors(int authorId, int bookId)
	{
		template.update("insert into tbl_book_authors (bookId, authorId) values (?,?)",
				new Object[] { bookId, authorId });
	}
	
	public void addBookGenres(int genreId, int bookId)
	{
		template.update("insert into tbl_book_genres (bookId, genre_id) values (?,?)",
				new Object[] { bookId, genreId });
	}

	public List<Book> getAllBooks() throws ClassNotFoundException,
			SQLException {
		return template.query("select * from tbl_book", this);
	}

	
	@Override
	public List<Book> extractData(ResultSet rs) {
		List<Book> books = new ArrayList<Book>();
		
		try {
			while (rs.next()) {
				Book b = new Book();

				b.setBookId(rs.getInt("bookId"));

				b.setTitle(rs.getString("title"));
				Publisher pub = new Publisher();
				pub.setPublisherId(rs.getInt("pubId"));
				b.setPublisher(pub);
				
//				List<Author> authors = (List<Author>)adao.template.query("select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
//						new Object[] { b.getBookId() },adao);
//				b.setAuthors(authors);
//				
//				List<Genre> genres = (List<Genre>) gdao.template.query("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)",
//						new Object[] { b.getBookId() },gdao);
//				b.setGenres(genres);
				
				books.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

//	@Override
//	public List extractDataFirstLevel(ResultSet rs) {
//		List<Book> books = new ArrayList<Book>();
//
//		try {
//			while (rs.next()) {
//				Book b = new Book();
//
//				b.setBookId(rs.getInt("bookId"));
//
//				b.setTitle(rs.getString("title"));
//				
//				books.add(b);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return books;
//	}
//
//
	
public int updateBook(Book book) throws ClassNotFoundException,
	SQLException {
return template.update("update tbl_book set bookName = ? where bookId = ?",
		new Object[] { book.getTitle(), book.getBookId() });
}

public int deleteBook(Book book) throws ClassNotFoundException,
	SQLException {
	int i=0;
	if(checkBook(book) == 0)
		i=template.update("delete from tbl_book where bookId=?",
		new Object[] { book.getBookId() });
	if(i>0)
		return deleteBookAuthors(book);
	else
		return 0;
}

public int checkBook(Book book) throws ClassNotFoundException,
SQLException {
return template.queryForObject("select Count(*) from tbl_book_loans where bookId=? and dateIn IS NOT NULL",
	new Object[] { book.getBookId() },Integer.class);
}


public int deleteBookAuthors(Book book) throws ClassNotFoundException,
SQLException {
return template.update("delete from tbl_book_authors where bookId=?",
	new Object[] { book.getBookId() });
}

public List<Book> getBookByAuthorId(int authorId){
	List<Book> books = (List<Book>) template.query(
			"select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)",
			new Object[] { authorId}, this);
	return books;
}

public List<Book> getBookByPublisher(int publisherId){
	List<Book> books =  (List<Book>)template.query(
			"select * from tbl_book where pubId =?",
			new Object[] { publisherId },this);
	return books;
}

public Book getBook(int bookId) throws ClassNotFoundException,
	SQLException {
List<Book> books = new ArrayList<Book>();
books = template.query("select * from tbl_book where bookId = ?",
		new Object[] { bookId },this);

if (books != null && books.size() > 0) {
	return books.get(0);
}
return null;
}

public List<Book> getBookByGenreId(int genreId) {
	// TODO Auto-generated method stub

	List<Book> books = (List<Book>) template.query(
			"select * from tbl_book where bookId in (select bookId from tbl_book_genres where genre_id = ?)",
			new Object[] { genreId}, this);
	return books;
}



}
