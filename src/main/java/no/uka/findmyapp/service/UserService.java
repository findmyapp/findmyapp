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

	public PrivacySetting getPrivacySettingForUserId(int userId, String privacyType) {
		UserPrivacy privacy = this.getUserPrivacyForUserId(userId);
		
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
	
	public UserPrivacy getUserPrivacyForUserId(int userId) {
		return data.getUserPrivacyForUserId(userId);
		
	}
	
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
	


		
	public UserPrivacy updatePrivacy(int userPrivacyId, int newPosition, int newEvents, int newMoney, int newMedia){		
		 
		UserPrivacy userPrivacy = data.retrievePrivacy(userPrivacyId);
		
		logger.info("before updating position" + userPrivacyId + " and " + newPosition );
		if (newPosition == 1 || newPosition == 2 || newPosition == 3){
			userPrivacy.setPositionPrivacySetting(PrivacySetting.getSetting(newPosition));
		} 
		logger.info("after updating position" + userPrivacyId + " and " +  newPosition  );
		
		logger.info("before updating events" + userPrivacyId + " and " + newEvents );
		if (newEvents == 1 || newEvents == 2 || newEvents == 3){
			userPrivacy.setEventsPrivacySetting(PrivacySetting.getSetting(newEvents));} 
		logger.info("after updating events" + userPrivacyId + " and " + newEvents );
		
		if (newMoney == 1 || newMoney == 2 || newMoney == 3){
			userPrivacy.setMoneyPrivacySetting(PrivacySetting.getSetting(newMoney));} 
		
		if (newMedia == 1 || newMedia == 2 || newMedia == 3){
			userPrivacy.setMediaPrivacySetting(PrivacySetting.getSetting(newMedia));} 
		
		updatePrivacy(userPrivacy);
		
		return userPrivacy;
	}	
	
	
	
	public void updatePrivacy(UserPrivacy userPrivacy){	
		 data.updatePrivacy(userPrivacy.getUserPrivacyId(), userPrivacy.getPositionPrivacySetting(), userPrivacy.getEventsPrivacySetting(), userPrivacy.getMoneyPrivacySetting(), userPrivacy.getMediaPrivacySetting());
	}
	
	
	public UserPrivacy createDefaultPrivacySettingsEntry(){
		
		// create a new entry in the database
		int privacyId = data.createDefaultPrivacySettingsEntry();
		
		// Create object to return, with PrivacySetting.FRIENDS as default
		UserPrivacy userPrivacy = new UserPrivacy();
		userPrivacy.setId(privacyId);
		userPrivacy.setEventsPrivacySetting(PrivacySetting.FRIENDS);
		userPrivacy.setMediaPrivacySetting(PrivacySetting.FRIENDS);
		userPrivacy.setMoneyPrivacySetting(PrivacySetting.FRIENDS);
		userPrivacy.setPositionPrivacySetting(PrivacySetting.FRIENDS);
		
		return userPrivacy;
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

	public int findUserPrivacyId(int userId) {
		return data.findUserPrivacyId(userId);
	}
}

