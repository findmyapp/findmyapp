package no.uka.findmyapp.model;

/**
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class Signal {

	public int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String bssid;
	public int signalStrength;
	
	public Signal () {}
	
	public Signal(String bssid, int signalStrength) {
		this.bssid = bssid;
		this.signalStrength = signalStrength;
	}
	
	public String getBssid() {
		return bssid;
	}
	
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public int getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}
}
