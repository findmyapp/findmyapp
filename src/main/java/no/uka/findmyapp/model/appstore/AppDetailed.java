package no.uka.findmyapp.model.appstore;

public class AppDetailed extends App{ 
	
	private Developer developer;
	private String consumerKey;
	private String consumerSecret;
	
	public AppDetailed() {
	}
	public Developer getDeveloper() {
		return developer;
	}
	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}
	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getConsumerSecret() {
		return consumerSecret;
	}
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
	@Override
	public String toString() {
		
		return super.toString() + "AppDetailed [developer=" + developer + ", consumerKey="
				+ consumerKey + ", consumerSecret=" + consumerSecret + "]";
	}
	
}