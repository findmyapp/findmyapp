package no.uka.findmyapp.service;


//import static org.junit.Assert.assertTrue;

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
	
	

	public UserPrivacy retrievePrivacy(int userPrivacyId){
		return data.retrievePrivacy(userPrivacyId);

	}
	

	// We recommend using retrievePrivacy instead.
	public PrivacySetting retrieveOnePrivacy(int userPrivacyId, String privacyType) throws IllegalArgumentException{
		// We could try to make an enum out of privacyType also, would this give two nested enum classes?
		UserPrivacy privacy =  data.retrievePrivacy(userPrivacyId);
		privacyType = privacyType.toLowerCase();
		
		if (privacyType.equals("position")){
			return privacy.getPositionPrivacySetting();
		}
		else if (privacyType.equals("events")){
			return privacy.getEventsPrivacySetting();
		}
		else if (privacyType.equals("money")){
			return privacy.getMoneyPrivacySetting();
		}
		else if (privacyType.equals("media")){
			return privacy.getMediaPrivacySetting();
		}
		else{
			throw new IllegalArgumentException("Function retreiveOnePrivacy was called with illegal input"); 
		}
	}


		
	public void updatePrivacy(int userPrivacyId, PrivacySetting newPosition, PrivacySetting newEvents, PrivacySetting newMoney, PrivacySetting newMedia){		
		 data.updatePrivacy(userPrivacyId, newPosition, newEvents, newMoney, newMedia);
	}	
	
	public void updatePrivacy(UserPrivacy userPrivacy){	
		 data.updatePrivacy(userPrivacy.getUserPrivacyId(), userPrivacy.getPositionPrivacySetting(), userPrivacy.getEventsPrivacySetting(), userPrivacy.getMoneyPrivacySetting(), userPrivacy.getMediaPrivacySetting());
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

	
	
	
	// Testing privacy settings: create defaultSettings, update and retrieve 
	public boolean testingForUserServiceOne() {
	
		boolean success = true;
				
		// Use default settings and verify changes (friends, friends, friends, friends)
		int userPrivacyId; 
		userPrivacyId = data.createDefaultPrivacySettingsEntry();
	
		
		UserPrivacy privacy; 
		privacy = data.retrievePrivacy(userPrivacyId);

		success = success && (privacy.getPositionPrivacySetting() == PrivacySetting.FRIENDS);
		success = success && (privacy.getEventsPrivacySetting() == PrivacySetting.FRIENDS);
		logger.info("2 tests done");
		success = success && (privacy.getMediaPrivacySetting() == PrivacySetting.FRIENDS);
		success = success && (privacy.getMoneyPrivacySetting() == PrivacySetting.FRIENDS);
		logger.info("4 tests done");
		
		
		//Test for update
		PrivacySetting newPosition = PrivacySetting.ANYONE;
		PrivacySetting newEvents = PrivacySetting.ONLY_ME;
		PrivacySetting newMedia = PrivacySetting.ONLY_ME;
		PrivacySetting newMoney= PrivacySetting.ANYONE;
		data.updatePrivacy(userPrivacyId, newPosition, newEvents, newMoney, newMedia);
		

		privacy = data.retrievePrivacy(userPrivacyId);

		
		success = success && (privacy.getPositionPrivacySetting() == PrivacySetting.ANYONE); 
		success = success && (privacy.getEventsPrivacySetting() == PrivacySetting.ONLY_ME);
		logger.info("6 tests done");
		success = success && (privacy.getMediaPrivacySetting() == PrivacySetting.ONLY_ME);
		success = success && (privacy.getMoneyPrivacySetting() == PrivacySetting.ANYONE);
		
		
		return success;
	}
}

