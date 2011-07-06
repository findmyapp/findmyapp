package no.uka.findmyapp.model;

import java.util.Date;


/*Each object is a measurement of the noise in a certain location at a certain time.
 * 
 */
public class Noise {
	private int id;
	private int location; 
	private int[] sample;

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
	
	public void setSample(int[] sample){
		for (int i= 0; i< sample.length; i++){
			this.sample[i] = sample[i];
		}
	}
	
	public int[] getSample(){
		return sample;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date=date;
	}

}
