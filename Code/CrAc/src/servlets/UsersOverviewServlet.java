package servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.UserManagerEJBLocal;

/**
 * Servlet implementation class UsersOverviewServlet
 */
@WebServlet("/UsersOverviewServlet")
@RequestScoped
public class UsersOverviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserManagerEJBLocal userManagerEJBLocal;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersOverviewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("allUsers", userManagerEJBLocal.getAllUsers());
		request.getRequestDispatcher("users_overview.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("allUsers", userManagerEJBLocal.getAllUsers());
		request.getRequestDispatcher("users_overview.jsp").forward(request,
				response);
	}

}
