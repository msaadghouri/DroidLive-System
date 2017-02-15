package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;

public class AgentInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int agentInfoId;
	private String clientName;
	private String clientVersion;
	private Date buildTime;
	private String clientDescription;
	private int userId;
	private Date createdDate;

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
				+"\""+ ", \"clientName\":" +"\""+ clientName 
				+"\""+ ", \"clientVersion\":" +"\""+ clientVersion		
				+"\""+ ", \"buildTime\":" +"\""+ buildTime
				+"\""+ ", \"clientDescription\":" +"\""+ clientDescription
				+"\""+ ", \"userId\":" +"\""+ userId 
				+"\""+ ", \"createdDate\":" +"\""+ createdDate +"\""+ "}";
	}	
}
