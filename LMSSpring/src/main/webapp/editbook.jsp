<%@ page import="com.gcit.lms.domain.Book"%>
<%@ page import="com.gcit.lms.domain.Genre"%>
<%@ page import="com.gcit.lms.domain.Publisher"%>
<%@ page import="com.gcit.lms.domain.Author"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	
	
	Book book = new Book();
	
	book = (Book)request.getAttribute("book");
	List<Author> authors =new ArrayList<Author>();
	authors =(List<Author>)request.getAttribute("authors");
	List<Genre> genres =new ArrayList<Genre>();
	genres =(List<Genre>)request.getAttribute("genres");
	
%>
<!-- saved from url=(0050)http://getbootstrap.com/examples/carousel/#contact -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="./template_files/icon.ico">

    <title>GCIT LMS</title>

    <!-- Bootstrap core CSS -->
    <link href="./template_files/bootstrap.min.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="./template_files/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Custom styles for this template -->
    <link href="./template_files/carousel.css" rel="stylesheet">
  <style type="text/css"></style>
  <script type="text/javascript">
  function editTitle(bookId,title){
	  $.ajax({
		  url: "editBook",
		  data: { bookId: bookId,title:title}
		})
		  .done(function( data ) {
		    $('#authorsTable').html(data);
		  });
			  
	}
  </script>
  </head>
<!-- NAVBAR
================================================== -->
  
<div class="container">
<div class="container-fluid">
      <div class="row">
      <br>
      								<div name="editbook" id="editbook">
									Book Tile:<input type="text" id="bookId" name="bookId"
									value="<%=book.getBookId()%>" style="display:none" >
									<input type="text" id="title" name="title"
									value="<%=book.getTitle()%>" > </td>
									<input type="button" onclick="javascript:editTitle(<%=book.getBookId()%>,'<%=book.getTitle()%>');" class="btn btn-sm btn-info" value="UPDATE"/>
									</div>
									 
									 Book Authors:
									<table>
									
									
										<%
											for (Author a : book.getAuthors()) {
										%>
									<tr><td><%=a.getAuthorName()%></td>
									<td ><button type="button" class="btn btn-xs btn-danger" 
									 onclick="javascript:location.href='deleteBookAuthor?authorId=<%=a.getAuthorId()%>&bookId=<%=book.getBookId()%>'">
									 DELETE</button></td></tr>
										<%
											}
										%>
										
										<tr>
										</tr>
										<tr>
										
										<form action="addBookAuthor" method="post">
										<td>
<select id="authorId" name="authorId" multiple>
<%for(Author a: authors){ %>
	<option value="<%=a.getAuthorId()%>" ><%=a.getAuthorName() %></option>	
<%} %>
</select>
</td>
		 <td>
		<input type="submit" class="btn btn-xs btn-info" value="Add Author">
		</td>
	
	</form>
	</tr>						
</table>
<table>
<th>Book Genre</th>
<tr>

										<%
											if(book.getGenres() !=null){for (Genre g : book.getGenres()) {
										%>
									<tr><td><%=g.getGenreName()%></td>
									<td ><button type="button" class="btn btn-xs btn-danger" 
									 onclick="javascript:location.href='deleteBookGenre?genreId=<%=g.getGenreId()%>&bookId=<%=book.getBookId()%>'">
									 DELETE</button></td></tr>
										<%
											}}
										%>
										
										<tr>
										</tr>
										<tr>
										<form action="addBookGenre" method="post">
										<td>
								<select id="genreId" name="genreId" multiple>
<%for(Genre a: genres){ %>
	<option value="<%=a.getGenreId()%>" ><%=a.getGenreName() %></option>	
<%} %>
</select>
</td>
		 <td>
		<input type="submit" class="btn btn-xs btn-info" value="Add Genre">
		</td>
	
	</form>
	</tr>						
</table>

          
        
      </div>
    </div>

    </div><!-- /.container -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./template_files/jquery.min.js"></script>
    <script src="./template_files/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="./template_files/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="./template_files/ie10-viewport-bug-workaround.js"></script>
  