package no.uka.findmyapp.controller.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL; 
import java.net.URLConnection;

import no.uka.findmyapp.datasource.FacebookAuthenticationDataHandler;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.facebook.FacebookUserProfile;
//import no.uka.findmyapp.model.;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class FacebookAuthenticationController 
{
	private static final String FACEBOOK_USER_DATA_URL = "https://graph.facebook.com/me?access_token=";
	
	@Autowired
	private FacebookAuthenticationDataHandler data; 
	
	@Autowired
	private Gson gson; 
	
	private static final Logger logger = LoggerFactory.getLogger(FacebookAuthenticationController.class);
	
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public ModelAndView getUserId(@RequestParam(value="accessToken", required=true) String accessToken) {
		logger.info("Browsing /auth?accessToken=" + accessToken);
		String facebookUrl = FACEBOOK_USER_DATA_URL + accessToken;
		FacebookUserProfile fbp = new FacebookUserProfile(); // default null
		 
		try {
			String userdata = this.getUserdataFromFacebook(facebookUrl);
			logger.info("userdata: " + userdata);
			fbp = this.parseFacebookProfile(userdata);
			logger.info("fbp.toString(): " + gson.toJson(fbp));
			this.saveUserProfile(fbp);
			
			logger.info("Creating view"); 
			return new ModelAndView("auth", "userdata", userdata);
		} 
		catch(Exception e) {
			logger.error("FacebookAuthenticationController:58 " + e.toString());
		}

		return new ModelAndView("auth", "userdata", "{\"exception\":\"400\"}"); 
	}
	
	private boolean saveUserProfile(FacebookUserProfile fbp) {
		if(data.userExists(fbp.getId())) {
		//if(data.userExists(1234)) {
			return false; 
		}
		data.saveUser(fbp);
		return true; 
	}
	
	private FacebookUserProfile parseFacebookProfile(String fbdata) {
		FacebookUserProfile fbup = new FacebookUserProfile(); 
		return gson.fromJson(fbdata, FacebookUserProfile.class);
	}
	
	private String getUserdataFromFacebook(String urlWithaccessToken) throws IOException {
		URL url = new URL(urlWithaccessToken);
		URLConnection connection = url.openConnection();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder content = new StringBuilder();

		String line; 
		while((line = bufferedReader.readLine()) != null) {
			content.append(line + "\n");
		}
		bufferedReader.close();
		logger.info("FacebookAuticationController:89: " + content.toString());
		
		return content.toString(); 
	}

	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}
