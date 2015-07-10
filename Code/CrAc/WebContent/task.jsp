<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="inloc.*,java.util.*,model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="US-ASCII">
<title>Task Profile</title>
<head>
	<!-- design -->
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<link href="bootstrap/bootstrap-datepicker/css/bootstrap-datepicker.standalone.min.css" rel="stylesheet">
	
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
						<li class="active"><a href="createTask">New Task</a></li>
						<li><a href="useroverview">Users</a></li>
						<li><a href="taskoverview">Tasks</a></li>
						<li><a href="compare">Compare</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>

    <form class="form-horizontal" action="createTask" method="post">
			<div class="jumbotron">
			<h4>Task data</h4>
			<div class="form-group">
				<label for=""name"" class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id=""name"" name="name"
						placeholder="Name">
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-2 control-label">Description</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="description" name="description"
						placeholder="Description">
				</div>
			</div>
			<div class="form-group">
				<label for="street" class="col-sm-2 control-label">Street</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="street" name="street"
						placeholder="Street">
				</div>
			</div>		
			<div class="form-group">
				<label for="number" class="col-sm-2 control-label">Number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="streetnumber" name="streetnumber"
						placeholder="Number">
				</div>
			</div>
			<div class="form-group">
				<label for="postalcode" class="col-sm-2 control-label">Postalcode</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="postalcode" name="postalcode"
						placeholder="Postalcode">
				</div>
			</div>
			<div class="form-group">
				<label for="city" class="col-sm-2 control-label">City</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="city" name="city"
						placeholder="City">
				</div>
			</div>

			</div>
			<div class="jumbotron">
			<h4>Required competencies</h4>
			<div class="form-group">
				<label for="gender" class="col-sm-2 control-label"></label>
				<div class="col-sm-10">
			    	<div class="tree well">
			    		<%
							if (request.getAttribute("competencies") != null) {
								out.println(request.getAttribute("competencies"));	
							}
						%>
					</div>
				</div>
			</div>
			</div>
			<div class="jumbotron">
			<h4>Required interests</h4>
			<div class="form-group">
				<label for="gender" class="col-sm-2 control-label"></label>
				<div class="col-sm-10">
			    	<div class="tree well">
			    		<%
							if (request.getAttribute("interests") != null) {
								out.println(request.getAttribute("interests"));	
							}
						%>
					</div>
				</div>
			</div>
			</div>
			
			<button type="submit" class="btn btn-default">Submit form</button>
			
		<script src="bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
        <script type="text/javascript">
            // When the document is ready
            $(document).ready(function () {
                
                $("input[class$='date']").datepicker({
                    format: "dd.mm.yyyy"
                });  

                $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
                $('.tree li.parent_li > span').on('click', function (e) {
                    var children = $(this).parent('li.parent_li').find(' > ul > li');
                    if (children.is(":visible")) {
                        children.hide('fast');
                        $(this).attr('title', 'Expand this branch').find(' > i').addClass('glyphicon-plus-sign').removeClass('glyphicon-minus-sign');
                    } else {
                        children.show('fast');
                        $(this).attr('title', 'Collapse this branch').find(' > i').addClass('glyphicon-minus-sign').removeClass('glyphicon-plus-sign');
                    }
                    e.stopPropagation();
                });
            });
        </script>
	</form>
</div>
</body>
</html>