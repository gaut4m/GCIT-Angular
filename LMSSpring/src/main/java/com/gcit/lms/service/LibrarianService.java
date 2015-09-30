package com.gcit.lms.service;


import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.LoansDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.BCopies;
import com.gcit.lms.domain.Branch;

public class LibrarianService {
	
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
	@Autowired
	AuthorDAO adao;
	@Autowired
	PublisherDAO pdao;

	@Transactional
	public List<BCopies> getAvailableBooks(int branchId) throws ClassNotFoundException, SQLException
	{
		 List<BCopies> bcopies = bcdao.getBorrowableBooks(branchId);
		 for(BCopies b: bcopies)
		 {
			 b.getBook().setAuthors(adao.getAuthorByBookId(b.getBook().getBookId()));
			 b.getBook().setPublisher(pdao.getPublisherByBook(b.getBook().getBookId()));
			
		 }
				return bcopies;
	}

	@Transactional
	public int updateCopies(BCopies bcopies) throws Exception {
		// TODO Auto-generated method stub
		int i=0;
		
				i = bcdao.updateBCopies(bcopies);
			
			
		return i;
	}
	
	@Transactional
	public List<BCopies> getCopies(int branchId) throws ClassNotFoundException, SQLException
	{
		
		return bcdao.getBranchCopies(branchId);
			
			
			
			
	}
	
	@Transactional
	public Branch getBranch(int branchId) throws Exception
	{	
			return bhdao.getBranch(branchId);
	}

	public BCopies getCopy(int branchId, int bookId) {
		// TODO Auto-generated method stub
		return bcdao.getCopy(branchId,bookId);
	}
	
}
