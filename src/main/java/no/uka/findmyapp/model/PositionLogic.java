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
	public Position getCurrentPosition(List<BSSID> bssidList) {
		
		List<TestPoint> testPoints = data.getAllTestPoints();
		double minDistance = testPoints.get(0).getDistance(bssidList);
		Position bestPosition = new Position(testPoints.get(0).getRoomName(), testPoints.get(0).getRoomID());
		for(TestPoint p: testPoints){
			double d = p.getDistance(bssidList);
			if (d < minDistance){
				minDistance = d;
				bestPosition = new Position(p.getRoomName(), p.getRoomID());
			}
		}
		return bestPosition;
		
	}

}
