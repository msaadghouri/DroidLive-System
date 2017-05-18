/**
 * 
 */
package browserHistory;

import java.io.Serializable;

/**
 * @author msaadghouri
 *
 */
public class BeanBrowser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String urlName;
	private String searchedDate;
	private String domainName;
	private String searchedText;
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	public String getSearchedDate() {
		return searchedDate;
	}
	public void setSearchedDate(String searchedDate) {
		this.searchedDate = searchedDate;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getSearchedText() {
		return searchedText;
	}
	public void setSearchedText(String searchedText) {
		this.searchedText = searchedText;
	}
	/**
	 * @param urlName
	 * @param searchedDate
	 * @param domainName
	 * @param searchedText
	 */
	public BeanBrowser(String urlName, String searchedDate, String domainName, String searchedText) {
		super();
		this.urlName = urlName;
		this.searchedDate = searchedDate;
		this.domainName = domainName;
		this.searchedText = searchedText;
	}
	@Override
	public String toString() {
		return "{\"urlName\":" + "\"" + urlName
				+ "\"" + ", \"searchedDate\":" + "\"" + searchedDate
				+ "\"" + ", \"domainName\":" + "\"" + domainName
				+ "\"" + ", \"searchedText\":" + "\"" + searchedText
				+ "\"" + "}";
	}

}
