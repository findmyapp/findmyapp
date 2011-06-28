package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/** Class for test points
 * 
 * @author Cecilie Haugstvedt
 *
 */
@JsonAutoDetect
public class Sample {
	
	private int Id;
	private int roomId;
	private List<Signal> signalList;
	
	public Sample() {
		signalList = new ArrayList<Signal>();
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public List<Signal> getSignalList() {
		return signalList;
	}
	public void setSignalList(List<Signal> signalList) {
		this.signalList = signalList;
	}
	
	/** 
	 * 
	 * @param Bssid
	 * @return the signal in the samples signallist with this Bssid or null if no such signal exists.
	 */
	public Signal getSignal(String Bssid){
		
		for (Signal s: signalList){
			if (s.getBssid().equals(Bssid)) {
				return s;
			}
		}
		return null;
		
	}
	
	/** Calculates the EuclIdean distance betwwen the signal list of this sample and the signal list given in as a parameter. 
	 * 
	 * @param signalList 
	 * @return EuclIdean distance 
	 */
	public double getDistance(List<Signal> signalList){
		
		double d = 0;
		
		for (Signal s : signalList){
			Signal sig = this.getSignal(s.getBssid());
			if (sig != null){
				double diff = s.getSignalStrength() - sig.getSignalStrength();
				diff = diff*diff;
				d += diff;
			} else {
				d += 1000; // max difference when signal not visible
			}
		}
		
		return Math.sqrt(d);
		
	}

}
