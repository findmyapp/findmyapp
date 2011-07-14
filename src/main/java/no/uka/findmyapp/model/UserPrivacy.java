package no.uka.findmyapp.model;

public class UserPrivacy {
	private int id;
	private PrivacySetting position;
	private PrivacySetting events;
	private PrivacySetting money;
	private PrivacySetting media;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PrivacySetting getPosition() {
		return position;
	}
	public void setPosition(PrivacySetting position) {
		this.position = position;
	}
	public PrivacySetting getEvents() {
		return events;
	}
	public void setEvents(PrivacySetting events) {
		this.events = events;
	}
	public PrivacySetting getMoney() {
		return money;
	}
	public void setMoney(PrivacySetting money) {
		this.money = money;
	}
	public PrivacySetting getMedia() {
		return media;
	}
	public void setMedia(PrivacySetting media) {
		this.media = media;
	}
	
	
	
}
