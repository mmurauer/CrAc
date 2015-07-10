<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="inloc.LOCdefinition, model.Attribute, model.Task, model.WorkAndEducationAttribute, 
	java.util.Map, java.util.List, java.util.ArrayList, java.util.HashMap, java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task</title>

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
					<li><a href="useroverview">Users</a></li>
					<li class="active"><a href="taskoverview">Tasks</a></li>
					<li><a href="compare">Compare</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		</nav>
		
		<% 
		//get request params
		Task task = null;
		if(request.getAttribute("task")!= null){
				task = (Task) request.getAttribute("task");
		}	
		
		String interestsForTagCloud = "";
		if(request.getAttribute("interestsForTagCloud")!= null){
				interestsForTagCloud = request.getAttribute("interestsForTagCloud").toString();
		}

		String competenciesForTagCloud = "";
		if(request.getAttribute("competenciesForTagCloud")!= null){
			competenciesForTagCloud = request.getAttribute("competenciesForTagCloud").toString();
		}

		boolean hasCompetencies = false;
		if(request.getAttribute("hasCompetencies") != null){
			hasCompetencies = Boolean.parseBoolean(request.getAttribute("hasCompetencies").toString());
		}
		
		String colors = "";
		if(request.getAttribute("colors") != null){
			colors = request.getAttribute("colors").toString();
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
					<%= task.getName() %>
				</h1>
				<ul>
					<li><%if( !task.getDescription().equals("") ){ %>Description: <%= task.getDescription() %><%} %>
					</li>
					<li><%if( !task.getDescription().equals("") ){ %>Address: <%= task.getLocation().toString() %><%} %></li>
				</ul>
			</div>	
			
			<div id="graphs" class="jumbotron">
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
			
			<% if (!competenciesForTagCloud.equals("")) {%>
			<div id="competencies">
				<h2>Competencies</h2>
				<a href="#" id="tag_cloud_competence_toggle" class="btn btn-lg btn-primary" role="button">Tag Cloud</a>
				<a href="#" id="bubble_cloud_competence_toggle" class="btn btn-lg btn-primary" role="button">Bubble Cloud</a>
				<a href="#" id="zoom_cloud_competence_toggle" class="btn btn-lg btn-primary" role="button">Zoom Cloud</a>
				
				<div id="tag_cloud_competence" style="display: none;"></div>
				<div id="bubble_cloud_competence" style="display: none;"></div>
				<div id="zoom_cloud_competence" style="display: none;"></div>
				
				<script type="text/javascript">
					$("#tag_cloud_competence_toggle").click(function(event) {
						event.preventDefault();
						$("#tag_cloud_competence").toggle();
					});
					$("#bubble_cloud_competence_toggle").click(function(event) {
						event.preventDefault();
						$("#bubble_cloud_competence").toggle();
					});
					$("#zoom_cloud_competence_toggle").click(function(event) {
						event.preventDefault();
						$("#zoom_cloud_competence").toggle();
					});
				</script>
			</div>
			<% } %>
			
			</div>
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
				var file_bubble_cloud_interest = "interestflare"+<%= task.getID()%>+".json";
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
				var file_zoom_cloud_interest = "interest_zoomflare"+<%= task.getID()%>+".json";
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
				var file_bubble_cloud_competence = "competenceflare"+<%= task.getID()%>+".json";
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
<!-- Zoom Cloud-->
			<script type="text/javascript">
				var file_zoom_cloud_competence = "competence_zoomflare"+<%= task.getID()%>+".json";
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

		</div>
	</div>
</body>
</html>