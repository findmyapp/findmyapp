package no.uka.findmyapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationStatus;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;
import no.uka.findmyapp.service.LocationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	private static final Logger logger = LoggerFactory
			.getLogger(LocationController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAllLocations() {
		logger.info("getAllLocations");
		List<Location> locations = service.getAllLocations();
		return new ModelAndView("json", "location", locations);
	}

	/*
	 * ************* POSITIONING *************
	 */

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getPosition(@RequestBody Signal[] signals)
			throws LocationNotFoundException {
		logger.info("getCurrentLocation ( " + signals.length + " )");
		List<Signal> signalList = Arrays.asList(signals);
		Location location = service.getCurrentLocation(signalList);
		logger.info("getCurrentPosition ( " + location + " )");
		return new ModelAndView("json", "location", location);
	}

	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
	public ModelAndView getUsersAtLocation(@PathVariable("id") int locationId) {
		logger.debug("getUsersAtLocation ( " + locationId + ")");
		List<User> users = service.getUsersAtLocation(locationId);
		return new ModelAndView("json", "users_at_location", users);
	}
	
	@RequestMapping(value = "/{id}/usercount", method = RequestMethod.GET)
	public ModelAndView getUserCountAtLocation(@PathVariable("id") int locationId) {
		logger.debug("getUserCountAtLocation ( " + locationId + ")");
		int count = service.getUserCountAtLocation(locationId);
		return new ModelAndView("json","usercount", count);
	}

	@RequestMapping(value = "/sample", method = RequestMethod.POST)
	public ModelAndView registerSample(@RequestBody Sample sample) {
		boolean regSample = service.registerSample(sample);
		logger.info("registerSample ( " + regSample + " )");
		return new ModelAndView("json", "regSample", regSample);
	}

	@RequestMapping(value = "{locationId}/users/{userId}", method = RequestMethod.POST)
	public ModelAndView registerUserLocation(@PathVariable int userId,
			@PathVariable int locationId) {
		boolean regUserPos = service.registerUserLocation(userId, locationId);
		logger.info("registerUserPosition ( " + regUserPos + " )");
		return new ModelAndView("json", "regUserPos", regUserPos);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ModelAndView getUserLocation(@PathVariable("id") int userId) {
		Location location = service.getUserLocation(userId);
		return new ModelAndView("json", "location", location);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView getAllUserLocations() {
		List<UserPosition> pos = service.getLocationOfAllUsers();
		return new ModelAndView("json", "user_position", pos);
	}

	@RequestMapping(value = "/friends/{id}", method = RequestMethod.GET)
	public ModelAndView getLocationOfFriend(@PathVariable("id") int friendId) {
		Location friendLocation = service.getLocationOfFriend(friendId);
		return new ModelAndView("json", "friend_location", friendLocation);
	}

	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public ModelAndView getLocationOfFriends(@PathVariable int userId) {
		Map<Integer, Integer> friendsPositions = service
				.getLocationOfFriends(userId);
		return new ModelAndView("json", "friends_positions", friendsPositions);
	}

	/*
	 * **************** FACT *****************
	 */
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)//Adds subjective user reported data, such as fun factor or queue length,on a location
	public ModelAndView addData(@PathVariable("id") int locationId,
			@RequestBody LocationStatus locationStatus){
		
		ModelAndView mav = new ModelAndView("ok_respons");
		logger.info("Status data logged for location: " + locationId);
		service.addData(locationStatus, locationId);
		mav.addObject("respons",locationStatus);
		return mav;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView getData(@PathVariable("id") int locationId){
		Location locale = service.getData(locationId);
		return new ModelAndView("json","location_real_time", locale);
	}

	@RequestMapping(value = "/{id}/facts", method = RequestMethod.GET)
	public ModelAndView getAllFacts(@PathVariable("id") int locationId) {
		logger.info("getAllFacts ( " + locationId + " )");
		List<Fact> facts = service.getAllFacts(locationId);
		return new ModelAndView("json", "facts", facts);
	}

	@RequestMapping(value = "/{id}/facts/random", method = RequestMethod.GET)
	public ModelAndView getRandomFact(@PathVariable("id") int locationId) {
		Fact fact = service.getRandomFact(locationId);
		return new ModelAndView("json", "random_fact", fact);
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	private void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	private void handleIncorrectResultSizeDataAccessException(
			IncorrectResultSizeDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(LocationNotFoundException.class)
	private void handleLocationNotFoundException(LocationNotFoundException ex) {
		logger.info("handleLocationNotFoundException ( "
				+ ex.getLocalizedMessage() + " )");
	}

}
