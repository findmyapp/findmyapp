package no.uka.findmyapp.model;

import java.sql.Timestamp;

public class User {

	private String facebookUserId; 
	private String localUserId; 
	private Timestamp userRegistered; 
	private Timestamp lastLogon;
	private Room lastKnownPosition;
	
	public User() {}
	
	public User(String facebookId) {
		this.facebookUserId = facebookId; 
	}
	
	public User(String facebookId, String localId, Timestamp registerTimestamp, Timestamp lastLogon) {
		this.facebookUserId = facebookId; 
		this.localUserId = localId; 
		this.userRegistered = registerTimestamp; 
		this.lastLogon = lastLogon; 
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
	
	public Timestamp getUserRegisteredTimestamp() {
		return userRegistered;
	}

	public void setUserRegistered(Timestamp ts) {
		this.userRegistered = ts;
	}

	public Timestamp getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(Timestamp lastLogon) {
		this.lastLogon = lastLogon;
	}

	public Room getLastKnownPosition() {
		return lastKnownPosition;
	}

	public void setLastKnownPosition(Room lastKnownPosition) {
		this.lastKnownPosition = lastKnownPosition;
	}

}
