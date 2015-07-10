<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="inloc.*,java.util.*,model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="US-ASCII">
<title>Compare Profiles</title>
<head>
	<script src="jquery.min.js"></script>

	<!-- chart -->
	<script src="d3.v3.min.js"></script>
	<script src="RadarChart.js"></script>
	
	<!-- design -->
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="bootstrap/css/bootstrap-multiselect.css" type="text/css" />
	<script type="text/javascript" src="bootstrap/js/bootstrap-multiselect.js"></script>
	
	<style>
		html, body, #map-canvas {
			height: 100%;
			margin: 0px;
			padding: 0px
		}
	</style>
	
	<!-- google maps javascript API and map script -->
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
	
	<script>
		var geocoder;
		var map;
		var markers = [];
		var markerCount = 0;
		var markerIcons = ["images/blue-dot.png", "images/orange-dot.png", "images/green-dot.png", "images/red-dot.png", "images/purple-dot.png",
			"images/brown-dot.png", "images/pink-dot.png", "images/grey-dot.png", "images/yellow-dot.png", "images/cyan-dot.png"
		];
	
		function initialize() {
			geocoder = new google.maps.Geocoder();
			var latlng = new google.maps.LatLng(0, 0);
			var mapOptions = {
				zoom: 13,
				center: latlng
			}
			map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
			initMap();
		}
	
		function codeAddress(address, name) {
			geocoder.geocode({
				'address': address
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					if (markerCount == 0)
						map.setCenter(results[0].geometry.location);
					
					var marker = new google.maps.Marker({
						map: map,
						position: results[0].geometry.location,
						icon: markerIcons[markerCount]
					});
					markers.push(marker);
					markerCount++;
					var infowindow = new google.maps.InfoWindow({
						content: "<b>" + name + "</b> " + address
					});
	
					google.maps.event.addListener(marker, 'click', function() {
						infowindow.open(map, marker);
					});
				} else {
					alert('Geocode was not successful for the following reason: ' + status);
				}
			});
		}
		google.maps.event.addDomListener(window, 'load', initialize);
	
		$(document).ready(function() {
			$('#users').multiselect({
				enableClickableOptGroups : true,
				includeSelectAllOption : true,
				enableFiltering : true
			});
			$('#tasks').multiselect({
				enableClickableOptGroups : true,
				includeSelectAllOption : true,
				enableFiltering : true
			});
		});
	</script>
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
						<li><a href="taskoverview">Tasks</a></li>
						<li class="active"><a href="compare">Compare</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>
		<form method="get">
			<select id="users" name="user[]" multiple="multiple">
				<optgroup label="Users">
					<%
						if (request.getAttribute("allUsers") != null) {
								List<User> users = (List<User>) request
										.getAttribute("allUsers");
								for (User user : users) {
					%>
					<option value="<%=user.getID()%>"><%=user.getName()%></option>
					<%
						}	
							}
					%>
				</optgroup>
			</select> <select id="tasks" name="task">
				<optgroup label="Tasks">
					<option value="-1">None</option>
					<%
						if (request.getAttribute("allTasks") != null) {
								List<Task> tasks = (List<Task>) request
										.getAttribute("allTasks");
								for (Task task : tasks) {
					%>
					<option value="<%=task.getID()%>"><%=task.getName()%></option>
					<%
						}	
							}
					%>
				</optgroup>
			</select>
			<button type="submit" class="btn btn-default">Compare</button>
		</form>
		<div id="body">
			<div id="chart"></div>
		</div>
		<%
			Map<User, Map<LOCdefinition, Integer>> compareUsers = null;
			if (request.getAttribute("compareUsers") != null) {
				compareUsers = new HashMap<User, Map<LOCdefinition, Integer>>();
				for (User u : (ArrayList<User>) request
						.getAttribute("compareUsers")) {
					compareUsers.put(u, new HashMap<LOCdefinition, Integer>());
				}
			}

			Visualizable v = null;
			// top level definitions correspond to the axis in the radar graph
			Map<LOCdefinition, Integer> topLevelDefinitions = new HashMap<LOCdefinition, Integer>();

			if (request.getAttribute("compareTask") != null) {
				//if a task is set to compare, use the task as master object
				v = (Task) request.getAttribute("compareTask");
			} else if (request.getAttribute("compareUsers") != null
					&& compareUsers.size() >= 2) {
				// if no task is set, use the first user as master object
				v = ((ArrayList<User>) request.getAttribute("compareUsers"))
						.get(0);
				compareUsers.remove(v);
			}
			if (v != null) {
				for (CompetenceAndInterestAttribute competence : v
						.getCompetencies()) {

					LOCdefinition locDefinition = LOCUtil
							.getLOCdefinitionByCompetenceId(competence
									.getLOCdefinition());

					for (LOCdefinition locDef : locDefinition
							.getTopLevelDefinitions()) {

						if (!topLevelDefinitions.containsKey(locDef))
							topLevelDefinitions.put(locDef, 1);
						else {
							Integer value = topLevelDefinitions.get(locDef);
							value++;
							topLevelDefinitions.put(locDef, value);
						}
						if (compareUsers != null) {
							for (Map.Entry<User, Map<LOCdefinition, Integer>> e : compareUsers
									.entrySet()) {
								if (!e.getValue().containsKey(locDef)) {
									e.getValue().put(locDef, 0);
								}

								if (e.getKey().hasCompetence(locDefinition,
										Calendar.getInstance())) {
									Integer value = e.getValue().get(locDef);
									value += 1;
									e.getValue().put(locDef, value);
								}
							}
						}
					}
				}
		%>
		<script type="text/javascript">
		var w = 500,
			h = 500;

		var colorscale = d3.scale.category10();
	
		//Legend titles
		var LegendOptions = [
		    <%if(request.getAttribute("compareTask") != null) {%> 
			'Task: <%=((Task)request.getAttribute("compareTask")).getName()%>',
			<%} else {%> 
			'User: <%=((User)v).getName()%>',
			<%}
		    	
			if(compareUsers != null) {
				for(User user : compareUsers.keySet()) {%>
					'User: <%=user.getName()%>',
				<%}
			}%>
		];
	
		//Data
		var d = [
				  [
					<%for(Map.Entry<LOCdefinition,Integer> e : topLevelDefinitions.entrySet()) {
						out.println("{axis:\"" + e.getKey() + "\",value:" + e.getValue() + ",description:\"test\"},");
					}%>
				  ],
				  <%if(compareUsers != null) {
						for(Map.Entry<User,Map<LOCdefinition,Integer>>  e : compareUsers.entrySet()) {
							out.println("[");
							for(Map.Entry<LOCdefinition,Integer> e2 : e.getValue().entrySet())
								out.println("{axis:\"" + e2.getKey() + "\",value:" + e2.getValue() + "},");
							out.println("],");
						}
				  }%>
				  ];
					//Options for the Radar chart, other than default
					var mycfg = {
					  w: w,
					  h: h,
					  maxValue:
						  <%=Collections.max(topLevelDefinitions.values())%>,
					  levels: 5,
					  ExtraWidthX: 300,
					  factor: 1
					}
					//Call function to draw the Radar chart
					//Will expect that data is in %'s
					RadarChart.draw("#chart", d, mycfg);
				
					////////////////////////////////////////////
					/////////// Initiate legend ////////////////
					////////////////////////////////////////////
				
					var svg = d3.select('#body')
						.selectAll('svg')
						.append('svg')
						.attr("width", w+300)
						.attr("height", h);
				
					//Initiate Legend	
					var legend = svg.append("g")
						.attr("class", "legend")
						.attr("height", 100)
						.attr("width", 200)
						.attr('transform', 'translate(90,20)') 
						;
						//Create colour squares
						legend.selectAll('rect')
						  .data(LegendOptions)
						  .enter()
						  .append("rect")
						  .attr("x", w - 65)
						  .attr("y", function(d, i){ return i * 20;})
						  .attr("width", 10)
						  .attr("height", 10)
						  .style("fill", function(d, i){ return colorscale(i);})
						  ;
						//Create text next to squares
						legend.selectAll('text')
						  .data(LegendOptions)
						  .enter()
						  .append("text")
						  .attr("x", w - 52)
						  .attr("y", function(d, i){ return i * 20 + 9;})
						  .attr("font-size", "11px")
						  .attr("fill", "#737373")
						  .text(function(d) { return d; })
						  ;
		</script>
		<%
			}
		%>
	</div>
	<%
		if(v != null) {
	%>
	<div
		style="height: 500px; width: 70%; margin-left: auto; margin-right: auto;">
		<div id="map-canvas"></div>
		<script>
		function initMap() {
		<%//print the users and task to the map
			out.println("codeAddress(\"" + v.getLocation() + "\",\"" + v.getName() + "\");");
			if(request.getAttribute("compareUsers") != null) {
				for(Map.Entry<User,Map<LOCdefinition,Integer>>  e : compareUsers.entrySet()) {
					out.println("codeAddress(\"" + e.getKey().getLocation() + "\",\"" + e.getKey().getName() + "\");");
				}
			}
		%>
		}
		</script>
	</div>
	<%
		}
	%>
</body>
</html>