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

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@RequestMapping(value = "/{id}/friend/all", method = RequestMethod.GET)
	public ModelMap getAllFriends(@PathVariable("id") int userId, ModelMap model) {
		List<User> friends = service.getAllFriends(userId);
		model.addAttribute(friends);
		return model;
	}
	
	@RequestMapping(value = "/{id}/events", method= RequestMethod.GET)
	public List<Event> getEventsOnUser(@PathVariable("id") int userId){
		List<Event> events;
		logger.info("getEventsOnUser -controller");
		events = service.getEventsOnUser(userId);
		logger.info("got events!");
		return events;
		
	}

}
