package no.uka.findmyapp.service;

import java.util.List;

import no.uka.findmyapp.model.auth.UKAppsConsumerDetails;
import no.uka.findmyapp.service.auth.ConsumerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookService {

	private static final Logger logger = LoggerFactory
			.getLogger(FacebookService.class);

	public List<String> getFacebookFriends(String facebookToken, String userId) throws ConsumerException {
		Facebook facebook = new FacebookTemplate(facebookToken);
		List<String> friendIds;
		try {
			friendIds = facebook.friendOperations().getFriendIds(userId);
		} catch (Exception e) {
			throw new ConsumerException("Error getting friends of user " + userId + ". The app may not have the right privileges?");
		}
		return friendIds;
	}

	public String getConsumerFacebookToken(UKAppsConsumerDetails consumerDetails)
			throws ConsumerException {

		String facebookId = consumerDetails.getFacebookId();
		String facebookSecret = consumerDetails.getFacebookSecret();
		RestTemplate rest = new RestTemplate();

		String response;
		try {
			logger.debug("Fetching Facebook consumer token for consumer with id "
					+ consumerDetails.getConsumerId());
			response = rest.getForObject("https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + facebookId + "&client_secret="
					+ facebookSecret + "&type=client_cred", String.class);

		} catch (RestClientException e) {
			logger.error("Exception while trying to connect to Facebook and fetching token");

			throw new ConsumerException(
					"Consumer token could not be retreived from Facebook for app "
							+ consumerDetails.getConsumerName());
		}

		int indexOfToken = response.indexOf('=') + 1;
		String consumerToken = response.substring(indexOfToken);
		logger.debug("Consumer token for app "
				+ consumerDetails.getConsumerId() + " is " + consumerToken);
		return consumerToken;
	}

}
