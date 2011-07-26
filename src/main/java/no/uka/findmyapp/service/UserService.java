package no.uka.findmyapp.service;

//import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.exception.InvalidUserIdOrAccessTokenException;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.PrivacySetting;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;
import no.uka.findmyapp.model.UserPrivacy;
import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.service.auth.ConsumerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	@Autowired
	private UserRepository data;
	@Autowired
	private AuthenticationService auth;
	@Autowired
	private FacebookService facebook;

	public PrivacySetting getPrivacySettingForUserId(int userId,
			String privacyType) {
		UserPrivacy privacy = this.getUserPrivacyForUserId(userId);

		if (privacyType.equals("position")) {
			return privacy.getPositionPrivacySetting();
		} else if (privacyType.equals("events")) {
			return privacy.getEventsPrivacySetting();
		} else if (privacyType.equals("money")) {
			return privacy.getMoneyPrivacySetting();
		} else if (privacyType.equals("media")) {
			return privacy.getMediaPrivacySetting();
		} else {
			throw new IllegalArgumentException(
					"Function retreiveOnePrivacy was called with illegal input");
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

	public List<UkaEvent> getEvents(int userId) {
		return data.getEvents(userId);
	}

	// Retrieve privacy from database
	public UserPrivacy retrievePrivacy(int userPrivacyId) {
		return data.retrievePrivacy(userPrivacyId);
	}

	/**
	 * Method where it is possible to change privacy settings
	 * 
	 * @param userPrivacyId
	 * @param newPosition
	 * @param newEvents
	 * @param newMoney
	 * @param newMedia
	 * @return
	 */
	public UserPrivacy updatePrivacy(int userPrivacyId, int newPosition,
			int newEvents, int newMoney, int newMedia) {

		UserPrivacy userPrivacy = data.retrievePrivacy(userPrivacyId);

		// there is only three types of privacy settings: ANYONE (1), FRIENDS
		// (2) and ONLY ME (3)
		// this method assures that the privacy settings only gets updated if
		// the settings are valid (1,2 or 3)
		if (newPosition == 1 || newPosition == 2 || newPosition == 3) {
			userPrivacy.setPositionPrivacySetting(PrivacySetting
					.getSetting(newPosition));
		}

		if (newEvents == 1 || newEvents == 2 || newEvents == 3) {
			userPrivacy.setEventsPrivacySetting(PrivacySetting
					.getSetting(newEvents));
		}

		if (newMoney == 1 || newMoney == 2 || newMoney == 3) {
			userPrivacy.setMoneyPrivacySetting(PrivacySetting
					.getSetting(newMoney));
		}

		if (newMedia == 1 || newMedia == 2 || newMedia == 3) {
			userPrivacy.setMediaPrivacySetting(PrivacySetting
					.getSetting(newMedia));
		}

		updatePrivacy(userPrivacy);

		return userPrivacy;
	}

	public void updatePrivacy(int userPrivacyId, PrivacySetting newPosition,
			PrivacySetting newEvents, PrivacySetting newMoney,
			PrivacySetting newMedia) {
		data.updatePrivacy(userPrivacyId, newPosition, newEvents, newMoney,
				newMedia);
	}

	public void updatePrivacy(UserPrivacy userPrivacy) {
		data.updatePrivacy(userPrivacy.getUserPrivacyId(),
				userPrivacy.getPositionPrivacySetting(),
				userPrivacy.getEventsPrivacySetting(),
				userPrivacy.getMoneyPrivacySetting(),
				userPrivacy.getMediaPrivacySetting());
	}

	/**
	 * Default privacy settings is set. FRIENDS is default for every privacy
	 * setting
	 * 
	 * @return
	 */
	public UserPrivacy createDefaultPrivacySettingsEntry() {

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

	/**
	 * Get all the facebook friends of a user which also resides in the local FMA datastore
	 * 
	 * @param userId local id of user
	 * @return list of facebook friends also registered in FMA storage.
	 * @throws ConsumerException
	 */
	public List<User> getRegisteredFacebookFriends(int userId)
			throws ConsumerException {
		List<String> friendIds = getFacebookIdOfFriends(userId);
		List<User> users = data.getRegisteredFacebookFriends(friendIds);
		return users;
	}

	/**
	 * Retreives all of the dudes friends based on event and userid.
	 * 
	 * @param eventId Id of the event in local datastore
	 * @param userId Id of the user in local datastore
	 * @return list of the dudes friends
	 * @throws ConsumerException
	 */
	public List<User> getFriendsAtEvent(int eventId, int userId)
			throws ConsumerException {
		List<String> friendIds = getFacebookIdOfFriends(userId);
		List<User> users = data.getFacebookFriendsAtEvent(eventId, friendIds);
		return users;
	}

	/**
	 * Generic method to extract a users friends based on its user id in the FMA data store.
	 * 
	 * @param userId User id from the local datastore
	 * @return List of ids of the users friends
	 * @throws ConsumerException
	 */
	public List<String> getFacebookIdOfFriends(int userId)
			throws ConsumerException {
		String facebookConsumerToken = getFacebookConsumerToken();
		String facebookUserId = data.getFacebookIdByUserId(userId);
		List<String> friendIds = getFacebookFriends(facebookConsumerToken,
				facebookUserId);
		return friendIds;
	}

	public int findUserPrivacyId(int userId)
			throws InvalidUserIdOrAccessTokenException {
		return data.findUserPrivacyId(userId);
	}

	/**
	 * Method used to get friends based on a consumer token from the calling app
	 * and a user id of the dude with the friends.
	 * 
	 * @param facebookConsumerToken
	 *            Consumer token for the app responsible for the call
	 * @param facebookUserId
	 *            The user id of the dude with the friends
	 * @return A list of ids of the dudes facebook friends
	 * @throws ConsumerException
	 *             Which tells you that something is wrong with either the
	 *             consumer credentials or permissions to access the dudes
	 *             friends.
	 */
	private List<String> getFacebookFriends(String facebookConsumerToken,
			String facebookUserId) throws ConsumerException {
		return facebook.getFacebookFriends(facebookConsumerToken,
				facebookUserId);
	}

	/**
	 * Retreives a consumer token for the calling app based on the details
	 * gathered from the data source based on passed OAuth consumer key and
	 * secret.
	 * 
	 * @return Consumer token for the calling app
	 * @throws ConsumerException
	 */
	private String getFacebookConsumerToken() throws ConsumerException {
		return facebook.getConsumerFacebookToken(auth.getConsumerDetails());
	}
	
	//TODO
	public Location getUserLocation(int userId) {
		return null;
	}

	//TODO
	public List<UserPosition> getLocationOfAllUsers() {
		return null;
	}
	
	//TODO
	public Location getLocationOfFriend(int friendId, int userId) {
		return null;
	}

	//TODO
	public Map<Integer, Integer> getLocationOfFriends(int userId) throws ConsumerException {
		return null;
	}
}
