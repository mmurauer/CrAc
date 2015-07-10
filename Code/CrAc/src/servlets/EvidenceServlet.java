package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.AttributeManagerEJBLocal;
import manager.IssuerManagerEJBLocal;
import manager.TaskManagerEJBLocal;
import manager.UserManagerEJBLocal;
import model.Evidence;
import model.Issuer;
import model.SimpleIssuer;
import model.User;

/**
 * This servlet is used for storing evidences for user attributes
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
@WebServlet("/EvidenceServlet")
@RequestScoped
public class EvidenceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private UserManagerEJBLocal userManagerEJBLocal;
	@EJB
	private TaskManagerEJBLocal taskManagerEJBLocal;
	@EJB 
	private AttributeManagerEJBLocal attributeManagerEJBLocal;
	@EJB
	private IssuerManagerEJBLocal issuerManagerEJBLocal;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getParameter("user") != null) {
			User user = userManagerEJBLocal.getUserById(Integer.parseInt(request.getParameter("user")));
			request.setAttribute("user", user);
			request.setAttribute("userid", request.getParameter("user"));
		}
		
		request.setAttribute("allUsers", userManagerEJBLocal.getAllUsers());
		request.setAttribute("allTasks", taskManagerEJBLocal.getAllTasks());
		
		request.getRequestDispatcher("evidence.jsp").forward(request,
				response);
		
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("user") != null) {
			User user = userManagerEJBLocal.getUserById(Integer.parseInt(request.getParameter("user")));
			request.setAttribute("user", user);
			request.setAttribute("userid", request.getParameter("user"));
		}
	
		// user userManagerEJBLocal and taskManagerEJBLocal to get only Types of given class
		request.setAttribute("allUsers", userManagerEJBLocal.getAllUsers());
		request.setAttribute("allTasks", taskManagerEJBLocal.getAllTasks());
		
		String message = "Evidence added!";
		
		Issuer issuer = null;
		if(request.getParameter("issuertype").equals("simple")) {
			issuer = new SimpleIssuer(request.getParameter("issuer"));
		} else if(request.getParameter("issuertype").equals("user")) {
			issuer = userManagerEJBLocal.getUserById(Integer.parseInt(request.getParameter("issuer")));

		} else if(request.getParameter("issuertype").equals("task")) {
			issuer = taskManagerEJBLocal.getTaskById(Integer.parseInt(request.getParameter("issuer")));
		}
		
		Evidence e = new Evidence(request.getParameter("type"), (request.getParameter("date") != null && !request.getParameter("date").isEmpty()) ? ServletUtil.parseDateString(request.getParameter("date")) : null,
				(request.getParameter("expiredate") != null && !request.getParameter("expiredate").isEmpty()) ? ServletUtil.parseDateString(request.getParameter("expiredate")) : null, request.getParameter("value"), 
						issuer);
		
		// save evidence
		if (request.getParameter("attributes") != null) {
			for (String a : request.getParameterValues("attributes")) {
				// user attributeManagerEJBLocal to get Attribute out of persistence context
				attributeManagerEJBLocal.getAttributeById(Integer.parseInt(a)).addEvidence(e);
			}
		}
		
		// user issuer manager to save all types: task, user, attribute, ...
		issuerManagerEJBLocal.update();
		
		request.setAttribute("message", message);
		RequestDispatcher rd = request.getRequestDispatcher("evidence.jsp");
		rd.forward(request, response);
	}
}
