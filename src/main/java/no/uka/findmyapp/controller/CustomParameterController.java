package no.uka.findmyapp.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import no.uka.findmyapp.model.CustomParameter;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationReport;
import no.uka.findmyapp.model.appstore.Developer;
import no.uka.findmyapp.service.CustomParameterService;
import no.uka.findmyapp.service.DeveloperService;
import no.uka.findmyapp.service.auth.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/* Controller that handles HTTP requests for location
 * 
 * @author Kåre Blakstad
 * 
 */
@Controller
public class CustomParameterController {

	@Autowired
	private CustomParameterService service;
	@Autowired
	private AuthenticationService auth;
	@Autowired
	private DeveloperService dev;

	private static final Logger logger = LoggerFactory
			.getLogger(CustomParameterController.class);

	
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.GET)
	public ModelAndView getLocationData(@PathVariable("id") int locationId) {
		Location locale = service.getAllData(locationId);
		logger.info("DEBUG",locale);
		return new ModelAndView("json", "location_real_time", locale);
	}
	

	/*
	 * -------------------------------UserReporting-------------------------
	 */
	


	@RequestMapping(value = "/locations/{id}/userreports", method = RequestMethod.POST)
	// add max limit per user.
	public ModelAndView addReport(@PathVariable("id") int locationId,
			@RequestBody LocationReport[] locationReport) {

		ModelAndView mav = new ModelAndView("ok_respons");
		logger.info("Status data logged for location: " + locationId);
		List<LocationReport> reportList = Arrays.asList(locationReport);
		service.addData(reportList, locationId);
		mav.addObject("respons", reportList);
		return mav;
	}

	// TODO COMMENT +++++++++++++
	@RequestMapping(value = "/locations/{id}/userreports", method = RequestMethod.GET)
	public ModelAndView getReports(
			@PathVariable("id") int locationId,// ADD ERROR HANDLING
			@RequestParam(required = false) String action,// average
			@RequestParam(required = false, defaultValue = "-1") int noe,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date from,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date to,
			@RequestParam(required = false) String parname) {
		try {
			
			List<LocationReport> reports = service.getReports(locationId, action, noe, from, to, parname);
			return new ModelAndView("json", "location_real_time", reports);
		}

		catch (Exception e) {
			logger.error("Could not get the requested data: " + e);
			return null;
		}
	}
	
	@RequestMapping(value = "/parameters", method = RequestMethod.GET)
	public ModelAndView listParameters() {

		List<CustomParameter> respons = service.listParameters();
		return new ModelAndView("json", "reponse", respons);
	}

	@RequestMapping(value = "/parameters/add", method = RequestMethod.PUT)
	public ModelAndView addParameter(
			@RequestParam String name) throws DataIntegrityViolationException{
		String consumerKey = auth.getConsumerDetails().getConsumerKey();
		Developer developer = dev.getDeveloperForConsumerKey(consumerKey);
		boolean respons = service.addParameter(name, developer.getDeveloperID());
		return new ModelAndView("json", "reponse", respons);
	}
	
	@RequestMapping(value = "/parameters/remove", method = RequestMethod.DELETE)
	public ModelAndView removeParameter(// ADD ERROR HANDLING, max elem
			@RequestParam String name) {
		String consumerKey = auth.getConsumerDetails().getConsumerKey();
		Developer developer = dev.getDeveloperForConsumerKey(consumerKey);
		boolean respons = service.removeParameter(name,developer.getDeveloperID());
		return new ModelAndView("json", "reponse", respons);
	}
	
	@RequestMapping(value = "/parameters/clean", method = RequestMethod.DELETE)
	public ModelAndView cleanParameter(// ADD ERROR HANDLING, max elem
			@RequestParam String name) {
		String consumerKey = auth.getConsumerDetails().getConsumerKey();
		Developer developer = dev.getDeveloperForConsumerKey(consumerKey);
		boolean respons = service.cleanParameter(name, developer.getDeveloperID());
		return new ModelAndView("json", "reponse", respons);
	}
	
	@SuppressWarnings("unused") 
	@ResponseStatus(value=HttpStatus.FORBIDDEN ,reason="Could not add parameter. Developer id not valid. ")
	@ExceptionHandler(DataIntegrityViolationException.class)
	private void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		logger.debug("handleDataIntegrityViolationException ( "
				+ ex.getLocalizedMessage() + " )");
	}
	
	@SuppressWarnings("unused") 
	@ResponseStatus(value=HttpStatus.FORBIDDEN ,reason="Could not add parameter. Parameter already exists")
	@ExceptionHandler(DuplicateKeyException.class)
	private void handleDuplicateKeyException(DuplicateKeyException ex) {
		logger.debug("handleDuplicateKeyException ( "
				+ ex.getLocalizedMessage() + " )");
	}
	

	@SuppressWarnings("unused") 
	@ResponseStatus(value=HttpStatus.FORBIDDEN ,reason="The operation could not be completed. No access.")
	@ExceptionHandler(DataAccessException.class)
	private void handleDataAccessException(DataAccessException ex) {
		logger.debug("handleDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}
}