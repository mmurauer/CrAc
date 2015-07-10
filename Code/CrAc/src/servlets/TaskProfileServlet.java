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
import java.util.List;
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

import manager.TaskManagerEJBLocal;
import model.CompetenceAndInterestAttribute;
import model.Task;

/**
 * Servlet implementation class UserProfileServlet create all graph data for
 * task profile
 * 
 * @author Florian Jungwirth, Michaela Murauer
 * 
 */
@WebServlet("/TaskProfileServlet")
@RequestScoped
public class TaskProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private TaskManagerEJBLocal taskManagerEJBLocal;

	// available colors, used in profile vizualisation
	private String[] avail_colors = { "#2384C4", "#404040", "#7FDE26",
			"#78061B", "#067863", "#FF7300", "#FADF2F", "#8A3A06", "#0E5719",
			"#5A076B", "#DB0B7A", "#0B0B3B", "#F22222", "#451D03", "#F6FF00" };

	// font size for tag cloud
	private final int stdFontSize = 15;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskProfileServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int taskid = Integer.parseInt(request.getParameter("taskid"));

		Task task = taskManagerEJBLocal.getTaskById(taskid);
		List<CompetenceAndInterestAttribute> competencies = task
				.getCompetencies();
		List<CompetenceAndInterestAttribute> interests = task.getInterests();

		String interestsForTagCloud = "";
		if (interests != null) {
			interestsForTagCloud = this.generateJSONForTagCloud(task,
					interests, "interest", Calendar.getInstance());
			this.generateJSON(task, interests, "interest",
					Calendar.getInstance());
			this.generateJSON(task, interests, "interest_zoom",
					Calendar.getInstance());
		}

		boolean hasCompetencies = false;

		// count how many different colors are needed, if available color are
		// not enough, generate random colors
		int colorCNT = 0;
		int currentTopLevelCNT = 0;
		String competenciesForTagCloud = "";
		if (competencies != null) {
			// generate JSON String for tag cloud
			competenciesForTagCloud = this.generateJSONForTagCloud(task,
					competencies, "competence", Calendar.getInstance());

			// generate JSON file for bubble graph
			currentTopLevelCNT = this.generateJSON(task, competencies,
					"competence", Calendar.getInstance());
			if (currentTopLevelCNT > 0)
				hasCompetencies = true;
			if (currentTopLevelCNT > colorCNT)
				colorCNT = currentTopLevelCNT;

			// generate JSON file for zoom cloud
			this.generateJSON(task, competencies, "competence_zoom",
					Calendar.getInstance());
		}

		// get colors as string - available colors + additional generated when
		// needed
		String colors = this.getColors(colorCNT);

		request.setAttribute("task", task);
		request.setAttribute("interestsForTagCloud", interestsForTagCloud);
		request.setAttribute("competenciesForTagCloud", competenciesForTagCloud);
		request.setAttribute("hasCompetencies", hasCompetencies);
		request.setAttribute("colors", colors);
		request.setAttribute("colorCNT", colorCNT);
		request.getRequestDispatcher("task_profile.jsp").forward(request,
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
	 * generate a task specific json file for visualizing
	 * competencies/interests/noninterests for two different graphs: bubble
	 * cloud and zoom cloud
	 * 
	 * @param task
	 *            - current task
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
	private int generateJSON(Task task,
			List<CompetenceAndInterestAttribute> jsonitems, String type,
			Calendar date) throws IOException {

		// 1. get TopLevel as you need them to display the graph
		// 2. get all gained sub-attributes
		// (competencies/interests/noninterests) recursive

		List<LOCtypeLOC> taskTopLevel = new ArrayList<>();

		// LOCStructure contained for competencies, as LOCfiles have different
		// structure
		switch (type) {
		case "competence":
		case "competencepastone":
		case "competencepasttwo":
		case "competence_zoom":
			taskTopLevel = getTopStructLevelList(jsonitems, type, date);
			break;
		case "interest":
		case "interest_zoom":
		case "noninterest":
		case "noninterest_zoom":
			taskTopLevel = getTopLevelList(jsonitems, type, date);
			break;
		}

		String name = type + "flare";

		// generate JSON Objects for graph visualitazion
		JSONObject root = new JSONObject();
		root.put("name", name);
		JSONArray children = new JSONArray();

		// build subtrees for all top levels
		for (LOCtypeLOC locType : taskTopLevel) {
			children.add(buildJSONTree(task, locType, 3000, type, date));
		}

		if (!children.isEmpty())
			root.put("children", children);

		// create and write file
		FileWriter file = new FileWriter(this.getServletContext().getRealPath(
				"profile_libs/" + name + task.getID() + ".json"));
		try {
			file.write(root.toJSONString());

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			file.flush();
			file.close();
		}

		// return amount of top levels = needed colors
		return taskTopLevel.size();
	}

	/**
	 * build JSON tree for all child branches
	 * 
	 * @param task
	 * @param locType
	 * @param level
	 *            - used for font size
	 * @param type
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONObject buildJSONTree(Task task, LOCtypeLOC locType,
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
			if (task.containsAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type))
				obj_children.add(buildJSONTree(task,
						(LOCdefinition) child.getLocType(), level / 2, type,
						date));

		}
		childs = locType.getChildren(LOCscheme.hasExample);
		for (LOCrel child : childs) {
			if (task.containsAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type))
				obj_children.add(buildJSONTree(task,
						(LOCdefinition) child.getLocType(), level / 2.5, type,
						date));
		}

		childs = locType.getChildren(LOCscheme.hasPart);
		for (LOCrel child : childs) {
			if (task.containsAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type))
				obj_children.add(buildJSONTree(task,
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
		} else {
			// size needed for outer object only for zoom graph - contained in
			// inner obj for bubble cloud
			obj.put("size", (int) size);
		}

		// add children if existing
		if (!obj_children.isEmpty()) {
			obj.put("children", obj_children);
		}
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
						|| item.getDate().compareTo(date) <= 0) {
					locDefinition = LOCUtil.getLOCdefinitionByCompetenceId(item
							.getLOCdefinition());
				}
				break;
			}

			if (locDefinition != null) {
				List<LOCdefinition> topLevel = locDefinition
						.getTopLevelDefinitions();
				for (LOCdefinition def : topLevel) {
					if (!userTopLevel.contains(def)) {
						userTopLevel.add(def);
					}
				}
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
						|| item.getDate().compareTo(date) <= 0) {
					locDefinition = LOCUtil.getLOCdefinitionByCompetenceId(item
							.getLOCdefinition());
				}
				break;
			}

			if (locDefinition != null) {
				List<LOCstructure> topLevel = locDefinition
						.getTopLevelStructures();
				for (LOCstructure struct : topLevel) {
					if (!userTopLevel.contains(struct)) {
						userTopLevel.add(struct);
					}
				}
			}
		}

		return userTopLevel;
	}

	/**
	 * generate JSON String for tag cloud
	 * 
	 * @param task
	 * @param jsonitems
	 * @param type
	 * @param date
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected String generateJSONForTagCloud(Task task,
			List<CompetenceAndInterestAttribute> jsonitems, String type,
			Calendar date) throws IOException {
		// get top levels - excluding structure
		List<LOCtypeLOC> taskTopLevel = getTopLevelList(jsonitems, type, date);

		JSONArray children = new JSONArray();

		// build sub branches for tag cloud
		for (LOCtypeLOC locType : taskTopLevel) {
			children.addAll(buildJSONTreeTagCloud(task, locType, type,
					Calendar.getInstance()));
		}

		if (taskTopLevel.isEmpty())
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
	private JSONArray buildJSONTreeTagCloud(Task task, LOCtypeLOC locType,
			String type, Calendar date) {
		JSONArray obj_children = new JSONArray();

		List<LOCrel> childs = locType.getChildren(LOCscheme.hasDefinedLevel);
		for (LOCrel child : childs) {
			if (task.containsAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type))
				obj_children.addAll(buildJSONTreeTagCloud(task,
						(LOCdefinition) child.getLocType(), type, date));

		}
		childs = locType.getChildren(LOCscheme.hasExample);
		for (LOCrel child : childs) {
			if (task.containsAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type))
				obj_children.addAll(buildJSONTreeTagCloud(task,
						(LOCdefinition) child.getLocType(), type, date));
		}

		childs = locType.getChildren(LOCscheme.hasPart);
		for (LOCrel child : childs) {
			if (task.containsAttributeForBubbleGraph(
					(LOCdefinition) child.getLocType(), type))
				obj_children.addAll(buildJSONTreeTagCloud(task,
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

}
