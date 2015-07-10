package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.TaskManagerEJBLocal;
import manager.UserManagerEJBLocal;
import model.Task;
import model.User;

/**
 * This servlet provides any neccesary information for comparing users and tasks in the ui
 * 
 * @author Florian Jungwirth, Michaela Murauer
 */
@WebServlet("/CompareServlet")
@RequestScoped
public class CompareServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private UserManagerEJBLocal userManagerEJBLocal;
	@EJB
	private TaskManagerEJBLocal taskManagerEJBLocal;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("allUsers", userManagerEJBLocal.getAllUsers());
		request.setAttribute("allTasks", taskManagerEJBLocal.getAllTasks());

		ArrayList<User> users = new ArrayList<User>();
		
		if(request.getParameter("user[]") != null) {
			String[] userParameters = request.getParameterValues("user[]");
			for(String user : userParameters) {
				users.add(userManagerEJBLocal.getUserById(Integer.parseInt(user)));
			}
			request.setAttribute("compareUsers", users);
		}
		
		if(request.getParameter("task") != null && !request.getParameter("task").equals("-1")) {
			Task task = taskManagerEJBLocal.getTaskById(Integer.parseInt(request.getParameter("task")));
			request.setAttribute("compareTask", task);
		}
		
		request.getRequestDispatcher("compare.jsp").forward(request,
				response);
	}
}
