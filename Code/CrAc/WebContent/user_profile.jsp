<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="inloc.LOCdefinition, model.Attribute, model.User, model.WorkAndEducationAttribute, 
	java.util.Map, java.util.List, java.util.ArrayList, java.util.HashMap, java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Profile</title>
	
	<link rel="stylesheet" href="profile_libs/styles.css" type="text/css">
	
	<!-- Bubble and Tag-Cloud -->
	<script src="d3.v3.min.js"></script>
	<script src="profile_libs/d3.layout.cloud.js"></script>
	
	<!-- Timeliner -->
	<link rel="stylesheet" href="profile_libs/timeliner/css/timeliner.css" type="text/css" media="screen">
	<script src="jquery.min.js"></script>
	<script type="text/javascript" src="profile_libs/timeliner/js/timeliner.min.js"></script>
	
	<!-- design -->
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="bootstrap/js/bootstrap.min.js"></script>
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

		<% 
		//get request params
		User user = null;
		if(request.getAttribute("user")!= null){
				user = (User) request.getAttribute("user");
		}	
		
		Map<Integer, List<WorkAndEducationAttribute>> timeline = null;
		if(request.getAttribute("timeline")!= null){
				timeline = (Map<Integer,List<WorkAndEducationAttribute>>) request.getAttribute("timeline");
		}
		
		String interestsForTagCloud = "";
		if(request.getAttribute("interestsForTagCloud")!= null){
				interestsForTagCloud = request.getAttribute("interestsForTagCloud").toString();
		}
		
		String nonInterestsForTagCloud = "";
		if(request.getAttribute("nonInterestsForTagCloud")!= null){
				nonInterestsForTagCloud = request.getAttribute("nonInterestsForTagCloud").toString();
		}

		String competenciesForTagCloud = "";
		if(request.getAttribute("competenciesForTagCloud")!= null){
			competenciesForTagCloud = request.getAttribute("competenciesForTagCloud").toString();
		}
		
		String hasWorkedWithForNetwork = "";
		if(request.getAttribute("hasWorkedWithForNetwork") != null){
			hasWorkedWithForNetwork = request.getAttribute("hasWorkedWithForNetwork").toString();
		}

		String likesToWorkWithForNetwork = "";
		if(request.getAttribute("likesToWorkWithForNetwork") != null){
			likesToWorkWithForNetwork = request.getAttribute("likesToWorkWithForNetwork").toString();
		}
		
		String likesNotToWorkWithForNetwork = "";
		if(request.getAttribute("likesNotToWorkWithForNetwork") != null){
			likesNotToWorkWithForNetwork = request.getAttribute("likesNotToWorkWithForNetwork").toString();
		}
		
		String overviewForNetwork = "";
		if(request.getAttribute("overviewForNetwork") != null){
			overviewForNetwork = request.getAttribute("overviewForNetwork").toString();
		}
		
		boolean hasCompetenciesCurrent = false;
		if(request.getAttribute("hasCompetenciesCurrent") != null){
			hasCompetenciesCurrent = Boolean.parseBoolean(request.getAttribute("hasCompetenciesCurrent").toString());
		}
		
		boolean hasCompetenciesOnePast = false;
		if(request.getAttribute("hasCompetenciesOnePast") != null){
			hasCompetenciesOnePast = Boolean.parseBoolean(request.getAttribute("hasCompetenciesOnePast").toString());
		}
		
		boolean hasCompetenciesTwoPast = false;
		if(request.getAttribute("hasCompetenciesTwoPast") != null){
			hasCompetenciesTwoPast = Boolean.parseBoolean(request.getAttribute("hasCompetenciesTwoPast").toString());
		}
			
		String colors = "";
		if(request.getAttribute("colors") != null){
			colors = request.getAttribute("colors").toString();
		}
		
		int level = 1;
		if(request.getAttribute("level") != null){
			level = Integer.parseInt(request.getAttribute("level").toString());
		}
		
		int maxLevel = 1;
		if(request.getAttribute("maxLevel") != null){
			maxLevel = Integer.parseInt(request.getAttribute("maxLevel").toString());
		}
		
		int colorCNT = 0;
		if(request.getAttribute("colorCNT") != null){
			colorCNT = Integer.parseInt(request.getAttribute("colorCNT").toString());
		}
		%>
		
		<!-- content -->
		<div id="maincontent">
			<div id="personal_info" class="jumbotron">
				<h1>
					<%= user.getName() %>
				</h1>
				<ul>
					<li>Age: <%= user.getAge() %>
					</li>
					<li>Address: <%= user.getLocation().toString() %></li>
				</ul>
			</div>	
			
			<div id="graphs" class="jumbotron">
			
			<% if(timeline != null && timeline.size()>0){ %>
			<div id="timeline_profile">
				<div id="timeline_container">
					<h2>Education and Work History</h2>
					<p>This is the visualization of WorkAndEducation attributes. </p>
					<div id="timeline" class="timeline-container">
						<% 
							for(Map.Entry<Integer, List<WorkAndEducationAttribute>> entry : timeline.entrySet()){
						%>
							<div class="timeline-wrapper">
								<h2 class="timeline-time"><%= entry.getKey() %></h2>
								<dl class="timeline-series">
									<% for(WorkAndEducationAttribute we : entry.getValue()){%>
									<dt class="timeline-event" id="<%= we.getID() %>">
										<a><%= we.getDescription() %></a>
									</dt>
									<dd class="timeline-event-content" id="<%= we.getID()+"EX" %>">
										<p><%= we.toString() %></p>
									</dd>
									<%}%>
								</dl>
							</div>
						<%}%>
					</div>
				</div>
			</div>
			<% } %>
			
			<% if(!interestsForTagCloud.equals("")) { %>
			<div id="interests">
				<h2>Interests</h2>
				<a href="#" id="tag_cloud_interest_toggle" class="btn btn-lg btn-primary" role="button">Tag Cloud</a>
				<a href="#" id="bubble_cloud_interest_toggle" class="btn btn-lg btn-primary" role="button">Bubble Cloud</a>
				<a href="#" id="zoom_cloud_interest_toggle" class="btn btn-lg btn-primary" role="button">Zoom Cloud</a>
				
				<div id="tag_cloud_interest" style='display: none;'></div>
				<div id="bubble_cloud_interest" style="display: none;"></div>
				<div id="zoom_cloud_interest" style="display: none;"></div>
				
				<script type="text/javascript">
					$("#tag_cloud_interest_toggle").click(function(event) {
						event.preventDefault();
						$("#tag_cloud_interest").toggle();
					});
					$("#bubble_cloud_interest_toggle").click(function(event) {
						event.preventDefault();
						$("#bubble_cloud_interest").toggle();
					});
					$("#zoom_cloud_interest_toggle").click(function(event) {
						event.preventDefault();
						$("#zoom_cloud_interest").toggle();
					});
				</script>
			</div>
			<% } %>
			
			<% if(!nonInterestsForTagCloud.equals("")) { %>
			<div id="non-interests">
				<h2>Non-Interests</h2>
				<a href="#" id="tag_cloud_noninterest_toggle" class="btn btn-lg btn-primary" role="button">Tag Cloud</a>
				<a href="#" id="bubble_cloud_noninterest_toggle" class="btn btn-lg btn-primary" role="button">Bubble Cloud</a>
				<a href="#" id="zoom_cloud_noninterest_toggle" class="btn btn-lg btn-primary" role="button">Zoom Cloud</a>
				
				<div id="tag_cloud_noninterest" style='display: none;'></div>
				<div id="bubble_cloud_noninterest" style="display: none;"></div>
				<div id="zoom_cloud_noninterest" style="display: none;"></div>
				
				<script type="text/javascript">
					$("#tag_cloud_noninterest_toggle").click(function(event) {
						event.preventDefault();
						$("#tag_cloud_noninterest").toggle();
					});
					$("#bubble_cloud_noninterest_toggle").click(function(event) {
						event.preventDefault();
						$("#bubble_cloud_noninterest").toggle();
					});
					$("#zoom_cloud_noninterest_toggle").click(function(event) {
						event.preventDefault();
						$("#zoom_cloud_noninterest").toggle();
					});
				</script>
			</div>
			<% } %>
			
			<% if (!competenciesForTagCloud.equals("")) {%>
			<div id="competencies">
				<h2>Competencies</h2>
				<a href="#" id="tag_cloud_competence_toggle" class="btn btn-lg btn-primary" role="button">Tag Cloud</a>
				<a href="#" id="bubble_cloud_competence_toggle" class="btn btn-lg btn-primary" role="button">Bubble Cloud</a>
				<a href="#" id="zoom_cloud_competence_toggle" class="btn btn-lg btn-primary" role="button">Zoom Cloud</a>
				
				<div id="tag_cloud_competence" style="display: none;"></div>
				<div id="bubble_cloud_competence_overview" style="display: none;">
					<input type="range" min="2013" max="2015" value="2015" id="range"/>
					<span class="label label-default" id="competence_time">2015</span>
					<div class="alert alert-info compinfo" role="alert" id="no_competencies_current" style="display: none;">
						This person has currently no competencies!
					</div>
					<div class="alert alert-info compinfo" role="alert" id="no_competencies_one_past" style="display: none;">
						This person has no competencies gained in 2014!
					</div>
					<div class="alert alert-info compinfo" role="alert" id="no_competencies_two_past" style="display: none;">
						This person has no competencies gained in 2013!
					</div>
				
					<% if(hasCompetenciesCurrent){ %>
						<div id="bubble_cloud_competence"></div>
					<%} %>
					<% if(hasCompetenciesOnePast){ %>
						<div id="bubble_cloud_competence_past_one" style="display: none;"></div>
					<%} %>
					<% if(hasCompetenciesTwoPast){ %>
						<div id="bubble_cloud_competence_past_two" style="display: none;"></div>
					<%} %>
				</div>
				
				<div id="zoom_cloud_competence" style="display: none;"></div>
				
				<script type="text/javascript">
					$("#tag_cloud_competence_toggle").click(function(event) {
						event.preventDefault();
						$("#tag_cloud_competence").toggle();
					});
					$("#bubble_cloud_competence_toggle").click(function(event) {
						event.preventDefault();
						<% if(hasCompetenciesCurrent){ %>
							$("#bubble_cloud_competence_overview").toggle();
							$("#no_competencies_current").hide();
						<%}else {%>
							$("#no_competencies_current").show();
						<%}%>
					});
					$("#zoom_cloud_competence_toggle").click(function(event) {
						event.preventDefault();
						$("#zoom_cloud_competence").toggle();
					});
					$("#range").change(function(event){
						event.preventDefault();
						$( "#competence_time" ).text($(this).val());
						if($(this).val()=="2013"){
							<% if(hasCompetenciesTwoPast){ %>
								$("#bubble_cloud_competence").hide();
								$("#no_competencies_current").hide();
								
								$("#bubble_cloud_competence_past_one").hide();
								$("#no_competencies_one_past").hide();
								
								$("#bubble_cloud_competence_past_two").show();
								$("#no_competencies_two_past").hide();
							<%}else{%>
								$("#bubble_cloud_competence").hide();
								$("#no_competencies_current").hide();
								
								$("#bubble_cloud_competence_past_one").hide();
								$("#no_competencies_one_past").hide();
								
								$("#bubble_cloud_competence_past_two").hide();
								$("#no_competencies_two_past").show();
							<%}%>
						}else if($(this).val()=="2014"){
							<% if(hasCompetenciesOnePast){ %>
								$("#bubble_cloud_competence").hide();
								$("#no_competencies_current").hide();
								
								$("#bubble_cloud_competence_past_one").show();
								$("#no_competencies_one_past").hide();
								
								$("#bubble_cloud_competence_past_two").hide();
								$("#no_competencies_two_past").hide();
							<%}else{%>
								$("#bubble_cloud_competence").hide();
								$("#no_competencies_current").hide();
								
								$("#bubble_cloud_competence_past_one").hide();
								$("#no_competencies_one_past").show();
								
								$("#bubble_cloud_competence_past_two").hide();
								$("#no_competencies_two_past").hide();
							<%}%>
						}else if($(this).val()=="2015"){
							<% if(hasCompetenciesCurrent){ %>
								$("#bubble_cloud_competence").show();
								$("#no_competencies_current").hide();
								
								$("#bubble_cloud_competence_past_one").hide();
								$("#no_competencies_one_past").hide();
								
								$("#bubble_cloud_competence_past_two").hide();
								$("#no_competencies_two_past").hide();
							<%}else{%>
								$("#bubble_cloud_competence").hide();
								$("#no_competencies_current").show();
								
								$("#bubble_cloud_competence_past_one").hide();
								$("#no_competencies_one_past").hide();
								
								$("#bubble_cloud_competence_past_two").hide();
								$("#no_competencies_two_past").hide();
							<%}%>
						}
					});
				</script>
			</div>
			<% } %>
			<div id="connections">
			<h2>Connections</h2>
				<div id="network_form">
					<p>Select how deep the recursion should be.</p>
					<form method="get" action="profile" id="profile">
						<input type="range" min="1" max="<%= maxLevel %>" value="<%= level %>" id="range_network" name="range_network"/>
						<span class="label label-default" id="network_level"><%= level %></span>
						<input type="hidden" name="userid" value="<%=user.getID()%>"/>
					</form>
				</div>
				
				<% if(!hasWorkedWithForNetwork.equals("")) {  %>
					<a href="#" id="has_worked_with_toggle" class="btn btn-lg btn-primary" role="button">has worked with..</a>
				<%} %>			
				<% if(!likesToWorkWithForNetwork.equals("")) {  %>
					<a href="#" id="likes_to_work_with_toggle" class="btn btn-lg btn-primary" role="button">likes to work with..</a>
				<%} %>
				<% if(!likesNotToWorkWithForNetwork.equals("")) {  %>	
					<a href="#" id="likes_not_to_work_with_toggle" class="btn btn-lg btn-primary" role="button">likes not to work with..</a>
				<%} %>
				<% if(!overviewForNetwork.equals("")) { %>
				<a href="#" id="connection_overview_toggle" class="btn btn-lg btn-primary" role="button"> Overview </a>				
				<%} %>
				
				<% if(!hasWorkedWithForNetwork.equals("")) {  %>
					<div id="has_worked_with" style="display: none;"></div>
				<%} %>	
				<% if(!likesToWorkWithForNetwork.equals("")) {  %>
					<div id="likes_to_work_with" style="display: none;"></div>
				<%} %>
				<% if(!likesNotToWorkWithForNetwork.equals("")) {  %>
					<div id="likes_not_to_work_with" style="display: none;"></div>
				<%} %>
				<% if(!overviewForNetwork.equals("")) { %>
				<div id="connection_overview" style="display:none;"></div>
				<%} %>
				
				<script type="text/javascript">
					$("#has_worked_with_toggle").click(function(event) {
						event.preventDefault();
						$("#has_worked_with").toggle();
					});
					$("#likes_to_work_with_toggle").click(function(event) {
						event.preventDefault();
						$("#likes_to_work_with").toggle();
					});
					$("#likes_not_to_work_with_toggle").click(function(event) {
						event.preventDefault();
						$("#likes_not_to_work_with").toggle();
					});
					$("#connection_overview_toggle").click(function(event) {
						event.preventDefault();
						$("#connection_overview").toggle();
					});
					$("#range_network").change(function(event){
						$("#network_level").text($(this).val());
						$("#profile").submit();
					});
				</script>
			</div>
			
			<div id="actions">
				<br/>
				<br/>
				<a href="evidence?user=<%=user.getID()%>" id="addevidence" class="btn btn-xs btn-primary" role="button"> Add Evidence for this user </a>
				<a href="createUser?userid=<%=user.getID()%>" id="edituser" class="btn btn-xs btn-primary" role="button"> Edit this user </a>	
				<a href="createUser?userid=<%=user.getID()%>&delete=true" id="deleteuser" class="btn btn-xs btn-primary" role="button"> Delete this user </a>				
			</div>
			</div>
			<!-- Timeliner Script-->
			<script type="text/javascript">
				$(document).ready(function() {
					$.timeliner({});
				});
			</script>
			
			<!-- Common Functions for Bubble Cloud -->
			<script type="text/javascript">
			var color_array =<%=colors%>;
			var diameter = 960;
			var margin = 20;
			var width = 960;
			var height = 500;
			var topCnt = 0;
			// Returns a flattened hierarchy containing all leaf nodes under the root.
			function classes(root, level) {
				var classes = [];
				topCnt = 0;
				function recurse(name, node, level, color_rec) {
					if (node.children)
						node.children.forEach(function(child) {
							recurse(node.name, child, level + 1, getColor(color_rec, level + 1));
						});
					else
						classes.push({
							packageName : name,
							className : node.name,
							shortName : node.shortname,
							value : node.size,
							color : color_rec
						});
				}

				recurse(null, root, 1, "#000000");
				return {
					children : classes
				};
			}

			function getColor(color_rec, level) {
				if (level == 2) {
					color_return = color_array[topCnt];
					topCnt += 1;
					return color_return;
				}
				
				return shadeColor1(color_rec, 20);
			}
			function shadeColor1(color, percent) {
				var num = parseInt(color.slice(1), 16), amt = Math
						.round(2.55 * percent), R = (num >> 16) + amt, G = (num >> 8 & 0x00FF)
						+ amt, B = (num & 0x0000FF) + amt;
				return "#"
						+ (0x1000000 + (R < 255 ? R < 1 ? 0 : R : 255)
								* 0x10000 + (G < 255 ? G < 1 ? 0 : G : 255)
								* 0x100 + (B < 255 ? B < 1 ? 0 : B : 255))
								.toString(16).slice(1);
			}
			</script>
			
			<!-- Script Graphs Interests -->	
			<!-- Tag Cloud -->
			<script type="text/javascript">
				var s = <%=interestsForTagCloud%>;
				var fill = d3.scale.category20();

				d3.layout.cloud().size([ 1000, 800 ]).words(s).rotate(
						function() {
							return ~~(Math.random() * 2) * 90;
						}).font("'Source Sans Pro', sans-serif").fontSize(
						function(d) {
							return d.size;
						}).on("end", draw).start();

				function draw(words) {
					d3.select("#tag_cloud_interest").append("svg").attr("width", 1000)
							.attr("height", 800).append("g").attr("transform",
									"translate(500,400)").selectAll("text")
							.data(words).enter().append("text").style(
									"font-size", function(d) {
										return d.size + "px";
									}).style("font-family",
									"'Source Sans Pro', sans-serif")
							.style("fill", function(d, i) {
								return color_array[(i % <%=colorCNT%> )];
							}).attr("text-anchor", "middle").attr(
									"transform",
									function(d) {
										return "translate(" + [ d.x, d.y ]
												+ ")rotate(" + d.rotate + ")";
									}).text(function(d) {
								return d.text;
							});
				}
			</script>
			<!-- Bubble Cloud -->
			<script type="text/javascript">
				var file_bubble_cloud_interest = "interestflare"+<%=user.getID()%>+".json";
				var bubble_cloud_interest = d3.layout.pack().sort(null).size(
						[ diameter, diameter ]).padding(1.5);

				var svg_bubble_cloud_interest = d3.select("#bubble_cloud_interest").append("svg").attr(
						"width", diameter).attr("height", diameter).attr(
						"class", "bubble").attr("id", "bubble_cloud_svg");

				d3.json("profile_libs/" + file_bubble_cloud_interest, function(error, root) {
					var node = svg_bubble_cloud_interest.selectAll(".node").data(
							bubble_cloud_interest.nodes(classes(root), 1).filter(function(d) {
								return !d.children;
							})).enter().append("g").attr("class", "node").attr(
							"transform", function(d) {
								return "translate(" + d.x + "," + d.y + ")";
							});

					node.append("title").text(function(d) {
						return d.className;
					});

					node.append("circle").attr("r", function(d) {
						return d.r;
					}).style("fill", function(d) {
						return d.color;
					});

					node.append("text").attr("dy", ".3em").style("text-anchor",
							"middle").text(function(d) {
						return (d.className.length<(d.r/3))?d.className : d.shortName;
					});
				});
				
				d3.select(self.frameElement).style("height", diameter + "px");
			</script>
			<!-- Zoom Bubble -->
			<script type="text/javascript">
				var file_zoom_cloud_interest = "interest_zoomflare"+<%=user.getID()%>+".json";
				var format_zoom_cloud_interest = d3.format(",d");
				
				var color_zoom_cloud_interest = d3.scale.linear().domain([ -1, 5 ]).range(
						[ "hsl(152,80%,80%)", "hsl(228,30%,40%)" ])
						.interpolate(d3.interpolateHcl);
	
				var pack_zoom_cloud_interest = d3.layout.pack().padding(2).size(
						[ diameter - margin, diameter - margin ]).value(
						function(d) {
							return d.size;
						});
				
				var svg_zoom_cloud_interest = d3.select("#zoom_cloud_interest").append("svg").attr("id",
						"zoom_cloud_svg_interest").attr("width", diameter).attr(
						"height", diameter).append("g").attr("transform",
						"translate(" + diameter / 2 + "," + diameter / 2 + ")");

				d3.json("profile_libs/" + file_zoom_cloud_interest, function(error, root_interest) {
					if (error)
						return console.error(error);

					var focus_interest = root_interest, nodes = pack_zoom_cloud_interest.nodes(root_interest), view;

					var circle = svg_zoom_cloud_interest.selectAll("circle").data(nodes).enter()
							.append("circle").attr(
									"class",
									function(d) {
										var node_class = d.parent ? d.children ? "node zoomcircle"
												: "node node--leaf zoomcircle"
												: "node node--root  zoomcircle";
										return node_class; 
									}).style("fill", function(d) {
								return d.children ? color_zoom_cloud_interest(d.depth) : null;
							}).on("click", function(d) {
								if (focus_interest !== d)
									zoom(d), d3.event.stopPropagation();
							});

					var text = svg_zoom_cloud_interest.selectAll("text").data(nodes).enter()
							.append("text").attr("class", "label").style(
									"fill-opacity", function(d) {
										return d.parent === root_interest ? 1 : 0;
									}).style("display", function(d) {
								return d.parent === root_interest ? null : "none";
							}).text(function(d) {
								return (d.name.length>25)?d.shortname_zoom:d.name;
							});

					var node = svg_zoom_cloud_interest.selectAll("circle,text");

					node.append("title").text(function(d) {
						return d.name;
					});
					
					d3.select("#zoom_cloud_interest").on("click", function() {
						zoom(root_interest);
					});

					zoomTo([ root_interest.x, root_interest.y, root_interest.r * 2 + margin ]);

					function zoom(d) {
						
						if(!d.children)
							d = d.parent;
						
						var focus0 = focus_interest; focus_interest = d;
						
						var transition = d3.transition().duration(
								d3.event.altKey ? 7500 : 750).tween(
								"zoom",
								function(d) {
									var i = d3.interpolateZoom(view, [ focus_interest.x,
											focus_interest.y, focus_interest.r * 2 + margin ]);
									return function(t) {
										zoomTo(i(t));
									};
								});

						transition.selectAll("text").filter(
								function(d) {
									return d.parent === focus_interest
											|| this.style.display === "inline";
								}).style("fill-opacity", function(d) {
							return d.parent === focus_interest ? 1 : 0;
						}).each("start", function(d) {
							if (d.parent === focus_interest)
								this.style.display = "inline";
						}).each("end", function(d) {
							if (d.parent !== focus_interest)
								this.style.display = "none";
						});
						
					}

					function zoomTo(v) {
						var k = diameter / v[2];
						view = v;
						node.attr("transform", function(d) {
							return "translate(" + (d.x - v[0]) * k + ","
									+ (d.y - v[1]) * k + ")";
						}).attr("title", function(d) {
							return d.name;
						});
						circle.attr("r", function(d) {
							return d.r * k;
						});
					}
				});

				d3.select(self.frameElement).style("height", diameter + "px");
			</script>
			
			<!-- Script Graphs Non-Interests -->
			<!-- Tag Cloud -->
			<script type="text/javascript">
				var s = <%=nonInterestsForTagCloud%>;
				var fill = d3.scale.category20();

				d3.layout.cloud().size([ 1000, 800 ]).words(s).rotate(
						function() {
							return ~~(Math.random() * 2) * 90;
						}).font("'Source Sans Pro', sans-serif").fontSize(
						function(d) {
							return d.size;
						}).on("end", draw).start();

				function draw(words) {
					d3.select("#tag_cloud_noninterest").append("svg").attr("width", 1000)
							.attr("height", 800).append("g").attr("transform",
									"translate(500,400)").selectAll("text")
							.data(words).enter().append("text").style(
									"font-size", function(d) {
										return d.size + "px";
									}).style("font-family",
									"'Source Sans Pro', sans-serif")
							.style("fill", function(d, i) {
								return color_array[(i % <%=colorCNT%> )];
							}).attr("text-anchor", "middle").attr(
									"transform",
									function(d) {
										return "translate(" + [ d.x, d.y ]
												+ ")rotate(" + d.rotate + ")";
									}).text(function(d) {
								return d.text;
							});
				}
			</script>
			<!-- Bubble Cloud -->
			<script type="text/javascript">
				var file_bubble_cloud_interest = "noninterestflare"+<%=user.getID()%>+".json";
				var bubble_cloud_noninterest = d3.layout.pack().sort(null).size(
						[ diameter, diameter ]).padding(1.5);

				var svg_bubble_cloud_noninterest = d3.select("#bubble_cloud_noninterest").append("svg").attr(
						"width", diameter).attr("height", diameter).attr(
						"class", "bubble").attr("id", "bubble_cloud_svg");

				d3.json("profile_libs/" + file_bubble_cloud_interest, function(error, root) {
					var node = svg_bubble_cloud_noninterest.selectAll(".node").data(
							bubble_cloud_noninterest.nodes(classes(root), 1).filter(function(d) {
								return !d.children;
							})).enter().append("g").attr("class", "node").attr(
							"transform", function(d) {
								return "translate(" + d.x + "," + d.y + ")";
							});

					node.append("title").text(function(d) {
						return d.className;
					});

					node.append("circle").attr("r", function(d) {
						return d.r;
					}).style("fill", function(d) {
						return d.color;
					});

					node.append("text").attr("dy", ".3em").style("text-anchor",
							"middle").text(function(d) {
						return (d.className.length<(d.r/3))?d.className : d.shortName;
					});
				});
		
				d3.select(self.frameElement).style("height", diameter + "px");
			</script>
			<!-- Zoom Cloud -->
			<script type="text/javascript">
				var file_zoom_cloud_noninterest = "noninterest_zoomflare"+<%=user.getID()%>+".json";

				var color_zoom_cloud_noninterest = d3.scale.linear().domain([ -1, 5 ]).range(
						[ "hsl(152,80%,80%)", "hsl(228,30%,40%)" ])
						.interpolate(d3.interpolateHcl);

				var pack_zoom_cloud_noninterest = d3.layout.pack().padding(2).size(
						[ diameter - margin, diameter - margin ]).value(
						function(d) {
							return d.size;
						});

				var svg_zoom_cloud_noninterest = d3.select("#zoom_cloud_noninterest").append("svg").attr("id",
						"zoom_cloud_svg_noninterest").attr("width", diameter).attr(
						"height", diameter).append("g").attr("transform",
						"translate(" + diameter / 2 + "," + diameter / 2 + ")");

				d3.json("profile_libs/" + file_zoom_cloud_noninterest, function(error, root) {
					if (error)
						return console.error(error);

					var focus_noninterest = root, nodes = pack_zoom_cloud_noninterest.nodes(root), view;

					var circle = svg_zoom_cloud_noninterest.selectAll("circle").data(nodes).enter()
							.append("circle").attr(
									"class",
									function(d) {
										return d.parent ? d.children ? "node zoomcircle"
												: "node node--leaf zoomcircle"
												: "node node--root zoomcircle";
									}).style("fill", function(d) {
								return d.children ? color_zoom_cloud_noninterest(d.depth) : null;
							}).on("click", function(d) {
								if (focus_noninterest !== d)
									zoom(d), d3.event.stopPropagation();
							});

					var text = svg_zoom_cloud_noninterest.selectAll("text").data(nodes).enter()
							.append("text").attr("class", "label").style(
									"fill-opacity", function(d) {
										return d.parent === root ? 1 : 0;
									}).style("display", function(d) {
								return d.parent === root ? null : "none";
							}).text(function(d) {
								return (d.name.length>25)?d.shortname_zoom:d.name;
							});

					var node = svg_zoom_cloud_noninterest.selectAll("circle,text");

					node.append("title").text(function(d) {
						return d.name;
					});
					
					d3.select("#zoom_cloud_noninterest").on("click", function() {
						zoom(root);
					});

					zoomTo([ root.x, root.y, root.r * 2 + margin ]);

					function zoom(d) {
						if(!d.children)
							d = d.parent;
						
						var focus0 = focus_noninterest;
						focus_noninterest = d;

						var transition = d3.transition().duration(
								d3.event.altKey ? 7500 : 750).tween(
								"zoom",
								function(d) {
									var i = d3.interpolateZoom(view, [ focus_noninterest.x,
											focus_noninterest.y, focus_noninterest.r * 2 + margin ]);
									return function(t) {
										zoomTo(i(t));
									};
								});

						transition.selectAll("text").filter(
								function(d) {
									return d.parent === focus_noninterest
											|| this.style.display === "inline";
								}).style("fill-opacity", function(d) {
							return d.parent === focus_noninterest ? 1 : 0;
						}).each("start", function(d) {
							if (d.parent === focus_noninterest)
								this.style.display = "inline";
						}).each("end", function(d) {
							if (d.parent !== focus_noninterest)
								this.style.display = "none";
						});
					}

					function zoomTo(v) {
						var k = diameter / v[2];
						view = v;
						node.attr("transform", function(d) {
							return "translate(" + (d.x - v[0]) * k + ","
									+ (d.y - v[1]) * k + ")";
						});
						circle.attr("r", function(d) {
							return d.r * k;
						});
					}
				});

				d3.select(self.frameElement).style("height", diameter + "px");
			</script>
			
			<!-- Script Graphs Competencies -->
			<!-- Tag Cloud -->
			<script type="text/javascript">
				var s = <%=competenciesForTagCloud%>;
				var fill = d3.scale.category20();

				d3.layout.cloud().size([ 1000, 800 ]).words(s).rotate(
						function() {
							return ~~(Math.random() * 2) * 90;
						}).font("'Source Sans Pro', sans-serif").fontSize(
						function(d) {
							return d.size;
						}).on("end", draw).start();

				function draw(words) {
					d3.select("#tag_cloud_competence").append("svg").attr("width", 1000)
							.attr("height", 800).append("g").attr("transform",
									"translate(500,400)").selectAll("text")
							.data(words).enter().append("text").style(
									"font-size", function(d) {
										return d.size + "px";
									}).style("font-family",
									"'Source Sans Pro', sans-serif")
							.style("fill", function(d, i) {
								return color_array[(i % <%=colorCNT%> )];
							}).attr("text-anchor", "middle").attr(
									"transform",
									function(d) {
										return "translate(" + [ d.x, d.y ]
												+ ")rotate(" + d.rotate + ")";
									}).text(function(d) {
								return d.text;
							});
				}
			</script>
			<!-- Bubble Cloud -->
			<!-- current -->
			<script type="text/javascript">
				var file_bubble_cloud_competence = "competenceflare"+<%=user.getID()%>+".json";
				var bubble_bubble_cloud_competence = d3.layout.pack().sort(null).size(
						[ diameter, diameter ]).padding(1.5);

				var svg_bubble_cloud_competence = d3.select("#bubble_cloud_competence").append("svg").attr(
						"width", diameter).attr("height", diameter).attr(
						"class", "bubble").attr("id", "bubble_cloud_svg");

				d3.json("profile_libs/" + file_bubble_cloud_competence, function(error, root) {
					var node = svg_bubble_cloud_competence.selectAll(".node").data(
							bubble_bubble_cloud_competence.nodes(classes(root), 1).filter(function(d) {
								return !d.children;
							})).enter().append("g").attr("class", "node").attr(
							"transform", function(d) {
								return "translate(" + d.x + "," + d.y + ")";
							});

					node.append("title").text(function(d) {
						return d.className;
					});

					node.append("circle").attr("r", function(d) {
						return d.r;
					}).style("fill", function(d) {
						return d.color;
					});

					node.append("text").attr("dy", ".3em").style("text-anchor",
							"middle").text(function(d) {
						return (d.className.length<(d.r/3))?d.className : d.shortName;
					});
				});
		
				d3.select(self.frameElement).style("height", diameter + "px");
			</script>
			<!-- past one -->
			<script type="text/javascript">
				var file_bubble_cloud_competence_past_one = "competencepastoneflare"+<%=user.getID()%>+".json";
				var bubble_bubble_cloud_competence_past_one = d3.layout.pack().sort(null).size(
						[ diameter, diameter ]).padding(1.5);

				var svg_bubble_cloud_competence_past_one = d3.select("#bubble_cloud_competence_past_one").append("svg").attr(
						"width", diameter).attr("height", diameter).attr(
						"class", "bubble").attr("id", "bubble_cloud_svg");

				d3.json("profile_libs/" + file_bubble_cloud_competence_past_one, function(error, root) {
					var node = svg_bubble_cloud_competence_past_one.selectAll(".node").data(
							bubble_bubble_cloud_competence_past_one.nodes(classes(root), 1).filter(function(d) {
								return !d.children;
							})).enter().append("g").attr("class", "node").attr(
							"transform", function(d) {
								return "translate(" + d.x + "," + d.y + ")";
							});

					node.append("title").text(function(d) {
						return d.className;
					});

					node.append("circle").attr("r", function(d) {
						return d.r;
					}).style("fill", function(d) {
						return d.color;
					});

					node.append("text").attr("dy", ".3em").style("text-anchor",
							"middle").text(function(d) {
						return (d.className.length<(d.r/3))?d.className : d.shortName;
					});
				});
		
				d3.select(self.frameElement).style("height", diameter + "px");
			</script>

			<!-- past two -->
			<script type="text/javascript">
				var file_bubble_cloud_competence_past_two = "competencepasttwoflare"+<%=user.getID()%>+".json";
				var bubble_bubble_cloud_competence_past_two = d3.layout.pack().sort(null).size(
						[ diameter, diameter ]).padding(1.5);

				var svg_bubble_cloud_competence_past_two = d3.select("#bubble_cloud_competence_past_two").append("svg").attr(
						"width", diameter).attr("height", diameter).attr(
						"class", "bubble").attr("id", "bubble_cloud_svg");

				d3.json("profile_libs/" + file_bubble_cloud_competence_past_two, function(error, root) {
					var node = svg_bubble_cloud_competence_past_two.selectAll(".node").data(
							bubble_bubble_cloud_competence_past_two.nodes(classes(root), 1).filter(function(d) {
								return !d.children;
							})).enter().append("g").attr("class", "node").attr(
							"transform", function(d) {
								return "translate(" + d.x + "," + d.y + ")";
							});

					node.append("title").text(function(d) {
						return d.className;
					});

					node.append("circle").attr("r", function(d) {
						return d.r;
					}).style("fill", function(d) {
						return d.color;
					});

					node.append("text").attr("dy", ".3em").style("text-anchor",
							"middle").text(function(d) {
						return (d.className.length<(d.r/3))?d.className : d.shortName;
					});
				});
		
				d3.select(self.frameElement).style("height", diameter + "px");
			</script>

			<!-- Zoom Cloud-->
			<script type="text/javascript">
				var file_zoom_cloud_competence = "competence_zoomflare"+<%=user.getID()%>+".json";
				var color_zoom_cloud_competence = d3.scale.linear().domain([ -1, 5 ]).range(
						[ "hsl(152,80%,80%)", "hsl(228,30%,40%)" ])
						.interpolate(d3.interpolateHcl);

				var pack_zoom_cloud_competence = d3.layout.pack().padding(2).size(
						[ diameter - margin, diameter - margin ]).value(
						function(d) {
							return d.size;
						});

				var svg_zoom_cloud_competence = d3.select("#zoom_cloud_competence").append("svg").attr("id",
						"zoom_cloud_svg").attr("width", diameter).attr(
						"height", diameter).append("g").attr("transform",
						"translate(" + diameter / 2 + "," + diameter / 2 + ")");

				d3.json("profile_libs/" + file_zoom_cloud_competence, function(error, root) {
					if (error)
						return console.error(error);

					var focus_competence = root, nodes = pack_zoom_cloud_competence.nodes(root), view;

					var circle = svg_zoom_cloud_competence.selectAll("circle").data(nodes).enter()
							.append("circle").attr(
									"class",
									function(d) {
										return d.parent ? d.children ? "node zoomcircle"
												: "node node--leaf zoomcircle"
												: "node node--root zoomcircle";
									}).style("fill", function(d) {
								return d.children ? color_zoom_cloud_competence(d.depth) : null;
							}).on("click", function(d) {
								if (focus_competence !== d)
									zoom(d), d3.event.stopPropagation();
							});

					var text = svg_zoom_cloud_competence.selectAll("text").data(nodes).enter()
							.append("text").attr("class", "label").style(
									"fill-opacity", function(d) {
										return d.parent === root ? 1 : 0;
									}).style("display", function(d) {
								return d.parent === root ? null : "none";
							}).text(function(d) {
								return (d.name.length>25)?d.shortname_zoom:d.name;
							});

					var node = svg_zoom_cloud_competence.selectAll("circle,text");
					
					node.append("title").text(function(d) {
						return d.name;
					});
					
					d3.select("#zoom_cloud_competence").on("click", function() {
						zoom(root);
					});

					zoomTo([ root.x, root.y, root.r * 2 + margin ]);

					function zoom(d) {
						if(!d.children)
							d = d.parent;
						
						var focus0 = focus_competence;
						focus_competence = d;

						var transition = d3.transition().duration(
								d3.event.altKey ? 7500 : 750).tween(
								"zoom",
								function(d) {
									var i = d3.interpolateZoom(view, [ focus_competence.x,
											focus_competence.y, focus_competence.r * 2 + margin ]);
									return function(t) {
										zoomTo(i(t));
									};
								});

						transition.selectAll("text").filter(
								function(d) {
									return d.parent === focus_competence
											|| this.style.display === "inline";
								}).style("fill-opacity", function(d) {
							return d.parent === focus_competence ? 1 : 0;
						}).each("start", function(d) {
							if (d.parent === focus_competence)
								this.style.display = "inline";
						}).each("end", function(d) {
							if (d.parent !== focus_competence)
								this.style.display = "none";
						});
					}

					function zoomTo(v) {
						var k = diameter / v[2];
						view = v;
						node.attr("transform", function(d) {
							return "translate(" + (d.x - v[0]) * k + ","
									+ (d.y - v[1]) * k + ")";
						});
						circle.attr("r", function(d) {
							return d.r * k;
						});
					}
				});

				d3.select(self.frameElement).style("height", diameter + "px");
			</script>
		
			<!-- Script Connections -->
			<!-- Script Simple Connections - has worked with -->
			<script type="text/javascript">
			var level = <%= level%>;
			var distance = 200;
			var charge = -500;
			
			// http://blog.thomsonreuters.com/index.php/mobile-patent-suits-graphic-of-the-day/
			var links_has_worked_with_temp = <%= hasWorkedWithForNetwork %>;
			
			var links_has_worked_with = [];
			links_has_worked_with_temp.forEach(function(link){
				if(link.level <= level){
					links_has_worked_with.push(link);
				}
			});
			
			var nodes_has_worked_with = {};
			
			// Compute the distinct nodes from the links.
			links_has_worked_with.forEach(function(link) {
				
			  	link.source = nodes_has_worked_with[link.source] || (nodes_has_worked_with[link.source] = {name: link.source});
			  	link.target = nodes_has_worked_with[link.target] || (nodes_has_worked_with[link.target] = {name: link.target});
				
			});
			
			var force_has_worked_with = d3.layout.force()
			    .nodes(d3.values(nodes_has_worked_with))
			    .links(links_has_worked_with)
			    .size([width, height])
			    .linkDistance(distance)
			    .charge(charge) //test original -300
			    .on("tick", tick_has_worked_with)
			    .start();
			
			var svg_has_worked_with = d3.select("#has_worked_with").append("svg")
			    .attr("width", width)
			    .attr("height", height);
			
			var link_has_worked_with = svg_has_worked_with.selectAll(".link")
			    .data(force_has_worked_with.links())
			  .enter().append("line")
			    .attr("class", "link");
			
			var node_has_worked_with = svg_has_worked_with.selectAll(".node")
			    .data(force_has_worked_with.nodes())
			  .enter().append("g")
			    .attr("class", "node")
			    .on("mouseover", mouseover_has_worked_with)
			    .on("mouseout", mouseout_has_worked_with)
			    .call(force_has_worked_with.drag);
			
			node_has_worked_with.append("circle")
			    .attr("r", 8)
			    .attr("class", "circle_connection_graph");
			
			node_has_worked_with.append("text")
			    .attr("x", 16)
			    .attr("dy", "1em")
			    .text(function(d) { return d.name; });
			
			function tick_has_worked_with() {
			  link_has_worked_with
			      .attr("x1", function(d) { return d.source.x; })
			      .attr("y1", function(d) { return d.source.y; })
			      .attr("x2", function(d) { return d.target.x; })
			      .attr("y2", function(d) { return d.target.y; })
			      .attr("class", function(d){return "link "+d.type;});
			
			  node_has_worked_with
			      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
			}
			
			function mouseover_has_worked_with() {
			  d3.select(this).select("circle").transition()
			      .attr("r", 16);
			}
			
			function mouseout_has_worked_with() {
			  d3.select(this).select("circle").transition()
			      .attr("r", 8);
			}
			
			</script>
			
			<!-- Script Directed Connections - likes to work with-->
			<script type="text/javascript">

			var links_likes_to_work_with_temp = <%= (likesToWorkWithForNetwork.equals("")?"[]":likesToWorkWithForNetwork) %>;
			
			var links_likes_to_work_with = [];
			links_likes_to_work_with_temp.forEach(function(link){
				if(link.level <= level){
					links_likes_to_work_with.push(link);
				}
			});
			
			var nodes_likes_to_work_with = {};

			// Compute the distinct nodes from the links.
			links_likes_to_work_with.forEach(function(link) {
			    link.source = nodes_likes_to_work_with[link.source] || 
			        (nodes_likes_to_work_with[link.source] = {name: link.source});
			    link.target = nodes_likes_to_work_with[link.target] || 
			        (nodes_likes_to_work_with[link.target] = {name: link.target});

			    link.value = +link.value;
			    link.type += " likes";
			});

			var force_likes_to_work_with = d3.layout.force()
			    .nodes(d3.values(nodes_likes_to_work_with))
			    .links(links_likes_to_work_with)
			    .size([width, height])
			    .linkDistance(distance)
			    .charge(charge)
			    .on("tick", tick_likes_to_work_with)
			    .start();

			var svg_likes_to_work_with = d3.select("#likes_to_work_with").append("svg")
			    .attr("width", width)
			    .attr("height", height);

			// build the arrow.
			svg_likes_to_work_with.append("svg:defs").selectAll("marker")
			    .data(["end"])      // Different link/path types can be defined here
			  .enter().append("svg:marker")    // This section adds in the arrows
			    .attr("id", String)
			    .attr("viewBox", "0 -5 10 10")
			    .attr("refX", 15)
			    .attr("refY", -1.5)
			    .attr("markerWidth", 6)
			    .attr("markerHeight", 6)
			    .attr("orient", "auto")
			  .append("svg:path")
			    .attr("d", "M0,-5L10,0L0,5");

			// add the links and the arrows
			var path_likes_to_work_with = svg_likes_to_work_with.append("svg:g").selectAll("path")
			    .data(force_likes_to_work_with.links())
			  .enter().append("svg:path")
			    .attr("class", function(d) { return "link " + d.type; })
			    .attr("marker-end", "url(#end)");

			// define the nodes
			var node_likes_to_work_with = svg_likes_to_work_with.selectAll(".node")
			    .data(force_likes_to_work_with.nodes())
			  .enter().append("g")
			    .attr("class", "node")
			    .call(force_likes_to_work_with.drag);

			// add the nodes
			node_likes_to_work_with.append("circle")
			    .attr("r", 5)
			    .attr("class", "circle_connection_graph");

			// add the text 
			node_likes_to_work_with.append("text")
			    .attr("x", 12)
			    .attr("dy", ".35em")
			    .text(function(d) { return d.name; });

			// add the curvy lines
			function tick_likes_to_work_with() {
			    path_likes_to_work_with.attr("d", function(d) {
			        var dx = d.target.x - d.source.x,
			            dy = d.target.y - d.source.y;
			            dr = Math.sqrt(dx * dx + dy * dy);
			        return "M" + 
			            d.source.x + "," + 
			            d.source.y + "A" + 
			            dr + "," + dr + " 0 0,1 " + 
			            d.target.x + "," + 
			            d.target.y;
			    });

			    node_likes_to_work_with
			        .attr("transform", function(d) { 
					    return "translate(" + d.x + "," + d.y + ")"; });
			}

			</script>
			<!-- Script Directed Connections - likes not to work with-->
			<script type="text/javascript">

			var links_likes_not_to_work_with_temp = <%= (likesNotToWorkWithForNetwork.equals("")?"[]":likesNotToWorkWithForNetwork) %>;
			
			var links_likes_not_to_work_with = [];
			links_likes_not_to_work_with_temp.forEach(function(link){
				if(link.level <= level){
					links_likes_not_to_work_with.push(link);
				}
			});
			
			var nodes_likes_not_to_work_with = {};

			// Compute the distinct nodes from the links.
			links_likes_not_to_work_with.forEach(function(link) {
			    link.source = nodes_likes_not_to_work_with[link.source] || 
			        (nodes_likes_not_to_work_with[link.source] = {name: link.source});
			    link.target = nodes_likes_not_to_work_with[link.target] || 
			        (nodes_likes_not_to_work_with[link.target] = {name: link.target});
			    link.value = +link.value;
			    link.type += " likesnot";
			});

			var force_likes_not_to_work_with = d3.layout.force()
			    .nodes(d3.values(nodes_likes_not_to_work_with))
			    .links(links_likes_not_to_work_with)
			    .size([width, height])
			    .linkDistance(distance)
			    .charge(charge)
			    .on("tick", tick_likes_not_to_work_with)
			    .start();

			var svg_likes_not_to_work_with = d3.select("#likes_not_to_work_with").append("svg")
			    .attr("width", width)
			    .attr("height", height);

			// build the arrow.
			svg_likes_not_to_work_with.append("svg:defs").selectAll("marker")
			    .data(["end"])      // Different link/path types can be defined here
			  .enter().append("svg:marker")    // This section adds in the arrows
			    .attr("id", String)
			    .attr("viewBox", "0 -5 10 10")
			    .attr("refX", 15)
			    .attr("refY", -1.5)
			    .attr("markerWidth", 6)
			    .attr("markerHeight", 6)
			    .attr("orient", "auto")
			  .append("svg:path")
			    .attr("d", "M0,-5L10,0L0,5");

			// add the links and the arrows
			var path_likes_not_to_work_with = svg_likes_not_to_work_with.append("svg:g").selectAll("path")
			    .data(force_likes_not_to_work_with.links())
			  .enter().append("svg:path")
			    .attr("class", function(d) { return "link " + d.type; })
			    .attr("marker-end", "url(#end)");

			// define the nodes
			var node_likes_not_to_work_with = svg_likes_not_to_work_with.selectAll(".node")
			    .data(force_likes_not_to_work_with.nodes())
			  .enter().append("g")
			    .attr("class", "node")
			    .call(force_likes_not_to_work_with.drag);

			// add the nodes
			node_likes_not_to_work_with.append("circle")
			    .attr("r", 5)
			    .attr("class", "circle_connection_graph");

			// add the text 
			node_likes_not_to_work_with.append("text")
			    .attr("x", 12)
			    .attr("dy", ".35em")
			    .text(function(d) { return d.name; });

			// add the curvy lines
			function tick_likes_not_to_work_with() {
			    path_likes_not_to_work_with.attr("d", function(d) {
			        var dx = d.target.x - d.source.x,
			            dy = d.target.y - d.source.y,
			            dr = Math.sqrt(dx * dx + dy * dy);
			        return "M" + 
			            d.source.x + "," + 
			            d.source.y + "A" + 
			            dr + "," + dr + " 0 0,1 " + 
			            d.target.x + "," + 
			            d.target.y;
			    });

			    node_likes_not_to_work_with
			        .attr("transform", function(d) { 
					    return "translate(" + d.x + "," + d.y + ")"; });
			}
			</script>
			
		<!-- Script Interactiv Overview -->
			<script type="text/javascript">

			var links_temp = <%= (overviewForNetwork.equals("")?"[]":overviewForNetwork) %>;
			
			var links = [];
			links_temp.forEach(function(link){
				if(link.level <= level){
					links.push(link);
				}
			});
			
			var nodes = {};

			// Compute the distinct nodes from the links.
			links.forEach(function(link) {
			    link.source = nodes[link.source] || 
			        (nodes[link.source] = {name: link.source});
			    link.target = nodes[link.target] || 
			        (nodes[link.target] = {name: link.target});
			    //link.value = +link.value;
			    link.value = link.value;
			    if(link.value == 1)
			    	link.type += " likes";
			    else if(link.value == 2)
			    	link.type += " likesnot";
			});

			var force = d3.layout.force()
			    .nodes(d3.values(nodes))
			    .links(links)
			    .size([width, height])
			    .linkDistance(distance)
			    .charge(-700)
			    .on("tick", tick)
			    .start();

			var svg = d3.select("#connection_overview").append("svg")
			    .attr("width", width)
			    .attr("height", height);

			// build the arrow.
			svg.append("svg:defs").selectAll("marker")
			    .data(["end"])      // Different link/path types can be defined here
			  .enter().append("svg:marker")    // This section adds in the arrows
			    .attr("id", String)
			    .attr("viewBox", "0 -5 10 10")
			    .attr("refX", 15)
			    .attr("refY", -1.5)
			    .attr("markerWidth", 6)
			    .attr("markerHeight", 6)
			    .attr("orient", "auto")
			  .append("svg:path")
			    .attr("d", "M0,-5L10,0L0,5");

			// add the links and the arrows
			var path = svg.append("svg:g").selectAll("path")
			    .data(force.links())
			  .enter().append("svg:path")
			    .attr("class", function(d) { return "link " + d.type; })
			    .attr("marker-end", "url(#end)");

			// define the nodes
			var node = svg.selectAll(".node")
			    .data(force.nodes())
			  .enter().append("g")
			    .attr("class", "node")
			    .call(force.drag);

			// add the nodes
			node.append("circle")
			    .attr("r", 5)
			    .attr("class", "circle_connection_graph");

			// add the text 
			node.append("text")
			    .attr("x", 12)
			    .attr("dy", ".35em")
			    .text(function(d) { return d.name; });

			// add the curvy lines
			function tick() {
			    path.attr("d", function(d) {
			        var dx = d.target.x - d.source.x,
			            dy = d.target.y - d.source.y,
			            dr = Math.sqrt(dx * dx + dy * dy);
			        return "M" + 
			            d.source.x + "," + 
			            d.source.y + "A" + 
			            dr + "," + dr + " 0 0,1 " + 
			            d.target.x + "," + 
			            d.target.y;
			    });

			    node
			        .attr("transform", function(d) { 
					    return "translate(" + d.x + "," + d.y + ")"; });
			}
			</script>
		</div>
	</div>
</body>
</html>