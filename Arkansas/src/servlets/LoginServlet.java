package servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.dao.ConstantUtils;
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
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			if(email==null || password==null || email=="" || password==""){
				session.setAttribute("errorMessage", "Enter both fields");
				response.setContentType("text/html");
				response.sendRedirect("Index.jsp");
			}
			else if(loginVerify(email, password)){
				if(session.getAttribute("errorMessage")!=null)
					session.removeAttribute("errorMessage");

				EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
				List<MyUserBean> allUsers=daoImpl.getUsersList();
				session.setAttribute("userName", "USER NAME");
				session.setAttribute("userData", allUsers);
				response.setContentType("text/html");
				response.sendRedirect("userDisplay.jsp");
			}else{
				session.setAttribute("errorMessage", "Invalid E-mail/Password");
				response.setContentType("text/html");
				response.sendRedirect("Index.jsp");
			}

		}

	}

	/**
	 * @param username
	 * @param password
	 */
	private boolean loginVerify(String email, String password) {
		String emailPass = email + password;
		String mdvalue = md5Funct(emailPass);
		ConstantUtils utils= new ConstantUtils();
		try {
			String queryStr = "SELECT KEYMD5,EmailID FROM arkansas.AccountHolders where KEYMD5 = '"+mdvalue+"'";
			Connection conn = utils.getConn();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			if(rs.next())
			{
				if(rs.getString("KEYMD5").equals(mdvalue) && rs.getString("EmailID").equals(email))
				{
					rs.close();
					stmt.close();
					conn.close();
					return true;

				}
			}
			rs.close();
			stmt.close();
			conn.close();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;

	}
	private String md5Funct(String userNamePass) {
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(userNamePass.getBytes());
			byte byteData[] = md.digest();
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
