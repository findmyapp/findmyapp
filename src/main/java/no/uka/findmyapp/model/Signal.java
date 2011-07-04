package no.uka.findmyapp.model;

/**
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class Signal {

	private int id;
	private String bssid;
	private int signalStrength;
	private int sampleId;
	
	public int getSampleId() {
		return sampleId;
	}

	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}

	public Signal () {}
	
	public Signal(String bssid, int signalStrength) {
		this.bssid = bssid;
		this.signalStrength = signalStrength;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
