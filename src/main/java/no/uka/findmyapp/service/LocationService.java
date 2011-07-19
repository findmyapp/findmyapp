package no.uka.findmyapp.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import no.uka.findmyapp.datasource.LocationRepository;
import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationStatus;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

	@Autowired
	private LocationRepository data;

	public List<Location> getAllLocations() {
		return data.getAllLocations();
	}

	/*
	 * ************* POSITIONING *************
	 */

	public List<User> getUsersAtLocation(int locationId) {
		return data.getUsersAtLocation(locationId);
	}

	public int getUserCountAtLocation(int locationId){
		return data.getUserCountAtLocation(locationId);
	}
	
	public boolean registerSample(Sample sample) {
		return data.registerSample(sample);
	}

	public boolean registerUserLocation(int userId, int locationId) {
		return data.registerUserLocation(userId, locationId);
	}

	public Location getUserLocation(int userId) {
		return data.getUserLocation(userId);
	}

	public List<UserPosition> getLocationOfAllUsers() {
		return data.getLocationOfAllUsers();
	}

	public Location getLocationOfFriend(int friendId) {
		return data.getLocationOfFriend(friendId);
	}

	public Map<Integer, Integer> getLocationOfFriends(int userId) {
		return data.getLocationOfFriends(userId);
	}

	public Location getCurrentLocation(List<Signal> signals)
			throws LocationNotFoundException {

		List<Sample> samples = data.getSamples();
		int totalNumberOfAccessPoints = data.getTotalNumOfAccesspoints();

		double minDistance = Math.sqrt(totalNumberOfAccessPoints * 14400);
		int bestPosition = -1;

		for (Sample sam : samples) {
			double distance = getEuclideanDistance(sam, signals,
					totalNumberOfAccessPoints);
			if (distance < minDistance) {
				minDistance = distance;
				bestPosition = sam.getLocationId();
			}

		}

		if (bestPosition == -1)
			throw new LocationNotFoundException(
					"Best euclidean distance equals the worst case distance");
		return (bestPosition != -1 ? data.getLocation(bestPosition) : null);
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
	public double getEuclideanDistance(Sample sample, List<Signal> signals,
			int totalNumberOfAccessPoints) throws LocationNotFoundException {

		double sum = 0;
		for (Signal signalInput : signals) {

			double diff = 120;

			for (Signal signalRepo : sample.getSignalList()) {

				if (signalInput.getBssid().equals(signalRepo.getBssid())) {
					diff = Math.abs(signalRepo.getSignalStrength()
							- signalInput.getSignalStrength());
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

	/*
	 * **************** FACT *****************
	 */
	public void addData(LocationStatus locationStatus, int locationId){
		data.addData(locationId, locationStatus);
	}

	public List<Fact> getAllFacts(int locationId) {
		return data.getAllFacts(locationId);
	}

	public Fact getRandomFact(int locationId) {
		return data.getRandomFact(locationId);
	}

	public Location getData(int locationId) {//Still doesn't take into account noise, humidity and so on.
		List <LocationStatus> locationData = data.getData(locationId);
		LocationStatus locationStatus = aggregateData(locationData);
		Location locale = new Location();
		locale.setLocationId(locationId);
		//TODO: locale.setLocationName(locationName);
		locale.setLocationStatus(locationStatus);
		return locale;
	}
	
	private LocationStatus aggregateData(List<LocationStatus> dataList){//Takes average over the last 5 minutes
		
		LocationStatus aggregatedData = new LocationStatus();
		float funFactor = -1, chatFactor = -1, danceFactor= -1, flirtFactor = -1;
		int ffCount =0, cfCount = 0,dfCount = 0,flirtfCount = 0;
		
		Iterator<LocationStatus> li = dataList.iterator();
		while(li.hasNext()){
			LocationStatus current =  li.next();
			Iterator<String> currentCommentsIterator = current.getComments().iterator(); 
			while(currentCommentsIterator.hasNext()){
				aggregatedData.addComment(currentCommentsIterator.next());
			}
			 
			if(current.getFunFactor()!=-1){
				ffCount  = ffCount+1;
				funFactor = funFactor + current.getFunFactor();
			}
			if(current.getChatFactor()!=-1){
				cfCount = cfCount +1;
				chatFactor = chatFactor +current.getChatFactor();
			}	
			if(current.getDanceFactor()!=-1){
				dfCount = dfCount+1;
				danceFactor = danceFactor + current.getDanceFactor();
			}
			if(current.getFlirtFactor()!=-1){
				flirtfCount = flirtfCount +1;
				flirtFactor = flirtFactor + current.getFlirtFactor();
			}
			
		}
		
		if(ffCount !=0){
			aggregatedData.setFunFactor(funFactor/ffCount);
		}
		if(cfCount !=0){
			aggregatedData.setChatFactor(chatFactor/cfCount);
		}
		if(dfCount !=0){
			aggregatedData.setDanceFactor(danceFactor/dfCount);
		}
		if(flirtfCount !=0){
			aggregatedData.setFlirtFactor(flirtFactor/flirtfCount);
		}
		return aggregatedData;
	}

}
