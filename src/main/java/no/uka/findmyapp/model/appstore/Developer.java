package no.uka.findmyapp.model.appstore;

public class Developer{
	private String fullName;
	private int developerID;
	private int wpId;
	private int userId;
	private String email;
	private String accessToken;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getDeveloperID() {
		return developerID;
	}
	public void setDeveloperID(int developerID) {
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
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	@Override
	public String toString() {
		return "Developer [fullName=" + fullName + ", developerID="
				+ developerID + ", wpId=" + wpId + ", userId=" + userId
				+ ", email=" + email + "]";
	}
	
}