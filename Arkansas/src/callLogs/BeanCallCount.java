/**
 * 
 */
package callLogs;

import java.io.Serializable;

/**
 * @author msaadghouri
 *
 */
public class BeanCallCount implements Serializable {
	private static final long serialVersionUID = 1L;
	private int totalCalls;
	private int incomingCalls;
	private int outgoingCalls;
	private int missedCalls;
	public int getTotalCalls() {
		return totalCalls;
	}
	public void setTotalCalls(int totalCalls) {
		this.totalCalls = totalCalls;
	}
	public int getIncomingCalls() {
		return incomingCalls;
	}
	public void setIncomingCalls(int incomingCalls) {
		this.incomingCalls = incomingCalls;
	}
	public int getOutgoingCalls() {
		return outgoingCalls;
	}
	public void setOutgoingCalls(int outgoingCalls) {
		this.outgoingCalls = outgoingCalls;
	}
	public int getMissedCalls() {
		return missedCalls;
	}
	public void setMissedCalls(int missedCalls) {
		this.missedCalls = missedCalls;
	}
	/**
	 * @param totalCalls
	 * @param incomingCalls
	 * @param outgoingCalls
	 * @param missedCalls
	 */
	public BeanCallCount(int totalCalls, int incomingCalls, int outgoingCalls, int missedCalls) {
		super();
		this.totalCalls = totalCalls;
		this.incomingCalls = incomingCalls;
		this.outgoingCalls = outgoingCalls;
		this.missedCalls = missedCalls;
	}
	@Override
	public String toString() {
		return "{\"totalCalls\":" + "\"" + totalCalls
				+ "\"" + ", \"incomingCalls\":" + "\"" + incomingCalls
				+ "\"" + ", \"outgoingCalls\":" + "\"" + outgoingCalls
				+ "\"" + ", \"missedCalls\":" + "\"" + missedCalls
				+ "\"" + "}";
	}
}
