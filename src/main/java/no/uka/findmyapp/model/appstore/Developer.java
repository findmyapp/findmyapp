package no.uka.findmyapp.model.appstore;

public class Developer{
	private String fullName;
	private String developerID;
	private int wpId;
	private int userId;
	private String email;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDeveloperID() {
		return developerID;
	}
	public void setDeveloperID(String developerID) {
		this.developerID = developerID;
	}
	public int getWpId() {
		return wpId;
	}
	public void setWpId(int wpId) {
		this.wpId = wpId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Developer [fullName=" + fullName + ", developerID="
				+ developerID + ", wpId=" + wpId + ", userId=" + userId
				+ ", email=" + email + "]";
	}
	
}