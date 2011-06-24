package no.uka.findmyapp.model;
/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
public class Position {

	public String name;
	public String strength;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	public String SSID;
}
