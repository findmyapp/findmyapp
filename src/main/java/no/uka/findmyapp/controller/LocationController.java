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
import no.uka.findmyapp.model.appstore.Developer;
import no.uka.findmyapp.service.DeveloperService;
import no.uka.findmyapp.service.LocationService;
import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.service.auth.ConsumerException;

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
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
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
@RequestMapping("/locations")
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
		logger.info("getCurrentLocation ( " + signals.length + " )");
		
		List<Signal> signalList = Arrays.asList(signals);
		Location location = service.getCurrentLocation(signalList);
		logger.debug("getCurrentPosition ( " + location + " )");
		
		return new ModelAndView("json", "location", location);
	}

	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = User.class)
	public ModelAndView getUsersAtLocation(@PathVariable("id") int locationId, 
			@RequestParam(required = true) String token) 
			throws ConsumerException {
		
		int tokenId = verifyToken(token);
		logger.debug("getUsersAtLocation ( " + locationId + ")");
		List<User> users = service.getUsersAtLocation(locationId, tokenId);
		return new ModelAndView("json", "usersAtLocation", users);
	}
	
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/{id}/friends", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = User.class)
	public ModelAndView getFriendsAtLocation(@PathVariable("id") int locationId, 
			@RequestParam(required = true) String token) 
			throws ConsumerException {
		
		int tokenId = verifyToken(token);
		logger.info("getFriendsAtLocation ( " + locationId + ")");
		List<User> users = service.getFriendsAtLocation(locationId, tokenId);
		return new ModelAndView("json", "friendsAtLocation", users);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getLocationData(@PathVariable("id") int locationId) {
		Location locale = service.getLocation(locationId);
		return new ModelAndView("json", "locationRealTime",locale);
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
	
	private int verifyToken(String token) throws InvalidTokenException {
		int tokenUserId = auth.verify(token);
		if (tokenUserId == -1)
			throw new InvalidTokenException("Invalid access token");
		return tokenUserId;
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
		return new ModelAndView("json", "randomFact", fact);
	}
	
	
	/*
	 * SCREEN
	 */
	
	@RequestMapping(value = "/screen", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = String.class)
	public ModelAndView getBartenderString() {
		String string = service.getBartenderString();
		return new ModelAndView("json", "bartenderString", string);
	}
	
	@RequestMapping(value = "/screen/{text}", method = RequestMethod.PUT)
	@ServiceModelMapping(returnType = Boolean.class)
	public ModelAndView postBartenderString(@RequestParam(required = true) String text) {
		boolean success = service.setBartenderString(text);
		return new ModelAndView("json", "putBartenderString", success);
	}
	
	/*
	 * WARNINGS
	 */

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
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ConsumerException.class)
	private void handleConsumerException(ConsumerException e) {
		logger.debug(e.getMessage());
	}


}