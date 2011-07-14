package no.uka.findmyapp.service;


import java.util.List;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.PrivacySetting;
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
	
	

	public UserPrivacy retrievePrivacy(int userId){
		return data.retrievePrivacy(userId);

	}
	

	public PrivacySetting retrieveOnePrivacy(int userId, String privacyType) throws IllegalArgumentException{
		// We could try to make an enum out of privacyType also, would this give two nested enum classes?
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


		
	public void updatePrivacy(int userId, PrivacySetting newPosition, PrivacySetting newEvents, PrivacySetting newMoney, PrivacySetting newMedia){		
		 data.updatePrivacy(userId, newPosition, newEvents, newMoney, newMedia);
	}	
	
	public void updatePrivacy(int userId, UserPrivacy userPrivacy){	
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

