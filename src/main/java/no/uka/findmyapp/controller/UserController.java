package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/{id}/event/{eventId}", method = RequestMethod.POST)
	public ModelMap addEvent(@PathVariable("id") int userId, @PathVariable("eventId") long eventId, ModelMap model) {
		boolean addEvent = service.addEvent(userId, eventId);
		model.addAttribute(addEvent);
		return model;
	}
	
	@RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
	public ModelMap getEvents(@PathVariable("id") int userId, ModelMap model) {
		List<Event> events = service.getEvents(userId);
		model.addAttribute(events);
		return model;
	}
}
