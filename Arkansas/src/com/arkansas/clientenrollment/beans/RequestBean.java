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
public class RequestBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int transactionID;
	private String flowNmae;
	private Date flowDate;
	private String status;
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public String getFlowNmae() {
		return flowNmae;
	}
	public void setFlowNmae(String flowNmae) {
		this.flowNmae = flowNmae;
	}
	public Date getFlowDate() {
		return flowDate;
	}
	public void setFlowDate(Date flowDate) {
		this.flowDate = flowDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param transactionID
	 * @param flowNmae
	 * @param flowDate
	 * @param status
	 */
	public RequestBean(int transactionID, String flowNmae, Date flowDate, String status) {
		super();
		this.transactionID = transactionID;
		this.flowNmae = flowNmae;
		this.flowDate = flowDate;
		this.status = status;
	}


}
