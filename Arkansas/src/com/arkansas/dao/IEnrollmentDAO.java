package com.arkansas.dao;

import java.util.List;

import com.arkansas.clientenrollment.beans.AgentInfo;
import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.Interfaces;
import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.clientenrollment.beans.OsInfo;
import com.arkansas.clientenrollment.beans.Users;

public interface IEnrollmentDAO {
	
	/**
	 * 
	 * @param actCode
	 * @return
	 */
	Users getUsers(String userName);
	AgentInfo getAgentInfo(String userName);
	Interfaces getInterfaces(String userName);
	OsInfo getOsInfo(String userName);
	List<MyUserBean> getUsersList();
	DiscoveryBean getDiscoveredData(String clientRefID, int transID);

	/**
	 * 
	 * @param userid
	 * @param answerlist
	 * @return
	 */
	boolean submitAgentInfo(String userName,AgentInfo agentInfo);
	boolean submitInterfaces(String userName,Interfaces interfaces);
	boolean submitOsInfo(String userName,OsInfo osInfo);
	//boolean submitUsers(String userName,Users users);
	
	boolean submitMyUsers(String userName, MyUserBean myUserBean);
	
	/**
	 * 
	 * @param userid
	 * @param answerlist
	 * @return
	 */
	//boolean submitClientEnrollment(String userName, AgentInfo agentInfo, Interfaces interfaces, OsInfo osInfo, Users users);
	boolean submitDiscovery(String userRefId,DiscoveryBean discoveryBean);
}
