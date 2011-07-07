package no.uka.findmyapp.model.appstore;

public class AppDetailed extends App{ 
	
	private Developer developer;

	public AppDetailed() {
	}
	public Developer getDeveloper() {
		return developer;
	}
	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}
	
}