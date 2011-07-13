package no.uka.findmyapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.datasource.UserRepository;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository data;
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	public List<User> getAllFriends(int userId) {
		return data.getAllFriends(userId);
	}
	
	public boolean areFriends(int userId1, int userId2) {
		return data.areFriends(userId1, userId2);
	}
	
	public List<Event> getEventOnUser(){
		List<Event> events = null;
		return events;
	}

	public List<Event> getEventsOnUser(int userId) {
		UkaProgramRepository rep = new UkaProgramRepository();
		return rep.getEventsOnUser(userId);
		}
	
	/*Given the correct access token, this method fetches 
	 * the data on the url location, and stores it in a buffered reader,
	 * which can be made in to a string using StringBuilder. Remember to
	 * close the BufferedReader.*/
	public BufferedReader getUserdataFromFacebook(String urlWithaccessToken) throws IOException {
		URL url = new URL(urlWithaccessToken);
		URLConnection connection = url.openConnection();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		return bufferedReader;
	}
	
	public String getFacebookFriends(String accesToken) throws IOException{
		
		String urlWithAccesToken = "https://graph.facebook.com/me/friends?access_token="+accesToken;
		BufferedReader br = getUserdataFromFacebook(urlWithAccesToken);
		StringBuilder content = new StringBuilder();
		
		String line; 
		while((line = br.readLine()) != null) {
			content.append(line + "\n");
		}
		br.close();
		logger.info("FacebookAuticationController:89: " + content.toString());
		return content.toString(); 
				
	}
}
