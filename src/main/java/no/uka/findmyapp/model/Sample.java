package no.uka.findmyapp.model;

import java.util.List;

/** Class for test points
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class Sample {
	
	public String roomID;
	public List<Signal> bssidList;
	
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public List<Signal> getBssidList() {
		return bssidList;
	}
	public void setBssidList(List<Signal> bssidList) {
		this.bssidList = bssidList;
	}
	
	/** Calculates the Euclidean distance to the BSSID-list associated with this
	 * test point. Assumes that the lists of BSSIDs are in the same order
	 * 
	 * @param bssidList in defined order
	 * @return the Euclidean distance between bssidList and the list of bssids associated with this test points
	 */
	public double getDistance(List<Signal> bssidList){
		
		double d = 0;
		for (int i = 0; i < bssidList.size(); i++){
			double diff = this.bssidList.get(i).getLevel() - bssidList.get(i).getLevel();
			diff = diff*diff;
			d += diff;
		}
		return java.lang.Math.sqrt(d*d);
		
	}

}
