package no.uka.findmyapp.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import no.uka.findmyapp.controller.auth.TokenException;
import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.CustomParameter;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationCount;
import no.uka.findmyapp.model.LocationReport;
import no.uka.findmyapp.model.ManageParameterRespons;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;
import no.uka.findmyapp.service.LocationService;
import no.uka.findmyapp.service.auth.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping
public class LocationController {

	@Autowired
	private LocationService service;
	@Autowired
	private AuthenticationService auth;

	private static final Logger logger = LoggerFactory
			.getLogger(LocationController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ServiceModelMapping(returnType = Location.class)
	public ModelAndView getAllLocations() {
		logger.debug("getAllLocations");
		List<Location> locations = service.getAllLocations();
		return new ModelAndView("json", "location", locations);
	}

	/*
	 * ************* POSITIONING *************
	 */

	@RequestMapping(method = RequestMethod.POST)
	@ServiceModelMapping(returnType = Location.class)
	public ModelAndView getPositionBasedOnWLANSignals(
			@RequestBody Signal[] signals) throws LocationNotFoundException {
		logger.debug("getCurrentLocation ( " + signals.length + " )");
		
		List<Signal> signalList = Arrays.asList(signals);
		Location location = service.getCurrentLocation(signalList);
		logger.debug("getCurrentPosition ( " + location + " )");
		
		return new ModelAndView("json", "location", location);
	}

	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = User.class)
	public ModelAndView getUsersAtLocation(@PathVariable("id") int locationId) {
		logger.debug("getUsersAtLocation ( " + locationId + ")");
		List<User> users = service.getUsersAtLocation(locationId);
		return new ModelAndView("json", "users_at_location", users);
	}

	@RequestMapping(value = "/{id}/users/count", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = int.class)
	public ModelAndView getUserCountAtLocation(
			@PathVariable("id") int locationId) {
		logger.debug("getUserCountAtLocation ( " + locationId + ")");
		int count = service.getUserCountAtLocation(locationId);
		return new ModelAndView("json", "usercount", count);
	}

	@RequestMapping(value = "/all/users/count", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = LocationCount.class)
	public ModelAndView getUserCountAtAllLocations() {
		List<LocationCount> count = service.getUserCountAtAllLocations();
		return new ModelAndView("json", "locationCount", count);
	}

	@Secured("ROLE_SAMPLER")
	@RequestMapping(value = "/sample", method = RequestMethod.POST)
	@ServiceModelMapping(returnType = boolean.class)
	public ModelAndView registerSample(@RequestBody Sample sample) {
		boolean regSample = service.registerSample(sample);
		logger.debug("registerSample ( " + regSample + " )");
		return new ModelAndView("json", "regSample", regSample);
	}

	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/{locationId}/users/{userId}", method = RequestMethod.PUT)
	@ServiceModelMapping(returnType = boolean.class)
	public ModelAndView registerUserLocation(
			@PathVariable int userId,
			@PathVariable int locationId,
			@RequestParam String token) throws TokenException {
		int tokenUserId = verifyToken(token);
		boolean regUserPos = false;
		if (tokenUserId == userId) {
			regUserPos = service.registerUserLocation(userId, locationId);
			logger.debug("Registering user postition for user " + userId);
		} else {
			throw new TokenException("Token and supplied user id did not match");
		}
		return new ModelAndView("json", "regUserPos", regUserPos);
	}

	private int verifyToken(String token) throws TokenException {
		int userId = auth.verify(token);
		if (userId == -1) {
			throw new TokenException("Invalid token");
		}
		return userId;
	}

	/*
	 * **************** FACT *****************
	 */
	@RequestMapping(value = "/{id}/facts", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = Fact.class)
	public ModelAndView getAllFacts(@PathVariable("id") int locationId) {
		logger.debug("getAllFacts ( " + locationId + " )");
		List<Fact> facts = service.getAllFacts(locationId);
		return new ModelAndView("json", "facts", facts);
	}

	@RequestMapping(value = "/{id}/facts/random", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = Fact.class)
	public ModelAndView getRandomFact(@PathVariable("id") int locationId) {
		Fact fact = service.getRandomFact(locationId);
		return new ModelAndView("json", "random_fact", fact);
	}

	@SuppressWarnings("unused")
	@ExceptionHandler(TokenException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Token did not match provided user id")
	private void handleTokenException(TokenException e) {
		logger.error(e.getMessage());
	}
	

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	private void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex) {
		logger.error(ex.getLocalizedMessage());
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	private void handleIncorrectResultSizeDataAccessException(
			IncorrectResultSizeDataAccessException ex) {
		logger.error(ex.getLocalizedMessage());
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(LocationNotFoundException.class)
	private void handleLocationNotFoundException(LocationNotFoundException ex) {
		logger.error(ex.getLocalizedMessage());
	}

	/*
	 * -------------------------------UserReporting-------------------------
	 */

	// COMMENT +++++
	// FETCHES EVERYTIHGN INCLUDING SENSOR DATA FRO LOCATION
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getLocationData(@PathVariable("id") int locationId) {
		Location locale = service.getAllData(locationId);
		logger.info("DEBUG",locale);
		return new ModelAndView("json", "location_real_time", locale);
	}

	@RequestMapping(value = "/{id}/userreports", method = RequestMethod.POST)
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
	@RequestMapping(value = "/{id}/userreports", method = RequestMethod.GET)
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

	@RequestMapping(value = "/parameters/add", method = RequestMethod.GET)
	public ModelAndView addParameter(
			@RequestParam String name) throws DataIntegrityViolationException{
		String devid = "1"; //replace this;
		boolean respons = service.addParameter(name, devid);
		return new ModelAndView("json", "reponse", respons);
	}
	
	@RequestMapping(value = "/parameters/remove", method = RequestMethod.GET)
	public ModelAndView removeParameter(// ADD ERROR HANDLING, max elem
			@RequestParam String name) {
		String devid = "1"; //replace this;
		boolean respons = service.removeParameter(name,devid);
		return new ModelAndView("json", "reponse", respons);
	}
	
	@RequestMapping(value = "/parameters/clean", method = RequestMethod.GET)
	public ModelAndView cleanParameter(// ADD ERROR HANDLING, max elem
			@RequestParam String name) {
		String devid = "1"; //replace this;
		boolean respons = service.cleanParameter(name, devid);
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