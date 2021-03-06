package no.uka.findmyapp.service;

import java.util.GregorianCalendar;
import java.util.List;

import no.uka.findmyapp.datasource.DeveloperRepository;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppDetailed;
import no.uka.findmyapp.model.appstore.Developer;
import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.utils.NumberUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperService {
	@Autowired
	DeveloperRepository developerRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationService authenticationService;

	private static final Logger logger = LoggerFactory
			.getLogger(DeveloperService.class);
	
	public Developer getDeveloperForWpId(int wpId) {
		return developerRepository.getDeveloperForWpId(wpId);
	}
	
	public Developer getDeveloperForConsumerKey(String consumerKey) {
		return developerRepository.getDeveloperForConsumerKey(consumerKey);
	}
	
	public int registerDeveloper(Developer developer) {
		
		
		String token = authenticationService.login(developer.getAccessToken());
		
		int userId = authenticationService.verify(token);
		//int userId = 0;
		if(userId == 0) {
			//TODO THROW USER NOT FOUND AND COULD NOT BE CREATED
			logger.debug("USER NOT FOUND AND COULD NOT BE CREATED");
		}
		developer.setUserId(userId);
		logger.debug("Got userId: " + userId);
		logger.debug("Registering developer: " + developer.toString());
		return developerRepository.registerDeveloper(developer);
	}

	public List<AppDetailed> getAppsFromDeveloperId(int developer_id) {
		return developerRepository.getAppsFromDeveloperId(developer_id);
	}

	public AppDetailed getDetailedApp(int developerId, int appId) {
		return developerRepository.getDetailedApp(developerId, appId);
	}
	
	
	public int registerApp(int developer_id, App app) {
		return developerRepository.registerApp(developer_id, app, generateConsumerKey(developer_id), generateConsumerSecret(developer_id));
	}
	
	public int updateApp(int developer_id, App app) {
		return developerRepository.updateApp(developer_id, app);
	}
	
	public int updateAppActivation(int developerId, int appId, boolean activated) {
		return developerRepository.updateAppActivation(developerId, appId, activated);
	}
	
	//TODO FIX GENERATION OF KEY/SECRET
	private String generateConsumerKey(int developer_id) {
		long time = + new GregorianCalendar().getTimeInMillis();
		return DigestUtils.shaHex("key" + NumberUtils.generateRandomInteger(1000000, 9999999) + developer_id + time);
	}
	
	private String generateConsumerSecret(int developer_id) {
		long time = + new GregorianCalendar().getTimeInMillis();
		return DigestUtils.shaHex("key" + NumberUtils.generateRandomInteger(1000000, 9999999) + developer_id + time);
	}
}
