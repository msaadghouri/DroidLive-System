package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.util.Date;
public class OsInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int osInfoId;
	private String system;
	private String node;
	private String release;
	private String version;
	private String machine;	
	private String kernel;
	private String FQDN;
	private String installDate;
	private int userId;
	private Date createdDate;

	public int getOsInfoId() {
		return osInfoId;
	}

	public void setOsInfoId(int osInfoId) {
		this.osInfoId = osInfoId;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public String getKernel() {
		return kernel;
	}

	public void setKernel(String kernel) {
		this.kernel = kernel;
	}

	public String getFQDN() {
		return FQDN;
	}

	public void setFQDN(String fQDN) {
		FQDN = fQDN;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
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
		return "{\"osInfoId\":" +"\""+ osInfoId 
				+"\""+ ", \"system\":" +"\""+ system 
				+"\""+ ", \"node\":" +"\""+ node		
				+"\""+ ", \"release\":" +"\""+ release
				+"\""+ ", \"version\":" +"\""+ version
				+"\""+ ", \"machine\":" +"\""+ machine 
				+"\""+ ", \"kernel\":"	+"\""+ kernel 
				+"\""+ ", \"FQDN\":" +"\""+ FQDN 				
				+"\""+ ", \"installDate\":" +"\""+ installDate 
				+"\""+ ", \"userId\":" +"\""+ userId 
				+"\""+ ", \"createdDate\":" +"\""+ createdDate +"\""+ "}";		
	}	
}
