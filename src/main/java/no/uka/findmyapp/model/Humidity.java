package no.uka.findmyapp.model;

import java.util.Date;



public class Humidity {
	
	private int id;
	private int location;
	private float value;
	private Date date;
	private Date time;
	
	
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Date getTime() {
		return time;
	}
	
}
