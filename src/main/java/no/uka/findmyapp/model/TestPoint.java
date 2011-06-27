package no.uka.findmyapp.model;

import java.util.List;

/** Class for test points
 * 
 * @author Cecilie Haugstvedt
 *
 */
public class TestPoint {
	
	public String roomID;
	public String roomName;
	public List<BSSID> bssidList;
	
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public List<BSSID> getBssidList() {
		return bssidList;
	}
	public void setBssidList(List<BSSID> bssidList) {
		this.bssidList = bssidList;
	}
	
	/**
	 * 
	 * @param bssidList 
	 * @return the Euclidean distance between bssidList and the list of bssids associated with this test points
	 */
	public double getDistance(List<BSSID> bssidList){
		return 0;
		
	}

}
