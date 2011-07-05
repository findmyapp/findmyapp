package no.uka.findmyapp.model;

import java.sql.Timestamp;


public class Beertap {
	private int id;
	private int location;
	private float value;
	private int tapnr;
	private Timestamp time;
	
	public int getTapnr(){
		return tapnr;
	}
	public void setTapnr(int tapnr){
		this.tapnr = tapnr;
	}
	
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
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
