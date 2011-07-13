package no.uka.findmyapp.service;


import java.util.List;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository data;
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	public List<User> getAllFriends(int userId) {
		return data.getAllFriends(userId);
	}
	
	public boolean areFriends(int userId1, int userId2) {
		return data.areFriends(userId1, userId2);
	}
	public List<Event> getEventsOnUser(int userId) {
		UkaProgramRepository rep = new UkaProgramRepository();
		return rep.getEventsOnUser(userId);
	}
	
	public boolean addEvent(int userId, long eventId) {
		return data.addEvent(userId, eventId);
	}
	
	public List<Event> getEvents(int userId) {
		return data.getEvents(userId);
	}
}
