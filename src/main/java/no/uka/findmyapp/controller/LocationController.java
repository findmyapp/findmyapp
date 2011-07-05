package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/* Controller that handles HTTP requests for location
 * 
 * @author Kåre Blakstad
 * 
 */
@Controller
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private LocationService service;

	@Autowired
	private Gson gson;

	private static final Logger logger = LoggerFactory
			.getLogger(LocationController.class);
	
	@RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
	public ModelAndView getUsersAtLocation(@PathVariable("id") int locationId) {
		logger.debug("getUsersAtLocation ( " + locationId + ")");
		ModelAndView mav = new ModelAndView();
		List<User> users = service.getUsersAtLocation(locationId);
		mav.addObject("usersAtLocation", users);
		return mav;
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
