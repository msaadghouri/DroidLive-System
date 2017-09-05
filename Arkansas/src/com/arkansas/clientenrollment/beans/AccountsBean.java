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
public class AccountsBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String accountsId;
	private String userRefId;
	private ArrayList<JSONObject> accountsList;
	private Date createdDate;
	private int transactionId;

	public AccountsBean() {
		super();
	}

	public String getAccountsId() {
		return accountsId;
	}

	public void setAccountsId(String accountsId) {
		this.accountsId = accountsId;
	}

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
	}

	public ArrayList<JSONObject> getAccountsList() {
		return accountsList;
	}

	public void setAccountsList(ArrayList<JSONObject> accountsList) {
		this.accountsList = accountsList;
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

	public AccountsBean(String userRefId, ArrayList<JSONObject> accountsList, Date createdDate, int transactionId) {
		this.userRefId = userRefId;
		this.accountsList = accountsList;
		this.createdDate = createdDate;
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "{\"accountsId\":" + "\"" + accountsId
				+ "\"" + ", \"userRefId\":" + "\"" + userRefId
				+ "\"" + ", \"accountsList\":" + "" + accountsList
				+ "" + ", \"createdDate\":" + "\"" + createdDate
				+ "\"" + ", \"transactionId\":" + "\"" + transactionId
				+ "\"" + "}";
	}
}
