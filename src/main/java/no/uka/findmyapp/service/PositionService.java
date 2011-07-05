package no.uka.findmyapp.service;

import java.util.List;

import no.uka.findmyapp.datasource.PositionDataRepository;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class with logic for calculating a users position.
 * 
 * @author Cecilie Haugstvedt
 * 
 */
// TODO: remove @Repository, and make sure the bean is still registered
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
	public Location getCurrentPosition(List<Signal> signals) {

		List<Sample> samples = data.getSamples();

		double minDistance = samples.get(0).getDistance(signals);
		
		int bestPosition = samples.get(0).getLocationId();
		System.out.println("NumOfSamples: "+samples.size());		
		for (Sample sam : samples) {
			double distance = getEuclideanDistance(sam, signals);
			if (distance < minDistance) {
				minDistance = distance;
				bestPosition = sam.getLocationId();
			}
		}		
		return (bestPosition != -1 ? data.getLocation(bestPosition) : null);
	}
	
	public boolean registerSample(Sample sample) {
		return data.registerSample(sample);
	}
	
	public boolean registerUserPosition(int user_id, int room_id) {
		return data.registerUserPosition(user_id, room_id);
	}
	
	public Location getUserPosition(int user_id) {
		return data.getUserPosition(user_id);
	}
	
	/** Calculates the Euclidean distance between the signal list of this sample (taken from the DB) and the signal list given in as a parameter. 
	 *  
	 *  Sample comes from data source, signal comes from service input
	 * 
	 * @param signals list of signals detected by user
	 * @return Euclidean distance 
	 */
	public double getEuclideanDistance(Sample sample, List<Signal> signals){
		double delta = 0;
		double numOfMatch = 0;
		for (Signal storedSignal : sample.getSignalList()){ 
			double diff = 120; // use signal strength of -120dB if no signal from access point
			for (Signal inputSignal : signals){
				if (inputSignal.getBssid().equals(storedSignal.getBssid())) {
					diff = Math.abs(storedSignal.getSignalStrength() - inputSignal.getSignalStrength());
					numOfMatch++;
					break; //will jump out of inner for-loop
				} 
			}
			diff *= diff;
			delta += diff;	
		}
		int numberOfTotalAccessPoints = data.totalNumOfAccesspoints();
		int notDiscoveredAccessPoints = numberOfTotalAccessPoints - sample.getSignalList().size();
		System.out.println(numberOfTotalAccessPoints+"(numOfAPs) - "+sample.getSignalList().size()+"(registeredAps) = "+notDiscoveredAccessPoints);
		delta += ((-120^2) * notDiscoveredAccessPoints);
		return Math.sqrt(delta);
		
	}
	
	public void getAllPositions() {
	}
}
