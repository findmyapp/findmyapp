package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;

public class LocationStatus {
	
	private	List<String> comments;
	private float fun_factor;
	private float dance_factor;
	private float flirt_factor;
	private float chat_factor;
	private float noise;
	private float temp;
	private float hum;
	private float beerTap;
	private float headCount;
	
	public LocationStatus(){//Setting default values upon construction
		this.fun_factor = -1;
		this.dance_factor = -1;
		this.flirt_factor = -1;
		this.chat_factor = -1;
		this.headCount = -1;
		this.noise = -1;
		this.temp = -1;
		this.hum = -1;
		this.beerTap = -1;
		this.comments = new ArrayList<String>();
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
		this.fun_factor = ff;
	}
	
	public float getFunFactor (){
		return this.fun_factor;
	}
	public void setDanceFactor(float df){
		this.dance_factor = df;
	}
	public float getDanceFactor (){
		return this.dance_factor;
	}
	public void setFlirtFactor(float ff){
		this.flirt_factor = ff;
	}
	public float getFlirtFactor (){
		return this.flirt_factor;
	}
	public void setChatFactor(float cf){
		this.chat_factor = cf;
	}
	public float getChatFactor (){
		return this.chat_factor;
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