package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.exception.InvalidUserIdOrAccessTokenException;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPrivacy;
import no.uka.findmyapp.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	private static final Logger logger = LoggerFactory
	.getLogger(UserController.class);

	@RequestMapping(value = "/friends")
	public ModelAndView getRegisteredFacebookFriends(@RequestParam String accessToken) {
		ModelAndView mav = new ModelAndView();
		List<User> users = service.getRegisteredFacebookFriends(accessToken);
		mav.addObject("users", users);
		return mav;
	}

	@RequestMapping(value = "/{id}/events/{eventId}", method = RequestMethod.POST)
	public ModelMap addEvent(@PathVariable("id") int userId, @PathVariable("eventId") long eventId, ModelMap model) {
		boolean addEvent = service.addEvent(userId, eventId);
		model.addAttribute(addEvent);
		return model;
	}

	@RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
	public ModelMap getEvents(@PathVariable("id") int userId, ModelMap model) {
		List<UkaEvent> events = service.getEvents(userId);
		model.addAttribute(events);
		return model;
	}

	/**
	 * POST method where it is possible to change privacy settings
	 * Privacy settings: 1 = ANYONE, 2 = FRIENDS, 3 = ONLY ME 
	 * @param userId is necessary to change privacy settings  
	 * @param privacySettingPosition 
	 * @param privacySettingEvents
	 * @param privacySettingMoney
	 * @param privacySettingMedia
	 * @param accessToken for our local database 
	 * @return
	 * @throws InvalidUserIdOrAccessTokenException
	 */

	@RequestMapping(value = "/{userId}/privacy", method = RequestMethod.POST)
	public ModelAndView postPrivacy(@PathVariable("userId") int userId,
			@RequestParam (defaultValue = "0") int privacySettingPosition,
			@RequestParam (defaultValue = "0") int privacySettingEvents,
			@RequestParam (defaultValue = "0") int privacySettingMoney,
			@RequestParam (defaultValue = "0") int privacySettingMedia,
			@RequestParam (defaultValue = "0") String accessToken) throws InvalidUserIdOrAccessTokenException {

		logger.info("update privacy with inputs" +  privacySettingPosition + " " + privacySettingEvents
				+ " " +  privacySettingMoney + " " + privacySettingMedia);
		
		// verify if the access token is valid, if not, throw exception
		boolean accessVerified = service.verifyAccessToken(userId, accessToken);
		if (!accessVerified){
			throw new InvalidUserIdOrAccessTokenException("Invalid access token");
		}

		// If access token is valid, json view is returned 
		int userPrivacyId = service.findUserPrivacyId(userId);
		UserPrivacy userPrivacy = service.updatePrivacy(userPrivacyId, privacySettingPosition, privacySettingEvents, privacySettingMoney, privacySettingMedia );
		return new ModelAndView("json", "privacy", userPrivacy);

	}



	/**
	 * GET method where privacy settings is read 
	 * @param userId is necessary to change privacy settings. UserId is our local Id  
	 * @param accessToken for our local database 
	 * @return
	 * @throws InvalidUserIdOrAccessTokenException
	 */
	@RequestMapping(value = "/{userId}/privacy", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=String.class, isList=true)

	public ModelAndView getPrivacy(
			@PathVariable int userId,
			@RequestParam (defaultValue = "0") String accessToken) throws InvalidUserIdOrAccessTokenException{
		UserPrivacy privacy;


		// verify if the access token is valid, if not, throw exception
		boolean accessVerified = service.verifyAccessToken(userId, accessToken);
		if (!accessVerified){
			throw new InvalidUserIdOrAccessTokenException("Invalid access token");
		}

		// If access token is valid, json view is returned 
		int userPrivacyId = service.findUserPrivacyId(userId);
		privacy = service.retrievePrivacy(userPrivacyId);
		return new ModelAndView("json", "privacy", privacy);
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(InvalidUserIdOrAccessTokenException.class)
	private void handleInvalidUserIdOrAccessTokenException(InvalidUserIdOrAccessTokenException e) {
		logger.info("InvalidUserIdOrAccessTokenException ( "+e.getLocalizedMessage()+ " )");
	}

}
