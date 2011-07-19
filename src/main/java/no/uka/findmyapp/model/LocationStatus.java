package no.uka.findmyapp.model;

import java.util.List;

public class LocationStatus {
	
	private	List<String> comments;
	private float funFactor;
	private float danceFactor;
	private float flirtFactor;
	private float chatFactor;
	private float noise;
	private float temp;
	private float hum;
	private float beerTap;
	private float headCount;
	
	public LocationStatus(){//Setting default values upon construction
		this.funFactor = -1;
		this.danceFactor = -1;
		this.flirtFactor = -1;
		this.chatFactor = -1;
		this.headCount = -1;
				
	}
	public void setComment(List<String> comments){
		this.comments = comments;
	}
	
	public List<String> getComments(){
		return this.comments;
	}
	public void addComment(String comment) {
		comments.add(comment);
		
	}
	public void setFunFactor(float ff){
		this.funFactor = ff;
	}
	
	public float getFunFactor (){
		return this.funFactor;
	}
	public void setDanceFactor(float df){
		this.danceFactor = df;
	}
	public float getDanceFactor (){
		return this.danceFactor;
	}
	public void setFlirtFactor(float ff){
		this.flirtFactor = ff;
	}
	public float getFlirtFactor (){
		return this.flirtFactor;
	}
	public void setChatFactor(float cf){
		this.chatFactor = cf;
	}
	public float getChatFactor (){
		return this.chatFactor;
	}
	public void setHeadCount(float hc){
		this.headCount = hc;
	}
	public float getHeadCount(){
		return this.headCount;
	}
	public void setNoise(float noise){
		this.noise = noise;
	}
	public float getNoise (){
		return this.noise;
	}
	public void setHumidity(float hum){
		this.hum = hum;
	}
	public float getHumidity (){
		return this.hum;
	}
	public void setTemperature(float temp){
		this.temp = temp;
	}
	public float getTemperature(){
		return temp;
	}
	public void setBeerTap(float bt){
		this.beerTap = bt;
	}
	public float getBeerTap(){
		return this.beerTap;
	}
	
}