package com.gcit.lms;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {
	
	@Autowired
	AuthorDAO authorDAO;
	
	@Autowired
	BookDAO bookDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/author/get", method={RequestMethod.GET, RequestMethod.POST} , produces="application/json")
	public List<Author> getAuthors() throws ClassNotFoundException{
		try {
			return authorDAO.getAllAuthors("", 1, 20);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Transactional
	@RequestMapping(value="/author/add", method={RequestMethod.GET, RequestMethod.POST} , consumes="application/json")
	public String addAuthor(@RequestBody Author author){
		try {
			authorDAO.createAuthor(author);
			return "Author added sucessfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Author add failed";
		}
		
	}
	
	
	
	
}
