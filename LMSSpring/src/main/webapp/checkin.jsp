<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="com.gcit.lms.domain.Loans"%>
<%@ page import="com.gcit.lms.domain.Loans"%>
    <%@ page import="com.gcit.lms.domain.Branch"%>
    <%@ page import="com.gcit.lms.domain.Author"%>
    <%@ page import="com.gcit.lms.domain.BCopies"%>
     <%@ page import="com.gcit.lms.domain.Borrower"%>
<%@ page import="com.gcit.lms.service.LibrarianService"%>
<%@ page import="com.gcit.lms.service.AdministratorService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
 <%
    Borrower br =new Borrower();
	  // User IS logged in.
	  br.setCardNo((Integer)session.getAttribute("cardNo"));
	  br.setName((String)session.getAttribute("name"));
	
%>
<%

AdministratorService admin = (AdministratorService)request.getAttribute("admin");
 List<Loans> loans = new ArrayList<Loans>();
	 loans = admin.getLoans(br.getCardNo()); 
%>
<% 
List<Branch> branches = new ArrayList<Branch>();
branches = (List<Branch>)admin.getService("branch","",-1,-1);
%>
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
  <style type="text/css"></style></head>
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
                  <li ><a href="home" >Home</a></li>
           <li  class="dropdown">
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
            <li><a class="active" href="borrower">Borrower</a></li>
            <li><a href="librarian">Librarian</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contact</a></li>
              </ul>
            </div>
          </div>
        </nav>

      </div>
    </div>


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
            <li ><a href="borrower">Overview <span class="sr-only">(current)</span></a></li>
            <li ><a href="#" data-toggle="modal" data-target="#myModal">CheckOut Books</a></li>
            <li ><a href="checkin">CheckIn Books</a></li>
            <li><form action="logOut" method="post">
    <input type="submit" value="Logout" />
</form></li>
          </ul>
          
        </div>
        <div class="col-sm-6  col-md-10  main">
          <h1 class="sub-header"> View Due Dates</h1>
			 ${message}
          <div class="row placeholders">
       <div class="table-responsive">
            <table class="table table-striped">
     <thead>
<tr>
	
	<th>Branch Name </th>
	<th>Book Title</th>
	<th>Check out Date</th>
	<th>Due Date </th>
	<th>Extend</th>
	</tr>
	</thead>
	<%
		for(Loans l: loans){%>
		<tr>
			<form  action="returnBook" id="myform"  method="post">
			
			<td align="center" >
			<input type="text" id="cardNo" name="cardNo" value="<%=br.getCardNo()%>" style="display:none">
			<input type="text" id="branchId" name="branchId" value="<%=l.getBranch().getBranchId()%>" style="display:none">
			<input type="text" style="text-align:center;" id="branchName" name="branchName" value="<%=l.getBranch().getBranchName()%>" readonly>
			</td>
			<td align="center" >
			<input type="text"  id="bookId" name="bookId" value="<%=l.getBook().getBookId()%>" style="display:none">
			<input type="text" style="text-align:center;" id="title" name="title" value="<%=l.getBook().getTitle()%>" readonly></td>
			<td align="center" ><input type="text" style="text-align:center;" id="dateOut" name="dateOut" value="<%=l.getDateOut()%>"  readonly></td>
			<td align="center" ><input type="text" style="text-align:center;" id="dueDate" name="dueDate" value="<%=l.getDueDate()%>" readonly></td>
			<td align="center">
			<input type="submit" value="Return">
			</td>
			
			</form>
			</tr>
	  <%}%>
	
</table>
</div>
           
          </div>
          </div>
          </div>
        
      </div>
   

    </div><!-- /.container -->
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"> Update Author Name</h4>
      </div>
      <div class="modal-body">
      		<form action="selectBranch"  onsubmit="myFunction()" method="post">
		Select Branch:
				<select id="branchId" name="branchId" >
					<option value="select"> ----Select----</option>
			<%for(Branch a: branches){ %>
					<option value="<%=a.getBranchId()+"."+a.getBranchName() %>" ><%=a.getBranchName() %></option>	
				<%} %>
				</select>
			<input type="submit" value="Select" />
			</form>
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
