package no.uka.findmyapp.controller;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.exception.UkaYearNotFoundException;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.service.UkaProgramService;
import no.uka.findmyapp.service.UserService;
import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.service.auth.ConsumerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UkaProgramController {

	@Autowired
	private UkaProgramService ukaProgramService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService auth;

	private static final Logger logger = LoggerFactory
	.getLogger(UkaProgramController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */


	@RequestMapping(value = "/program/{ukaYear}/events", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaEvent.class)
	public ModelAndView getUkaProgramForDate(
			@PathVariable String ukaYear,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date date,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date from,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date to,
			@RequestParam(required=false) String place)
			throws UkaYearNotFoundException { 

		logger.debug("getUkaProgram - new");
		//TODO FIX SERVICE TO RETURN A LIST TO REFLECT THE CONTROLLER
		UkaProgram program = ukaProgramService.getUkaProgram(ukaYear, date, from, to, place);	
		
		return new ModelAndView("json", "program", program.getEvents());
	
	}


	@RequestMapping(value = "/program/{ukaYear}/events/search", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaProgram.class)
	public ModelAndView searchForUkaProgramByName(
			@PathVariable String ukaYear,
			@RequestParam(required=true) String eventName)
			throws UkaYearNotFoundException {
			
		UkaProgram program = new UkaProgram();
		logger.debug("searchForUkaProgramByName");
		program = ukaProgramService.titleSearch(ukaYear, eventName);	

		return new ModelAndView("json", "program", program);
	}

	@RequestMapping(value = "/program/{ukaYear}/places", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=String.class, isList=true)
	public ModelAndView getUkaProgramPlaces(
			@PathVariable String ukaYear)
			throws UkaYearNotFoundException {
		List<String> places;
		logger.debug("getUkaProgramPlaces");
		places = ukaProgramService.getUkaPlaces(ukaYear);

		return new ModelAndView("json", "places", places);
	}
	
	@RequestMapping(value = "/program/{ukaYear}/places/{place}", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaEvent.class)
	public ModelAndView getEventsOnPlace(
			@PathVariable String ukaYear, @PathVariable String place)
			throws UkaYearNotFoundException {
		
		logger.debug("getEventsOnPlace");
		List<UkaEvent> events = ukaProgramService.getEventsOnPlace(ukaYear, place);

		return new ModelAndView("json", "event", events);
	}
	
	@RequestMapping(value = "/program/{ukaYear}/places/{place}/next", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaEvent.class)
	public ModelAndView getNextUkaEvent(
			@PathVariable String ukaYear, @PathVariable String place)
			throws UkaYearNotFoundException {
		
		logger.debug("getNextUkaEvent");
		UkaEvent event = ukaProgramService.getNextUkaEvent(ukaYear, place);

		return new ModelAndView("json", "event", event);
	}
	
	@RequestMapping(value = "/program/{ukaYear}/places/{place}/today", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaEvent.class)
	public ModelAndView getUkaEventsToday(
			@PathVariable String ukaYear, @PathVariable String place)
			throws UkaYearNotFoundException {
		
		logger.debug("getUkaEventsToday");
		List<UkaEvent> events = ukaProgramService.getEventsToday(ukaYear, place);

		return new ModelAndView("json", "event", events);
	}
	
	@RequestMapping(value = "/program/{ukaYear}/places/{place}/tomorrow", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaEvent.class)
	public ModelAndView getUkaEventsTomorrow(
			@PathVariable String ukaYear, @PathVariable String place)
			throws UkaYearNotFoundException {
		
		logger.debug("getUkaEventsTomorrow");
		List<UkaEvent> events = ukaProgramService.getEventsTomorrow(ukaYear, place);

		return new ModelAndView("json", "event", events);
	}
	
	@RequestMapping(value = "/program/{ukaYear}", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaProgramConfiguration.class)
	public ModelAndView getUkaProgramStartEndDate(
			@PathVariable String ukaYear)
			throws UkaYearNotFoundException {

		logger.debug("getUkaProgramStartEndDate using a config file");
		UkaProgramConfiguration config = ukaProgramService.getUkaProgramConfiguration(ukaYear);
		return new ModelAndView("json", "ukaProgram", config);
	}
	@RequestMapping(value = "/program", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaProgramConfiguration.class, isList=true)
	public ModelAndView getUkaProgramStartEndDate() {
		logger.debug("get all ukaprograms");
		List<UkaProgramConfiguration> configs = ukaProgramService.getUkaProgramConfiguration();
		return new ModelAndView("json", "ukaProgram", configs);
	}

	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/events/{id}/friends", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=User.class, isList=true)
	public ModelAndView getFriendsAttendingEvent(
			@PathVariable("id") int eventId,
			@RequestParam(required = true) String token) throws ConsumerException{
		ModelAndView mav = new ModelAndView("json");
		int userId = auth.verify(token);
		List<User> users;
		if (userId != -1) {
			users = userService.getFriendsAtEvent(eventId, userId);
		} else {
			throw new InvalidTokenException("Token not valid");
		}
		mav.addObject("users", users);
		return mav;
	}

	@RequestMapping(value = "/program/{ukaYear}/events/{id}", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaEvent.class)
	public ModelAndView getUkaEventById(
			@PathVariable int id, 
			@PathVariable String ukaYear) 
			throws UkaYearNotFoundException {
		UkaEvent event;
		logger.debug("getUkaEventById");
		event = ukaProgramService.getUkaEventById(ukaYear, id);

		return new ModelAndView("json", "event", event);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public void handleIllegalArgumentException(
			IllegalArgumentException ex) {
		logger.error("handleIllegalArgumentException ( "
				+ ex.getLocalizedMessage() + " )");
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex) {
		logger.error("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UkaYearNotFoundException.class)
	private void handleUkaYearNotFoundException(UkaYearNotFoundException e) {
		logger.error("UkaYearNotFoundException ( "+e.getLocalizedMessage()+ " )");
	}
}

