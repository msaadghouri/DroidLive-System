/**
 * 
 */
package callLogs;

import java.io.Serializable;

/**
 * @author msaadghouri
 *
 */
public class BeanCOI implements Serializable {

	private static final long serialVersionUID = 1L;
	private String startDate;
	private String cNumber;
	private int totalDur;
	private int frequency;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getcNumber() {
		return cNumber;
	}
	public void setcNumber(String cNumber) {
		this.cNumber = cNumber;
	}
	public int getTotalDur() {
		return totalDur;
	}
	public void setTotalDur(int totalDur) {
		this.totalDur = totalDur;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	/**
	 * @param startDate
	 * @param totalDur
	 * @param frequency
	 */
	public BeanCOI(String startDate, int totalDur, int frequency) {
		super();
		this.startDate = startDate;
		this.totalDur = totalDur;
		this.frequency = frequency;
	}
	
	/**
	 * @param cNumber
	 * @param totalDur
	 * @param frequency
	 */
	public BeanCOI(int totalDur, int frequency,String cNumber) {
		super();
		this.totalDur = totalDur;
		this.frequency = frequency;
		this.cNumber = cNumber;
	}
	@Override
	public String toString() {
		return "{\"startDate\":" + "\"" + startDate
				+ "\"" + ", \"cNumber\":" + "\"" + cNumber
				+ "\"" + ", \"totalDur\":" + "\"" + totalDur
				+ "\"" + ", \"frequency\":" + "\"" + frequency
				+ "\"" + "}";
	}

}
