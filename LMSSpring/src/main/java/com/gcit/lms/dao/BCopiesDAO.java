package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BCopies;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Branch;

@SuppressWarnings("rawtypes")
public class BCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BCopies>> {

	@Autowired
	BookDAO bdao;
	
	public int createBCopies(BCopies bc) throws ClassNotFoundException,
			SQLException {
		return template.update("insert into tbl_book_copies (bookId,branchId,noOfCopies) values(?,?,?)",
				new Object[] { bc.getBook().getBookId(),bc.getBranch().getBranchId(),bc.getNoofCopies() });
	}

	public int updateBCopies(BCopies bc) throws ClassNotFoundException,
			SQLException {
		return template.update("update tbl_book_copies set noOfCopies = noOfCopies + ? where bookId=? and branchId = ?",
				new Object[] { bc.getAddCopies(),bc.getBook().getBookId(),bc.getBranch().getBranchId()});
	}

	public int deleteBCopies(BCopies bc) throws ClassNotFoundException,
			SQLException {
		return template.update("delete from tbl_book_copies where bookId=? and branchId=?",
				new Object[] {bc.getBook().getBookId(),bc.getBranch().getBranchId() });
	}

	public List<BCopies> getAllBookCopiess() throws ClassNotFoundException,
			SQLException {
		return template.query("select tbl_book.bookId,tbl_branch.branchId, branchName, title,noOfCopies from tbl_book,tbl_book_copies,tbl_branch where tbl_branch.branchId = tbl_book_copies.branchId and tbl_book_copies.bookId=tbl_book.bookId",this);
	}

	public List<BCopies> getBranchCopies(int branchId) throws ClassNotFoundException,
			SQLException {
		
		return  template.query("select tbl_book.bookId,tbl_library_branch.branchId, branchName,title,noOfCopies from tbl_book,tbl_book_copies,tbl_library_branch where tbl_library_branch.branchId = ? and tbl_book_copies.bookId=tbl_book.bookId and tbl_library_branch.branchId = tbl_book_copies.branchId",
				new Object[] {branchId },this);
	}
	
	public List<BCopies> getBorrowableBooks(int branchId) throws ClassNotFoundException, SQLException
	{
		return  template.query("select tbl_book.bookId,title,tbl_library_branch.branchId, branchName,noOfCopies from tbl_book_copies,tbl_library_branch,tbl_book where tbl_library_branch.branchId = ? and tbl_library_branch.branchId = tbl_book_copies.branchId and tbl_book.bookId=tbl_book_copies.bookId and noOfCopies > 0",
				new Object[] {branchId },this);
		
	}

	@Override
	public List<BCopies> extractData(ResultSet rs) {
		List<BCopies> bcopies = new ArrayList<BCopies>();
		

		try {
			while (rs.next()) {
				BCopies a = new BCopies();
				Book book = new Book();
				Branch branch = new Branch();
				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));
				branch.setBranchId(rs.getInt("branchId"));
				branch.setBranchName(rs.getString("branchName"));
				a.setNoofCopies(rs.getInt("noOfCopies"));
				a.setBook(book);
				a.setBranch(branch);
				bcopies.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bcopies;
	}

	public BCopies getCopy(int branchId, int bookId) {
		// TODO Auto-generated method stub
		List<BCopies> bcopies = new ArrayList<BCopies>();
		bcopies = template.query("select tbl_book.bookId,tbl_library_branch.branchId, branchName,title,noOfCopies from tbl_book,tbl_book_copies,tbl_library_branch where tbl_library_branch.branchId = ? and tbl_book.bookId=? and tbl_book_copies.bookId=tbl_book.bookId and  tbl_library_branch.branchId = tbl_book_copies.branchId",
				new Object[] {branchId,bookId },this);

		if (bcopies != null && bcopies.size() > 0) {
			return bcopies.get(0);
		}
		return null;
	}

	
}