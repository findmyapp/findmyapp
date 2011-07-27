package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.controller.auth.TokenException;
import no.uka.findmyapp.exception.InvalidUserIdOrAccessTokenException;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;
import no.uka.findmyapp.model.UserPrivacy;
import no.uka.findmyapp.service.UserService;
import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.service.auth.ConsumerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private AuthenticationService authService;

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/me/friends", method = RequestMethod.GET)
	public ModelAndView getRegisteredFacebookFriends(@RequestParam(required = true) String token)
			throws ConsumerException {
		ModelAndView mav = new ModelAndView("json");
		int userId = verifyToken(token);
		List<User> users = service.getRegisteredFacebookFriends(userId);
		mav.addObject("users", users);
		return mav;
	}
	
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/me/events/{eventId}", method = RequestMethod.POST)
	public ModelAndView addEvent(@PathVariable long eventId, @RequestParam String token) {
		int userId = verifyToken(token);
		boolean eventAdded = service.addEvent(userId, eventId);
		ModelAndView data = new ModelAndView("json");
		data.addObject("success", eventAdded);
		return data;
	}
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/me/events/{eventId}", method = RequestMethod.DELETE)
	public ModelAndView removeEvent(@PathVariable long eventId, @RequestParam(required = true) String token) {
		int userId = verifyToken(token);
		boolean eventRemoved = service.removeEvent(userId, eventId);
		ModelAndView data = new ModelAndView("json");
		data.addObject("success", eventRemoved);
		return data;
	}
	
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/{idOrMe}/events", method = RequestMethod.GET)
	public ModelAndView getEvents(@PathVariable String idOrMe, @RequestParam String token) throws NumberFormatException, ConsumerException {
		ModelAndView data = new ModelAndView("json");
		int tokenUserId = verifyToken(token);
		List<UkaEvent> events;
		if (idOrMe.equalsIgnoreCase("me")) {
			 events = service.getEvents(tokenUserId);
		} else {
			events = service.getEvents(Integer.parseInt(idOrMe), tokenUserId);
		}
		data.addObject("events", events);
		return data;
	}	

	/**
	 * POST method where it is possible to change privacy settings Privacy
	 * settings: 1 = ANYONE, 2 = FRIENDS, 3 = ONLY ME
	 * 
	 * @param userId
	 *            is necessary to change privacy settings
	 * @param positionPrivacySetting
	 * @param eventsPrivacySetting
	 * @param moneyPrivacySetting
	 * @param mediaPrivacySetting
	 * @param token
	 *            for our local database
	 * @return
	 * @throws InvalidUserIdOrAccessTokenException
	 */

	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/me/privacy", method = RequestMethod.POST)
	public ModelAndView postPrivacy(
			@RequestParam(defaultValue = "0") int positionPrivacySetting,
			@RequestParam(defaultValue = "0") int eventsPrivacySetting,
			@RequestParam(defaultValue = "0") int moneyPrivacySetting,
			@RequestParam(defaultValue = "0") int mediaPrivacySetting,
			@RequestParam(required = true) String token)
			throws ConsumerException {

		logger.info("update privacy with inputs" + positionPrivacySetting + " "
				+ eventsPrivacySetting + " " + moneyPrivacySetting + " "
				+ mediaPrivacySetting);

		// verify if the access token is valid, if not, throw exception
		int userId = verifyToken(token);

		// If access token is valid, json view is returned
		int userPrivacyId = service.findUserPrivacyId(userId);
		UserPrivacy userPrivacy = service.updatePrivacy(userPrivacyId,
				positionPrivacySetting, eventsPrivacySetting,
				moneyPrivacySetting, mediaPrivacySetting);
		return new ModelAndView("json", "privacy", userPrivacy);
	}

	/**
	 * GET method where privacy settings is read
	 * 
	 * @param userId
	 *            is necessary to change privacy settings. UserId is our local
	 *            Id
	 * @param token
	 *            for our local database
	 * @return
	 * @throws InvalidUserIdOrAccessTokenException
	 */
	@RequestMapping(value = "/me/privacy", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = String.class, isList = true)
	public ModelAndView getPrivacy(@RequestParam(required = true) String token)throws ConsumerException {
		// verify if the access token is valid, if not, throw exception
		int userId = verifyToken(token);

		// If access token is valid, json view is returned
		int userPrivacyId = service.findUserPrivacyId(userId);
		UserPrivacy privacy = service.retrievePrivacy(userPrivacyId);
		return new ModelAndView("json", "privacy", privacy);
	}
	
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/me/location/{locationId}", method = RequestMethod.PUT)
	@ServiceModelMapping(returnType = boolean.class)
	public ModelAndView registerUserLocation(
			@PathVariable int locationId,
			@RequestParam(required = true) String token) throws TokenException {
		int tokenUserId = verifyToken(token);
		boolean regUserPos = false;
		if(tokenUserId != -1) {
			regUserPos = service.registerUserLocation(tokenUserId, locationId);
			logger.debug("Registering user postition for user " + tokenUserId);
			regUserPos = true;
		}
		else {
			throw new TokenException("Token and supplied user id did not match");
		}
		return new ModelAndView("json", "regUserPos", regUserPos);
	}
	

	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/{id}/location", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = Location.class)
	public ModelAndView getUserLocation(
			@PathVariable("id") int userId,
			@RequestParam(required = true) String token) throws TokenException, ConsumerException {
		int tokenUserId = verifyToken(token);
		Location location = service.getUserLocation(userId , tokenUserId);
		return new ModelAndView("json", "location", location);
	}

	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/all/location", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = UserPosition.class)
	public ModelAndView getAllUserLocations(
			@RequestParam(required = true) String token) {
		verifyToken(token);
		List<UserPosition> pos = service.getLocationOfAllUsers();
		return new ModelAndView("json", "user_position", pos);
	}
	
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "me/friends/all/location", method = RequestMethod.GET)
	@ServiceModelMapping(returnType = UserPosition.class)
	public ModelAndView getLocationOfFriends(@PathVariable int userId,
			@RequestParam(required = true) String token) throws ConsumerException, TokenException {
		int tokenUserId = verifyToken(token);
		List<UserPosition> friendsPositions;
		if (tokenUserId == userId) {
			friendsPositions = service.getLocationOfFriends(userId);
		} else throw new TokenException();
		return new ModelAndView("json", "friends_positions", friendsPositions);
	}

	private int verifyToken(String token) throws InvalidTokenException {
		int tokenUserId = authService.verify(token);
		if (tokenUserId == -1)
			throw new InvalidTokenException("Invalid access token");
		return tokenUserId;
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidUserIdOrAccessTokenException.class)
	private void handleInvalidUserIdOrAccessTokenException(
			InvalidUserIdOrAccessTokenException e) {
		logger.debug(e.getMessage());
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ConsumerException.class)
	private void handleConsumerException(ConsumerException e) {
		logger.debug(e.getMessage());
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	private void handleNumberFormatException(NumberFormatException e) {
		logger.debug(e.getMessage());
	}

}
