package no.uka.findmyapp.service.auth;

import java.util.GregorianCalendar;

import no.uka.findmyapp.configuration.AuthenticationConfiguration;
import no.uka.findmyapp.datasource.AuthenticationRepository;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.auth.AppAuthInfo;
import no.uka.findmyapp.model.auth.UKAppsConsumerDetails;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.FacesWebRequest;

@Service
public class AuthenticationService {

	@Autowired
	AuthenticationRepository authRepo;
	@Autowired
	UserRepository userRepo;

	@Autowired
	AuthenticationConfiguration authConfig;

	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationService.class);

	private static final char USER_ID_DELIMITER = 'i';
	private static final char TIMESTAMP_DELIMITER = 't';

	public String login(String facebookToken) {
		Facebook facebook = new FacebookTemplate(facebookToken);
		String facebookId;
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		facebookId = profile.getId();

		String token = null;
		
		if (facebookId != null) {
			logger.info("Find userId of user with facebookId " + facebookId);
			int userId = userRepo.getUserIdByFacebookId(facebookId);

			if (userId == -1) {
				logger.debug("User not found. Adding user with facebook id: "
						+ facebookId);

				String facebookName;
				facebookName = profile.getFirstName() + " " + profile.getLastName();
				userId = userRepo.addUserWithFacebookId(facebookId, facebookName);
			} else {
				logger.debug("User with userId " + userId + " found.");
			}
			token = generateToken(userId);
			userRepo.updateUserLogonTime(userId);
		}
		return token;
	}

	private String generateToken(int userId) {
		long tokenIssued = new GregorianCalendar().getTimeInMillis();
		String base = "i" + Integer.toString(userId) + "t"
				+ Long.toString(tokenIssued);

		logger.debug("Generating token for base " + base);
		String hash = calculateHash(base + authConfig.getTokenSecret());

		logger.debug("Hash generated: " + hash);
		String token = hash + base;

		logger.debug("Updating user profile for user " + userId);
		int updated = userRepo.updateUserTokenIssueTime(tokenIssued, userId, getConsumerDetails().getConsumerKey());//NEW
		if (updated == 0)
			token = null;

		logger.debug("Profile updated. Token generated: " + token);
		return token;
	}

	private String calculateHash(String base) {
		return DigestUtils.shaHex(base);
	}

	/**
	 * @param token
	 *            The token attached to the message
	 * @return User ID of the token owner. -1 if token is not valid.
	 */
	public int verify(String token) {
		if (token == null || token.length() <= 42)
			return -1;

		int startOfUserId = token.lastIndexOf(USER_ID_DELIMITER);
		int startOfTimestamp = token.lastIndexOf(TIMESTAMP_DELIMITER);

		String hash = token.substring(0, startOfUserId);
		String base = token.substring(startOfUserId);
		int userId;
		try {
			userId = Integer.parseInt(token.substring(startOfUserId + 1,
					startOfTimestamp));
		} catch (NumberFormatException e1) {
			return -1;
		}
		String timestamp = token.substring(startOfTimestamp + 1);

		logger.debug("Verifying token structure");
		String calculatedHash = calculateHash(base
				+ authConfig.getTokenSecret());
		if (hash.equals(calculatedHash)) {
			logger.debug("Token structure verified. Verifying token timestamp");
			long storedTimestamp = userRepo.getUserTokenIssued(userId, getConsumerDetails().getConsumerKey());//NEW
			try {
				long tokenTimestamp = Long.parseLong(timestamp);
				if (tokenTimestamp == storedTimestamp) {
					logger.debug("Timestamp is valid");
				} else {
					logger.debug("Timestamp is invalid ("+tokenTimestamp+" != "+storedTimestamp+")");
					return -1;
				}
				return userId;
			} catch (NumberFormatException e) {
				return -1;
			}
		} else {
			logger.debug("Token structure not valid");
			return -1;
		}
	}

	public UKAppsConsumerDetails loadConsumer(String consumerKey)
			throws ConsumerKeyNotFoundException {
		AppAuthInfo authInfo = authRepo
				.getAppAuthInfoByConsumerKey(consumerKey);

		UKAppsConsumerDetails consumerDetails = new UKAppsConsumerDetails();
		consumerDetails.setConsumerId(authInfo.getAppId());
		consumerDetails.setConsumerName(authInfo.getAppName());
		consumerDetails.setConsumerKey(authInfo.getConsumerKey());
		consumerDetails.setSignatureSecret(authInfo.getConsumerSecret());
		consumerDetails.setAuthority(authInfo.getConsumerRole());
		consumerDetails.setFacebookId(authInfo.getFacebookId());
		consumerDetails.setFacebookSecret(authInfo.getFacebookSecret());
		return consumerDetails;
	}

	public UKAppsConsumerDetails getConsumerDetails() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		UKAppsConsumerDetails details = null;
		if (authentication.getPrincipal() instanceof UKAppsConsumerDetails) {
			details = (UKAppsConsumerDetails) authentication.getPrincipal();
		}
		return details;
	}
	
}
