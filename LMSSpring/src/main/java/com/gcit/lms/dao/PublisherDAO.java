/**
 * 
 */
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

import com.gcit.lms.domain.Publisher;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>> {

	@Autowired
	BookDAO bdao;

	@Autowired
	MongoOperations mongoOps;
	
	
	private static final String PUB_COLLECTIONS="Publisher";
	
	public int createPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		
		publisher.setPublisherId((int)getNextSequenceId(PUB_COLLECTIONS));
		
		mongoOps.save(publisher,PUB_COLLECTIONS);
		
		return 1;
	}

	public List<Publisher> readAll() throws ClassNotFoundException, SQLException {
		return template.query("select * from tbl_publisher", this);
	}

	public Publisher getPublisherById(int publisherId) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		
		publishers = (List<Publisher>)template.query("select * from tbl_publisher where publisherId = ?", new Object[] { publisherId },
				this);

		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		return null;
	}

	public Publisher getPublisher(int publisherId) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		publishers = template.query("select * from tbl_publisher where publisherId = ?", new Object[] { publisherId },
				this);

		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) {
		List<Publisher> publishers = new ArrayList<Publisher>();

		try {
			while (rs.next()) {
				Publisher p = new Publisher();
				p.setPublisherId(rs.getInt("publisherId"));
				p.setPublisherAddress(rs.getString("publisherAddress"));
				p.setPublisherName(rs.getString("publisherName"));
				p.setPublisherPhone(rs.getString("publisherPhone"));
				// List<Book> books;
				//
				// books = (List<Book>)bdao.template.query(
				// "select * from tbl_book where pubId =?",
				// new Object[] { p.getPublisherId() },bdao);
				// p.setBooks(books);

				publishers.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return publishers;
	}

	// @Override
	// public List extractDataFirstLevel(ResultSet rs) {
	// List<Publisher> publishers = new ArrayList<Publisher>();
	//
	// try {
	// while (rs.next()) {
	// Publisher p = new Publisher();
	// p.setPublisherId(rs.getInt("publisherId"));
	// p.setPublisherAddress(rs.getString("publisherAddress"));
	// p.setPublisherName(rs.getString("publisherName"));
	// p.setPublisherPhone(rs.getString("publisherPhone"));
	//
	// publishers.add(p);
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return publishers;
	// }
	//
	
	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("publisherId").is(publisher.getPublisherId()));
		
		Update update = new Update();
		update.set("publisherName", publisher.getPublisherName());
		update.set("publisherAddress", publisher.getPublisherAddress());
		update.set("publisherPhone", publisher.getPublisherPhone());
		
		mongoOps.updateFirst(query, update, PUB_COLLECTIONS);
		
	}

	public int deletePublisher(int publisherId) throws ClassNotFoundException, SQLException {
		Query query = new Query();
		query.addCriteria(Criteria.where("publisherId").is(publisherId));
		mongoOps.remove(query,PUB_COLLECTIONS);
		return 1;
	}

	private int checkPublisher(int publisherId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return template.queryForObject("select Count(*) from tbl_book where pubId=?", new Object[] { publisherId },
				Integer.class);
	}

	public List<Publisher> getAllPublishers(String searchString, int pageNo, int pageSize)
			throws ClassNotFoundException, SQLException {
		Query query=new Query();
		Criteria criteria =new Criteria();
		if(searchString !=null)
		criteria.orOperator(Criteria.where("publisherName").regex(searchString),Criteria.where("publisherAddress").regex(searchString));
		query.addCriteria(criteria);
		return mongoOps.find(query,Publisher.class, PUB_COLLECTIONS);
	}

	public int getCount(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";
		return template.queryForObject(
				"select count(*) as count from tbl_publisher where publisherName like ? or publisherAddress like ? or publisherPhone like ?",
				new Object[] { searchString, searchString, searchString }, Integer.class);

	}

	public Publisher getPublisherByBook(int bookId) {
		List<Publisher> publishers = new ArrayList<Publisher>();
		publishers = template.query("select * from tbl_publisher where publisherId in(select pubId from tbl_book where bookId= ?)", new Object[] { bookId },
				this);

		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		return null;
	}

}
