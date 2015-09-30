


<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.gcit.lms.domain.Branch" %>
<%@ page import="com.gcit.lms.service.AdministratorService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	
	
	List<Branch> branch = new ArrayList<Branch>();
	branch = (List<Branch>)request.getAttribute("branch");
%>
<!DOCTYPE html>
<!-- saved from url=(0050)http://getbootstrap.com/examples/carousel/#contact -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="./template_files/favicon.png">

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
  </head>
<!-- NAVBAR
================================================== -->
  <body>
  
    <div class="navbar-wrapper">
      <div class="container">

         <nav class="navbar navbar-inverse navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="home">GCIT LMS</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                  <li ><a href="home">Home</a></li>
           <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Admin <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="author">Author Management</a></li>
            <li><a href="book">Book Management</a></li>
            <li><a href="publisher">Publisher Management</a></li>
            <li><a href="users">Borrower Management</a></li>
            <li><a href="branchdetails">Branch Management</a></li>
            <li><a href="genre">Genre Management</a></li>
             <li><a href="date">Extend Date</a></li>
                  </ul>
                </li>
            <li><a href="borrower">Borrower</a></li>
            <li><a class="active" href="librarian">Librarian</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contact</a></li>
              </ul>
            </div>
          </div>
        </nav>

      </div>
    </div>


    <!-- Carousel
    ==================================================
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <!-- Indicators 
      <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class=""></li>
        <li data-target="#myCarousel" data-slide-to="1" class=""></li>
        <li data-target="#myCarousel" data-slide-to="2" class="active"></li>
      </ol>
      <div class="carousel-inner" role="listbox">
        <div class="item">
          <img class="first-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="First slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>Example headline.</h1>
              <p>Note: If you're viewing this page via a <code>file://</code> URL, the "next" and "previous" Glyphicon buttons on the left and right might not load/display properly due to web browser security rules.</p>
              <p><a class="btn btn-lg btn-primary" href="http://getbootstrap.com/examples/carousel/#" role="button">Sign up today</a></p>
            </div>
          </div>
        </div>
        <div class="item">
          <img class="second-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Second slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>Another example headline.</h1>
              <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <p><a class="btn btn-lg btn-primary" href="http://getbootstrap.com/examples/carousel/#" role="button">Learn more</a></p>
            </div>
          </div>
        </div>
        <div class="item active">
          <img class="third-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Third slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>One more for good measure.</h1>
              <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <p><a class="btn btn-lg btn-primary" href="http://getbootstrap.com/examples/carousel/#" role="button">Browse gallery</a></p>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="http://getbootstrap.com/examples/carousel/#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="http://getbootstrap.com/examples/carousel/#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div> /.carousel -->


<br>
<br>
<br>
<br>
<div class="container">
<div class="container-fluid">
      <div class="row">
      
        <div class="col-sm-3 col-md-2 sidebar">
        <br>
          <ul class="nav nav-sidebar">
        
          </ul>
        </div>
        <div class="col-sm-6  col-md-10  main">
          <h1 class="sub-header">Branch Management</h1>
			 ${message}
          <div class="row placeholders">
           
           <div class="table-responsive">
            <table class="table table-striped" id="publisherTable">

	<thead>
	<tr>
	<th>Branch Name</th>
	<th>Branch Address</th>
	<th>Edit Branch</th>
	</tr>
	</thead>
	<%for(Branch b: branch){ %>
		<tr>
			
			<td  ><input type="text"  id="cardNo" name="cardNo" value="<%=b.getBranchId()%>" style="display:none">
			<%=b.getBranchName()%></td>
			<td ><%=b.getBranchAddress() %></td>
			
			<td ><button type="button" class="btn btn-sm btn-info" onclick="javascript:location.href='editbranch?branchId=<%=b.getBranchId() %>'">EDIT</button></td>
			
			</tr>
	<%} %>
	
</table>
</div>
              
                    
          </div>
          </div>
        
      </div>
    </div>

    </div><!-- /.container -->
<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit Branch</h4>
      </div>
		</div>
	</div>
</div>
	 <!-- FOOTER -->
      <footer class="navbar-fixed-bottom container">
        <p class="pull-right"><a href="#">Back to top</a></p>
        <p>© 2014 Company, Inc. · <a href="http://getbootstrap.com/examples/carousel/#">Privacy</a> · <a href="http://getbootstrap.com/examples/carousel/#">Terms</a></p>
      </footer>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./template_files/jquery.min.js"></script>
    <script src="./template_files/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="./template_files/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="./template_files/ie10-viewport-bug-workaround.js"></script>
  

</body></html>