package com.gcit.lms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Publisher;

@RestController
public class WebServiceController {

	@Autowired
	AuthorDAO adao;
	@Autowired
	PublisherDAO pdao;
	
	
	@RequestMapping(value = "/insertAuthor", method = {RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public String insertAuthor(@RequestBody Author author){
		try{
			adao.createAuthor(author);
			return "Author added sucessfully";
		}catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "Author add failed";
		}
	}
	
	@RequestMapping(value = "/updateAuthor", method = {RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public String updateAuthor(@RequestBody Author author){
		try{
			adao.updateAuthor(author);
			return "Author updated sucessfully";
		}catch (Exception e){
			e.printStackTrace();
			return "Author update failed";
		}
	}
	
	@RequestMapping(value = "/deleteAuthor", method = {RequestMethod.GET, RequestMethod.POST}, consumes="application/json")
	public String deleteAuthor(@RequestBody Author author){
		try{
			adao.deleteAuthor(author.getAuthorId());
			return "Author deleted sucessfully";
		}catch (Exception e){
			e.printStackTrace();
			return "Author delete failed";
		}
	}
	
	@RequestMapping(value = "/getAuthors", method = {RequestMethod.GET,RequestMethod.POST},produces="application/json")
	public List<Author> getAuthors(){
		
		try {
			return adao.getAllAuthors("",1, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/searchAuthors", method = {RequestMethod.GET,RequestMethod.POST},produces="application/json")
	public List<Author> searchAuthors(@RequestBody String searchString){
		
		try {
			return adao.getAllAuthors(searchString,1, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/deletePublisher", method= {RequestMethod.GET,RequestMethod.POST}, consumes="application/json")
	public String deletePublisher(@RequestBody Publisher pub){
		
		try{
			pdao.deletePublisher(pub.getPublisherId());
			return "publisher added sucessfully";
		}catch (Exception e){
			e.printStackTrace();
			return "publisher add failed";
		}
	}
	
	@RequestMapping(value="/addPublisher", method= {RequestMethod.GET,RequestMethod.POST}, consumes="application/json")
	public String addPublisher(@RequestBody Publisher pub){
		
		try{
			pdao.createPublisher(pub);
			return "publisher added sucessfully";
		}catch (Exception e){
			e.printStackTrace();
			return "publisher add failed";
		}
	}
	
	@RequestMapping(value="/updatePublisher", method= {RequestMethod.GET,RequestMethod.POST}, consumes="application/json")
	public String updatePublisher(@RequestBody Publisher pub){
		
		try{
			pdao.updatePublisher(pub);
			return "publisher updated sucessfully";
		}catch (Exception e){
			e.printStackTrace();
			return "publisher update failed";
		}
	}
	
	@RequestMapping(value = "/getPublishers", method = {RequestMethod.GET,RequestMethod.POST},produces="application/json")
	public List<Publisher> getPublishers(){
		
		try {
			return pdao.getAllPublishers("",1, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/searchPublishers", method = {RequestMethod.GET,RequestMethod.POST},produces="application/json")
	public List<Publisher> searchPublishers(@RequestBody String searchString){
		
		try {
			return pdao.getAllPublishers(searchString,1, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
