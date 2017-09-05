package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.RequestBean;
import com.arkansas.dao.EnrollmentDAOImpl;

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
		response.sendRedirect("NewMenu.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String transID = request.getParameter("data");
		String arr[] = transID.split(" ", 2);
		String flowName = arr[0].trim();   
		String iD = arr[1].trim();
		int tID = Integer.parseInt(iD);
		System.out.println(tID);
		EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
		if(session.getAttribute("disData")!=null){
			session.removeAttribute("disData");
		}if(session.getAttribute("hisData")!=null){
			session.removeAttribute("hisData");
		}if(session.getAttribute("usageData")!=null){
			session.removeAttribute("usageData");
		}
		if(flowName.equalsIgnoreCase("Discover")){
			DiscoveryBean discoveredData=daoImpl.getDiscoveredData((String) session.getAttribute("clientID"),tID);
			session.setAttribute("disData", discoveredData);		
		}else if (flowName.equalsIgnoreCase("GetAccounts")) {
			ArrayList<JSONObject> accounts=daoImpl.getDeviceAccounts((String) session.getAttribute("clientID"),tID);
			session.setAttribute("hisData", accounts);
		}else if (flowName.equalsIgnoreCase("UsageStats")) {
			String usageStats=daoImpl.getUsageData((String) session.getAttribute("clientID"),tID);
			session.setAttribute("usageData", usageStats.replaceAll(" ", "<br/>"));
		}
		response.setContentType("text/html");
		response.sendRedirect("NewMenu.jsp");
	}
}
