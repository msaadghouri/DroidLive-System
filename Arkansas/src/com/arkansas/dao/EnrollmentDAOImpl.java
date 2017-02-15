package com.arkansas.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.arkansas.clientenrollment.beans.AgentInfo;
import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.Interfaces;
import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.clientenrollment.beans.OsInfo;
import com.arkansas.clientenrollment.beans.RequestBean;
import com.arkansas.clientenrollment.beans.Users;

public class EnrollmentDAOImpl implements IEnrollmentDAO{
	AgentInfo AgentInfo = new AgentInfo();
	Interfaces Interfaces = new Interfaces();
	OsInfo OsInfo = new OsInfo();
	Users Users = new Users();


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
			//Properties prop = new Properties();		
			//aspak prop.load(new FileInputStream("C:\\TEMP\\config.properties"));

			String driverclass = "com.mysql.jdbc.Driver"; //aspak prop.getProperty("driverclass");
			String dbuser = "root"; //aspak prop.getProperty("dbuser");
			String dbpassword = "root"; //aspak prop.getProperty("dbpassword");
			String dburl = "jdbc:mysql://localhost:3306/"; //aspakprop.getProperty("dburl");
			String dataBaseName = "arkansas"; //aspak prop.getProperty("dbdatabase");


			url=dburl+dataBaseName+"?user="+dbuser+"&password="+dbpassword;
			driver=driverclass;
			Class.forName(driver);

		} 
		//aspak		catch (IOException e1) {
		//		
		//			e1.printStackTrace();
		//		} 
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

	@Override
	public Users getUsers(String userName) {
		return Users;
	}

	@Override
	public AgentInfo getAgentInfo(String userName) {
		// TODO Auto-generated method stub
		return AgentInfo;
	}

	@Override
	public Interfaces getInterfaces(String userName) {
		// TODO Auto-generated method stub
		return Interfaces;
	}

	@Override
	public OsInfo getOsInfo(String userName) {
		// TODO Auto-generated method stub
		return OsInfo;
	}

	@Override
	public boolean submitAgentInfo(String userName, AgentInfo agentInfo) {
		// TODO Auto-generated method stub
		return false;			
	}

	@Override
	public boolean submitInterfaces(String userName, Interfaces interfaces) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean submitOsInfo(String userName, OsInfo osInfo) {
		// TODO Auto-generated method stub
		return false;
	}



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

	//
	//	public static void main(String args[]){
	//		EnrollmentDAOImpl daoImpl= new EnrollmentDAOImpl();
	//		DiscoveryBean a = daoImpl.getDiscoveredData("C.12345");
	//		System.out.println(a);
	//	}

}
