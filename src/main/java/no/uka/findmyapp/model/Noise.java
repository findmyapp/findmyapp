package no.uka.findmyapp.model;

import java.lang.reflect.Type;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/*Each object is a measurement of the noise in a certain location at a certain time.
 * 
 */
public class Noise {
	private int id;
	private int location; 
	private double average;
	private int max;
	private int min;
	private double standardDeviation;
	private int[] samples;
	
	private Gson gson = new Gson();
	
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

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date=date;
	}

	public void setAverage(double average) {
		this.average = average;
		
	}

	public double getAverage() {
		return average;
	}

	public void setMax(int max) {
		this.max = max;
		
	}

	public void setMin(int min) {
		this.min = min;
		
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
		
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public int[] getSamples() {
		return samples;
	}

	public void setSamples(int[] samples) {
		this.samples = samples;
	}
	
	public void setJsonSamples(String samples){
		Type typeToken = new TypeToken<Integer[]>(){}.getType();
		this.samples = (int[])gson.fromJson(samples,typeToken);
	}
	public String getJsonSamples(){
		return gson.toJson(this.samples);
	}
	public int getNumberOfSamples(){
		return samples.length;
	}

}
