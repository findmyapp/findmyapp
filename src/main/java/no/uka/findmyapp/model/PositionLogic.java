package no.uka.findmyapp.model;

import java.util.List;

import no.uka.findmyapp.datasource.PositionDataHandler;

import org.springframework.beans.factory.annotation.Autowired;

/** Class with logic for calculating a users position.
 * 
 * @author Cecilie Haugstvedt
 * 
 */
public class PositionLogic {
	
	@Autowired
	private PositionDataHandler data;
	
	/**
	 * 
	 * @param bssidList List of visible BSSIDs with level
	 * @return current position
	 */
	public Room getCurrentPosition(List<Signal> bssidList) {
		
		List<Sample> samples = data.getAllTestPoints();
		double minDistance = samples.get(0).getDistance(bssidList);
		Room bestPosition = new Room(samples.get(0).getRoomID());
		for(Sample p: samples){
			double d = p.getDistance(bssidList);
			if (d < minDistance){
				minDistance = d;
				bestPosition = new Room(p.getRoomID());
			}
		}
		return bestPosition;
		
	}

}
