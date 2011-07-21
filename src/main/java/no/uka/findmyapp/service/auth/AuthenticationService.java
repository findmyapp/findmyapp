package no.uka.findmyapp.service.auth;

import java.util.GregorianCalendar;

import no.uka.findmyapp.configuration.AuthenticationConfiguration;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.User;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationConfiguration authConfig;

	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationService.class);

	public String login(String facebookToken) throws CouldNotRetreiveUserIdException {
		Facebook facebook = new FacebookTemplate(facebookToken);
		String userId = facebook.userOperations().getUserProfile().getId();
		String token = null;
		if (userId != null)
			token = getToken(userId);
		else
			throw new CouldNotRetreiveUserIdException("Could not retreive userId with token");
		
		return token;
	}

	private String getToken(String userId) {
		String token = null;
		logger.debug("Fetching user with facebook id: " + userId);
		
		int count = userRepository.isExistingUser(userId);
		if (count == 0) {
			logger.debug("User not found. Adding user with facebook id: "
					+ userId);
			userRepository.addUserWithFacebookId(userId);
		}
		
		token = generateToken(userId);
		return token;
	}

	private String generateToken(String userId) {
		String base = userId + new GregorianCalendar().getTimeInMillis();
		logger.debug("Generating token for base: " + userId);
		String hash = calculateHash(base + authConfig.getTokenSecret());
		logger.debug("Hash generated: " + hash);
		String token = hash + base;
		logger.debug("Token generated: " + token);
		return token;
	}

	private String calculateHash(String base) {
		return DigestUtils.shaHex(base);
	}
	
	/**
	 * @param token The token attached to the message
	 * @return User ID of the token owner
	 */
	private boolean verifyToken(String token) {
		if (token == null || token.length() <= 40) 
			return false;
		String hash = token.substring(0, 40);
		String base = token.substring(40, token.length());
		String calculatedHash = calculateHash(base + authConfig.getTokenSecret());

		return hash.equals(calculatedHash);
	}

	public ConsumerDetails loadConsumer(String consumerKey) {
		User user = userRepository.getUserByConsumerKey(consumerKey);
		
	}

}
