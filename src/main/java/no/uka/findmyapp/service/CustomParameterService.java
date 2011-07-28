package no.uka.findmyapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import no.uka.findmyapp.datasource.CustomParameterRepository;
import no.uka.findmyapp.datasource.LocationRepository;
import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.CustomParameter;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationCount;
import no.uka.findmyapp.model.LocationReport;
import no.uka.findmyapp.model.LocationStatus;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.Temperature;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.service.auth.ConsumerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class CustomParameterService {

	@Autowired
	private CustomParameterRepository data;
	@Autowired
	private LocationRepository locationData;
	@Autowired
	private SensorRepository sensor;
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(CustomParameterService.class);



	public void addData(List<LocationReport> reportList, int locationId) {
		Iterator<LocationReport> reportIterator = reportList.iterator();
		while (reportIterator.hasNext()) {
			LocationReport locationReport = reportIterator.next();
			data.addData(locationReport, locationId);
		}

	}

	public Location getAllData(int locationId) {// Creates and returns a
												// location object with average
												// of all the latest data on the
												// location
		Location locationOfInterest = locationData.getLocation(locationId);
		// int time = -10;
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.MINUTE,time);
		// Date tenminago = cal.getTime();

		// Fetching data:
		List<LocationReport> last10usercomments = getReports(locationId, null,
				10, null, null, "comment");
		List<LocationReport> averagefun = getReports(locationId, "average", 10,
				null, null, "fun_factor");
		List<LocationReport> averagechat = getReports(locationId, "average",
				10, null, null, "chat_factor");
		List<LocationReport> averagedance = getReports(locationId, "average",
				10, null, null, "dance_factor");
		List<LocationReport> averageflirt = getReports(locationId, "average",
				10, null, null, "flirt_factor");

		List<Noise> noiselist = sensor.getLatestNoiseData(locationId, 1);
		List<Temperature> templist = sensor.getLatestTemperatureData(
				locationId, 1);
		List<Humidity> humlist = sensor.getLatestHumidityData(locationId, 1);
		Noise noise = null;
		Humidity hum = null;
		Temperature temp = null;
		int beerTappedOnLocation = sensor.getBeertapSum(locationId);
		int headcount = locationData.getUserCountAtLocation(locationId);

		if (!noiselist.isEmpty()) {
			noise = noiselist.get(0);
		}
		if (!humlist.isEmpty()) {
			hum = humlist.get(0);
		}
		if (!templist.isEmpty()) {
			temp = templist.get(0);
		}

		// Creating location status from all data
		LocationStatus statusAtLocation = new LocationStatus();
		if (beerTappedOnLocation != -1) {
			statusAtLocation.setBeerTap(beerTappedOnLocation);
		}
		if (noise != null) {
			statusAtLocation.setNoise((float) noise.getAverage());
		}
		if (hum != null) {
			statusAtLocation.setHumidity(hum.getValue());
		}
		if (temp != null) {
			statusAtLocation.setTemperature(temp.getValue());
		}
		if (headcount != -1) {
			statusAtLocation.setHeadCount(headcount);
		}
		if (averagechat != null) {
			statusAtLocation.setChatFactor(averagechat.get(0)
					.getParameterNumberValue());
		}
		if (averageflirt != null) {
			statusAtLocation.setFlirtFactor(averageflirt.get(0)
					.getParameterNumberValue());
		}
		if (averagefun != null) {
			statusAtLocation.setFunFactor(averagefun.get(0)
					.getParameterNumberValue());
		}
		if (averagedance != null) {
			statusAtLocation.setDanceFactor(averagedance.get(0)
					.getParameterNumberValue());
		}
		if (last10usercomments != null) {
			Iterator<LocationReport> comments = last10usercomments.iterator();
			while (comments.hasNext()) {
				statusAtLocation.addComment(comments.next()
						.getParameterTextValue());
			}
		}

		// Fill location with status
		locationOfInterest.setLocationStatus(statusAtLocation);

		return locationOfInterest;
	}

	public List<LocationReport> getReports(int locationId, String action,
			int numberOfelements, Date from, Date to, String parName)
			throws IllegalArgumentException {

		List<LocationReport> reportedData = new ArrayList<LocationReport>();
		logger.info("data:" + locationId + "," + numberOfelements);

		if (numberOfelements > 0 && from == null && to == null) {
			// Fetch latest reports with paraName x
			logger.info("got in!");
			reportedData = data.getLastUserReportedData(locationId,
					numberOfelements, parName);

		} else if (from != null && numberOfelements == -1) {
			logger.info("got in date!");
			if (to != null) {
				logger.info("got in from to!");
				reportedData = data.getUserReportedDataFromTo(locationId, from,
						to, parName);
			} else {
				logger.info("got in from!");
				reportedData = data.getUserReportedDataFrom(locationId, from,
						parName);
			}

		} else if (from == null && to == null && numberOfelements == -1
				&& parName != null) {
			reportedData = data.getUserReportedData(locationId, parName);
		} else {
			throw new IllegalArgumentException(
					"Read API for what arguments are allowed");
		}
		if (action == null) {
			return reportedData;
		}// Return the data raw
		else if (action.equals("average")) {// find average
			List<LocationReport> averageData = averageData(reportedData,
					parName);
			return averageData;
		}// something is wrong if you get here.
		else {
			throw new IllegalArgumentException(
					"Read API for what arguments are allowed");
		}

	}

	/**
	 * Finds the average of the number value of a list of LocationReports
	 * 
	 * @param reportedData
	 * @param parName
	 * @return averageData
	 */

	private List<LocationReport> averageData(List<LocationReport> reportedData,
			String parName) {
		if (reportedData == null) {// No data was acquired from DB
			return null;
		}
		if (reportedData.isEmpty()) {// Making sure we don't proceed when no
										// data was acquired from DB
			return null;
		}
		List<LocationReport> averageData = new ArrayList<LocationReport>();
		LocationReport averageReport = new LocationReport();
		Iterator<LocationReport> reports = reportedData.iterator();
		int counter = 0;
		float paramvalue = 0;

		while (reports.hasNext()) {// iterates through
			LocationReport current = reports.next();
			if (current.getParameterNumberValue() != -1) {// -1 means value is
															// not set.
				float value = current.getParameterNumberValue();
				paramvalue = paramvalue + value;

				counter++;

			}
		}
		float finalvalue = -1;
		if (paramvalue != -1) {
			finalvalue = (paramvalue) / counter;
		}// check that value has been changed from default
		averageReport.setParameterName(parName);
		averageReport.setParameterNumberValue(finalvalue);
		averageData.add(averageReport);
		return averageData;
	}

	public int addParameter(String parameterName,
			int devId) throws DataAccessException {
		return data.addParameter(parameterName, devId);
	}

	public boolean removeParameter(String parameterName,
			int devId) throws DataAccessException {
		return data.removeParameter(parameterName, devId);
	}

	public boolean cleanParameter(String parameterName,
			int devId) throws DataAccessException {
		return data.cleanParameter(parameterName, devId);
	}

	public List<CustomParameter> listParameters() {
		return data.findAllParameters();
	}
	public List<CustomParameter> listParameters(int developerId) {
		return data.findAllParameters(developerId);
	}


	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ConsumerException.class)
	private void handleConsumerException(ConsumerException e) {
		logger.debug(e.getMessage());
	}
}
