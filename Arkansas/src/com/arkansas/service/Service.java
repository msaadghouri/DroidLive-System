package com.arkansas.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arkansas.clientenrollment.beans.AgentInfo;
import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.Interfaces;
import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.clientenrollment.beans.OsInfo;
import com.arkansas.clientenrollment.beans.Users;
import com.arkansas.dao.EnrollmentDAOImpl;
import com.arkansas.dao.IEnrollmentDAO;

@Path("/arkansas")
public class Service {
	public static String FIREBASE_SERVER_KEY = "AAAAwJPZdJk:APA91bFodWExOdhQrFq7rqcx1TOscgDk1NGNx-R2MMFd_VsxfscHER5SpBhshH7noTKdCXWXFAIVheJ2_CFP53dkQCggsFhi-jZMNiR8y0K_rM_HQzuNkLRn7JPpIm_yodUFFPXZBd2n";
	@GET
	@Path("/RestHello/{name}")
	public String HelloMethod(@PathParam("name") String name){

		return "HELLO "+name.toUpperCase();
	}//http://localhost:8080/Arkansas/WebRest/arkansas/RestHello/saad

	@GET	
	@Path("/getUsers/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@PathParam("userName") String userName){

		Users user=new Users();

		if(userName.trim() != null && userName.trim() != "" && userName.trim() != "0"){

			System.out.println(userName+" has trying get Users");

			IEnrollmentDAO IDAO =new EnrollmentDAOImpl();

			user=IDAO.getUsers(userName);

			System.out.println(user);				
		}
		return Response.status(200).entity(user.toString()).build();			
	}

	@GET	
	@Path("/getAgentInfo/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAgentInfo(@PathParam("userName") String userName){

		AgentInfo agentInfo=new AgentInfo();

		if(userName.trim() != null && userName.trim() != "" && userName.trim() != "0"){

			System.out.println(userName+" has trying get AgentInfo");

			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();

			agentInfo=IDAO.getAgentInfo(userName);

			System.out.println(agentInfo);				
		}
		return Response.status(200).entity(agentInfo.toString()).build();			
	}

	@GET	
	@Path("/getInterfaces/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInterfaces(@PathParam("userName") String userName){

		Interfaces interfaces=new Interfaces();

		if(userName.trim() != null && userName.trim() != "" && userName.trim() != "0"){

			System.out.println(userName+" has trying get Interfaces");

			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();

			interfaces=IDAO.getInterfaces(userName);

			System.out.println(interfaces);					
		}
		return Response.status(200).entity(interfaces.toString()).build();			
	}

	@GET	
	@Path("/getOsInfo/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOsInfo(@PathParam("userName") String userName){

		OsInfo osInfo=new OsInfo();

		if(userName.trim() != null && userName.trim() != "" && userName.trim() != "0"){

			System.out.println(userName+" has trying get OsInfo");

			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();

			osInfo=IDAO.getOsInfo(userName);

			System.out.println(osInfo);				
		}
		return Response.status(200).entity(osInfo.toString()).build();			
	}

	@POST	
	@Path("/submitAgentInfo/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitAgentInfo(@PathParam("userName") String userName,AgentInfo agentInfo){

		boolean flag=false;	

		System.out.println("UserName : "+userName+" is submitting AgentInfo : "+ agentInfo);

		if(userName != "" && agentInfo != null){

			System.out.println(userName + " has trying to submit his AgentInfo with values as "+ agentInfo);

			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();

			flag=IDAO.submitAgentInfo(userName, agentInfo);

			System.out.println("UserName: "+ userName + " submitAgentInfo got value As : " + flag);		
		}		
		return flag;			
	}

	@POST	
	@Path("/submitInterfaces/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitInterfaces(@PathParam("userName") String userName,Interfaces interfaces){

		boolean flag=false;	

		System.out.println("UserName : "+userName+" is submitting Interfaces : "+ interfaces);

		if(userName != "" && interfaces != null){

			System.out.println(userName + " has trying to submit his Interfaces with values as "+ interfaces);

			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();

			flag=IDAO.submitInterfaces(userName, interfaces);

			System.out.println("UserName: "+ userName + " submitInterfaces got value As : " + flag);		
		}		
		return flag;			
	}

	@POST	
	@Path("/submitOsInfo/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean submitOsInfo(@PathParam("userName") String userName,OsInfo osInfo){

		boolean flag=false;	

		System.out.println("UserName : "+userName+" is submitting OsInfo : "+ osInfo);

		if(userName != "" && osInfo != null){

			System.out.println(userName + " has trying to submit his OsInfo with values as "+ osInfo);

			IEnrollmentDAO IDAO=new EnrollmentDAOImpl();

			flag=IDAO.submitOsInfo(userName, osInfo);

			System.out.println("UserName: "+ userName + " submitOsInfo got value As : " + flag);		
		}		
		return flag;			
	}

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
}
