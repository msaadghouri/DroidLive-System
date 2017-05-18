package com.arkansas.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.HistoryBean;
import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.clientenrollment.beans.ServerCLBean;
import com.arkansas.clientenrollment.beans.ServerDCBean;
import com.arkansas.clientenrollment.beans.ServerSMSBean;
import com.arkansas.dao.EnrollmentDAOImpl;
import com.arkansas.dao.IEnrollmentDAO;

@Path("/arkansas")
public class Service {
	public static String FIREBASE_SERVER_KEY = "AAAAwJPZdJk:APA91bFodWExOdhQrFq7rqcx1TOscgDk1NGNx-R2MMFd_VsxfscHER5SpBhshH7noTKdCXWXFAIVheJ2_CFP53dkQCggsFhi-jZMNiR8y0K_rM_HQzuNkLRn7JPpIm_yodUFFPXZBd2n";

	//	@GET	
	//	@Path("/getAgentInfo/{userName}")
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public Response getAgentInfo(@PathParam("userName") String userName){
	//
	//		AgentInfo agentInfo=new AgentInfo();
	//
	//		if(userName.trim() != null && userName.trim() != "" && userName.trim() != "0"){
	//
	//			System.out.println(userName+" has trying get AgentInfo");
	//
	//			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();
	//
	//			agentInfo=IDAO.getAgentInfo(userName);
	//
	//			System.out.println(agentInfo);				
	//		}
	//		return Response.status(200).entity(agentInfo.toString()).build();			
	//	}



	@POST	
	@Path("/clientEnroll/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitUsers(@PathParam("userName") String userName,MyUserBean myUserBean){

		boolean flag=false;	
		if(userName != "" && myUserBean != null){
			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();
			flag=IDAO.submitMyUsers(userName, myUserBean);		
		}
		return flag;			
	}
	//	public static void main(String args[]){
	//		Service service= new Service();
	//		MyUserBean bean= new MyUserBean();
	//		bean.setUserRefId("C.12345");
	//		bean.setUserName("testUSer");
	//		bean.setFullName("Test Full Name");
	//		java.sql.Date date= new java.sql.Date(Calendar.getInstance().getTime().getTime());
	//		bean.setFirstLogOn(date);
	//		bean.setLastLogOn(date);
	//		bean.setHomeDir("/home/test/");
	//		service.submitUsers("saad", bean);
	//	}

	@POST	
	@Path("/submitDiscovery/{userRefId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitClientDiscovery(@PathParam("userRefId") String userRefId,DiscoveryBean discoveryBean){
		boolean flag=false;	
		if(userRefId != "" && discoveryBean != null){			
			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();
			flag=IDAO.submitDiscovery(userRefId,discoveryBean);
		}		
		return flag;			
	}
	@POST	
	@Path("/submitHistory/{userRefId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitBrowserHistory(@PathParam("userRefId") String userRefId,HistoryBean historyBean){
		boolean flag=false;	
		if(userRefId != "" && historyBean != null){		
			System.out.println(historyBean);
			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();
			flag=IDAO.submitHistory(userRefId,historyBean);
		}		
		return flag;			
	}
	
	@POST	
	@Path("/submitCallLogs/{userRefId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitCallLogs(@PathParam("userRefId") String userRefId,ServerCLBean sclBean){
		System.out.println("Submit Call Logs");
		boolean flag=false;	
		if(userRefId != "" && sclBean != null){		
			System.out.println("SERVICE "+sclBean);
			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();
			flag=IDAO.submitCallLogs(userRefId,sclBean);
		}		
		return flag;			
	}
	
	@POST	
	@Path("/submitContacts/{userRefId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitContacts(@PathParam("userRefId") String userRefId,ServerDCBean sdcBean){
		System.out.println("Submit Contacts");
		boolean flag=false;	
		if(userRefId != "" && sdcBean != null){		
			System.out.println("SERVICE "+sdcBean);
			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();
			flag=IDAO.submitContacts(userRefId,sdcBean);
		}		
		return flag;			
	}
	
	@POST	
	@Path("/submitSMS/{userRefId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitSMS(@PathParam("userRefId") String userRefId,ServerSMSBean smsBean){
		System.out.println("Submit SMS");
		boolean flag=false;	
		if(userRefId != "" && smsBean != null){		
			System.out.println("SERVICE "+smsBean);
			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();
			flag=IDAO.submitSMS(userRefId,smsBean);
		}		
		return flag;			
	}
}
