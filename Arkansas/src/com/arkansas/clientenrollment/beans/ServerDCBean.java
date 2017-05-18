package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mohammad-Ghouri on 3/15/17.
 */

public class ServerDCBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String contId;
	private String userRefId;
	private List<ContactsBean> contactsBean;
	private Date createdDate;
	private int transactionId;
	/**
	 * 
	 */
	public ServerDCBean() {
		// TODO Auto-generated constructor stub
	}
	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
	}

	public List<ContactsBean> getcontactsBean() {
		return contactsBean;
	}

	public void setcontactsBean(List<ContactsBean> contactsBean) {
		this.contactsBean = contactsBean;
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

	public ServerDCBean(String userRefId, List<ContactsBean> contactsBean, Date createdDate, int transactionId) {
		this.userRefId = userRefId;
		this.contactsBean = contactsBean;
		this.createdDate = createdDate;
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "{\"contId\":" + "\"" + contId
				+ "\"" + ", \"userRefId\":" + "\"" + userRefId
				+ "\"" + ", \"contactsBean\":" + "" + contactsBean
				+ "" + ", \"createdDate\":" + "\"" + createdDate
				+ "\"" + ", \"transactionId\":" + "\"" + transactionId
				+ "\"" + "}";
	}
}
