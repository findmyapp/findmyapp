package no.uka.findmyapp.service;

import java.util.List;

import no.uka.findmyapp.datasource.PositionDataRepository;
import no.uka.findmyapp.model.Room;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class with logic for calculating a users position.
 * 
 * @author Cecilie Haugstvedt
 * 
 */
// TODO: remove @Repository, and make sure the bean is still registered
@Repository
public class PositionService {

	@Autowired
	private PositionDataRepository data;

	private static final Logger logger = LoggerFactory
	.getLogger(PositionService.class);
	
	
	/**
	 * 
	 * @param bssidList
	 *            List of visible BSSIDs with level
	 * @return current position
	 */
	public Room getCurrentPosition(List<Signal> signals) {

		List<Sample> samples = data.getAllSamples();
		logger.info("getAllSamplesNumber ( " + samples.size() + " )");

		double minDistance = samples.get(0).getDistance(signals);
		logger.info("signallist size: " + signals.size());
		
		int bestPosition = samples.get(0).getRoomId();
		for (Sample sam : samples) {
			double distance = sam.getDistance(signals);
			if (distance < minDistance) {
				minDistance = distance;
				bestPosition = sam.getRoomId();
			}
		}
		logger.info("getAllSamplesBestPostition ( " + bestPosition + " )");
		
		return (bestPosition != -1 ? data.getRoom(bestPosition) : null);
	}
}