package no.uka.findmyapp.model;

public class UserPrivacy {
	public static final int ALL = 1;
	public static final int FRIENDS = 2;
	public static final int NONE = 3;
	private int id;
	private int position;
	private int events;
	private int money;
	private int media;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getEvents() {
		return events;
	}
	public void setEvents(int events) {
		this.events = events;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	} 
	public int getMedia() {
		return media;
	}
	public void setMedia(int media) {
		this.media = media;
	} 

}
