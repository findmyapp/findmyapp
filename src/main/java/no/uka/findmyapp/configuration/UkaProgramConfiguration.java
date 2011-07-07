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
	Date startDate = new Date(1);
	Date endDate = new Date(10);
	String ukaYearForStartAndEndDate = "s";
	
	
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
	public String getUkaYearForStartAndEndDate() {
		return ukaYearForStartAndEndDate;
	}
	public void setUkaYearForStartAndEndDate(String ukaYearForStartAndEndDate) {
		this.ukaYearForStartAndEndDate = ukaYearForStartAndEndDate;
	}


	
}
