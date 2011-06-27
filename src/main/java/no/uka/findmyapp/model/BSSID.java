package no.uka.findmyapp.model;

/**
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class BSSID {

	public String bssid; ///BSSID
	public int level; //strength of signal
	
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
