package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.arkansas.clientenrollment.beans.CallLogsBean;
import com.arkansas.clientenrollment.beans.RequestBean;
import com.arkansas.dao.ConstantUtils;
import com.arkansas.dao.EnrollmentDAOImpl;
import com.arkansas.service.RequestClass;

import browserHistory.BrowserHistoryDialog;
import callLogs.BeanCOI;
import callLogs.BeanCallCount;
import callLogs.BeanDaysofWeek;
import callLogs.BeanGraphElements;
import callLogs.CallLogsDialog;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConstantUtils constantUtils= new ConstantUtils();
	CallLogsDialog dialog= new CallLogsDialog();
	BrowserHistoryDialog bHDialog= new BrowserHistoryDialog();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String flowName = request.getParameter("flowname").toString();
		if(flowName!=null){
			EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
			String userRefId=(String) session.getAttribute("clientID");
			String fcmId= (String) session.getAttribute("fcmRegId");
			int transId=getTransactionId();
			Date flowDate=new Date(Calendar.getInstance().getTime().getTime());
			RequestClass class1= new RequestClass();
			String resValue = class1.sendRequest(userRefId, fcmId, flowName, flowDate, transId);
			System.out.println("Request Sent Result: "+resValue);
			//			if(flowName.equalsIgnoreCase("Discover")){
			//				String resValue = class1.sendRequest(userRefId, fcmId, flowName, flowDate, transId);
			//				System.out.println("Request Sent Result: "+resValue);
			//			}else if (flowName.equalsIgnoreCase("BrowserHistory")) {
			//				
			//				String resValue = class1.sendRequest(userRefId, fcmId, flowName, flowDate, transId);
			//				System.out.println("Request Sent Result: "+resValue);
			//			}else if (flowName.equalsIgnoreCase("CallLogs")) {
			//				
			//				String resValue = class1.sendRequest(userRefId, fcmId, flowName, flowDate, transId);
			//				System.out.println("Request Sent Result: "+resValue);
			//			}
			List<RequestBean> allRequests = daoImpl.getRequests(userRefId);
			session.setAttribute("allRequests", allRequests);
		}
		response.setContentType("text/html");
		response.sendRedirect("NewMenu.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		if(request.getParameter("datepicked")!= null){
			String date =request.getParameter("datepicked");
			session.setAttribute("selectedDate", date);
			String userRefId=(String) session.getAttribute("clientID");
			List<String> aa=constantUtils.returnDates(date);
			String d1 = aa.get(0);
			String d2 = aa.get(1);

			ArrayList<CallLogsBean> logList= dialog.getCallRecords(d1, d2, userRefId);

			int in = 0,out=0,missed=0,inDur=0,outDur=0;

			for(int i=0;i<logList.size();i++){
				String type=logList.get(i).getCallType();
				if(type.equalsIgnoreCase("Incoming")){
					inDur+=logList.get(i).getCallDuration();
					in++;
				}else if(type.equalsIgnoreCase("Outgoing")){
					outDur+=logList.get(i).getCallDuration();
					out++;
				}else if(type.equalsIgnoreCase("Missed")){
					missed++;
				}
			}
			BeanCallCount count= new BeanCallCount(logList.size(), in, out, missed);

			JSONObject pieData= new JSONObject();
			pieData.put("inDur", inDur/60);
			pieData.put("outDur", outDur/60);

			ArrayList<BeanDaysofWeek> daysofWeeks= dialog.getDaysofWeek(logList);
			ArrayList<BeanCOI> callofInterest= dialog.getMonthlyAnalysis(d1, d2, userRefId);

			ArrayList<BeanGraphElements> graph = dialog.elementGraph(d1, d2, userRefId);
			ArrayList<String> nodes=new ArrayList<>();
			ArrayList<String> edges=new ArrayList<>();

			nodes.add("{data:{id:'"+userRefId+"'}}");

			for(int i=0;i<graph.size();i++){
				if(graph.get(i).getStrength()>=3){
					String test=null;
					if((graph.get(i).getcType()).equalsIgnoreCase("Outgoing")){
						test="{data:{source:'"+userRefId+"',target:'"+graph.get(i).getcNumber()+"',strength:"+graph.get(i).getStrength()+"}}";

					}else if ((graph.get(i).getcType()).equalsIgnoreCase("Incoming")) {
						test="{data:{target:'"+userRefId+"',source:'"+graph.get(i).getcNumber()+"',strength:"+graph.get(i).getStrength()+"}}";
					}
					nodes.add("{data:{id:'"+graph.get(i).getcNumber()+"'}}");
					edges.add(test);
				}
			}
			session.setAttribute("nodes", nodes);
			session.setAttribute("edges", edges);
			session.setAttribute("count", count);
			session.setAttribute("pieData", pieData);
			session.setAttribute("daysofWeeks", daysofWeeks);
			session.setAttribute("callofInterest", callofInterest);

			response.setContentType("text/html");
			response.sendRedirect("VisCallLogs.jsp");
		}else if(request.getParameter("data")!= null){
			String incomingDate = request.getParameter("data");
			incomingDate=incomingDate.substring(0, 15);
			DateFormat df = new SimpleDateFormat("EEE MMM dd yyyy"); 
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();
			java.util.Date date;
			String newStartDate = null;
			String newEndDate;
			try {
				date = df.parse(incomingDate);
				newStartDate = df1.format(date);
				c.setTime(df1.parse(newStartDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.add(Calendar.MONTH, 1);

			newEndDate = df1.format(c.getTime()); 	
			System.out.println("start date"+newStartDate);
			System.out.println("end date"+newEndDate);
			String userRefId=(String) session.getAttribute("clientID");

			ArrayList<BeanCOI> callofID=dialog.getCallofIntD(newStartDate, newEndDate, userRefId);
			//ArrayList<BeanCOI> callofIF=dialog.getCallofIntF(newStartDate, newEndDate, userRefId);

			session.setAttribute("callofID", callofID);
			//session.setAttribute("callofIF", callofIF);
			response.setContentType("text/html");
			response.sendRedirect("VisCallLogs.jsp");
		}
		else if(request.getParameter("data1")!= null){
			String incomingDate = request.getParameter("data1");
			incomingDate=incomingDate.substring(0, 15);
			DateFormat df = new SimpleDateFormat("EEE MMM dd yyyy"); 
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();
			java.util.Date date;
			String newStartDate = null;
			String newEndDate;
			try {
				date = df.parse(incomingDate);
				newStartDate = df1.format(date);
				c.setTime(df1.parse(newStartDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.add(Calendar.MONTH, 1);

			newEndDate = df1.format(c.getTime()); 	
			System.out.println("start date"+newStartDate);
			System.out.println("end date"+newEndDate);
			String userRefId=(String) session.getAttribute("clientID");

			//ArrayList<BeanCOI> callofID=dialog.getCallofIntD(newStartDate, newEndDate, userRefId);
			ArrayList<BeanCOI> callofIF=dialog.getCallofIntF(newStartDate, newEndDate, userRefId);

			//session.setAttribute("callofID", callofID);
			session.setAttribute("callofIF", callofIF);
			response.setContentType("text/html");
			response.sendRedirect("VisCallLogs.jsp");
		}else if(request.getParameter("bhDatePicked")!= null){
			String date =request.getParameter("bhDatePicked");
			session.setAttribute("bhSelectedDate", date);
			String userRefId=(String) session.getAttribute("clientID");
			List<String> aa=constantUtils.returnDates(date);
			String d1 = aa.get(0);
			String d2 = aa.get(1);

			ArrayList<BeanDaysofWeek> daysAnalysis = bHDialog.getDaysofWeek(d1,d2,userRefId);
			ArrayList<JSONObject> searchedTerms = bHDialog.getTopKeywords(d1,d2,userRefId);
			session.setAttribute("bhDayAnalysis", daysAnalysis);
			session.setAttribute("searchedTerms", searchedTerms);
			response.setContentType("text/html");
			response.sendRedirect("VisBrowser.jsp");
		}
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
