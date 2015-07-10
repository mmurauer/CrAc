package servlets;

import inloc.LOCUtil;
import inloc.LOCstructure;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import manager.IssuerManagerEJBLocal;
import manager.UserManagerEJBLocal;
import model.Address;
import model.Attribute;
import model.AvailabilityAttribute;
import model.CompetenceAttribute;
import model.DateAttribute;
import model.InterestAttribute;
import model.LocationAttribute;
import model.NonInterestAttribute;
import model.Organization;
import model.StringAttribute;
import model.User;
import model.WorkAndEducationAttribute;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

/**
 * This servlet handles the requests for creating and editing user profiles
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
@WebServlet("/CreateUserServlet")
@RequestScoped
public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private UserManagerEJBLocal userManagerEJBLocal;
	@EJB
	private IssuerManagerEJBLocal issuerManagerEJBLocal;
	
	/**
	 * The get Method prepares all the necessary data that is needed for displaying the create/update form in the UI
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("userid") != null
				&& !request.getParameter("userid").equals("null")
				&& request.getParameter("delete") != null
				&& request.getParameter("delete").equals("true")) {

			User user = (User) issuerManagerEJBLocal.getIssuerById(Integer
					.parseInt(request.getParameter("userid")));
			
			try {
				String message = "";
				issuerManagerEJBLocal.delete(user);
				message = "User deleted";
				request.setAttribute("message", message);
			}
			catch (Exception e) {
				String error = "User can't be deleted because of foreign key constraints.";
				request.setAttribute("error", error);
			}

			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		} else {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			try {
				builder = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			// build xml tree for competencies
			Document competencies_doc = builder.newDocument();
			Element comp_element = competencies_doc.createElement("ul");
			competencies_doc.appendChild(comp_element);

			for (LOCstructure structure : LOCUtil
					.getLocStructuresCompetencies())
				ServletUtil.buildXmlForm(structure, comp_element,
						BigDecimal.ZERO, "competence", true);

			// build xml tree for interests
			Document interests_doc = builder.newDocument();
			Element interests_element = interests_doc.createElement("ul");
			interests_doc.appendChild(interests_element);

			for (LOCstructure structure : LOCUtil.getLocStructuresInterests())
				ServletUtil.buildXmlForm(structure, interests_element,
						BigDecimal.ZERO, "interests", false);

			// build xml tree for non interests
			Document noninterests_doc = builder.newDocument();
			Element noninterests_element = noninterests_doc.createElement("ul");
			noninterests_doc.appendChild(noninterests_element);

			for (LOCstructure structure : LOCUtil.getLocStructuresInterests())
				ServletUtil.buildXmlForm(structure, noninterests_element,
						BigDecimal.ZERO, "noninterests", false);
			try {
				request.setAttribute("competencies",
						ServletUtil.prettyPrint(competencies_doc));
				request.setAttribute("interests",
						ServletUtil.prettyPrint(interests_doc));
				request.setAttribute("noninterests",
						ServletUtil.prettyPrint(noninterests_doc));
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("allUsers", userManagerEJBLocal.getAllUsers());

			if (request.getParameter("userid") != null) {
				request.setAttribute("user", userManagerEJBLocal
						.getUserById(Integer.parseInt(request
								.getParameter("userid"))));
			}

			request.getRequestDispatcher("user.jsp").forward(request, response);
		}
	}

	/**
	 * The post method takes all the post data sent by the UI after submitting the create/edit form and
	 * executes all the steps for updating the database
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = "";

		User user = new User();
		
		if(request.getParameter("userid").equals("null") == false)
			user = (User) issuerManagerEJBLocal.getIssuerById(Integer.parseInt(request.getParameter("userid")));
		
		Map<String, String[]> params = request.getParameterMap();

		List<Attribute> attributes = new ArrayList<>();

		// add firstname as StringAttribute to user
		if (request.getParameter("firstname") != null
				&& !request.getParameter("firstname").isEmpty())
			attributes.add(new StringAttribute("firstname", request
					.getParameter("firstname")));

		// add lastname as StringAttribute to user
		if (request.getParameter("lastname") != null
				&& !request.getParameter("lastname").isEmpty())
			attributes.add(new StringAttribute("lastname", request
					.getParameter("lastname")));

		// add birthday as DateAttribute to user
		if (request.getParameter("birthday") != null
				&& !request.getParameter("birthday").isEmpty()) {
			attributes.add(new DateAttribute("birthday",
					ServletUtil.parseDateString(request.getParameter("birthday"))));
		}

		// add hometown as LocationAttribute to user
		Address address = new Address();

		if (request.getParameter("street") != null
				&& !request.getParameter("street").isEmpty())
			address.setStreet(request.getParameter("street"));
		if (request.getParameter("streetnumber") != null
				&& !request.getParameter("streetnumber").isEmpty())
			address.setStreetnumber(request.getParameter("streetnumber"));
		if (request.getParameter("city") != null
				&& !request.getParameter("city").isEmpty())
			address.setCity(request.getParameter("city"));
		if (request.getParameter("postalcode") != null
				&& !request.getParameter("postalcode").isEmpty())
			address.setPostalcode(request.getParameter("postalcode"));

		if (request.getParameter("city") != null
				&& !request.getParameter("city").isEmpty())
			attributes.add(new LocationAttribute("location", 0, 0, address));

		// add gender as StringAttribute to user
		if (request.getParameter("gender") != null
				&& !request.getParameter("gender").isEmpty())
			attributes.add(new StringAttribute("gender", request
					.getParameter("gender")));

		// add interest as InterestAttribute
		// add noninterests as InterestAttribute
		// add competencies as CompetenceAttribute
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			if (entry.getKey().startsWith("interest")) {
				if (LOCUtil.getLOCdefinitionByInterestId(entry.getValue()[0]) != null)
					attributes.add(new InterestAttribute("interest", entry
							.getValue()[0]));
			}
			if (entry.getKey().startsWith("competence")) {
				if (LOCUtil.getLOCdefinitionByCompetenceId(entry.getValue()[0]) != null) {

					Calendar date = null;

					if (request.getParameter("competence_"
							+ entry.getValue()[0] + "_date") == null
							|| request.getParameter(
									"competence_" + entry.getValue()[0]
											+ "_date").isEmpty())
						date = Calendar.getInstance();

					else
						date = ServletUtil.parseDateString(request
								.getParameter("competence_"
										+ entry.getValue()[0] + "_date"));

					Calendar expireDate = null;
					if (request.getParameter("competence_"
							+ entry.getValue()[0] + "_expireDate") != null
							&& !request.getParameter(
									"competence_" + entry.getValue()[0]
											+ "_expireDate").isEmpty())
						expireDate = ServletUtil.parseDateString(request
								.getParameter("competence_"
										+ entry.getValue()[0] + "_expireDate"));

					attributes.add(new CompetenceAttribute("competence",
							entry.getValue()[0], date, expireDate));
				}

			}
			if (entry.getKey().startsWith("noninterest")) {
				if (LOCUtil.getLOCdefinitionByInterestId(entry.getValue()[0]) != null)
					attributes.add(new NonInterestAttribute("noninterest", entry
						.getValue()[0]));

			}
			
			if (entry.getKey().startsWith("availability")) {
				JSONParser parser=new JSONParser();
				try {
					JSONObject obj = (JSONObject) parser.parse(entry.getValue()[0]);
					attributes.add(new AvailabilityAttribute("availability",ServletUtil.parseDateTimeString(obj.get("start").toString()),
							ServletUtil.parseDateTimeString(obj.get("end").toString())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		// add work or education as WorkAndEducationAttribute to user
		// in first prototype only four fieldsets available -> go through it and
		// insert if description is set
		for (int i = 1; params.containsKey("description-" + i); i++) {
			if (params.containsKey("description-" + i)
					&& params.get("description-" + i).length > 0
					&& !params.get("description-" + i)[0].isEmpty()) {
				WorkAndEducationAttribute we = new WorkAndEducationAttribute();
				we.setDescription(params.get("description-" + i)[0]);
				if (params.containsKey("begin-" + i)
						&& params.get("begin-" + i).length > 0
						&& !params.get("begin-" + i)[0].isEmpty()) {
					we.setBegin(ServletUtil.parseDateString(params.get("begin-" + i)[0]));
				}
				if (params.containsKey("end-" + i)
						&& params.get("end-" + i).length > 0
						&& !params.get("end-" + i)[0].isEmpty()) {
					we.setEnd(ServletUtil.parseDateString(params.get("end-" + i)[0]));
				}
				if (params.containsKey("organization-" + i)
						&& params.get("organization-" + i).length > 0
						&& !params.get("organization-" + i)[0].isEmpty())
					we.setOrganization(new Organization(params
							.get("organization-" + i)[0]));

				attributes.add(we);
			}
		}
		
		user.setAttributes(attributes);
		
		// check user relationships
		if (request.getParameter("hasworkedwith") != null) {
			for (String u : request.getParameterValues("hasworkedwith")) {
				user.addHasWorkedWith((User) issuerManagerEJBLocal.getIssuerById(Integer
						.parseInt(u)));
			}
		}
		if (request.getParameter("likestoworkwith") != null) {
			for (String u : request.getParameterValues("likestoworkwith")) {
				user.addLikesToWorkWith((User) issuerManagerEJBLocal.getIssuerById(Integer
						.parseInt(u)));
			}
		}
		if (request.getParameter("likesnottoworkwith") != null) {
			for (String u : request.getParameterValues("likesnottoworkwith")) {
				user.addLikesNotToWorkWith((User) issuerManagerEJBLocal.getIssuerById(Integer.parseInt(u)));
			}
		}
		
		if (request.getParameter("userid").equals("null") && issuerManagerEJBLocal.insert(user)) {
			message = "User inserted";
		} else if(request.getParameter("userid") != null) {
			message = "User updated";
		}
		else {
			message = "Error while inserting User";
		}
		request.setAttribute("message", message);

		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

}
