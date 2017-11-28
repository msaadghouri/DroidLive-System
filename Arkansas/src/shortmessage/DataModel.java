/**
 * 
 */
package shortmessage;

import java.io.Serializable;

/**
 * @author msaadghouri
 *
 */
public class DataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	/**
	 * 
	 */
	public DataModel() {
		// TODO Auto-generated constructor stub
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public DataModel(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "{\"firstName\":" + "\"" + firstName
				+ "\"" + ", \"lastName\":" + "\"" + lastName
				+ "\"" + "}";
	}
}
