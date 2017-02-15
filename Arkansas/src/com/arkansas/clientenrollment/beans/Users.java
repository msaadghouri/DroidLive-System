package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;

public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;
	private String userName;
	private String fullName;
	private Date firstLogOn;
	private Date lastLogOn;
	private String allLogOn;
	private String homeDir;
	private String password;
	private String imeiNo;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getFirstLogOn() {
		return firstLogOn;
	}

	public void setFirstLogOn(Date firstLogOn) {
		this.firstLogOn = firstLogOn;
	}

	public Date getLastLogOn() {
		return lastLogOn;
	}

	public void setLastLogOn(Date lastLogOn) {
		this.lastLogOn = lastLogOn;
	}

	public String getAllLogOn() {
		return allLogOn;
	}

	public void setAllLogOn(String allLogOn) {
		this.allLogOn = allLogOn;
	}

	public String getHomeDir() {
		return homeDir;
	}

	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	@Override
	public String toString() {		
		return "{\"userId\":" +"\""+ userId 
				+"\""+ ", \"userName\":" +"\""+ userName 
				+"\""+ ", \"fullName\":" +"\""+ fullName		
				+"\""+ ", \"firstLogOn\":" +"\""+ firstLogOn
				+"\""+ ", \"lastLogOn\":" +"\""+ lastLogOn
				+"\""+ ", \"allLogOn\":" +"\""+ allLogOn 
				+"\""+ ", \"homeDir\":"	+"\""+ homeDir 
				+"\""+ ", \"password\":" +"\""+ password 
				+"\""+ ", \"imeiNo\":" +"\""+ imeiNo +"\""+
				"}";
	}		
}
