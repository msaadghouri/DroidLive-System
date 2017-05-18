/**
 * 
 */
package callLogs;

import java.io.Serializable;

/**
 * @author msaadghouri
 *
 */
public class BeanGraphElements implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cNumber;
	private String cType;
	private int strength;
	public String getcNumber() {
		return cNumber;
	}
	public void setcNumber(String cNumber) {
		this.cNumber = cNumber;
	}
	public String getcType() {
		return cType;
	}
	public void setcType(String cType) {
		this.cType = cType;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	/**
	 * @param cNumber
	 * @param cType
	 * @param strength
	 */
	public BeanGraphElements(String cNumber, String cType, int strength) {
		super();
		this.cNumber = cNumber;
		this.cType = cType;
		this.strength = strength;
	}
	@Override
	public String toString() {
		return "{\"cNumber\":" + "\"" + cNumber
				+ "\"" + ", \"cType\":" + "\"" + cType
				+ "\"" + ", \"strength\":" + "\"" + strength
				+ "\"" + "}";
	}
}
