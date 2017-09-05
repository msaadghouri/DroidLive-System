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
public class UsageStatsBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private String statsId;
	private String userRefId;
	private StringBuilder builder;
	private Date createdDate;
	private int transactionId;

	public UsageStatsBean() {
		super();
	}

	public String getUserRefId() {
		return userRefId;
	}

	public void setUserRefId(String userRefId) {
		this.userRefId = userRefId;
	}

	public String getStatsId() {
		return statsId;
	}

	public void setStatsId(String statsId) {
		this.statsId = statsId;
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
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

	public UsageStatsBean(String userRefId, StringBuilder builder, Date createdDate, int transactionId) {
		this.userRefId = userRefId;
		this.builder = builder;
		this.createdDate = createdDate;
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "{\"statsId\":" + "\"" + statsId
				+ "\"" + ", \"userRefId\":" + "\"" + userRefId
				+ "\"" + ", \"builder\":" + "\"" + builder
				+ "\"" + ", \"createdDate\":" + "\"" + createdDate
				+ "\"" + ", \"transactionId\":" + "\"" + transactionId
				+ "\"" + "}";
	}
}
