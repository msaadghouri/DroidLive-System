package com.arkansas.dao;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.arkansas.clientenrollment.beans.CallLogsBean;
import com.arkansas.clientenrollment.beans.ContactsBean;
import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.HistoryBean;
import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.clientenrollment.beans.RequestBean;
import com.arkansas.clientenrollment.beans.SMSBean;
import com.arkansas.clientenrollment.beans.ServerCLBean;
import com.arkansas.clientenrollment.beans.ServerDCBean;
import com.arkansas.clientenrollment.beans.ServerSMSBean;

public class EnrollmentDAOImpl implements IEnrollmentDAO{
	Connection conn=null;											
	PrintWriter pw=null;											
	PreparedStatement pstmt=null;
	Statement st=null;
	ResultSet rs=null;

	int status;
	String details=null;
	static String url=null;
	static String driver=null;

	static{
		try {
			String driverclass = "com.mysql.jdbc.Driver";
			String dbuser = "root";
			String dbpassword = "root";
			String dburl = "jdbc:mysql://localhost:3306/";
			String dataBaseName = "arkansas";


			url=dburl+dataBaseName+"?user="+dbuser+"&password="+dbpassword;
			driver=driverclass;
			Class.forName(driver);

		}  
		catch (ClassNotFoundException e) {

			e.printStackTrace();
		}		
	}

	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#getUsersList()
	 */
	@Override
	public List<MyUserBean> getUsersList() {
		List<MyUserBean> allUserList= new ArrayList<>();
		try {
			conn=DriverManager.getConnection(url);	
			String queryStr="Select * from User_Table";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String userRefId = rset.getString("UserRefId");
				String userName = rset.getString("UserName");
				String fullName = rset.getString("FullName");
				Date firstLogOn=rset.getDate("FirstLogOn");
				Date lastLogOn=rset.getDate("LastLogOn");
				String homeDir = rset.getString("HomeDirectory");
				String firebaseRegId = rset.getString("FCMRegId");
				MyUserBean bean= new MyUserBean(userRefId, userName, fullName, firstLogOn, lastLogOn, homeDir,firebaseRegId);
				allUserList.add(bean);
			}
			rset.close();
			stmt.close();
			conn.close();
			return allUserList;
		} catch (SQLException ex) {

			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#getDiscoveredData(java.lang.String)
	 */
	@Override
	public DiscoveryBean getDiscoveredData(String clientRefID, int transID) {
		DiscoveryBean discoveryBean = new DiscoveryBean();

		try {
			conn=DriverManager.getConnection(url);	
			String queryStr="Select * from DiscoveryTable where TransactionID = '"+transID+"' and UserRefId='"+ clientRefID +"'";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				String system = rset.getString("System");
				String node = rset.getString("Node");
				String release = rset.getString("ReleaseForm");
				String  version= rset.getString("Version");
				String  machine= rset.getString("Machine");
				String  kernel= rset.getString("Kernel");
				String  fQDN= rset.getString("Fqdn");
				String  installDate= rset.getString("InstallDate");
				String  clientName= rset.getString("ClientName");
				String  clientVersion= rset.getString("ClientVersion");
				Date buildTime=rset.getDate("BuildTime");
				String  clientDescription= rset.getString("ClientDescription");
				String  macAddress= rset.getString("MacAddress");
				String  ipv4= rset.getString("Ipv4");
				String  ipv6= rset.getString("Ipv6");
				Date createdDate=rset.getDate("CreatedDate");

				discoveryBean= new DiscoveryBean(system, node, release, version, machine, kernel, fQDN, installDate, 
						clientName, clientVersion, clientDescription, buildTime, macAddress, ipv4, ipv6, createdDate);
				System.out.println(discoveryBean);
			}
			rset.close();
			stmt.close();
			conn.close();
			return discoveryBean;
		} catch (SQLException ex) {

			return null;
		}
	}

	public String getFCMId(String userRefId) {
		String fcmID= null;
		try {
			conn=DriverManager.getConnection(url);	
			String queryStr="SELECT FCMRegId FROM arkansas.User_Table where UserRefId = '"+userRefId+"'";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				fcmID = rset.getString("FCMRegId");
			}
			rset.close();
			stmt.close();
			conn.close();
			return fcmID;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<RequestBean> getRequests(String userRefId) {
		List<RequestBean> allReqList= new ArrayList<>();
		try {
			conn=DriverManager.getConnection(url);	
			String queryStr="Select * from RequestTable where UserRefId = '"+userRefId+"'";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				int tranactionId = rset.getInt("TransactionID");
				String flowName = rset.getString("FlowName");
				Date flowDate=rset.getDate("FlowDate");
				String reqStatus = rset.getString("RequestStatus");
				RequestBean bean= new RequestBean(tranactionId, flowName, flowDate, reqStatus);
				allReqList.add(bean);
			}
			rset.close();
			stmt.close();
			conn.close();
			return allReqList;
		} catch (SQLException ex) {

			return null;
		}
	}

