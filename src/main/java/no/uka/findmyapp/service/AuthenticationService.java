package no.uka.findmyapp.service;

import java.util.GregorianCalendar;

import javax.security.auth.login.LoginException;

import no.uka.findmyapp.configuration.AuthenticationConfiguration;
import no.uka.findmyapp.datasource.AuthenticationRepository;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.auth.AppAuthInfo;
import no.uka.findmyapp.model.auth.UKAppsConsumerDetails;
import no.uka.findmyapp.service.auth.ConsumerKeyNotFoundException;
import no.uka.findmyapp.service.auth.ErrorLoadingUserAssociatedWithTokenException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

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
			facebookId = facebook.userOperations().getUserProfile().getId();

		String token = null;
		if (facebookId != null) {
			logger.debug("Find userId of user with facebookId " + facebookId);
			int userId = userRepo.getUserIdByFacebookId(facebookId);

			if (userId == 0) {
				logger.debug("User not found. Adding user with facebook id: "
						+ facebookId);
				userRepo.addUserWithFacebookId(facebookId);
			} else {
				logger.debug("User with userId " + userId + " found.");
			}
			token = generateToken(userId);
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

		logger.debug("Updating user profile.");
		int updated = userRepo.updateUserTokenIssueTime(tokenIssued, userId);
		if (updated != 1)
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
	 * @return User ID of the token owner
	 */
	public boolean verify(String token) {
		if (token == null || token.length() <= 42) 
			return false;
		
		int startOfUserId = token.lastIndexOf(USER_ID_DELIMITER);
		int startOfTimestamp = token.lastIndexOf(TIMESTAMP_DELIMITER);
		
		String hash = token.substring(0, startOfUserId);
		String base = token.substring(startOfUserId);
		String userId = token.substring(startOfUserId+1, startOfTimestamp);
		String timestamp = token.substring(startOfTimestamp+1);
		
		logger.debug("Verifying token structure");
		String calculatedHash = calculateHash(base + authConfig.getTokenSecret());
		if (hash.equals(calculatedHash)) {
			logger.debug("Token structure verified. Verifying token timestamp");
			long storedTimestamp = userRepo.getUserTokenIssued(userId);
			try {
				long tokenTimestamp = Long.parseLong(timestamp);
				boolean timestampValid = tokenTimestamp == storedTimestamp;
				logger.debug("Timestamp of token is " + (timestampValid ? "valid" : "invalid"));
				return timestampValid;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			logger.debug("Token structure not valid");
			return false;
		}
	}

	public UKAppsConsumerDetails loadConsumer(String consumerKey)
			throws ConsumerKeyNotFoundException {
		AppAuthInfo authInfo = authRepo
				.getAppAuthInfoByConsumerKey(consumerKey);

		UKAppsConsumerDetails consumerDetails = new UKAppsConsumerDetails();
		consumerDetails.setConsumerId(authInfo.getAppId());
		consumerDetails.setConsumerKey(authInfo.getConsumerKey());
		consumerDetails.setSignatureSecret(authInfo.getConsumerSecret());
		consumerDetails.setAuthority(authInfo.getConsumerRole());

		return consumerDetails;
	}

}
