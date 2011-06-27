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
	
	/** Calculates the Euclidean distance betwwen the signal list of this sample and the signal list given in as a parameter. 
	 * 
	 * @param signalList list of signals detected by user
	 * @return Euclidean distance 
	 */
	public double getDistance(List<Signal> signalList){
		
		double d = 0;
		
		//TODO: have to iterate over list from database instead
		for (Signal s: signalList){
			Signal sig = this.getSignal(s.getBssid());
			if (sig != null){
				double diff = s.getLevel() - sig.getLevel();
				diff = diff*diff;
				d += diff;
			} else {
				d += 1000; // max difference when signal not visible
			}
		}
		
		return java.lang.Math.sqrt(d);
		
	}

}
