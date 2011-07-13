package no.uka.findmyapp.configuration;

import java.util.Date;

/**
 * Used to hold properties. Populated as bean in root-context.xml
 * 
 * @author haakon.bakka
 *
 */
public class UkaProgramConfiguration {
	/**
	 * Default values
	 */
	private Date startDate = new Date(1);
	private Date endDate = new Date(10);
	private String ukaYear = "s";
	private String title = "unknown";
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getUkaYear() {
		return ukaYear;
	}
	public void setUkaYear(String ukaYear) {
		this.ukaYear = ukaYear;
	}
	public String toString() {
		return ukaYear+" - "+startDate+" - "+endDate;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}


	
}
