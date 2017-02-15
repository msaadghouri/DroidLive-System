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
public class MyUserBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userRefId;
	private String userName;
	private String fullName;
	private Date firstLogOn;
	private Date lastLogOn;
	private String homeDir;
	private String firebaseRegID;

    public String getFirebaseRegID() {
        return firebaseRegID;
    }

    public void setFirebaseRegID(String firebaseRegID) {
        this.firebaseRegID = firebaseRegID;
    }

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
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

	

	public String getHomeDir() {
		return homeDir;
	}

	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}


	/**
	 * 
	 */
	public MyUserBean() {
		super();
	}

	/**
	 * @param userRefId
	 * @param userName
	 * @param fullName
	 * @param firstLogOn
	 * @param lastLogOn
	 * @param homeDir
	 * @param firebaseRegID
	 */
	public MyUserBean(String userRefId, String userName, String fullName, Date firstLogOn, Date lastLogOn,
			String homeDir, String firebaseRegID) {
		super();
		this.userRefId = userRefId;
		this.userName = userName;
		this.fullName = fullName;
		this.firstLogOn = firstLogOn;
		this.lastLogOn = lastLogOn;
		this.homeDir = homeDir;
		this.firebaseRegID=firebaseRegID;
	}

	@Override
	public String toString() {		
		return "{\"userRefId\":" +"\""+ userRefId 
				+"\""+ ", \"userName\":" +"\""+ userName 
				+"\""+ ", \"fullName\":" +"\""+ fullName		
				+"\""+ ", \"firstLogOn\":" +"\""+ firstLogOn
				+"\""+ ", \"lastLogOn\":" +"\""+ lastLogOn
				+"\""+ ", \"homeDir\":" +"\""+ homeDir 
				 + "\"" + ", \"firebaseRegID\":" + "\"" + firebaseRegID
				+"\""+ "}";
	}

}
