/**
 * 
 */
package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author msaadghouri
 *
 */
public class MyAgentInfoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int agentInfoId;
	private String userRefId;
	private String clientName;
	private String clientVersion;
	private String clientDescription;
	private Date buildTime;
	private Date createdDate;

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
	}

	public int getAgentInfoId() {
		return agentInfoId;
	}

	public void setAgentInfoId(int agentInfoId) {
		this.agentInfoId = agentInfoId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public Date getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}

	public String getClientDescription() {
		return clientDescription;
	}

	public void setClientDescription(String clientDescription) {
		this.clientDescription = clientDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "{\"agentInfoId\":" +"\""+ agentInfoId 
				+"\""+ ", \"userRefId\":" +"\""+ userRefId 
				+"\""+ ", \"clientName\":" +"\""+ clientName 
				+"\""+ ", \"clientVersion\":" +"\""+ clientVersion		
				+"\""+ ", \"clientDescription\":" +"\""+ clientDescription
				+"\""+ ", \"buildTime\":" +"\""+ buildTime
				+"\""+ ", \"createdDate\":" +"\""+ createdDate +"\""+ "}";
	}	
}
