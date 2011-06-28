package no.uka.findmyapp.model;

import java.util.List;

/** Class for test points
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class Sample {
	
	public int roomID;
	public List<Signal> signalList;
	
	public Sample() {
		super();
	}

	public Sample(int roomID, List<Signal> signalList) {
		super();
		this.roomID = roomID;
		this.signalList = signalList;
	}
	
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public List<Signal> getSignalList() {
		return signalList;
	}
	public void setSignalList(List<Signal> signalList) {
		this.signalList = signalList;
	}
	
	/** 
	 * 
	 * @param bssid
	 * @return the signal in the samples signallist with this bssid or null if no such signal exists.
	 */
	public Signal getSignal(String bssid){
		
		for (Signal s: signalList){
			if (s.getBssid().equals(bssid)) {
				return s;
			}
		}
		return null;
		
	}
	
	/** Calculates the Euclidean distance between the signal list of this sample taken from the DB and the signal list given in as a parameter. 
	 * 
	 * @param signalList list of signals detected by user
	 * @return Euclidean distance 
	 */
	public double getDistance(List<Signal> signalList){
		
		double d = 0;
		List<Signal> databaseSignalList = this.signalList;
		for (Signal s: databaseSignalList){ 
			double signalStrength = -120; // use signal strength of -120dB if no signal from access point
			for (Signal sig: signalList){
				if (sig.getBssid().equals(s.getBssid())) {
					signalStrength = sig.getLevel();
					break; //will jump out of inner for-loop
				} 
			} 
			double diff = s.getLevel() - signalStrength;
			diff = diff*diff;
			d += diff;	
		}
		
		return java.lang.Math.sqrt(d);
		
	}

}
