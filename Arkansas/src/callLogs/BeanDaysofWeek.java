/**
 * 
 */
package callLogs;

import java.io.Serializable;

/**
 * @author msaadghouri
 *
 */
public class BeanDaysofWeek implements Serializable{
	private static final long serialVersionUID = 1L;
	private String weekDay;
	private int frequency;
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	/**
	 * @param weekDay
	 * @param frequency
	 */
	public BeanDaysofWeek(String weekDay, int frequency) {
		super();
		this.weekDay = weekDay;
		this.frequency = frequency;
	}
	@Override
	public String toString() {
		return "{\"weekDay\":" + "\"" + weekDay
				+ "\"" + ", \"frequency\":" + "\"" + frequency
				+ "\"" + "}";
	}
}
