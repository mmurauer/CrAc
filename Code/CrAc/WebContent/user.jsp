<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8" import="inloc.*,java.util.*,model.*,java.text.SimpleDateFormat"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>User Profile</title>
<head>
	<!-- calendar plugin -->
	<link href="fullcalendar/fullcalendar.min.css" rel="stylesheet">
	<link href="fullcalendar/fullcalendar.print.css" rel="stylesheet" media="print">
	<script src="fullcalendar/moment.min.js"></script>
	<script src="fullcalendar/jquery.min.js"></script>
	<script src="fullcalendar/fullcalendar.min.js"></script>
	
	<!-- design -->
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/bootstrap-datepicker/css/bootstrap-datepicker.standalone.min.css" rel="stylesheet">
	<script src="bootstrap/js/bootstrap.min.js"></script>
	
	<!-- tree -->
	<link href="tree.css" rel="stylesheet">
	
	<style>
		body {
			padding: 0;
			font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
			font-size: 14px;
		}
	
		#calendar {
			background-color: white;
			padding: 15px;
			margin: 0 auto;
		}
	</style>
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
						<li class="active"><a href="createUser">New User</a></li>
						<li><a href="createTask">New Task</a></li>
						<li><a href="useroverview">Users</a></li>
						<li><a href="compare">Compare</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>

		<%
		// user attribute is set if we are editing a user, not for creating a new one
		User u = null;
		if (request.getAttribute("user") != null) {
			u = (User) request.getAttribute("user");
		}
		%>
		<form class="form-horizontal" id="createuser" action="createUser" method="post">
			<div class="jumbotron">
			<h4>User data</h4>
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">Firstname</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="firstname" name="firstname"
						placeholder="Firstname" value="<% if(u != null)  out.print(u.getFirstname()); %>" required>
				</div>
			</div>
			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">Lastname</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="lastname" name="lastname"
						placeholder="Lastname" value="<% if(u != null)  out.print(u.getLastname()); %>" required>
				</div>
			</div>
			<div class="form-group">
				<label for="birthday" class="col-sm-2 control-label">Birthday</label>
				<div class="col-sm-10">
					<input type="text" class="form-control date" id="birthday" name="birthday"
						placeholder="Birthday" value="<% if(u != null)  out.print(u.getBirthday()); %>" required>
				</div>
			</div>
			<div class="form-group">
				<label for="street" class="col-sm-2 control-label">Street</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="street" name="street"
						placeholder="Street" value="<% if(u != null && u.getLocation() != null && u.getLocation().getAddress().getStreet() != null)  out.print(u.getLocation().getAddress().getStreet()); %>">
				</div>
			</div>		
			<div class="form-group">
				<label for="number" class="col-sm-2 control-label">Number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="streetnumber" name="streetnumber"
						placeholder="Number" value="<% if(u != null && u.getLocation() != null && u.getLocation().getAddress().getStreetnumber() != null)  out.print(u.getLocation().getAddress().getStreetnumber()); %>">
				</div>
			</div>
			<div class="form-group">
				<label for="postalcode" class="col-sm-2 control-label">Postalcode</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="postalcode" name="postalcode"
						placeholder="Postalcode" value="<% if(u != null && u.getLocation() != null && u.getLocation().getAddress().getPostalcode() != null)  out.print(u.getLocation().getAddress().getPostalcode()); %>">
				</div>
			</div>
			<div class="form-group">
				<label for="city" class="col-sm-2 control-label">City</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="city" name="city"
						placeholder="City" required value="<% if(u != null && u.getLocation() != null && u.getLocation().getAddress().getCity() != null)  out.print(u.getLocation().getAddress().getCity()); %>">
				</div>
			</div>
			<div class="form-group">
				<label for="gender" class="col-sm-2 control-label">Gender</label>
				<div class="col-sm-10">
					<div class="radio">
				      <label><input type="radio" name="gender" value="male" <% if(u != null && u.getAttribute("gender") != null && u.getAttribute("gender").equals("male"))  out.print(" checked "); %>>Male</label>
				    </div>
				    <div class="radio">
				      <label><input type="radio" name="gender" value="female">Female</label>
				    </div>
				</div>
			</div>
			</div>
			<div class="jumbotron">
			<h4>User competencies</h4>
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
			<h4>User interests</h4>
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
			<div class="jumbotron">
			<h4>User non-interests</h4>
			<div class="form-group">
				<label for="gender" class="col-sm-2 control-label"></label>
				<div class="col-sm-10">
			    	<div class="tree well">
			    		<%
							if (request.getAttribute("noninterests") != null) {
								out.println(request.getAttribute("noninterests"));	
							}
						%>
					</div>
				</div>
			</div>
			</div>
			<div class="jumbotron">
			<h4>User relationships</h4>
			<div class="form-group">
				<label for="gender" class="col-sm-2 control-label">User has worked with</label>
				<div class="col-sm-10">
			    	<select multiple class="form-control" name="hasworkedwith">
					  <%
							if (request.getAttribute("allUsers") != null) {
								List<User> users = (List<User>) request
										.getAttribute("allUsers");
								for (User user : users) {
									boolean selected = false;
									if(u != null && u.getHasWorkedWith().contains(user))
										selected = true;
								%>
									<option <% if(selected) { out.print("selected"); } %> value="<%= user.getID() %>"><%=user.getName()%></option>
								<%
								}	
							}
						%>
					</select>
				</div>
				<label for="gender" class="col-sm-2 control-label">User likes to work with</label>
				<div class="col-sm-10">
			    	<select multiple class="form-control" name="likestoworkwith">
					  <%
							if (request.getAttribute("allUsers") != null) {
								List<User> users = (List<User>) request
										.getAttribute("allUsers");
								for (User user : users) {
									boolean selected = false;
									if(u != null && u.getLikesToWorkWith().contains(user))
										selected = true;
								%>
									<option <% if(selected) { out.print("selected"); } %> value="<%= user.getID() %>"><%=user.getName()%></option>
								<%
								}	
							}
						%>
					</select>
				</div>
				<label for="gender" class="col-sm-2 control-label">User does not like to work with</label>
				<div class="col-sm-10">
			    	<select multiple class="form-control" name="likesnottoworkwith">
			    		
			    		<%
							if (request.getAttribute("allUsers") != null) {
								List<User> users = (List<User>) request
										.getAttribute("allUsers");
								for (User user : users) {
									boolean selected = false;
									if(u != null && u.getLikesNotToWorkWith().contains(user))
										selected = true;
								%>
									<option <% if(selected) { out.print("selected"); } %> value="<%= user.getID() %>"><%=user.getName()%></option>
								<%
								}	
							}
						%>
					</select>
				</div>
			</div>
			</div>
			<%
			int workAndEducation = 1;
			
			if(u != null) {
				for(WorkAndEducationAttribute we : u.getWorkAndEducationAttributes()) {
					%>
					<div class="jumbotron" id="workandeducation-<%= workAndEducation %>">
					<h4>Work & Education</h4>
					
					<div class="form-group">
						<label for="begin" class="col-sm-2 control-label">Begin</label>
						<div class="col-sm-10">
							<input type="text" class="form-control date" id="begin-<%= workAndEducation %>" name="begin-<%= workAndEducation %>"
								placeholder="Begin" value="<% if(we.getBegin() != null)  out.print(new SimpleDateFormat("dd.MM.yyyy").format(we.getBegin().getTime())); %>">
						</div>
					</div>
					<div class="form-group">
						<label for="end" class="col-sm-2 control-label">End</label>
						<div class="col-sm-10">
							<input type="text" class="form-control date" id="end-<%= workAndEducation %>" name="end-<%= workAndEducation %>"
								placeholder="End" value="<% if(we.getEnd() != null)  out.print(new SimpleDateFormat("dd.MM.yyyy").format(we.getEnd().getTime())); %>">
						</div>
					</div>
					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">Description</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="description-<%= workAndEducation %>" name="description-<%= workAndEducation %>"
								placeholder="Description" value="<% if(we.getDescription() != null) out.print(we.getDescription()); %>">
						</div>
					</div>	
					<div class="form-group">
						<label for="organization" class="col-sm-2 control-label">Organization</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="organization-<%= workAndEducation %>" name="organization-<%= workAndEducation %>"
								placeholder="Organization" value="<% if(we.getOrganization() != null) out.print(we.getOrganization().getTitle()); %>">
						</div>
					</div>	
					</div>
					<%
					workAndEducation++;
				}
			}
			%>
			<div class="jumbotron" id="workandeducation-<%= workAndEducation %>">
			<h4>Work & Education</h4>
			
			<div class="form-group">
				<label for="begin" class="col-sm-2 control-label">Begin</label>
				<div class="col-sm-10">
					<input type="text" class="form-control date" id="begin-<%= workAndEducation %>" name="begin-<%= workAndEducation %>"
						placeholder="Begin">
				</div>
			</div>
			<div class="form-group">
				<label for="end" class="col-sm-2 control-label">End</label>
				<div class="col-sm-10">
					<input type="text" class="form-control date" id="end-<%= workAndEducation %>" name="end-<%= workAndEducation %>"
						placeholder="End">
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-2 control-label">Description</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="description-<%= workAndEducation %>" name="description-<%= workAndEducation %>"
						placeholder="Description">
				</div>
			</div>	
			<div class="form-group">
				<label for="organization" class="col-sm-2 control-label">Organization</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="organization-<%= workAndEducation %>" name="organization-<%= workAndEducation %>"
						placeholder="Organization">
				</div>
			</div>	
			</div>
			
			<button class="btn btn-default" type="submit" id="addWE">+ Add Work & Education</button>
			
			<p/>
			
			<div class="jumbotron" id="availability">
				<h4>User availability</h4>
			    	<div id='calendar'></div>
			</div>
			
			<button type="submit" id="save" class="btn btn-default">Submit form</button>
			
		<script src="bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {

            	$("#save").click(function (e) {
            		 e.preventDefault();
                    var eventsFromCalendar = $('#calendar').fullCalendar('clientEvents');
                    $.each( eventsFromCalendar, function(index, value) {
						  var start = value.start.format("DD.MM.YYYY HH:mm");
						  if(value.end != null)
							  var end = value.end.format("DD.MM.YYYY HH:mm");

						  var title = value.title;

						  var event = {start:start,end:end,title:title};
						  
						  var input = $("<input>")
		                	.attr("type", "hidden")
		                	.attr("name", "availability" + index).val(JSON.stringify(event));

			 				$('#createuser').append($(input));
						});

                    if ($('#lastname').val().length == 0 || $('#firstname').val().length == 0
                    		|| $('#birthday').val().length == 0 || $('#city').val().length == 0) {
                        alert("One ore more required fields are missing: Firstname, Lastname, Birthday, City.")
                    }
                    else    
 						$('#createuser').submit();
            	});
            	
                $("input[class$='date']").datepicker({
                    format: "dd.mm.yyyy"
                });  

                var next=<%= workAndEducation %>;
                
                $("#addWE").click(function(e){
                    e.preventDefault();
                    var addto = "#workandeducation-" + next;
                    var newInput = $("#workandeducation-"+next).clone();
					newInput.find('input:text').val('');

                    next = next + 1;
                    newInput.attr("id", "workandeducation-" + next);
					//rename the child inputs with the new counter
					$.each( newInput.find('input:text'), function() {
						  var oldId = $(this).attr("id").split("-");
						  $(this).attr("id",oldId[0] + "-" + next)
						  $(this).attr("name",oldId[0] + "-" + next)
						});
                    newInput.insertAfter(addto);

                    //init the new calendar inputs
                    $("input[class$='date']").datepicker({
                        format: "dd.mm.yyyy"
                    }); 
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

                $('#calendar').fullCalendar({
        			header: {
        				left: 'prev,next today',
        				center: 'title',
        				right: 'month,agendaWeek,agendaDay'
        			},
        			selectable: true,
        			selectHelper: true,
        			select: function(start, end) {
        				var title = prompt('Event Title:');
        				var eventData;
        				if (title) {
        					eventData = {
        						title: title,
        						start: start,
        						end: end
        					};
        					console.log(eventData);
        					$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
        				}
        				$('#calendar').fullCalendar('unselect');
        			},
        			editable: true,
        			eventLimit: true
        		});

                <%
                	if(u != null) {
                		for(CompetenceAndInterestAttribute c : u.getCompetencies()) {
                			out.println("$('input[name=\"competence_" + c.getLOCdefinition() + "\"]').prop('checked', true);");
                			out.println("$('input[name=\"competence_" + c.getLOCdefinition() + "_date\"]').val('" + c.getFormatedDate() + "');");
                			out.println("$('input[name=\"competence_" + c.getLOCdefinition() + "_expireDate\"]').val('" + c.getFormatedExpireDate() + "');");
                		}
                		
                		for(CompetenceAndInterestAttribute i : u.getInterests()) {
                			out.println("$('input[name=\"interests_" + i.getLOCdefinition() + "\"]').prop('checked', true);");
                		}
                		
                		for(CompetenceAndInterestAttribute i : u.getNonInterests()) {
                			out.println("$('input[name=\"noninterests_" + i.getLOCdefinition() + "\"]').prop('checked', true);");
                		}
                		
                		for(AvailabilityAttribute a : u.getAvailabilities()) {
                			out.println("$('#calendar').fullCalendar('renderEvent', {title: \"available\",start: \"" + a.getFormatedDate() + "\", end: \""+ a.getFormatedEndDate() + "\"}, true);");
                		}
                	}
                %>
            });
        </script>
        
        <input type="hidden" name="userid" value="<%= request.getParameter("userid") %>" />
		</form>
	</div>
</body>
</html>