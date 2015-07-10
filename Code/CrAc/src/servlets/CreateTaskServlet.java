package servlets;

import inloc.LOCUtil;
import inloc.LOCstructure;

import java.io.IOException;
import java.math.BigDecimal;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import manager.IssuerManagerEJBLocal;
import model.Address;
import model.CompetenceAttribute;
import model.InterestAttribute;
import model.LocationAttribute;
import model.RangeAttribute;
import model.StringAttribute;
import model.Task;

/**
 * Servlet implementation class TaskManagerServlet
 */
@WebServlet("/TaskManagerServlet")
@RequestScoped
public class CreateTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IssuerManagerEJBLocal issuerManagerEJBLocal;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTaskServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
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

		for (LOCstructure structure : LOCUtil.getLocStructuresCompetencies())
			ServletUtil.buildXmlForm(structure, comp_element, BigDecimal.ZERO, "competence", false);

		// build xml tree for interests
		Document interests_doc = builder.newDocument();
		Element interests_element = interests_doc.createElement("ul");
		interests_doc.appendChild(interests_element);

		for (LOCstructure structure : LOCUtil.getLocStructuresInterests())
			ServletUtil.buildXmlForm(structure, interests_element, BigDecimal.ZERO,
					"interests", false);

		try {
			request.setAttribute("competencies", ServletUtil.prettyPrint(competencies_doc));
			request.setAttribute("interests", ServletUtil.prettyPrint(interests_doc));
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("task.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		Task task = new Task();

		task.addAttribute(new StringAttribute("name", request
				.getParameter("name")));
		task.addAttribute(new StringAttribute("description", request
				.getParameter("description")));

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
			task.addAttribute(new LocationAttribute("location", 0, 0, address));

		RangeAttribute age = new RangeAttribute();
		if (request.getParameter("from") != null
				&& !request.getParameter("from").isEmpty())
			age.setFrom(Integer.parseInt(request.getParameter("from")));
		if (request.getParameter("to") != null
				&& !request.getParameter("to").isEmpty())
			age.setTo(Integer.parseInt(request.getParameter("to")));

		if (request.getParameter("from") != null
				&& !request.getParameter("from").isEmpty()
				|| request.getParameter("to") != null
				&& !request.getParameter("to").isEmpty()) {
			age.setName("age");
			task.addAttribute(age);
		}

		for (Map.Entry<String, String[]> entry : request.getParameterMap()
				.entrySet()) {
			if (entry.getKey().startsWith("interest")) {
				if (LOCUtil.getLOCdefinitionByInterestId(entry.getValue()[0]) != null)
					task.addAttribute(new InterestAttribute("interest", entry
							.getValue()[0]));
			}
			if (entry.getKey().startsWith("competence")) {
				if (LOCUtil.getLOCdefinitionByCompetenceId(entry.getValue()[0]) != null)
					task.addAttribute(new CompetenceAttribute("competence",
							entry.getValue()[0]));
			}
		}

		if (issuerManagerEJBLocal.insert(task)) {
			message = "Task inserted";
		} else {
			message = "Error while inserting Task";
		}

		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

}
