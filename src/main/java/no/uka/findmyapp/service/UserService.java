package no.uka.findmyapp.service;


import java.util.List;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPrivacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	@Autowired
	private UserRepository data;

	public boolean areFriends(int userId1, int userId2) {
		return data.areFriends(userId1, userId2);
	}
		
	public boolean addEvent(int userId, long eventId) {
		return data.addEvent(userId, eventId);
	}

	public List<Event> getEvents(int userId) {
		return data.getEvents(userId);
	}
	
	
/**
 * RetrievePrivacy returns information about position, events, money and media. 
 * 1: lowest privacy level: sharing information with everybody 
 * 2: privacy level 2: sharing only with you Facebook friends
 * 3: highest privacy level: sharing with nobody 
 * @param userId
 * @return
 */
	public UserPrivacy retrievePrivacy(int userId){
		return data.retrievePrivacy(userId);

	}
	
	
/**
 * RetrieveOnePrivacy returns information for one of the parameters. You will get 1,2 or 3 as answers. 
 * 1: lowest privacy level: sharing information with everybody 
 * 2: privacy level 2: sharing only with you Facebook friends
 * 3: highest privacy level: sharing with nobody 
 * @param userId
 * @param privacyType
 * @return
 * @throws IllegalArgumentException
 */

	public int retrieveOnePrivacy(int userId, String privacyType) throws IllegalArgumentException{
		UserPrivacy privacy =  data.retrievePrivacy(userId);
		privacyType = privacyType.toLowerCase();
		
		if (privacyType.equals("position")){
			return privacy.getPosition();
		}
		else if (privacyType.equals("events")){
			return privacy.getEvents();
		}
		else if (privacyType.equals("money")){
			return privacy.getMoney();
		}
		else if (privacyType.equals("media")){
			return privacy.getMedia();
		}
		else{
			throw new IllegalArgumentException("Function retreiveOnePrivacy was called with illegal input"); 
		}
	}


		
	public void updatePrivacy(int userId, int newPosition, int newEvents, int newMoney, int newMedia){		
		 data.updatePrivacy(userId, newPosition, newEvents, newMoney, newMedia);
	}	
	
	public void updatePrivacy(int userId, UserPrivacy userPrivacy){	
		// if bad input throw exception
		 data.updatePrivacy(userId, userPrivacy.getPosition(), userPrivacy.getEvents(), userPrivacy.getMoney(), userPrivacy.getMedia());
	}
	
	
	public int createDefaultPrivacySettingsEntry(){
		return data.createDefaultPrivacySettingsEntry();
	}

	public List<User> getRegisteredFacebookFriends(String accessToken) {
		List<String> friendIds = getFacebookFriends(accessToken);
		List<User> users = data.getRegisteredFacebookFriends(friendIds);
		return users;
	}

	public List<User> getFriendsAtEvent(int eventId, String accessToken) {
		List<String> friendIds = getFacebookFriends(accessToken);
		List<User> users = data.getFacebookFriendsAtEvent(eventId, friendIds);
		return users;
	}
	
	private List<String> getFacebookFriends(String accessToken) {
		Facebook facebook = new FacebookTemplate(accessToken);
		List<String> friendIds = facebook.friendOperations().getFriendIds();
		return friendIds;
	}
}

