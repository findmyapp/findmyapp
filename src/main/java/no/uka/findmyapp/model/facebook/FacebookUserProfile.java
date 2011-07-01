package no.uka.findmyapp.model.facebook;

import java.lang.reflect.Type;
import java.util.Date; 

public class FacebookUserProfile {
	
	private int id; 
	private String name; 
	private String link; 
/*	private String firstName; 
	private String lastName; 
	private Date birthday;
    private Education education; 
*/  private String gender; 
/*	private int timezone; 
	private Locale locale; 
	private boolean verified; 
	private Date updatedTime;
*/ 
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
/*	public String getFirst_name() {
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
*/	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
/*	public int getTimezone() {
		return timezone;
	}
	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
*/
}
