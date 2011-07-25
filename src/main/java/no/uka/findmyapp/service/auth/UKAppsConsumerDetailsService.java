package no.uka.findmyapp.service.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

public class UKAppsConsumerDetailsService implements ConsumerDetailsService {

	@Autowired 
	AuthenticationService service;

	public ConsumerDetails loadConsumerByConsumerKey(String consumerKey)
			throws OAuthException {
		ConsumerDetails details;
		try {
			details = service.loadConsumer(consumerKey);
		} catch (ConsumerKeyNotFoundException e) {
			throw new OAuthException(e.getMessage(), e.getCause());
		}
		return details;
	}
	
	public AuthenticationService getService() {
		return service;
	}
	
	public void setService(AuthenticationService service) {
		this.service = service;
	}

}
