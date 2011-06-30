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
	
	private static final Logger logger = LoggerFactory.getLogger(FacebookAuthenticationController.class);
	
	/**
	 * Velger 
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	
	public ModelAndView getUserId(@RequestParam(value="accessToken", required=true) String accessToken) {
	/*  
	 	json for debug purpose
		String userdata = "{ \"id\": \"772612305\", \"name\": \"Torstein M. Barkve\", \"first_name\": \"Torstein\", \"last_name\": \"M. Barkve\", \"link\": \"http://www.facebook.com/profile.php?id=772612305\", \"birthday\": \"05/10/1990\", \"education\": [ {\"school\": { \"id\": \"109956045693499\", \"name\": \"St. Svithun Videreg\u00e5ende Skole\" },  \"type\": \"High School\" }, { \"school\": { \"id\": \"109449622407837\", \"name\": \"Oslo University College\" }, \"year\": { \"id\": \"136328419721520\", \"name\": \"2009\" }, \"concentration\": [ {    \"id\": \"135775433156356\", \"name\": \"Anvendt datateknologi\" } ], \"type\": \"College\" } ], \"gender\": \"male\", \"timezone\": 2, \"locale\": \"nn_NO\", \"verified\": true, \"updated_time\": \"2011-06-06T12:18:15+0000\" }";
		if(accessToken.equals("debug")) {
			return new ModelAndView("auth", "userid", jsonTemp); 
		}
	*/
		
		String facebookUrl = FACEBOOK_USER_DATA_URL + accessToken; 
		//String userdata = this.getUserdataFromFacebook(facebookUrl);
	
/*		if(userdata.equals("Exception")) {
			return new ModelAndView("auth", "userdata", userdata);
		}
		else {
			//data
		}
		*/
		User user = data.getUser("123");
		
		return new ModelAndView("auth", "userdata", user); 
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
