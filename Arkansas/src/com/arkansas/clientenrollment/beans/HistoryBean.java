/**
 * 
 */
package com.arkansas.clientenrollment.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import org.json.simple.JSONObject;

/**
 * @author msaadghouri
 *
 */
public class HistoryBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String historyId;
	private String userRefId;
	private ArrayList<JSONObject> browserHistory;
	private Date createdDate;
	private int transactionId;

	/**
	 * 
	 */
	public HistoryBean() {
		super();
	}

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
	}

	public ArrayList<JSONObject> getBrowserHistory() {
		return browserHistory;
	}

	public void setBrowserHistory(ArrayList<JSONObject> browserHistory) {
		this.browserHistory = browserHistory;
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

	public HistoryBean(String userRefId, ArrayList<JSONObject> browserHistory, Date createdDate, int transactionId) {
		this.userRefId = userRefId;
		this.browserHistory = browserHistory;
		this.createdDate = createdDate;
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "{\"historyId\":" + "\"" + historyId
				+ "\"" + ", \"userRefId\":" + "\"" + userRefId
				+ "\"" + ", \"browserHistory\":" + "" + browserHistory
				+ "" + ", \"createdDate\":" + "\"" + createdDate
				+ "\"" + ", \"transactionId\":" + "\"" + transactionId
				+ "\"" + "}";
	}

}
