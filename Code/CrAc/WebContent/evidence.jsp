<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8" import="inloc.*,java.util.*,model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Evidence</title>
<head>
<!-- calendar plugin -->
<link href="fullcalendar/fullcalendar.min.css" rel="stylesheet">
<link href="fullcalendar/fullcalendar.print.css" rel="stylesheet"
	media="print">
<script src="fullcalendar/moment.min.js"></script>
<script src="fullcalendar/jquery.min.js"></script>
<script src="fullcalendar/fullcalendar.min.js"></script>

<!-- design -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link
	href="bootstrap/bootstrap-datepicker/css/bootstrap-datepicker.standalone.min.css"
	rel="stylesheet">
<script src="bootstrap/js/bootstrap.min.js"></script>

<!-- tree -->
<link href="tree.css" rel="stylesheet">

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
					<li class="active"><a href="useroverview">Users</a></li>
					<li><a href="taskoverview">Tasks</a></li>
					<li><a href="compare">Compare</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		</nav>
		
		<form class="form-horizontal" id="evidence" action="evidence"
			method="post">
			<input type="hidden" name="user" value="<%= request.getAttribute("userid")%>"/>
			<div class="jumbotron">
				<% if (request.getAttribute("message") != null) { %>
				<div class="alert alert-success" role="alert">
		        	<strong>Success!</strong> ${message}
		     	</div>
		     	<% }if(request.getAttribute("user") != null){ 
		     		User user = (User) request.getAttribute("user");
		     	%>
		     	<p>
					<a class="btn btn-xs btn-primary" role="button" href='profile?userid=<%=user.getID()%>'> Back to user profile </a>
				</p>
		     	<%} %>
				<h4>Evidence</h4>
				<div class="form-group">
					<label for="attributes" class="col-sm-2 control-label">For
						which attributes do you want to record an evidence:</label>
					<div class="col-sm-10">
						<select multiple class="form-control" name="attributes">
							<%
								if (request.getAttribute("user") != null) {
									List<Attribute> attributes = ((User) request
											.getAttribute("user")).getAttributes();
									for (Attribute a : attributes) {
										String name = a.getName();
										if(a instanceof WorkAndEducationAttribute)
											name += ": " + ((WorkAndEducationAttribute)a).getDescription();
										if(a instanceof CompetenceAttribute || a instanceof InterestAttribute || a instanceof NonInterestAttribute)
											name += ": " + ((CompetenceAndInterestAttribute)a).getLOCdefinition().toString();
							%>
							<option value="<%=a.getID()%>"><%=name%></option>
							<%
								}
								}
							%>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="type" class="col-sm-2 control-label">Type</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="type" name="type"
							placeholder="Type" required>
					</div>
				</div>
				<div class="form-group">
					<label for="value" class="col-sm-2 control-label">Value</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="type" name="value"
							placeholder="Value" required>
					</div>
				</div>
				<div class="form-group">
					<label for="date" class="col-sm-2 control-label">Date</label>
					<div class="col-sm-10">
						<input type="text" class="form-control date" id="date" name="date"
							placeholder="Date">
					</div>
				</div>
				<div class="form-group">
					<label for="expiredate" class="col-sm-2 control-label">Expiredate</label>
					<div class="col-sm-10">
						<input type="text" class="form-control date" id="expiredate"
							name="expiredate" placeholder="Expiredate">
					</div>
				</div>
				<div class="form-group">
					<label for="expiredate" class="col-sm-2 control-label">Issuer</label>
					<div class="col-sm-10">
						<div class="radio" >
							<label><input checked type="radio" name="issuertype" value="simple" id="simpleissuerradio">Simple
								Issuer</label> <input type="text" class="form-control" id="simpleissuer"
								name="issuer" placeholder="Issuer">
						</div>
						<div class="radio">
							<label><input type="radio" name="issuertype" value="user"id="userissuerradio">User</label> <select
								 class="form-control" name="issuer" id="userissuer" disabled>
								<%
									if (request.getAttribute("allUsers") != null) {
										List<User> users = (List<User>) request
												.getAttribute("allUsers");
										for (User u : users) {
								%>
								<option value="<%=u.getID()%>"><%=u.getName()%></option>
								<%
									}
									}
								%>
							</select>
						</div>
						<div class="radio">
							<label><input type="radio" name="issuertype" value="task" id="taskissuerradio">Task</label>
							<select  class="form-control" name="issuer" id="taskissuer" disabled>
								<%
									if (request.getAttribute("allTasks") != null) {
										List<Task> tasks = (List<Task>) request
												.getAttribute("allTasks");
										for (Task t : tasks) {
								%>
								<option value="<%=t.getID()%>"><%=t.getName()%></option>
								<%
									}
									}
								%>
							</select>
						</div>
					</div>
				</div>
			</div>
			<button type="submit" id="save" class="btn btn-default">Save evidence</button>
			
		<script src="bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
        <script type="text/javascript">
            // When the document is ready
            $(document).ready(function () {
                $("input[class$='date']").datepicker({
                    format: "dd.mm.yyyy"
                });

                $("#taskissuerradio").click(function (e) {
                    $("#userissuer").attr('disabled',true);
                    $("#simpleissuer").attr('disabled',true);
                    $("#taskissuer").attr('disabled',false);
                });
                $("#userissuerradio").click(function (e) {
                    $("#userissuer").attr('disabled',false);
                    $("#simpleissuer").attr('disabled',true);
                    $("#taskissuer").attr('disabled',true);
                });
                $("#simpleissuerradio").click(function (e) {
                    $("#userissuer").attr('disabled',true);
                    $("#simpleissuer").attr('disabled',false);
                    $("#taskissuer").attr('disabled',true);
                });
            });
        </script>
		</form>
	</div>
</body>
</html>