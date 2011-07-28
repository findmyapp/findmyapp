package no.uka.findmyapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import no.uka.findmyapp.datasource.CustomParameterRepository;
import no.uka.findmyapp.datasource.LocationRepository;
import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.CustomParameter;
import no.uka.findmyapp.model.CustomParameterDetailed;
import no.uka.findmyapp.model.LocationReport;
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
	public List<CustomParameterDetailed> listParameters(int developerId) {
		return data.findAllParametersDetaiedForDeveloperId(developerId);
	}


	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ConsumerException.class)
	private void handleConsumerException(ConsumerException e) {
		logger.debug(e.getMessage());
	}
}
