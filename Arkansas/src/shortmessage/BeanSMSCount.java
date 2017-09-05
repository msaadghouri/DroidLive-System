/**
 * 
 */
package shortmessage;

import java.io.Serializable;

/**
 * @author msaadghouri
 *
 */
public class BeanSMSCount implements Serializable{
	private static final long serialVersionUID = 1L;
	private int totalSMS;
	private int receivedSMS;
	private int sentSMS;
	private int totalWords;
	private int averageWords;
	public int getTotalSMS() {
		return totalSMS;
	}
	public void setTotalSMS(int totalSMS) {
		this.totalSMS = totalSMS;
	}
	public int getReceivedSMS() {
		return receivedSMS;
	}
	public void setReceivedSMS(int receivedSMS) {
		this.receivedSMS = receivedSMS;
	}
	public int getSentSMS() {
		return sentSMS;
	}
	public void setSentSMS(int sentSMS) {
		this.sentSMS = sentSMS;
	}
	public int getTotalWords() {
		return totalWords;
	}
	public void setTotalWords(int totalWords) {
		this.totalWords = totalWords;
	}
	public int getAverageWords() {
		return averageWords;
	}
	public void setAverageWords(int averageWords) {
		this.averageWords = averageWords;
	}
	/**
	 * @param totalSMS
	 * @param receivedSMS
	 * @param sentSMS
	 * @param totalWords
	 * @param averageWords
	 */
	public BeanSMSCount(int totalSMS, int receivedSMS, int sentSMS, int totalWords, int averageWords) {
		super();
		this.totalSMS = totalSMS;
		this.receivedSMS = receivedSMS;
		this.sentSMS = sentSMS;
		this.totalWords = totalWords;
		this.averageWords = averageWords;
	} 
	@Override
	public String toString() {
		return "{\"totalSMS\":" + "\"" + totalSMS
				+ "\"" + ", \"receivedSMS\":" + "\"" + receivedSMS
				+ "\"" + ", \"sentSMS\":" + "\"" + sentSMS
				+ "\"" + ", \"totalWords\":" + "\"" + totalWords
				+ "\"" + ", \"averageWords\":" + "\"" + averageWords
				+ "\"" + "}";
	}
}
