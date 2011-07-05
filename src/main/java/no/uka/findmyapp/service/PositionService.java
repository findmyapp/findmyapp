package no.uka.findmyapp.service;

import java.util.List;

import no.uka.findmyapp.datasource.PositionDataRepository;
import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.UserPosition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 
 * @author Cecilie Haugstvedt
 * 
 */
@Service
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
	public Location getCurrentPosition(List<Signal> signals) throws LocationNotFoundException {
		
		List<Sample> samples = data.getSamples();
		int totalNumberOfAccessPoints = data.getTotalNumOfAccesspoints();
		
		double minDistance = Math.sqrt(totalNumberOfAccessPoints * 14400);
		int bestPosition = -1;
		
		for (Sample sam : samples) {
			double distance = getEuclideanDistance(sam, signals, totalNumberOfAccessPoints);
			if (distance < minDistance) {
				minDistance = distance;
				bestPosition = sam.getLocationId();
			}

		}
		
		if (bestPosition == -1) throw new LocationNotFoundException("Best euclidean distance equals the worst case distance");
		return (bestPosition != -1 ? data.getLocation(bestPosition) : null);
	}

	public boolean registerSample(Sample sample) {
		return data.registerSample(sample);
	}

	public boolean registerUserPosition(int userId, int locationId) {
		return data.registerUserPosition(userId, locationId);
	}
	
	public Location getUserPosition(int userId) {
		return data.getUserPosition(userId);
	}

	/**
	 * Calculates the Euclidean distance between the signal list of this sample
	 * (taken from the DB) and the signal list given in as a parameter.
	 * 
	 * Sample comes from data source, signal comes from service input
	 * 
	 * @param signals
	 *            list of signals detected by user
	 * @return Euclidean distance
	 */

	public double getEuclideanDistance(Sample sample, List<Signal> signals, int totalNumberOfAccessPoints)
			throws LocationNotFoundException {
		
		double sum = 0;
		for (Signal signalInput : signals) {
			
			double diff = 120;
			
			for (Signal signalRepo : sample.getSignalList()) {
				
				if (signalInput.getBssid().equals(signalRepo.getBssid())) {
					diff = Math.abs(signalRepo.getSignalStrength() - signalInput.getSignalStrength());
					break;
				}
				
			}
			diff *= diff;
			sum += diff;
		}
		sum += (totalNumberOfAccessPoints - signals.size()) * 14400;
		
		System.out.println("Worst case: " + totalNumberOfAccessPoints * 14400);
		System.out.println("Actual: " + sum);
		
		return Math.sqrt(sum);
	}

	public List<UserPosition> getPositionOfAllUsers() {
		return data.getPositionOfAllUsers();
	}
	
}
