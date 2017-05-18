package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Mohammad-Ghouri on 3/2/17.
 */

public class CallLogsBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int callId;
	private String contactName;
	private String contactNumber;
	private int callDuration;
	private Date callDate;
	private String callType;
	private String countryISO;
	/**
	 * 
	 */
	public CallLogsBean() {
		// TODO Auto-generated constructor stub
	}

	public int getCallId() {
		return callId;
	}

	public void setCallId(int callId) {
		this.callId = callId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getCountryISO() {
		return countryISO;
	}

	public void setCountryISO(String countryISO) {
		this.countryISO = countryISO;
	}


	public CallLogsBean(int callId, String contactName, String contactNumber, int callDuration,
			Date callDate, String callType, String countryISO) {
		this.callId = callId;
		this.contactName = contactName;
		this.contactNumber = contactNumber;
		this.callDuration = callDuration;
		this.callDate = callDate;
		this.callType = callType;
		this.countryISO = countryISO;
	}

	public CallLogsBean(String contactName, String contactNumber, int callDuration,
			Date callDate, String callType) {
		this.contactName = contactName;
		this.contactNumber = contactNumber;
		this.callDuration = callDuration;
		this.callDate = callDate;
		this.callType = callType;
	}

	@Override
	public String toString() {
		return "{\"callId\":" + "\"" + callId
				+ "\"" + ", \"contactName\":" + "\"" + contactName
				+ "\"" + ", \"contactNumber\":" + "\"" + contactNumber
				+ "\"" + ", \"callDuration\":" + "\"" + callDuration
				+ "\"" + ", \"callDate\":" + "\"" + callDate
				+ "\"" + ", \"callType\":" + "\"" + callType
				+ "\"" + ", \"countryISO\":" + "\"" + countryISO
				+ "\"" + "}";
	}
}
