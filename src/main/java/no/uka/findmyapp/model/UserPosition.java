package no.uka.findmyapp.model;

import java.sql.Timestamp;

public class UserPosition {

	private int userId;
	private int locationId;
	private Timestamp timestamp;
	//private Timestamp checkOutTimestamp;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int user) {
		this.userId = user;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int location) {
		this.locationId = location;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
/*
	public Timestamp getCheckOutTimestamp() {
		return checkOutTimestamp;
	}

	public void setCheckOutTimestamp(Timestamp checkOutTimestamp) {
		this.checkOutTimestamp = checkOutTimestamp;
	}
	*/
}