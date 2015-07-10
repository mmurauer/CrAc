<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>User & Task Profiles in a Volunteering Context</title>

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">

		<!-- Static navbar -->
		<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">User & Task Profiles in a
					Volunteering Context</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="index.jsp">Index</a></li>
					<li><a href="createUser">New User</a></li>
					<li><a href="createTask">New Task</a></li>
					<li><a href="useroverview">Users</a></li>
					<li><a href="taskoverview">Tasks</a></li>
					<li><a href="compare">Compare</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		</nav>

		<!-- Main component for a primary marketing message or call to action -->
		<% if (request.getAttribute("message") != null) { %>
		<div class="alert alert-success" role="alert">
        	<strong>Success!</strong> ${message}
     	</div>
     	<% } %>
     	<% if (request.getAttribute("error") != null) { %>
		<div class="alert alert-danger" role="alert">
        	<strong>Warning!</strong> ${error}
     	</div>
     	<% } %>
		<div class="jumbotron">
			<h1>Welcome!</h1>
			<p>This is the index of the prototype for our practical project. Here you can:</p>
			<p>
				<a class="btn btn-lg btn-primary" href="createUser" role="button">Create
					a new <b>user</b> profile
				</a>
			</p>
			<p>
				<a class="btn btn-lg btn-primary" href="createTask" role="button">Create
					a new <b>task</b> profile
				</a>
			</p>
			<p>
				<a class="btn btn-lg btn-primary" href="useroverview" role="button">View all available <b>users</b>
				</a>
			</p>
			<p>
				<a class="btn btn-lg btn-primary" href="taskoverview" role="button">View all available <b>tasks</b></a>
			</p>
			<p>
				<a class="btn btn-lg btn-primary" href="compare" role="button">Compare
					tasks and/or users</a>
			</p>
		</div>

	</div>
	<!-- /container -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>