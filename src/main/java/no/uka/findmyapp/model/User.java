package no.uka.findmyapp.model;

import java.sql.Timestamp;

public class User {

	private String facebookUserId; 
	private int localUserId; 
	private Timestamp userRegistered; 
	private Timestamp lastLogon;
	private Location lastKnownPosition;
	private UserPrivacy userPrivacy;
	private String fullName;
	
	public User() {}
	
	public User(String facebookId) {
		this.facebookUserId = facebookId; 
	}
	
	public User(String facebookId, int localId, Timestamp registerTimestamp, Timestamp lastLogon, UserPrivacy userPrivacy ) {
		this.facebookUserId = facebookId; 
		this.localUserId = localId; 
		this.userRegistered = registerTimestamp; 
		this.lastLogon = lastLogon; 
		this.userPrivacy = userPrivacy;
	}
	
	//author: Haakon Bakka
	// I could not use this code as it calls the repository layer, but someone may need these methods.
//	public User(int facebookId, int localId, Timestamp registerTimestamp, Timestamp lastLogon, int userPrivacyId ) {
//		this.facebookUserId = facebookId; 
//		this.localUserId = localId; 
//		this.userRegistered = registerTimestamp; 
//		this.lastLogon = lastLogon;
//		UserService data = new UserService();
//		this.userPrivacy = data.retrievePrivacy(userPrivacyId);
//	}
//	public User(int facebookId, int localId, Timestamp registerTimestamp, Timestamp lastLogon) {
//		this.facebookUserId = facebookId; 
//		this.localUserId = localId; 
//		this.userRegistered = registerTimestamp; 
//		this.lastLogon = lastLogon;
//		UserService data = new UserService();
//		this.userPrivacy = data.createDefaultPrivacySettingsEntry();
//	}
	

	public String getFacebookUserId() {
		return facebookUserId;
	}

	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}

	public int getLocalUserId() {
		return localUserId;
	}

	public void setLocalUserId(int localUserId) {
		this.localUserId = localUserId;
	}

	public Timestamp getUserRegistered() {
		return userRegistered;
	}

	public void setUserRegistered(Timestamp userRegistered) {
		this.userRegistered = userRegistered;
	}

	public Timestamp getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(Timestamp lastLogon) {
		this.lastLogon = lastLogon;
	}

	public Location getLastKnownPosition() {
		return lastKnownPosition;
	}

	public void setLastKnownPosition(Location lastKnownPosition) {
		this.lastKnownPosition = lastKnownPosition;
	}

	public UserPrivacy getUserPrivacy() {
		return userPrivacy;
	}

	public void setUserPrivacy(UserPrivacy userPrivacy) {
		this.userPrivacy = userPrivacy;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}
