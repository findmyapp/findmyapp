package no.uka.findmyapp.controller.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL; 
import java.net.URLConnection;

import no.uka.findmyapp.datasource.FacebookAuthenticationDataHandler;
import no.uka.findmyapp.model.User;
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
	private Gson g; 
	
	private static final Logger logger = LoggerFactory.getLogger(FacebookAuthenticationController.class);
	
	/**
	 * Velger 
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	
	public ModelAndView getUserId(@RequestParam(value="accessToken", required=true) String accessToken) {
		logger.info("Browsing /auth?accessToken=" + accessToken);
		
		String facebookUrl = FACEBOOK_USER_DATA_URL + accessToken; 
		String userdata = this.getUserdataFromFacebook(facebookUrl);
	/*
		if(userdata.equals("Exception") || userdata.equals("MalformedUrl")) {
			return new ModelAndView("auth", "userdata", userdata);
		}
		else {
			this.registerUserInDatabase(userdata);
		}
		
		return new ModelAndView("auth", "userdata", g.toJson(user)); 
		*/
		return new ModelAndView(); 
	}
	
	private boolean registerUserInDatabase(String userdata) {
		
		
		return false; 
	}
	
	private String getUserdataFromFacebook(String urlWithaccessToken) {
		URL url;
		String returnValue; 

		try {
			url = new URL(urlWithaccessToken);
			URLConnection connection = url.openConnection();
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
			StringBuilder content = new StringBuilder();
			String line;
			
			while((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}
			bufferedReader.close();
			returnValue = content.toString(); 
		} 
		catch (MalformedURLException e) {
			returnValue = "MalformedUrl";
			//returnValue = e.getMessage();
		} 
		catch (Exception e) {
			// Check for error 400, if it's a authentification error - facebook has provided
			// a json object containing the error message. 
			// returnValue = e.getMessage(); 
			returnValue = "Exception";
		}
		return returnValue;
	}

	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}
