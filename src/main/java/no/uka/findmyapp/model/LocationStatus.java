package no.uka.findmyapp.model;

public class LocationStatus {
	
	private	float queueLength;
	private float funFactor;
	private float danceFactor;
	private float flirtFactor;
	private float chatFactor;
	private Noise noise;
	private Temperature temperature;
	private Humidity humidity;
	private BeerTap beerTap;
	private float headCount;
	
	public LocationStatus(){//Setting default values upon construction
		this.queueLength = -1;
		this.funFactor = -1;
		this.danceFactor = -1;
		this.flirtFactor = -1;
		this.chatFactor = -1;
		this.humidity = new Humidity();
		this.noise = new Noise();
		this.temperature = new Temperature();
		this.beerTap = new BeerTap();
		this.headCount = -1;
				
	}
	public void setQueueLegth(float queueLength){
		this.queueLength = queueLength;
	}
	
	public float getQueueLength(){
		return this.queueLength;
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
	public void setNoise(Noise noise){
		this.noise = noise;
	}
	public Noise getNoise (){
		return this.noise;
	}
	public void setHumidity(Humidity hum){
		this.humidity = hum;
	}
	public Humidity getHumidity (){
		return this.humidity;
	}
	public void setTemperature(Temperature temp){
		this.temperature = temp;
	}
	public Temperature getTemperature(){
		return temperature;
	}
	public void setBeerTap(BeerTap bt){
		this.beerTap = bt;
	}
	public BeerTap getBeerTap(){
		return this.beerTap;
	}
}