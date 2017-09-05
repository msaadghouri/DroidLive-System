package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Mohammad-Ghouri on 3/14/17.
 */

public class SMSBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int smsID;
	private String smsAddress;
	private Date smsDate;
	private String smsAction;
	private String smsBody;

	/**
	 * 
	 */
	public SMSBean() {
		// TODO Auto-generated constructor stub
	}

	public int getSmsID() {
		return smsID;
	}

	public void setSmsID(int smsID) {
		this.smsID = smsID;
	}

	public String getSmsAddress() {
		return smsAddress;
	}

	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}

	public Date getSmsDate() {
		return smsDate;
	}

	public void setSmsDate(Date smsDate) {
		this.smsDate = smsDate;
	}

	public String getSmsAction() {
		return smsAction;
	}

	public void setSmsAction(String smsAction) {
		this.smsAction = smsAction;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public SMSBean(int smsID, String smsAddress, Date smsDate, String smsAction, String smsBody) {
		this.smsID = smsID;
		this.smsAddress = smsAddress;
		this.smsDate = smsDate;
		this.smsAction = smsAction;
		this.smsBody = smsBody;
	}

	public SMSBean(String smsAction, String smsBody, Date smsDate) {
		this.smsDate = smsDate;
		this.smsAction = smsAction;
		this.smsBody = smsBody;
	}

	@Override
	public String toString() {
		return "{\"smsID\":" + "\"" + smsID
				+ "\"" + ", \"smsAddress\":" + "\"" + smsAddress
				+ "\"" + ", \"smsDate\":" + "\"" + smsDate
				+ "\"" + ", \"smsAction\":" + "\"" + smsAction
				+ "\"" + ", \"smsBody\":" + "\"" + smsBody
				+ "\"" + "}";
	}
}
