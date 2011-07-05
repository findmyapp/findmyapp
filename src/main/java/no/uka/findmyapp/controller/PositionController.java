package no.uka.findmyapp.controller;

import java.util.Arrays;
import java.util.List;

import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.service.PositionService;

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

import com.google.gson.Gson;

/* Controller that handles HTTP requests for position
 * 
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 * 
 */
@Controller
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private PositionService service;

	@Autowired
	private Gson gson;

	private static final Logger logger = LoggerFactory
			.getLogger(PositionController.class);

	// maps the URL with SSID asking for position to page showing name
	// associated with that SSID
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getPosition(@RequestBody Signal[] signals)
			throws LocationNotFoundException {
		logger.info("getPosition ( " + signals.length + " )");
		ModelAndView mav = new ModelAndView("pos"); // pos.jsp is the name of
													// the page displaying the
													// result

		List<Signal> signalList = Arrays.asList(signals);
		Location location = service.getCurrentPosition(signalList);
		logger.info("getCurrentPosition ( " + location + " )");
		mav.addObject("room", location); // model name, model object

		return mav;
	}

	@RequestMapping(value = "/sample", method = RequestMethod.POST)
	public ModelAndView registerSample(@RequestBody Sample sample) {
		ModelAndView mav = new ModelAndView("registerPositionSample");
		boolean regSample = service.registerSample(sample);
		logger.info("registerSample ( " + regSample + " )");
		mav.addObject("regSample", regSample); // model name, model object

		return mav;
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
	public ModelAndView registerUserPosition(@PathVariable("id") int userId, @RequestParam int locationId) {
		ModelAndView mav = new ModelAndView("registerUserPosition");
		boolean regUserPos = service.registerUserPosition(userId, locationId);
		logger.info("registerUserPosition ( " + regUserPos + " )");
		mav.addObject("regUserPos", regUserPos); // model name, model object
		return mav;
	}

	@RequestMapping(value = "user/{id}", method = RequestMethod.GET)
	public ModelMap getUserPosition(@PathVariable("id") int userId) {
		ModelMap mm = new ModelMap();
		Location location = service.getUserPosition(userId);
		mm.addAttribute(location);
		return mm;
	}

	@RequestMapping(value = "users", method = RequestMethod.GET)
	public void getAll(ModelMap model) {
		model.addAttribute(service.getPositionOfAllUsers());
	}

	@RequestMapping(value = "/fact/{name}", method = RequestMethod.GET)
	public ModelMap getAllFacts(@PathVariable("name") String locationName) {
		logger.info("getAllFacts ( " + locationName + " )");
		ModelMap model = new ModelMap();
		List<Fact> facts = service.getAllFacts(locationName);
		model.addAttribute(facts);
		return model;

	}
	
	@RequestMapping(value = "position/locations", method = RequestMethod.GET)
	public ModelMap getAllLocations() {
		logger.info("getAllLocations");
		ModelMap model = new ModelMap();
		List<Location> locations = service.getAllLocations();
		model.addAttribute(locations);
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
