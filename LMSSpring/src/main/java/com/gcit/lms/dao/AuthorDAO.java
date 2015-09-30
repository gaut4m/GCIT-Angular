package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Publisher;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>> {

	@Autowired
	BookDAO bdao;
	@Autowired
	MongoOperations mongoOps;
	
	public AuthorDAO() {

	}
	
	private static final String AUTH_COLLECTIONS="Authors";


	public int createAuthor(final Author author) throws Exception {
		
		author.setAuthorId((int) getNextSequenceId(AUTH_COLLECTIONS));
		mongoOps.save(author,AUTH_COLLECTIONS);
		
		return 1;
	}

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		Query query = new Query();
		query.addCriteria(Criteria.where("authorId").is(author.getAuthorId()));
		
		Update update = new Update();
		update.set("authorName", author.getAuthorName());
		mongoOps.updateFirst(query, update, AUTH_COLLECTIONS);
	}

	public int deleteAuthor(int authorId) throws ClassNotFoundException, SQLException {

		Query query = new Query();
		query.addCriteria(Criteria.where("authorId").is(authorId));
		mongoOps.remove(query,AUTH_COLLECTIONS);
		return 1;
	}

	private int checkAuthor(int authorId) throws ClassNotFoundException, SQLException {

		return template.queryForObject("select count(*) from tbl_book_authors where authorId=?",
				new Object[] { authorId }, Integer.class);

	}

	public List<Author> getAuthorByBookId(int bookId) {
		return (List<Author>) template.query(
				"select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
				new Object[] { bookId }, this);
	}

	
	public List<Author> getAllAuthors(String searchString, int pageNo, int pageSize)
			throws ClassNotFoundException, SQLException {
		Query query=new Query();
		query.addCriteria(Criteria.where("authorName").regex(searchString));
		return mongoOps.find(query,Author.class, AUTH_COLLECTIONS);
		
	}

	public int getCount(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";
		return template.queryForObject("select count(*) as count from tbl_author where authorName like ?",
				new Object[] { searchString }, Integer.class);
	}

	public List<Author> getAuthorsByName(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";

		return (List<Author>) template.query("select * from tbl_author where authorName like ?",
				new Object[] { searchString }, this);

	}

	public Author getAuthor(Author author) throws ClassNotFoundException, SQLException {
		List<Author> authors = new ArrayList<Author>();
		authors = (List<Author>) template.query("select * from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() }, this);

		if (authors != null && authors.size() > 0) {
			return authors.get(0);
		}
		return null;
	}

	@Override
	public List<Author> extractData(ResultSet rs) {
		List<Author> authors = new ArrayList<Author>();

		try {
			while (rs.next()) {
				Author a = new Author();
				a.setAuthorId(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));
				authors.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return authors;
	}

	// @Override
	// public List<Author> extractDataFirstLevel(ResultSet rs) {
	// List<Author> authors = new ArrayList<Author>();
	//
	// try {
	// while (rs.next()) {
	// Author a = new Author();
	// a.setAuthorId(rs.getInt("authorId"));
	// a.setAuthorName(rs.getString("authorName"));
	// authors.add(a);
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return authors;
	// }
}