	public boolean submitRequest(String userRefId,String firebaseRegID, String flowName, Date flowDate, int transactionID, String status) {
		System.out.println("Submitting Request");
		String agentQuery = "insert into RequestTable (TransactionID,UserRefId,FCMRegId,FlowName,FlowDate,RequestStatus) values(?,?,?,?,?,?)";

		try{
			conn=DriverManager.getConnection(url);	
			PreparedStatement pstmt = conn.prepareStatement(agentQuery);
			pstmt.setInt(1, transactionID);
			pstmt.setString(2, userRefId);
			pstmt.setString(3, firebaseRegID);
			pstmt.setString(4, flowName);
			pstmt.setDate(5,flowDate);
			pstmt.setString(6, status);

			pstmt.execute();
			pstmt.close();
			conn.close();
			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	//	public static void main(String args[]){
	//		EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
	//		daoImpl.getUsersList();
	//	}


	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#submitMyUsers(java.lang.String, com.arkansas.clientenrollment.beans.MyUserBean)
	 */
	@Override
	public boolean submitMyUsers(String userName, MyUserBean myUserBean) {
		boolean flag=false;
		String queryStr = "insert into User_Table (UserRefId,UserName,FullName,FirstLogOn,LastLogOn,HomeDirectory,FCMRegId) values(?,?,?,?,?,?,?)";
		try{

			conn=DriverManager.getConnection(url);	

			pstmt=conn.prepareStatement(queryStr);	
			pstmt.setString(1,myUserBean.getUserRefId());
			pstmt.setString(2, myUserBean.getUserName());
			pstmt.setString(3,myUserBean.getFullName());
			pstmt.setDate(4, myUserBean.getFirstLogOn());
			pstmt.setDate(5, myUserBean.getLastLogOn());
			pstmt.setString(6, myUserBean.getHomeDir());
			pstmt.setString(7, myUserBean.getFirebaseRegID());
			int uc=pstmt.executeUpdate();			

			if(uc!=0){
				flag=true;
			}
			conn.close();			
		}
		catch(SQLException e){		
			e.printStackTrace();
		}	
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#submitDiscovery(java.lang.String, com.arkansas.clientenrollment.beans.MyAgentInfoBean, com.arkansas.clientenrollment.beans.MyInterfaceBean, com.arkansas.clientenrollment.beans.MyOsInfoBean)
	 */
	@Override
	public boolean submitDiscovery(String userRefId, DiscoveryBean discoveryBean) {


		String subData = "insert into DiscoveryTable (UserRefId,System,Node,ReleaseForm,Version,Machine,Kernel,Fqdn,InstallDate,ClientName,ClientVersion,ClientDescription,BuildTime,MacAddress,Ipv4,Ipv6,CreatedDate,TransactionID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try{
			conn=DriverManager.getConnection(url);	
			PreparedStatement pstmt = conn.prepareStatement(subData);
			pstmt.setString(1, discoveryBean.getUserRefId());
			pstmt.setString(2, discoveryBean.getSystem());
			pstmt.setString(3, discoveryBean.getNode());
			pstmt.setString(4, discoveryBean.getRelease());
			pstmt.setString(5, discoveryBean.getVersion());
			pstmt.setString(6, discoveryBean.getMachine());
			pstmt.setString(7, discoveryBean.getKernel());
			pstmt.setString(8, discoveryBean.getFQDN());
			pstmt.setString(9,discoveryBean.getInstallDate());
			pstmt.setString(10, discoveryBean.getClientName());
			pstmt.setString(11, discoveryBean.getClientVersion());
			pstmt.setString(12, discoveryBean.getClientDescription());
			pstmt.setDate(13,discoveryBean.getBuildTime());	
			pstmt.setString(14, discoveryBean.getMacAddress());
			pstmt.setString(15, discoveryBean.getIpv4());
			pstmt.setString(16, discoveryBean.getIpv6());
			pstmt.setDate(17,discoveryBean.getCreatedDate());
			pstmt.setInt(18, discoveryBean.getTransactionId());
			pstmt.execute();

			String updateStatus="UPDATE RequestTable SET RequestStatus = 'SUCCESS' WHERE TransactionID = '"+discoveryBean.getTransactionId()+"' and UserRefId = '"+discoveryBean.getUserRefId()+"'";
			pstmt=conn.prepareStatement(updateStatus);
			pstmt.execute();
			pstmt.close();
			conn.close();
			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	//	/* (non-Javadoc)
	//	 * @see com.arkansas.dao.IEnrollmentDAO#submitHistory(java.lang.String, com.arkansas.clientenrollment.beans.HistoryBean)
	//	 */
	//	@Override
	//	public boolean submitHistory(String userRefId, HistoryBean HistoryBean) {
	//
	//		String subData = "insert into HistoryTable (UserRefId,BrowserHistory,CreatedDate,TransactionID) values(?,?,?,?)";
	//		System.out.println(HistoryBean);
	//		try{
	//			conn=DriverManager.getConnection(url);	
	//			PreparedStatement pstmt = conn.prepareStatement(subData);
	//			pstmt.setString(1, HistoryBean.getUserRefId());
	//			pstmt.setString(2, HistoryBean.getBrowserHistory());
	//			pstmt.setDate(3,HistoryBean.getCreatedDate());
	//			pstmt.setInt(4, HistoryBean.getTransactionId());
	//			pstmt.execute();
	//
	//			String updateStatus="UPDATE RequestTable SET RequestStatus = 'SUCCESS' WHERE TransactionID = '"+HistoryBean.getTransactionId()+"' and UserRefId = '"+HistoryBean.getUserRefId()+"'";
	//			pstmt=conn.prepareStatement(updateStatus);
	//			pstmt.execute();
	//			pstmt.close();
	//			conn.close();
	//			return true;
	//		}catch(SQLException e)
	//		{
	//			e.printStackTrace();
	//			return false;
	//		}
	//	}

	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#submitHistory(java.lang.String, com.arkansas.clientenrollment.beans.HistoryBean)
	 */
	@Override
	public boolean submitHistory(String userRefId, HistoryBean HistoryBean) {

		String subData = "insert into BrowserTable (UserRefId,URLName,SearchedDate,DomainName,SearchedText,CreatedDate,TransactionID) values(?,?,?,?,?,?,?)";
		System.out.println(HistoryBean);
		ArrayList<JSONObject> list = HistoryBean.getBrowserHistory();
		try{
			conn=DriverManager.getConnection(url);	
			PreparedStatement pstmt = conn.prepareStatement(subData);
			for(int i=0;i<list.size();i++){

				pstmt.setString(1, HistoryBean.getUserRefId());
				JSONObject jsonObject= (JSONObject) list.get(i);
				String url =(String) jsonObject.get("url");
				pstmt.setString(2, url);
				String date =(String) jsonObject.get("date");
				pstmt.setString(3, date);
				URI uri= new URI(url);
				String domain= uri.getHost();
				pstmt.setString(4, domain);
				String searchedText=null;
				if(url.contains("search")){
					searchedText=(url.substring(url.indexOf("?q=")+3, url.indexOf("&oq"))).replace("+", " ");
					pstmt.setString(5, searchedText);
				}else{
					pstmt.setString(5, searchedText);
				}
				pstmt.setDate(6,HistoryBean.getCreatedDate());
				pstmt.setInt(7, HistoryBean.getTransactionId());
				pstmt.addBatch();
			}

			pstmt.executeBatch();

			String updateStatus="UPDATE RequestTable SET RequestStatus = 'SUCCESS' WHERE TransactionID = '"+HistoryBean.getTransactionId()+"' and UserRefId = '"+HistoryBean.getUserRefId()+"'";
			pstmt=conn.prepareStatement(updateStatus);
			pstmt.execute();
			pstmt.close();
			conn.close();
			return true;
		}catch(SQLException | URISyntaxException e)
		{
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * @param clientRefID
	 * @param tID
	 * @return
	 */
	public String getHistoryData(String clientRefID, int tID) {
		String browserhistory=null;

		try {
			conn=DriverManager.getConnection(url);	
			String queryStr="Select Browserhistory from HistoryTable where TransactionID = '"+tID+"' and UserRefId='"+ clientRefID +"'";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(queryStr);
			while (rset.next())
			{
				browserhistory = rset.getString("Browserhistory");

			}
			rset.close();
			stmt.close();
			conn.close();
			return browserhistory;
		} catch (SQLException ex) {

			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#submitCallLogs(java.lang.String, com.arkansas.clientenrollment.beans.ServerCLBean)
	 */
	@Override
	public boolean submitCallLogs(String userRefId, ServerCLBean sclBean) {
		String subData = "insert into CallLogsTable (UserRefId,CallLogID,ContactName,ContactNumber,CallDuration,CallDate,CallType,CountryISO,CreatedDate,TransactionID) values(?,?,?,?,?,?,?,?,?,?)";
		System.out.println(sclBean);
		List<CallLogsBean> list = sclBean.getLogsBean();
		try{
			conn=DriverManager.getConnection(url);	
			PreparedStatement pstmt = conn.prepareStatement(subData);
			for (CallLogsBean a : list) {
				pstmt.setString(1, sclBean.getUserRefId());
				pstmt.setInt(2,a.getCallId());
				pstmt.setString(3, a.getContactName());
				pstmt.setString(4, a.getContactNumber());
				pstmt.setInt(5,a.getCallDuration());
				pstmt.setDate(6,a.getCallDate());
				pstmt.setString(7, a.getCallType());
				pstmt.setString(8, a.getCountryISO());
				pstmt.setDate(9,sclBean.getCreatedDate());
				pstmt.setInt(10, sclBean.getTransactionId());
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			String updateStatus="UPDATE RequestTable SET RequestStatus = 'SUCCESS' WHERE TransactionID = '"+sclBean.getTransactionId()+"' and UserRefId = '"+sclBean.getUserRefId()+"'";
			pstmt=conn.prepareStatement(updateStatus);
			pstmt.execute();
			pstmt.close();
			conn.close();
			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#submitContacts(java.lang.String, com.arkansas.clientenrollment.beans.ServerCLBean)
	 */
	@Override
	public boolean submitContacts(String userRefId, ServerDCBean sdcBean) {
		String subData = "insert into ContactsTable (UserRefId,ContactID,PhoneName,PhoneNumber,CreatedDate,TransactionID) values(?,?,?,?,?,?)";
		System.out.println(sdcBean);
		List<ContactsBean> list = sdcBean.getcontactsBean();
		try{
			conn=DriverManager.getConnection(url);	
			PreparedStatement pstmt = conn.prepareStatement(subData);
			for (ContactsBean a : list) {
				pstmt.setString(1, sdcBean.getUserRefId());
				pstmt.setInt(2,a.getContactId());
				pstmt.setString(3, a.getPhoneName());
				pstmt.setString(4, a.getPhoneNumber());
				pstmt.setDate(5,sdcBean.getCreatedDate());
				pstmt.setInt(6, sdcBean.getTransactionId());
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			String updateStatus="UPDATE RequestTable SET RequestStatus = 'SUCCESS' WHERE TransactionID = '"+sdcBean.getTransactionId()+"' and UserRefId = '"+sdcBean.getUserRefId()+"'";
			pstmt=conn.prepareStatement(updateStatus);
			pstmt.execute();
			pstmt.close();
			conn.close();
			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.arkansas.dao.IEnrollmentDAO#submitSMS(java.lang.String, com.arkansas.clientenrollment.beans.ServerCLBean)
	 */
	@Override
	public boolean submitSMS(String userRefId, ServerSMSBean smsBean) {
		String subData = "insert into SMSTable (UserRefId,SMSID,SMSAddress,SMSDate,SMSAction,SMSBody,CreatedDate,TransactionID) values(?,?,?,?,?,?,?,?)";
		System.out.println(smsBean);
		List<SMSBean> list = smsBean.getSmsBean();
		try{
			conn=DriverManager.getConnection(url);	
			PreparedStatement pstmt = conn.prepareStatement(subData);
			for (SMSBean a : list) {
				pstmt.setString(1, smsBean.getUserRefId());
				pstmt.setInt(2,a.getSmsID());
				pstmt.setString(3, a.getSmsAddress());
				pstmt.setDate(4,a.getSmsDate());
				pstmt.setString(5, a.getSmsAction());
				pstmt.setString(6, a.getSmsBody());
				pstmt.setDate(7,smsBean.getCreatedDate());
				pstmt.setInt(8, smsBean.getTransactionId());
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			String updateStatus="UPDATE RequestTable SET RequestStatus = 'SUCCESS' WHERE TransactionID = '"+smsBean.getTransactionId()+"' and UserRefId = '"+smsBean.getUserRefId()+"'";
			pstmt=conn.prepareStatement(updateStatus);
			pstmt.execute();
			pstmt.close();
			conn.close();
			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	//
	//		public static void main(String args[]){
	//			EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
	//			DiscoveryBean a = daoImpl.getHistoryData("C.352991068681083", tID);
	//			System.out.println(a);
	//		}

}
