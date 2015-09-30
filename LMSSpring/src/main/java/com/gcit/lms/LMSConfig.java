package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LoansDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdministratorService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;
import com.mongodb.MongoClient;

@EnableTransactionManagement
@Configuration
public class LMSConfig {
	
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	private static final String user = "root";
	private static final String pwd = "root";
	
	@Bean
	public AdministratorService service(){
		return new AdministratorService();
	}
	
	@Bean
	public BorrowerService user(){
		return new BorrowerService();
	}

	@Bean
	public LibrarianService lib(){
		return new LibrarianService();
	}
	
	@Bean
	public AuthorDAO authorDAO(){
		return new AuthorDAO();
	}
	
	@Bean
	public BranchDAO branchDAO(){
		return new BranchDAO();
	}
	@Bean
	public BorrowerDAO borrowerDAO(){
		return new BorrowerDAO();
	}
	
	@Bean
	public LoansDAO loanDAO(){
		return new LoansDAO();
	}
	
	@Bean
	public BCopiesDAO bcopiesDAO(){
		return new BCopiesDAO();
	}
	
	@Bean
	public BookDAO bookDAO(){
		return new BookDAO();
	}
	
	@Bean
	public PublisherDAO publisherDAO(){
		return new PublisherDAO();
	}
	
	@Bean
	public GenreDAO genreDAO(){
		return new GenreDAO();
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(datasource());
		
		return tx;
	}
	
	@Bean
	public JdbcTemplate template() {
		JdbcTemplate jdbc = new JdbcTemplate();
		jdbc.setDataSource(datasource());
		
		return jdbc;
	}
	
	@Bean
	public BasicDataSource datasource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pwd);

		return ds;
	}
	
	@Bean
	public MongoDbFactory getMongoDbFactory() throws Exception{
		return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "local");
	}
	
	@Bean
	public MongoTemplate getMongoTemplate() throws Exception{
		MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory());
		return mongoTemplate;
	}
}
