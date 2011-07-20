package no.uka.findmyapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import no.uka.findmyapp.datasource.LocationRepository;
import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationReport;
import no.uka.findmyapp.model.LocationStatus;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.Temperature;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

	@Autowired
	private LocationRepository data;
	@Autowired
	private SensorRepository sensor;

	private static final Logger logger = LoggerFactory
	.getLogger(UkaProgramRepository.class);
	
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
	public void addData(List<LocationReport> reportList, int locationId){
		Iterator<LocationReport> reportIterator = reportList.iterator();
		while(reportIterator.hasNext()){
			LocationReport locationReport = reportIterator.next();
			data.addData(locationReport, locationId);
		}
		
	}

	public List<Fact> getAllFacts(int locationId) {
		return data.getAllFacts(locationId);
	}

	public Fact getRandomFact(int locationId) {
		return data.getRandomFact(locationId);
	}

	public Location getAllData(int locationId) {//Creates and returns a location object with all the latest data on the location
		Location locationOfInterest = data.getLocation(locationId);
		int time = -10;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE,time);
		Date tenminago = cal.getTime();
		
		//Fetching data:
		List <LocationReport> last10usercomments = getReports(locationId,null,10,null,null,"comment");
		List <LocationReport> averagefun = getReports(locationId,"average",0,tenminago,null,"fun_factor");
		List <LocationReport> averagechat = getReports(locationId,"average",0,tenminago,null,"chat_factor");
		List <LocationReport> averagedance = getReports(locationId,"average",0,tenminago,null,"dance_factor");
		List <LocationReport> averageflirt = getReports(locationId,"average",0,tenminago,null,"flirt_factor");
		
		Noise noise = sensor.getLatestNoiseData(locationId);
		Temperature temp = sensor.getLatestTemperatureData(locationId);
		Humidity hum = sensor.getLatestHumidityData(locationId);
		int beerTappedOnLocation = sensor.getBeertapSum(locationId);
		int headcount = data.getUserCountAtLocation(locationId);
		
		//Creating location status from all data
		LocationStatus statusAtLocation = new LocationStatus();
		statusAtLocation.setBeerTap(beerTappedOnLocation);
		statusAtLocation.setNoise((float)noise.getAverage());
		statusAtLocation.setHumidity(hum.getValue());
		statusAtLocation.setTemperature(temp.getValue());
		statusAtLocation.setHeadCount(headcount);
		statusAtLocation.setChatFactor(averagechat.get(0).getParameterNumberValue());
		statusAtLocation.setFlirtFactor(averageflirt.get(0).getParameterNumberValue());
		statusAtLocation.setFunFactor(averagefun.get(0).getParameterNumberValue());
		statusAtLocation.setDanceFactor(averagedance.get(0).getParameterNumberValue());
		Iterator<LocationReport> comments = last10usercomments.iterator();
		while (comments.hasNext()){
			statusAtLocation.addComment(comments.next().getParameterTextValue());
		}
		
		//Fill location with status
		locationOfInterest.setLocationStatus(statusAtLocation);
		
		return locationOfInterest;
	}
	

	  

	  

	 
	

	public List<LocationReport> getReports(int locationId, String action, int numberOfelements, Date from, Date to, String parName)throws IllegalArgumentException {
		List <LocationReport> reportedData =null;
				
		if(numberOfelements >0 && from ==null && to ==null){
			 reportedData = data.getLastUserReportedData(locationId, numberOfelements,parName);
		}else  if(from != null){
			if(to != null){
			 reportedData = data.getUserReportedDataFromTo(locationId, from,to,parName);
			}else{reportedData = data.getUserReportedDataFrom(locationId, from,parName);}
		
		}else if(from ==null && to == null && numberOfelements ==0){
			reportedData = data.getUserReportedData(locationId,parName);
		}
		else{
			throw new IllegalArgumentException("Read API for what arguments are allowed");
		}
		if(action.equals("average")){//find average
			List <LocationReport> averageData = averageData(reportedData,parName);
			return averageData;
		}//Return the data raw
		else if(action.equals(null)){
			return reportedData;
		}//something is wrong if you get here.
		else{
			throw new IllegalArgumentException("Read API for what arguments are allowed");
		}
		
		
	}
	/**
	 * Finds the average of the number value of a list of LocationReports
	 * @param reportedData
	 * @param parName
	 * @return averageData
	 */

	private List<LocationReport> averageData(List<LocationReport> reportedData,String parName) {
		if(reportedData == null){//No data was acquired from DB
			return null;
		}
		List<LocationReport> averageData = new ArrayList<LocationReport>();//DOES THIS WORK?
		LocationReport averageReport = new LocationReport();
		Iterator<LocationReport> reports = reportedData.iterator();
		int counter = 1;
		float paramvalue =0;
		
		while(reports.hasNext()){//iterates through 
		 	LocationReport current=reports.next();
		 	if(current.getParameterNumberValue()!= -1){//-1 means value is not set.
		 		float value = current.getParameterNumberValue();
		 		paramvalue = paramvalue + value; 
			 	counter++;
		 	}
		 }
		float finalvalue = -1;
		if(paramvalue !=-1){finalvalue = paramvalue/counter; }//check that value has been changed from default
		averageReport.setParameterName(parName);
		averageReport.setParameterNumberValue(finalvalue);
		averageData.add(averageReport);
		return averageData;
	}

	
	public void manageParams(String action, String parName) throws IllegalArgumentException{//must also check dev id, and clean string.
		if(action.equals("add")){
			data.addParameter(parName);
		}else if(action.equals("remove")){
			data.removeParameter(parName);
		}else if(action.equals("clean")){
			data.cleanParameter(parName);
		}else{throw new IllegalArgumentException("Read API for what arguments are allowed");}
	}
	
}
