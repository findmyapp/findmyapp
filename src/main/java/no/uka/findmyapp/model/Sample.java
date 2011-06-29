package no.uka.findmyapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import no.uka.findmyapp.controller.PositionController;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.slf4j.LoggerFactory;

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
	
	
	/** Calculates the Euclidean distance between the signal list of this sample (taken from the DB) and the signal list given in as a parameter. 
	 * 
	 * @param signals list of signals detected by user
	 * @return Euclidean distance 
	 */
	public double getDistance(List<Signal> signals){
		double delta = 0;
		for (Signal storedSignal : getSignalList()){ 
			double signalStrength = -120; // use signal strength of -120dB if no signal from access point
			for (Signal inputSignal : signals){
				if (inputSignal.getBssid().equals(storedSignal.getBssid())) {
					signalStrength = inputSignal.getSignalStrength();
					break; //will jump out of inner for-loop
				} 
			} 
			double diff = storedSignal.getSignalStrength() - signalStrength;
			diff *= diff;
			delta += diff;	
		}
		
		return Math.sqrt(delta);
		
	}

}
