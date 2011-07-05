package no.uka.findmyapp.model;

import java.sql.Timestamp;


/*Each object is a measurement of the noise in a certain location at a certain time.
 * 
 */
public class Noise {
	private int id;
	private String location; 
	private float decibel;	
	private int raw_average;	// raw data average over time step
	private int raw_max;    // raw max over in time step
	private int raw_min;	// raw min over time step
	private Timestamp time; 
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	public void setLocation(String location ){
		this.location=location;
	}
	
	public String getLocation(){
		return location;
	}
		
	public void setDecibel(float db){
		this.decibel=db;
	}
	
	public float getDecibel(){
		return decibel;
	}	
	
	public void setRawAverage(int raw ){
		this.raw_average = raw;
	}
	
	public int getRawAverage(){
		return raw_average;
	}

	public void setRawMax(int max ){
		this.raw_max=max;
	}
	
	public int getRawMax(){
		return raw_max;
	}

	public void setRawMin(int min ){
		this.raw_min = min;
	}
	
	public int getRawMin(){
		return raw_min;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
