package no.uka.findmyapp.model;

public class User {
		
	private String facebookUserId; 
	private String localUserId; 
	
	public User() {}
	
	public User(String facebookId, String localId) {
		this.facebookUserId = facebookId; 
		this.localUserId = localId; 
	}
	
	public String getFacebookUserId() {
		return facebookUserId;
	}

	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}

	public String getLocalUserId() {
		return localUserId;
	}

	public void setLocalUserId(String localUserId) {
		this.localUserId = localUserId;
	}
}
