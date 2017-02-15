/**
 * 
 */
package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * @author msaadghouri
 *
 */
public class MyInterfaceBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int interfaceId;
	private String userRefId;
	private String macAddress;
	private String ipv4;
	private String ipv6;
	private Date createdDate;

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
	}

	public int getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getIpv6() {
		return ipv6;
	}

	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "{\"interfaceId\":" +"\""+ interfaceId 
				+"\""+ ", \"userRefId\":" +"\""+ userRefId 
				+"\""+ ", \"macAddress\":" +"\""+ macAddress 
				+"\""+ ", \"ipv4\":" +"\""+ ipv4		
				+"\""+ ", \"ipv6\":" +"\""+ ipv6 
				+"\""+ ", \"createdDate\":" +"\""+ createdDate +"\""+ "}";
	}
}
