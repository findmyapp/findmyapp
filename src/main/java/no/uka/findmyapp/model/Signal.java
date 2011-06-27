package no.uka.findmyapp.model;

/**
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class Signal {

	public String bssid; ///BSSID
	public int signalStrength; //strength of signal
	
	public Signal() {
		super();
	}
	public Signal(String bssid, int signalStrength) {
		super();
		this.bssid = bssid;
		this.signalStrength = signalStrength;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public int getLevel() {
		return signalStrength;
	}
	public void setLevel(int level) {
		this.signalStrength = level;
	}
	
	
}
