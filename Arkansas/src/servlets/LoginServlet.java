package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.dao.EnrollmentDAOImpl;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public LoginServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
		List<MyUserBean> allUsers=daoImpl.getUsersList();
		if(session.getAttribute("disData")!=null){
			session.removeAttribute("disData");
		}else if(session.getAttribute("hisData")!=null){
			session.removeAttribute("hisData");
		}
		session.setAttribute("userData", allUsers);
		response.setContentType("text/html");
		response.sendRedirect("userDisplay.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		if((request.getParameter("loginButton"))!= null)
		{
			EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
			List<MyUserBean> allUsers=daoImpl.getUsersList();
			session.setAttribute("userData", allUsers);
		}
		response.setContentType("text/html");
		response.sendRedirect("userDisplay.jsp");
	}

}
