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
	public void setBssidList(List<Signal> signalList) {
		this.signalList = signalList;
	}
	
	/** Calculates the Euclidean distance to the BSSID-list associated with this
	 * test point. Assumes that the lists of BSSIDs are in the same order
	 * 
	 * @param bssidList in defined order
	 * @return the Euclidean distance between bssidList and the list of bssids associated with this test points
	 */
	public double getDistance(List<Signal> signalList){
		
		double d = 0;
		for (int i = 0; i < signalList.size(); i++){
			double diff = this.signalList.get(i).getLevel() - signalList.get(i).getLevel();
			diff = diff*diff;
			d += diff;
		}
		return java.lang.Math.sqrt(d*d);
		
	}

}
