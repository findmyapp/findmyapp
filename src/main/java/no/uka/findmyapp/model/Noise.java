package no.uka.findmyapp.model;

import java.util.Date;


/*Each object is a measurement of the noise in a certain location at a certain time.
 * 
 */
public class Noise {
	private int id;
	private int location; 
	private float decibel;	
	private int raw_average;	// raw data average over time step
	private int raw_max;    // raw max over in time step
	private int raw_min;	// raw min over time step
	private Date date; 
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	public void setLocation(int location ){
		this.location=location;
	}
	
	public int getLocation(){
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
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date=date;
	}

}
