package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.RequestBean;
import com.arkansas.dao.EnrollmentDAOImpl;
import com.arkansas.service.RequestClass;

/**
 * Servlet implementation class DiscoveryServlet
 */
@WebServlet("/DiscoveryServlet")
public class DiscoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DiscoveryServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userRefId = request.getParameter("uname");
		HttpSession session = request.getSession();
		EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
		String fcmRegId=daoImpl.getFCMId(userRefId);
		List<RequestBean> allRequests = daoImpl.getRequests(userRefId);
		session.setAttribute("clientID", userRefId);
		session.setAttribute("fcmRegId", fcmRegId);
		session.setAttribute("allRequests", allRequests);
		if(session.getAttribute("disData")!=null)
			session.setAttribute("disData", null);
		response.setContentType("text/html");
		response.sendRedirect("NewFile.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		//		if((request.getParameter("discoveryButton"))!= null)
		//		{
		//			EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
		//			DiscoveryBean discoveredData=daoImpl.getDiscoveredData((String) session.getAttribute("clientID"));
		//			session.setAttribute("disData", discoveredData);
		//		}
		if((request.getParameter("discoverFlow"))!= null)
		{
			String userRefId=(String) session.getAttribute("clientID");
			String fcmId= (String) session.getAttribute("fcmRegId");
			int transId=getTransactionId();
			String flowName="Discover";
			Date flowDate=new Date(Calendar.getInstance().getTime().getTime());
			RequestClass class1= new RequestClass();
			String resValue = class1.sendRequest(userRefId, fcmId, flowName, flowDate, transId);
			System.out.println("Request Sent Result: "+resValue);
		}else{

			//			String transID = request.getParameter("data");
			int tID=Integer.parseInt(request.getParameter("data"));
			//			String type=(String) session.getAttribute("calScale");
			System.out.println(tID);
			EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
			DiscoveryBean discoveredData=daoImpl.getDiscoveredData((String) session.getAttribute("clientID"),tID);
			session.setAttribute("disData", discoveredData);
		}
		response.setContentType("text/html");
		response.sendRedirect("NewFile.jsp");
	}

	/**
	 * @return
	 */
	private int getTransactionId() {
		Random random= new Random();
		long randomVal = System.currentTimeMillis()+Math.abs(random.nextLong());
		int z= (int)(randomVal);
		return Math.abs(z);
	}

}
