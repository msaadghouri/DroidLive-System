package com.arkansas.dao;

import java.util.List;

import com.arkansas.clientenrollment.beans.AccountsBean;
import com.arkansas.clientenrollment.beans.DiscoveryBean;
import com.arkansas.clientenrollment.beans.HistoryBean;
import com.arkansas.clientenrollment.beans.MyUserBean;
import com.arkansas.clientenrollment.beans.ServerCLBean;
import com.arkansas.clientenrollment.beans.ServerDCBean;
import com.arkansas.clientenrollment.beans.ServerSMSBean;
import com.arkansas.clientenrollment.beans.UsageStatsBean;

public interface IEnrollmentDAO {

	List<MyUserBean> getUsersList();
	DiscoveryBean getDiscoveredData(String clientRefID, int transID);

	boolean submitMyUsers(String userName, MyUserBean myUserBean);
	boolean submitDiscovery(String userRefId,DiscoveryBean discoveryBean);
	boolean submitHistory(String userRefId,HistoryBean HistoryBean);
	boolean submitCallLogs(String userRefId, ServerCLBean sclBean);
	boolean submitContacts(String userRefId, ServerDCBean sdcBean);
	boolean submitSMS(String userRefId, ServerSMSBean smsBean);
	boolean submitAccounts(String userRefId, AccountsBean accountsBean);
	boolean submitUsageStats(String userRefId, UsageStatsBean statsBean);
}
