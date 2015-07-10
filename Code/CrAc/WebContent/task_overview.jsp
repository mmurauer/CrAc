<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,model.Task"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<title>Tasks</title>
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
						<li><a href="index.jsp">Index</a></li>
						<li><a href="createUser">New User</a></li>
						<li><a href="createTask">New Task</a></li>
						<li><a href="useroverview">Users</a></li>
						<li class="active"><a href="taskoverview">Tasks</a></li>
						<li><a href="compare">Compare</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>

<div class="jumbotron">
	<h1>Task Overview</h1>
	<p>Select a task to show its full profile!</p>
	<p>		
	<%
		if (request.getAttribute("allTasks") != null) {
			List<Task> tasks = (List<Task>) request
					.getAttribute("allTasks");
			if (tasks.size() > 0) {
				%><p><%
				for (Task task : tasks) {
					%>
					<a class="btn btn-lg btn-primary" role="button" href='task_profile?taskid=<%=task.getID()%>'> <%=task.getName()%> </a>
					<%
				}	
				%></p><%
			}
		}
	%>
	</p>
</div>	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
</div>
</body>
</html>