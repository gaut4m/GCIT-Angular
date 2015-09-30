package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Branch;

public class BranchDAO extends BaseDAO implements ResultSetExtractor<List<Branch>> {

	

	public void createBranch(Branch branch) throws ClassNotFoundException,
			SQLException {
		template.update("insert into tbl_library_branch (branchName,branchAddress) values(?,?)",
				new Object[] { branch.getBranchName(),branch.getBranchAddress() });
	}

	public void updateBranch(Branch branch) throws ClassNotFoundException,
			SQLException {
		template.update("update tbl_library_branch set branchName = ?,branchAddress=? where branchId = ?",
				new Object[] { branch.getBranchName(),branch.getBranchAddress(), branch.getBranchId() });
	}

	public int deleteBranch(int branchId) throws ClassNotFoundException,
			SQLException {
		int i=0;
		if(checkBranch(branchId)==0){
			template.update("delete from tbl_book_copies where branchId=?",
				new Object[] { branchId });

		return	template.update("delete from tbl_library_branch where branchId=?",
					new Object[] {branchId });
		}
		
		return 0;
	}
	
	private int checkBranch(int branchId) throws ClassNotFoundException,
	SQLException {
		// TODO Auto-generated method stub
		return template.queryForObject("select Count(*) from tbl_book_loans where branchId=?",
				new Object[] {branchId},Integer.class);
		
	}

	public List<Branch> getAllBranchs() throws ClassNotFoundException,
			SQLException {
		return template.query("select * from tbl_library_branch", this);
	}

	public Branch getBranch(int branchId) throws ClassNotFoundException,
			SQLException {
		List<Branch> branches = new ArrayList<Branch>();
		branches = template.query("select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId },this);

		if (branches != null && branches.size() > 0) {
			return branches.get(0);
		}
		return null;
	}

	@Override
	public List<Branch> extractData(ResultSet rs) {
		List<Branch> branches = new ArrayList<Branch>();

		try {
			while (rs.next()) {
				Branch a = new Branch();
				a.setBranchId(rs.getInt("branchId"));
				a.setBranchName(rs.getString("branchName"));
				a.setBranchAddress(rs.getString("branchAddress"));
				
				
				branches.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return branches;
	}

	public int  addBookCopy(int branchId, int bookId, int i) {
		return template.update("insert into tbl_book_copies (branchId,bookId,noOfCopies) values(?,?,?)",
				new Object[] { branchId,bookId,i });
		
	}

	
}
