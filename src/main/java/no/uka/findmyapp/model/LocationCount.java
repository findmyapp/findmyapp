package no.uka.findmyapp.model;

public class LocationCount {
	private String locationName;
	private int userCount;
	
	public LocationCount() {}

	public LocationCount(String locationName, int userCount) {
		this.locationName = locationName;
		this.userCount = userCount;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

}