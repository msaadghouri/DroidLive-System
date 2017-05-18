package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mohammad-Ghouri on 3/15/17.
 */

public class ServerSMSBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msgId;
	private String userRefId;
	private List<SMSBean> smsBean;
	private Date createdDate;
	private int transactionId;
	/**
	 * 
	 */
	public ServerSMSBean() {
		// TODO Auto-generated constructor stub
	}
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
	}

	public List<SMSBean> getSmsBean() {
		return smsBean;
	}

	public void setSmsBean(List<SMSBean> smsBean) {
		this.smsBean = smsBean;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public ServerSMSBean(String userRefId, List<SMSBean> smsBean, Date createdDate, int transactionId) {
		this.userRefId = userRefId;
		this.smsBean = smsBean;
		this.createdDate = createdDate;
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "{\"msgId\":" + "\"" + msgId
				+ "\"" + ", \"userRefId\":" + "\"" + userRefId
				+ "\"" + ", \"smsBean\":" + "" + smsBean
				+ "" + ", \"createdDate\":" + "\"" + createdDate
				+ "\"" + ", \"transactionId\":" + "\"" + transactionId
				+ "\"" + "}";
	}
}
