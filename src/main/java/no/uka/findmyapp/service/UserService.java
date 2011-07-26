package no.uka.findmyapp.service;

//import static org.junit.Assert.assertTrue;

import java.util.List;

import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.exception.InvalidUserIdOrAccessTokenException;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.PrivacySetting;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPrivacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	
	
	public int getUserIdFromToken(String facebookToken) {
		Facebook facebook = new FacebookTemplate(facebookToken);
		String facebookId;
			facebookId = facebook.userOperations().getUserProfile().getId();

		if (facebookId != null) {
			logger.debug("Find userId of user with facebookId " + facebookId);
			int userId = 0;
			
			try {
				userId = data.getUserIdByFacebookId(facebookId);
			} catch (EmptyResultDataAccessException e) {
				// Empty result ok
			}
			

			if (userId == 0) {
				logger.debug("User not found. Adding user with facebook id: "
						+ facebookId);
				userId = data.addUserWithFacebookId(facebookId);
			} else {
				logger.debug("User with userId " + userId + " found.");
			}
			return userId;
		}
		return -1;
	}

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
	 * there is only three types of privacy settings: ANYONE (1), FRIENDS (2) and ONLY ME (3)
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


		// this method assures that privacy settings only gets updated if settings are valid (1,2 or 3)
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

	public List<String> getFacebookFriends(String accessToken) {
		Facebook facebook = new FacebookTemplate(accessToken);
		List<String> friendIds = facebook.friendOperations().getFriendIds();
		return friendIds;
	}

	public int findUserPrivacyId(int userId)
			throws InvalidUserIdOrAccessTokenException {
		return data.findUserPrivacyId(userId);
	}

	public boolean verifyAccessToken(int userId, String accessToken) {
		// TODO Auto-generated method stub
		return true;
	}
}
