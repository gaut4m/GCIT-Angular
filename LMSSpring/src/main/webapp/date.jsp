<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.gcit.lms.domain.Loans"%>
    <%@ page import="com.gcit.lms.domain.Borrower"%>
    <%@ page import="com.gcit.lms.service.AdministratorService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	AdministratorService admin = (AdministratorService)request.getAttribute("admin");
	List<Borrower> br = new ArrayList<Borrower>();
	br = (List<Borrower>)admin.getService("users");
	
	 for(Borrower b: br)
	 {
		 List<Loans> loans = new ArrayList<Loans>();
		 loans = admin.getLoans(b.getCardNo());
		 b.setLoans(loans);
	 }
	 
	 
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
                  <li ><a href="home" class="active">Home</a></li>
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
            <li><a href="borrower">Borrower</a></li>
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
          <h1 class="sub-header">view Borrowered Books</h1>
			 ${message}
          <div class="row placeholders">
             <div class="table-responsive">
            <table class="table table-striped" id="authorsTable">
	<tr>
	
	<th>Borrower Name</th>
	<th>Branch Name </th>
	<th>Book Title</th>
	<th>Check out Date</th>
	<th>Due Date </th>
	<th>Extend</th>
	</tr>
	<%for(Borrower b: br){ 
		for(Loans l: b.getLoans()){%>
		<tr>
			<form  action="extendDate" id="myform"  method="post">
			<td   ><input type="text" id="cardNo" name="cardNo" value="<%=b.getCardNo()%>" style="display:none">
			<input type="text" style="text-align:center;" id="name" name="name" value="<%=b.getName()%>" size="4" readonly></td>
			<td   >
			<input type="text" id="branchId" name="branchId" value="<%=l.getBranch().getBranchId()%>" style="display:none">
			<input type="text" style="text-align:center;" id="branchName" name="branchName" value="<%=l.getBranch().getBranchName()%>" size="7" readonly>
			</td>
			<td >
			<input type="text"  id="bookId" name="bookId" value="<%=l.getBook().getBookId()%>" style="display:none">
			<input type="text" style="text-align:center;" id="title" name="title" value="<%=l.getBook().getTitle()%>"  readonly></td>
			<td   ><input type="text" style="text-align:center;" id="dateOut" name="dateOut" value="<%=l.getDateOut()%>"  size="8" readonly></td>
			<td   ><input type="text" style="text-align:center;" id="dueDate" name="dueDate" value="<%=l.getDueDate()%>"  size="8" readonly></td>
			<td  >
			<input type="submit" value="Extend">
			</td>
			
			</form>
			</tr>
	  <%}
	} %>
	
</table>
</div>
           
          </div>
          </div>
        
      </div>
   

    </div><!-- /.container -->


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