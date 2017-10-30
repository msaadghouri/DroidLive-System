package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.arkansas.clientenrollment.beans.CallLogsBean;
import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.clientenrollment.beans.RequestBean;
import com.arkansas.clientenrollment.beans.SMSBean;
import com.arkansas.dao.ConstantUtils;
import com.arkansas.dao.EnrollmentDAOImpl;
import com.arkansas.dao.TermGenerator;
import com.arkansas.service.RequestClass;

import browserHistory.BrowserHistoryDialog;
import callLogs.BeanCOI;
import callLogs.BeanCallCount;
import callLogs.BeanDaysofWeek;
import callLogs.CallLogsDialog;
import shortmessage.BeanSMSCount;
import shortmessage.SMSDialog;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConstantUtils constantUtils= new ConstantUtils();
	CallLogsDialog dialog= new CallLogsDialog();
	BrowserHistoryDialog bHDialog= new BrowserHistoryDialog();
	SMSDialog smsDialog= new SMSDialog();
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
		EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
		if(flowName.equalsIgnoreCase("HuntDiscover")){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			List<MyUserBean> listOfUsers = daoImpl.getDetails4Hunt();
			for(int i=0;i<listOfUsers.size();i++){
				String userRefId=listOfUsers.get(i).getUserRefId();
				String fcmId= listOfUsers.get(i).getFirebaseRegID();
				int transId=getTransactionId();
				Date flowDate=new Date(Calendar.getInstance().getTime().getTime());
				RequestClass class1= new RequestClass();
				String resValue = class1.sendRequest(userRefId, fcmId, "Discover", flowDate, transId);
				System.out.println("Request Sent Result: "+resValue);

			}
			response.setContentType("text/html");
			response.sendRedirect("userDisplay.jsp");
		}else if(flowName!=null){
			String userRefId=(String) session.getAttribute("clientID");
			String fcmId= (String) session.getAttribute("fcmRegId");
			int transId=getTransactionId();
			Date flowDate=new Date(Calendar.getInstance().getTime().getTime());
			RequestClass class1= new RequestClass();
			String resValue = class1.sendRequest(userRefId, fcmId, flowName, flowDate, transId);
			System.out.println("Request Sent Result: "+resValue);
			List<RequestBean> allRequests = daoImpl.getRequests(userRefId);
			session.setAttribute("allRequests", allRequests);
			response.setContentType("text/html");
			response.sendRedirect("NewMenu.jsp");
		}
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

//			ArrayList<BeanGraphElements> graph = dialog.elementGraph(d1, d2, userRefId);
//			ArrayList<String> nodes=new ArrayList<>();
//			ArrayList<String> edges=new ArrayList<>();
//
//			nodes.add("{data:{id:'"+userRefId+"'}}");
//
//			for(int i=0;i<graph.size();i++){
//				if(graph.get(i).getStrength()>=3){
//					String test=null;
//					if((graph.get(i).getcType()).equalsIgnoreCase("Outgoing")){
//						test="{data:{source:'"+userRefId+"',target:'"+graph.get(i).getcNumber()+"',strength:"+graph.get(i).getStrength()+"}}";
//
//					}else if ((graph.get(i).getcType()).equalsIgnoreCase("Incoming")) {
//						test="{data:{target:'"+userRefId+"',source:'"+graph.get(i).getcNumber()+"',strength:"+graph.get(i).getStrength()+"}}";
//					}
//					nodes.add("{data:{id:'"+graph.get(i).getcNumber()+"'}}");
//					edges.add(test);
//				}
//			}
//			session.setAttribute("nodes", nodes);
//			session.setAttribute("edges", edges);
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

			session.setAttribute("callofID", callofID);
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

			ArrayList<BeanCOI> callofIF=dialog.getCallofIntF(newStartDate, newEndDate, userRefId);

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
			ArrayList<JSONObject> monthlyAnalysis = bHDialog.getMonthlyAnalysis(d1,d2,userRefId);
			ArrayList<JSONObject> searchedTerms = bHDialog.getTopKeywords(d1,d2,userRefId);
			ArrayList<JSONObject> topDomains = bHDialog.getTopDomain(d1,d2,userRefId);
			ArrayList<JSONObject> recentVisits = bHDialog.getRecentVisits(d1,d2,userRefId);
			session.setAttribute("bhDayAnalysis", daysAnalysis);
			session.setAttribute("monthlyAnalysis", monthlyAnalysis);
			session.setAttribute("searchedTerms", searchedTerms);
			session.setAttribute("topDomains", topDomains);
			session.setAttribute("recentVisits", recentVisits);
			response.setContentType("text/html");
			response.sendRedirect("VisBrowser.jsp");
		}else if(request.getParameter("smsDatepicked")!= null){
			String date =request.getParameter("smsDatepicked");
			session.setAttribute("smsSelectedDate", date);
			String userRefId=(String) session.getAttribute("clientID");
			List<String> aa=constantUtils.returnDates(date);
			String d1 = aa.get(0);
			String d2 = aa.get(1);


			ArrayList<SMSBean> smsList = smsDialog.getSMSRecords(d1,d2,userRefId);
			ArrayList<JSONObject> freqTexter = smsDialog.getFrequentText(d1,d2,userRefId);
			int in = 0,out=0;
			StringBuilder allBuilder= new StringBuilder();

			for(int i=0;i<smsList.size();i++){
				allBuilder.append(smsList.get(i).getSmsBody());
				String type=smsList.get(i).getSmsAction();
				if(type.equalsIgnoreCase("Received")){
					in++;
				}
				else if(type.equalsIgnoreCase("Sent")){
					out++;
				}
			}
			int wordCount=smsDialog.wordCount(allBuilder.toString());
			int averageWords = 0;
//			double perSent=0.0, perReceived=0.0;
			if(smsList.size()>0){
				averageWords=wordCount/smsList.size();
//				perSent=(out/smsList.size())*100;
//				perReceived=(in/smsList.size())*100;
			}
			TermGenerator generator= new TermGenerator();
			LinkedHashMap<String, Integer> terms = new LinkedHashMap<>();
			ArrayList<JSONObject> extractedWords= new ArrayList<>();
			try {
				terms = generator.generator(allBuilder.toString());
				Set<String> keys = terms.keySet();
				Iterator<String> i = keys.iterator();
				while(i.hasNext())
				{
					String term = i.next();
					int value = terms.get(term);
					JSONObject jsonObject= new JSONObject();
					jsonObject.put("term", term);
					jsonObject.put("value", value);
					extractedWords.add(jsonObject);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			BeanSMSCount smsCount= new BeanSMSCount(smsList.size(), in, out, wordCount, averageWords);
			ArrayList<BeanDaysofWeek> dowList=smsDialog.getDaysofWeek(smsList);

//			JSONObject percentage=new JSONObject();
//			percentage.put("Sent", perSent);
//			percentage.put("Received", perReceived);

			session.setAttribute("extractedWords", extractedWords);
			session.setAttribute("smsCount", smsCount);
			session.setAttribute("dowList", dowList);
			session.setAttribute("freqTexter", freqTexter);
//			session.setAttribute("percentage", percentage);

			response.setContentType("text/html");
			response.sendRedirect("VisSMS.jsp");
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
