package no.uka.findmyapp.model;


public class Arduino {
	
	private String location;
	private String sensor;
	private int value;
	
	public Arduino() {
	
	}
	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
