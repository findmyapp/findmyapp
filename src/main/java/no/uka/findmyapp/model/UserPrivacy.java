package no.uka.findmyapp.model;

public class UserPrivacy {
	private int id;
	private PrivacySetting positionPrivacySetting;
	private PrivacySetting eventsPrivacySetting;
	private PrivacySetting moneyPrivacySetting;
	private PrivacySetting mediaPrivacySetting;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PrivacySetting getPositionPrivacySetting() {
		return positionPrivacySetting;
	}
	public void setPositionPrivacySetting(PrivacySetting positionPrivacySetting) {
		this.positionPrivacySetting = positionPrivacySetting;
	}
	public PrivacySetting getEventsPrivacySetting() {
		return eventsPrivacySetting;
	}
	public void setEventsPrivacySetting(PrivacySetting eventsPrivacySetting) {
		this.eventsPrivacySetting = eventsPrivacySetting;
	}
	public PrivacySetting getMoneyPrivacySetting() {
		return moneyPrivacySetting;
	}
	public void setMoneyPrivacySetting(PrivacySetting moneyPrivacySetting) {
		this.moneyPrivacySetting = moneyPrivacySetting;
	}
	public PrivacySetting getMediaPrivacySetting() {
		return mediaPrivacySetting;
	}
	public void setMediaPrivacySetting(PrivacySetting mediaPrivacySetting) {
		this.mediaPrivacySetting = mediaPrivacySetting;
	}
	
	


	
}
