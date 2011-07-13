package no.uka.findmyapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.service.LocationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

	private static final Logger logger = LoggerFactory
			.getLogger(LocationController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelMap getAllLocations() {
		logger.info("getAllLocations");
		ModelMap model = new ModelMap();
		List<Location> locations = service.getAllLocations();
		model.addAttribute(locations);
		return model;
	}

	/*
	 * ************* POSITIONING *************
	 */

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getPosition(@RequestBody Signal[] signals)
			throws LocationNotFoundException {
		logger.info("getCurrentLocation ( " + signals.length + " )");
		ModelAndView mav = new ModelAndView("pos");
		List<Signal> signalList = Arrays.asList(signals);
		Location location = service.getCurrentLocation(signalList);
		logger.info("getCurrentPosition ( " + location + " )");
		mav.addObject("room", location); // model name, model object

		return mav;
	}

	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
	public ModelAndView getUsersAtLocation(@PathVariable("id") int locationId) {
		logger.debug("getUsersAtLocation ( " + locationId + ")");
		ModelAndView mav = new ModelAndView();
		List<User> users = service.getUsersAtLocation(locationId);
		mav.addObject("usersAtLocation", users);
		return mav;
	}
	
	@RequestMapping(value = "/{id}/usercount", method = RequestMethod.GET)
	public ModelAndView getUserCountAtLocation(@PathVariable("id") int locationId) {
		logger.debug("getUserCountAtLocation ( " + locationId + ")");
		int count = service.getUserCountAtLocation(locationId);
		return new ModelAndView("pos","usercount", count);
	}

	@RequestMapping(value = "/sample", method = RequestMethod.POST)
	public ModelAndView registerSample(@RequestBody Sample sample) {
		ModelAndView mav = new ModelAndView("registerPositionSample");
		boolean regSample = service.registerSample(sample);
		logger.info("registerSample ( " + regSample + " )");
		mav.addObject("regSample", regSample); // model name, model object

		return mav;
	}

	@RequestMapping(value = "{locationId}/users/{userId}", method = RequestMethod.POST)
	public ModelAndView registerUserLocation(@PathVariable int userId,
			@PathVariable int locationId) {
		ModelAndView mav = new ModelAndView("registerUserPosition");
		boolean regUserPos = service.registerUserLocation(userId, locationId);
		logger.info("registerUserPosition ( " + regUserPos + " )");
		mav.addObject("regUserPos", regUserPos); // model name, model object
		return mav;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ModelMap getUserLocation(@PathVariable("id") int userId,
			ModelMap model) {
		Location location = service.getUserLocation(userId);
		model.addAttribute(location);
		return model;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public void getAllUserLocations(ModelMap model) {
		model.addAttribute(service.getLocationOfAllUsers());
	}

	@RequestMapping(value = "/friends/{id}", method = RequestMethod.GET)
	public ModelMap getLocationOfFriend(@PathVariable("id") int friendId,
			ModelMap model) {
		Location friendLocation = service.getLocationOfFriend(friendId);
		model.addAttribute(friendLocation);
		return model;
	}

	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public ModelMap getLocationOfFriends(@PathVariable int userId,
			ModelMap model) {
		Map<Integer, Integer> friendsPositions = service
				.getLocationOfFriends(userId);
		model.addAttribute(friendsPositions);
		return model;
	}

	/*
	 * **************** FACT *****************
	 */

	@RequestMapping(value = "/{id}/facts", method = RequestMethod.GET)
	public ModelMap getAllFacts(@PathVariable("id") int locationId) {
		logger.info("getAllFacts ( " + locationId + " )");
		ModelMap model = new ModelMap();
		List<Fact> facts = service.getAllFacts(locationId);
		model.addAttribute(facts);
		return model;
	}

	@RequestMapping(value = "/{id}/facts/random", method = RequestMethod.GET)
	public ModelMap getRandomFact(@PathVariable("id") int locationId) {
		ModelMap model = new ModelMap();
		Fact fact = service.getRandomFact(locationId);
		model.addAttribute(fact);
		return model;
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
