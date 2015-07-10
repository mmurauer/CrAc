package servlets;

import inloc.LOCUtil;
import inloc.LOCdefinition;
import inloc.LOCrel;
import inloc.LOCscheme;
import inloc.LOCstructure;
import inloc.LOCtypeLOC;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import manager.UserManagerEJBLocal;
import model.CompetenceAndInterestAttribute;
import model.User;
import model.WorkAndEducationAttribute;

/**
 * Servlet implementation class UserProfileServlet create all graph data for
 * user profile
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@WebServlet("/UserProfileServlet")
@RequestScoped
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserManagerEJBLocal userManagerEJBLocal;

	// available colors, used in profile vizualisation
	private final String[] avail_colors = { "#2384C4", "#404040", "#7FDE26",
			"#78061B", "#067863", "#FF7300", "#FADF2F", "#8A3A06", "#0E5719",
			"#5A076B", "#DB0B7A", "#0B0B3B", "#F22222", "#451D03", "#F6FF00" };

	// font size for tag cloud
	private final int stdFontSize = 20;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfileServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int userid = Integer.parseInt(request.getParameter("userid"));

		int currentLevel = 1;
		if (request.getParameter("range_network") != null)
			currentLevel = Integer.parseInt(request
					.getParameter("range_network"));

		User user = userManagerEJBLocal.getUserById(userid);

		List<CompetenceAndInterestAttribute> competencies = user
				.getCompetencies();
		List<CompetenceAndInterestAttribute> interests = user.getInterests();
		List<CompetenceAndInterestAttribute> noninterests = user
				.getNonInterests();

		Map<Integer, List<WorkAndEducationAttribute>> timeline = user
				.getTimeline();

		// level for slider for connection graphs
		int maxLevel = 1;

		// count how many different colors are needed, if available color are
		// not enough, generate random colors
		int colorCNT = 0;
		int currentTopLevelCNT = 0;

		String interestsForTagCloud = "";
		if (interests != null) {
			// generate JSON String for tag cloud
			interestsForTagCloud = this.generateJSONForTagCloud(user,
					interests, "interest", Calendar.getInstance());

			// generate JSON file for bubble graph
			currentTopLevelCNT = this.generateJSON(user, interests, "interest",
					Calendar.getInstance());
			if (currentTopLevelCNT > colorCNT)
				colorCNT = currentTopLevelCNT;

			// generate JSON file for zoom cloud
			this.generateJSON(user, interests, "interest_zoom",
					Calendar.getInstance());
		}

		boolean hasCompetenciesCurrent = false;
		boolean hasCompetenciesOnePast = false;
		boolean hasCompetenciesTwoPast = false;
		String competenciesForTagCloud = "";
		if (competencies != null) {
			// generate JSON String for tag cloud
			competenciesForTagCloud = this.generateJSONForTagCloud(user,
					competencies, "competence", Calendar.getInstance());

			// generate JSON file for bubble cloud - current time
			currentTopLevelCNT = this.generateJSON(user, competencies,
					"competence", Calendar.getInstance());
			if (currentTopLevelCNT > 0)
				hasCompetenciesCurrent = true;
			if (currentTopLevelCNT > colorCNT)
				colorCNT = currentTopLevelCNT;

			// generate JSON file for bubble cloud - time in past: currently
			// hard coded
			currentTopLevelCNT = this.generateJSON(user, competencies,
					"competencepastone", new GregorianCalendar(2014, 13, 31));
			if (currentTopLevelCNT > 0)
				hasCompetenciesOnePast = true;
			if (currentTopLevelCNT > colorCNT)
				colorCNT = currentTopLevelCNT;

			// generate JSON file for bubble cloud - time in past: currently
			// hard coded
			currentTopLevelCNT = this.generateJSON(user, competencies,
					"competencepasttwo", new GregorianCalendar(2013, 13, 31));
			if (currentTopLevelCNT > 0)
				hasCompetenciesTwoPast = true;
			if (currentTopLevelCNT > colorCNT)
				colorCNT = currentTopLevelCNT;

			// generate JSON file for zoom cloud
			this.generateJSON(user, competencies, "competence_zoom",
					Calendar.getInstance());
		}

		String nonInterestsForTagCloud = "";
		if (noninterests != null) {
			// generate JSON String for tag cloud
			nonInterestsForTagCloud = this.generateJSONForTagCloud(user,
					noninterests, "noninterest", Calendar.getInstance());

			// generate JSON file for bubble graph
			currentTopLevelCNT = this.generateJSON(user, noninterests,
					"noninterest", Calendar.getInstance());
			if (currentTopLevelCNT > colorCNT)
				colorCNT = currentTopLevelCNT;

			// generate JSON file for zoom cloud
			this.generateJSON(user, noninterests, "noninterest_zoom",
					Calendar.getInstance());
		}

		JSONObject networkGraph = new JSONObject();
		JSONArray users;
		int currentMaxLevel;

		// get network graphs
		// get connections to user current user has worked with
		String hasWorkedWithForNetwork = "";
		if (user.getHasWorkedWith() != null) {
			networkGraph = this.getUsersForNetwork("hasWorkedWith", user,
					new HashMap<Integer, List<User>>());
			// JSON string
			users = (JSONArray) networkGraph.get("users");
			// recursion level
			currentMaxLevel = Integer.parseInt(networkGraph.get("level").toString());

			// update maxLevel
			if (currentMaxLevel > maxLevel)
				maxLevel = currentMaxLevel;
			if (users.size() > 0)
				hasWorkedWithForNetwork = users.toJSONString();
		}

		// get connections to user current user likes to work with
		String likesToWorkWithForNetwork = "";
		if (user.getLikesToWorkWith() != null) {
			networkGraph = this.getUsersForNetwork("likesToWorkWith", user,
					new HashMap<Integer, List<User>>());
			// JSON string
			users = (JSONArray) networkGraph.get("users");
			// recursion level
			currentMaxLevel = Integer.parseInt(networkGraph.get("level").toString());

			// update maxLevel
			if (currentMaxLevel > maxLevel)
				maxLevel = currentMaxLevel;
			if (users.size() > 0)
				likesToWorkWithForNetwork = users.toJSONString();
		}

		// get connections to user current user likes not to work with
		String likesNotToWorkWithForNetwork = "";
		if (user.getLikesNotToWorkWith() != null) {
			networkGraph = this.getUsersForNetwork("likesNotToWorkWith", user,
					new HashMap<Integer, List<User>>());
			// JSON string
			users = (JSONArray) networkGraph.get("users");
			// recursion level
			currentMaxLevel = Integer.parseInt(networkGraph.get("level").toString());

			// update maxLevel
			if (currentMaxLevel > maxLevel)
				maxLevel = currentMaxLevel;
			if (users.size() > 0)
				likesNotToWorkWithForNetwork = users.toJSONString();
		}

		// get all user connections to be shown in an overview graph
		String overviewForNetwork = "";
		if (user.getHasWorkedWith() != null
				|| user.getLikesToWorkWith() != null
				|| user.getLikesNotToWorkWith() != null) {
			networkGraph = getUsersForNetworkOverview(user);
			users = (JSONArray) networkGraph.get("users");
			currentMaxLevel = Integer.parseInt(networkGraph.get("level").toString());
			if (currentMaxLevel > maxLevel)
				maxLevel = currentMaxLevel;
			if (users.size() > 0)
				overviewForNetwork = users.toJSONString();
		}

		// get colors as string - available colors + additional generated when
		// needed
		String colors = this.getColors(colorCNT);

		request.setAttribute("user", user);
		request.setAttribute("timeline", timeline);

		request.setAttribute("interestsForTagCloud", interestsForTagCloud);
		request.setAttribute("nonInterestsForTagCloud", nonInterestsForTagCloud);
		request.setAttribute("competenciesForTagCloud", competenciesForTagCloud);

		request.setAttribute("hasCompetenciesCurrent", hasCompetenciesCurrent);
		request.setAttribute("hasCompetenciesOnePast", hasCompetenciesOnePast);
		request.setAttribute("hasCompetenciesTwoPast", hasCompetenciesTwoPast);

		request.setAttribute("hasWorkedWithForNetwork", hasWorkedWithForNetwork);
		request.setAttribute("likesToWorkWithForNetwork",
				likesToWorkWithForNetwork);
		request.setAttribute("likesNotToWorkWithForNetwork",
				likesNotToWorkWithForNetwork);
		request.setAttribute("overviewForNetwork", overviewForNetwork);

		request.setAttribute("level", currentLevel);
		request.setAttribute("maxLevel", maxLevel);

		request.setAttribute("colors", colors);
		request.setAttribute("colorCNT", colorCNT);
		request.getRequestDispatcher("user_profile.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * generate a user specific json file for visualizing
	 * competencies/interests/noninterests for two different graphs: bubble
	 * cloud and zoom cloud
	 * 
	 * @param user
	 *            - current user
	 * @param jsonitems
	 *            - competence/interest/noninterest-attributes
	 * @param type
	 *            - competence/interest/noninterest + type of graph / time used
	 *            for filename (+ userID)
	 * @param date
	 *            - only competencies gained before specific date
	 * 
	 * @return int - count how many colors are needed
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected int generateJSON(User user,
			List<CompetenceAndInterestAttribute> jsonitems, String type,
			Calendar date) throws IOException {

		// 1. get TopLevel as you need them to display the graph
		// 2. get all gained sub-attributes
		// (competencies/interests/noninterests) recursive

		List<LOCtypeLOC> userTopLevel = new ArrayList<>();

		// LOCStructure contained for competencies, as LOCfiles have different
		// structure
		switch (type) {
		case "competence":
		case "competencepastone":
		case "competencepasttwo":
		case "competence_zoom":
			userTopLevel = getTopStructLevelList(jsonitems, type, date);
			break;
		case "interest":
		case "interest_zoom":
		case "noninterest":
		case "noninterest_zoom":
			userTopLevel = getTopLevelList(jsonitems, type, date);
			break;
		}

		String name = type + "flare";

		// generate JSON Objects for graph visualitazion
		JSONObject root = new JSONObject();
		root.put("name", name);
		JSONArray children = new JSONArray();

		// build subtrees for all top levels
		for (LOCtypeLOC locType : userTopLevel)
			children.add(buildJSONTree(user, locType, 3000, type, date));

		if (!children.isEmpty())
			root.put("children", children);

		// create and write file
		FileWriter file = new FileWriter(this.getServletContext().getRealPath(
				"profile_libs/" + name + user.getID() + ".json"));
		try {
			file.write(root.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			file.flush();
			file.close();
		}

		// return amount of top levels = needed colors
		return userTopLevel.size();
	}

	/**
	 * build JSON tree for all child branches
	 * 
	 * @param user
	 * @param locType
	 * @param level
	 *            - used for font size
	 * @param type
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONObject buildJSONTree(User user, LOCtypeLOC locType,
			double level, String type, Calendar date) {
		JSONObject obj = new JSONObject();

		String name = locType.toString();

		// add current attribute to graph, use different short names for
		// different graphs - not necessary now, but if contained in LOCfile
		// this can be used
		obj.put("name", name);
		obj.put("shortname",
				locType.toString().substring(
						0,
						((locType.toString().length() < 5) ? locType.toString()
								.length() : 5))
						+ "...");
		obj.put("shortname_zoom",
				locType.toString().substring(
						0,
						((locType.toString().length() < 25) ? locType
								.toString().length() : 25))
						+ "...");

		// get sub branches
		// add children to tree, if user has attribute
		// alter level to get different bubble size
		JSONArray obj_children = new JSONArray();

		List<LOCrel> childs = locType.getChildren(LOCscheme.hasDefinedLevel);
		for (LOCrel child : childs) {
			if (user.hasAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type, date))
				obj_children.add(buildJSONTree(user,
						(LOCdefinition) child.getLocType(), level / 2, type,
						date));
		}
		childs = locType.getChildren(LOCscheme.hasExample);
		for (LOCrel child : childs) {
			if (user.hasAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type, date))
				obj_children.add(buildJSONTree(user,
						(LOCdefinition) child.getLocType(), level / 2.5, type,
						date));
		}

		childs = locType.getChildren(LOCscheme.hasPart);
		for (LOCrel child : childs) {
			if (user.hasAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type, date))
				obj_children.add(buildJSONTree(user,
						(LOCdefinition) child.getLocType(), level / 1.5, type,
						date));
		}

		// calculate bubble size, depending on number of children
		double size = level;
		if (!obj_children.isEmpty()) {
			size += (level * 0.2 * obj_children.size());
			if (size > (level * 2))
				size = (level * 2);
		}

		// add current attribute to bubble graph (special structure needed for
		// library)
		if (!type.contains("zoom")) {
			JSONObject inner_obj = new JSONObject();
			inner_obj.put("name", locType.toString());
			inner_obj.put(
					"shortname",
					locType.toString().substring(
							0,
							((locType.toString().length() < 5) ? locType
									.toString().length() : 5))
							+ "...");
			inner_obj.put("size", size);

			obj_children.add(inner_obj);
		} else
			// size needed for outer object only for zoom graph - contained in
			// inner obj for bubble cloud
			obj.put("size", (int) size);

		// add children if existing
		if (!obj_children.isEmpty())
			obj.put("children", obj_children);

		return obj;
	}

	/**
	 * get all top level parents of attribute - excluding LOCStructure (highest
	 * level in LOCfile)
	 * 
	 * @param jsonitems
	 * @param type
	 * @param date
	 * @return
	 */
	private List<LOCtypeLOC> getTopLevelList(
			List<CompetenceAndInterestAttribute> jsonitems, String type,
			Calendar date) {
		List<LOCtypeLOC> userTopLevel = new ArrayList<>();
		for (CompetenceAndInterestAttribute item : jsonitems) {
			LOCdefinition locDefinition = null;
			switch (type) {
			case "interest":
			case "interest_zoom":
				locDefinition = LOCUtil.getLOCdefinitionByInterestId(item
						.getLOCdefinition());
				break;
			case "noninterest":
			case "noninterest_zoom":
				locDefinition = LOCUtil.getLOCdefinitionByInterestId(item
						.getLOCdefinition());
				break;
			case "competence":
			case "competencepastone":
			case "competencepasttwo":
			case "competence_zoom":
				if (item.getDate() == null
						|| item.getDate().compareTo(date) <= 0)
					locDefinition = LOCUtil.getLOCdefinitionByCompetenceId(item
							.getLOCdefinition());
				break;
			}

			if (locDefinition != null) {
				List<LOCdefinition> topLevel = locDefinition
						.getTopLevelDefinitions();
				for (LOCdefinition def : topLevel)
					if (!userTopLevel.contains(def))
						userTopLevel.add(def);
			}
		}
		return userTopLevel;
	}

	/**
	 * get all top level parents of attribute - including LOCStructure (highest
	 * level in LOCfile)
	 * 
	 * @param jsonitems
	 * @param type
	 * @param date
	 * @return
	 */
	private List<LOCtypeLOC> getTopStructLevelList(
			List<CompetenceAndInterestAttribute> jsonitems, String type,
			Calendar date) {
		List<LOCtypeLOC> userTopLevel = new ArrayList<>();
		for (CompetenceAndInterestAttribute item : jsonitems) {
			LOCdefinition locDefinition = null;
			switch (type) {
			case "interest":
			case "interest_zoom":
				locDefinition = LOCUtil.getLOCdefinitionByInterestId(item
						.getLOCdefinition());
				break;
			case "noninterest":
			case "noninterest_zoom":
				locDefinition = LOCUtil.getLOCdefinitionByInterestId(item
						.getLOCdefinition());
				break;
			case "competence":
			case "competencepastone":
			case "competencepasttwo":
			case "competence_zoom":
				if (item.getDate() == null
						|| item.getDate().compareTo(date) <= 0)
					locDefinition = LOCUtil.getLOCdefinitionByCompetenceId(item
							.getLOCdefinition());

				break;
			}

			if (locDefinition != null) {
				List<LOCstructure> topLevel = locDefinition
						.getTopLevelStructures();
				for (LOCstructure struct : topLevel)
					if (!userTopLevel.contains(struct))
						userTopLevel.add(struct);
			}
		}

		return userTopLevel;
	}

	/**
	 * generate JSON String for tag cloud
	 * 
	 * @param user
	 * @param jsonitems
	 * @param type
	 * @param date
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected String generateJSONForTagCloud(User user,
			List<CompetenceAndInterestAttribute> jsonitems, String type,
			Calendar date) throws IOException {
		// get top levels - excluding structure
		List<LOCtypeLOC> userTopLevel = getTopLevelList(jsonitems, type, date);

		JSONArray children = new JSONArray();

		// build sub branches for tag cloud
		for (LOCtypeLOC locType : userTopLevel)
			children.addAll(buildJSONTreeTagCloud(user, locType, type,
					Calendar.getInstance()));

		if (userTopLevel.isEmpty())
			return "";

		return children.toJSONString();

	}

	/**
	 * build JSON tree for all child branches
	 * 
	 * @param user
	 * @param locType
	 * @param type
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONArray buildJSONTreeTagCloud(User user, LOCtypeLOC locType,
			String type, Calendar date) {
		JSONArray obj_children = new JSONArray();

		List<LOCrel> childs = locType.getChildren(LOCscheme.hasDefinedLevel);
		for (LOCrel child : childs) {
			if (user.hasAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type, date))
				obj_children.addAll(buildJSONTreeTagCloud(user,
						(LOCdefinition) child.getLocType(), type, date));
		}

		childs = locType.getChildren(LOCscheme.hasExample);
		for (LOCrel child : childs) {
			if (user.hasAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type, date))
				obj_children.addAll(buildJSONTreeTagCloud(user,
						(LOCdefinition) child.getLocType(), type, date));
		}

		childs = locType.getChildren(LOCscheme.hasPart);
		for (LOCrel child : childs) {
			if (user.hasAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type, date))
				obj_children.addAll(buildJSONTreeTagCloud(user,
						(LOCdefinition) child.getLocType(), type, date));
		}

		// calculate font size in relation to number of children
		int size = stdFontSize;
		if (!obj_children.isEmpty()) {
			size += (int) (7 * obj_children.size());
			if (size > 100)
				size = 100;
		}

		// add attribute itself to graph
		JSONObject inner_obj = new JSONObject();
		inner_obj.put("text", locType.toString());
		inner_obj.put("size", size);
		obj_children.add(inner_obj);

		return obj_children;
	}

	/**
	 * get array of needed colors, use available ones, if not enough generate
	 * random
	 * 
	 * @param colorCNT
	 * @return
	 */
	private String getColors(int colorCNT) {
		String colors = "[";
		Random random = new Random();
		Color color;

		int count = (colorCNT < avail_colors.length) ? avail_colors.length
				: colorCNT;

		for (int i = 0; i < count; i++) {
			if (i < avail_colors.length)
				colors += "'" + avail_colors[i] + "',";
			else {
				color = Color.getHSBColor(random.nextFloat(), 0.9f, 1.0f);
				colors += "'"
						+ String.format("#%02X%02X%02X", color.getRed(),
								color.getGreen(), color.getBlue()) + "',";
			}
		}
		colors = colors.substring(0, colors.length() - 1);
		colors += "]";

		return colors;
	}

	/**
	 * get connections for all users and all types (has worked with, likes to
	 * work with, doesn't like to work with)
	 * 
	 * @param user
	 *            - all connections are in relation with this user (start point)
	 * @return JSONObject - users: JSONArray containing all the connections -
	 *         level: int containing number of levels, used for slider
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getUsersForNetworkOverview(User user) {
		JSONObject networkGraph = new JSONObject();
		int maxLevel = 0;

		// sort all users in levels - level is distance to current user
		Map<Integer, List<User>> map = new HashMap<>();

		// for overview use all types
		map.put(0, new ArrayList<User>());
		map = getUserLevelsForNetwork(user, 1, map, "hasWorkedWith", true);

		map.put(0, new ArrayList<User>());
		map = getUserLevelsForNetwork(user, 1, map, "likesToWorkWith", true);

		map.put(0, new ArrayList<User>());
		map = getUserLevelsForNetwork(user, 1, map, "likesNotToWorkWith", true);

		JSONArray users = new JSONArray();

		if (map.size() <= 1)
			map = new HashMap<>();
		else {
			// remove empty levels and visited (level 0)
			map = clearMap(map);

			maxLevel = map.size();

			// get connections between the levels
			users.addAll(getConnections(user, map, "hasWorkedWith"));
			users.addAll(getConnections(user, map, "likesToWorkWith"));
			users.addAll(getConnections(user, map, "likesNotToWorkWith"));
		}

		networkGraph.put("users", users);
		networkGraph.put("level", maxLevel);

		return networkGraph;
	}

	/**
	 * get connections for all users and ONE type (has worked with, likes to
	 * work with, doesn't like to work with)
	 * 
	 * @param user
	 *            - all connections are in relation with this user (start point)
	 * @return JSONObject - users: JSONArray containing all the connections -
	 *         level: int containing number of levels, used for slider
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getUsersForNetwork(String type, User user,
			Map<Integer, List<User>> map) {
		JSONObject networkGraph = new JSONObject();
		JSONArray users = new JSONArray();
		int maxLevel = 0;

		map.put(0, new ArrayList<User>());
		map = getUserLevelsForNetwork(user, 1, map, type, false);

		if (map.size() <= 1)
			map = new HashMap<>();
		else {
			map = clearMap(map);
			maxLevel = map.size();
			users = getConnections(user, map, type);
		}

		networkGraph.put("users", users);
		networkGraph.put("level", maxLevel);

		return networkGraph;
	}

	/**
	 * delete level 0 - only temporarly needed for recursion delete empty
	 * levels, update keys - there must be no gap
	 * 
	 * @param map
	 * @return cleared map
	 */
	private Map<Integer, List<User>> clearMap(Map<Integer, List<User>> map) {
		Map<Integer, List<User>> clearedMap = new HashMap<>();
		int current_index = 1;
		for (Entry<Integer, List<User>> entry : map.entrySet()) {
			if (!entry.getValue().isEmpty() && entry.getKey() != 0) {
				clearedMap.put(current_index, entry.getValue());
				current_index++;
			}
		}

		return clearedMap;
	}

	/**
	 * build a map - key = level - value = list of users connected with user of
	 * level 1 in distance = level
	 * 
	 * level 1: current user level 2: direct connections ...
	 * 
	 * @param currentUser
	 * @param level
	 * @param map
	 * @param type
	 * @param overview
	 * @return
	 */
	private Map<Integer, List<User>> getUserLevelsForNetwork(User currentUser,
			int level, Map<Integer, List<User>> map, String type,
			boolean overview) {
		List<User> connectedUsers = new ArrayList<>();

		// level 0: used for recursion, all visited user are saved here
		List<User> visited = map.get(0);

		// get data for right type
		switch (type) {
		case "hasWorkedWith":
			connectedUsers = currentUser.getHasWorkedWith();
			break;
		case "likesNotToWorkWith":
			connectedUsers = currentUser.getLikesNotToWorkWith();
			break;
		case "likesToWorkWith":
			connectedUsers = currentUser.getLikesToWorkWith();
			break;
		}

		// if either visited or contained in higher level than current
		if (visited.contains(currentUser)
				|| (overview && higherLevelContains(map, currentUser, level))) {
			// check if current level is lower change level and visit again
			boolean move = false;
			for (Entry<Integer, List<User>> entry : map.entrySet()) {
				if (entry.getValue().contains(currentUser)
						&& entry.getKey() > level) {
					// remove entry from wrong level
					List<User> userList = map.get(entry.getKey());
					userList.remove(currentUser);
					map.put(entry.getKey(), userList);

					// visit node again
					move = true;
				}
			}

			// stop recursion when all users are visited in the lowest possible
			// level
			if (!move) {
				map.put(0, visited);
				return map;
			}
		}

		visited.add(currentUser);

		// add user to current level
		List<User> list = new ArrayList<>();
		if (map.containsKey(level))
			list = map.get(level);

		if (!overview
				|| (overview && !lowerLevelContains(map, currentUser, level)))
			list.add(currentUser);

		map.put(level, list);

		// increase level and traverse through connected user
		level++;
		for (User connectedUser : connectedUsers) {
			map.put(0, visited);
			map = getUserLevelsForNetwork(connectedUser, level, map, type,
					overview);
			visited = map.get(0);
		}

		// update visited of map
		map.put(0, visited);
		return map;
	}

	/**
	 * check if user is already contained in higher level - needed for overview:
	 * user has to be moved to lower level
	 * 
	 * @param map
	 * @param user
	 * @param level
	 * @return
	 */
	private boolean higherLevelContains(Map<Integer, List<User>> map,
			User user, int level) {
		for (Entry<Integer, List<User>> entry : map.entrySet())
			if (entry.getKey() > level && entry.getValue().contains(user))
				return true;

		return false;
	}

	/**
	 * don't add user to current level if it is already contained in lower level
	 * 
	 * @param map
	 * @param user
	 * @param level
	 * @return
	 */
	private boolean lowerLevelContains(Map<Integer, List<User>> map, User user,
			int level) {
		for (Entry<Integer, List<User>> entry : map.entrySet())
			if (entry.getKey() <= level && entry.getValue().contains(user)
					&& entry.getKey() > 0)
				return true;

		return false;
	}

	/**
	 * get all connection needed for Map<level, List<User>> for specific type
	 * 
	 * @param user
	 * @param map
	 * @param type
	 *            - needed for visualization, different types different css
	 *            style
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONArray getConnections(User user, Map<Integer, List<User>> map,
			String type) {
		JSONArray users = new JSONArray();

		// get direct connections for lower levels and currentlevel
		for (Entry<Integer, List<User>> entry : map.entrySet()) {
			for (User currentUser : entry.getValue()) {
				List<User> connectedUsers = null;
				int value = 0;

				// get right data for right type
				switch (type) {
				case "hasWorkedWith":
					connectedUsers = currentUser.getHasWorkedWith();
					break;
				case "likesToWorkWith":
					connectedUsers = currentUser.getLikesToWorkWith();
					value = 1;
					break;
				case "likesNotToWorkWith":
					connectedUsers = currentUser.getLikesNotToWorkWith();
					value = 2;
					break;
				}

				for (User connectedUser : connectedUsers) {
					// connection in same level, shown in lower level
					if (map.get(entry.getKey()).contains(connectedUser))
						users.add(getConnection(user, value,
								entry.getKey() - 1, currentUser, connectedUser));

					// connections to lower levels, shown in lower level
					int currentLevel = entry.getKey() - 1;
					while (currentLevel > 0) {
						if (map.get(currentLevel).contains(connectedUser))
							users.add(getConnection(user, value,
									entry.getKey() - 1, currentUser,
									connectedUser));
						currentLevel--;
					}

					// connections to higher level
					currentLevel = entry.getKey() + 1;
					while (currentLevel <= map.size()) {
						if (map.get(currentLevel).contains(connectedUser))
							users.add(getConnection(user, value,
									entry.getKey(), currentUser, connectedUser));
						currentLevel++;
					}
				}
			}
		}
		return users;
	}

	/**
	 * 
	 * @param user
	 * @param value
	 * @param level
	 * @param currentUser
	 * @param connectedUser
	 * @return JSONObject containing 
	 * 			- to user (source & target)
	 * 			- value (css style)
	 * 			- level (display depending on slider)
	 * 			- type (css style)
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getConnection(User user, int value, int level,
			User currentUser, User connectedUser) {
		JSONObject user_json = new JSONObject();
		user_json.put("source", currentUser.getName());
		user_json.put("target", connectedUser.getName());
		user_json.put("value", value);
		user_json.put("level", level);

		if (currentUser.equals(user) || connectedUser.equals(user))
			user_json.put("type", "direct");
		else
			user_json.put("type", "notdirect");

		return user_json;
	}
}
