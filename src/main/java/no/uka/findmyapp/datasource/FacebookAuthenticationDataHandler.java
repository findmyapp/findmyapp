package no.uka.findmyapp.datasource;

import java.util.Date; 
import java.sql.Timestamp;

import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.facebook.FacebookUserProfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository 
public class FacebookAuthenticationDataHandler {

	@Autowired
	private JdbcTemplate jdbc; 
	
	private static final Logger logger = LoggerFactory.getLogger(UkaProgramRepository.class);
	
	public User getUser(String userId) {
		logger.info(".getUser: " + userId);
		User user = jdbc.queryForObject("SELECT * FROM USER WHERE facebook_id = ?",
				new UserRowMapper(), userId); 
		
		logger.info("FacebookAuthenticationDataHandler:34: " + user.toString());
		
		return user; 
	}
	
	public boolean saveUser(FacebookUserProfile fbp) {
		logger.info("test");
		Timestamp now = new Timestamp(new Date().getTime()); 
		String sql = "INSERT INTO USER (facebook_id, registered_date) " +
					 "VALUES (" + fbp.getId() + ", '" + now.toString() + "')";
		
		logger.info("FacebookAuthenticationDataHandler:43: sql = " + sql);
		
		try {
			jdbc.execute(sql);
		} catch (Exception e) {
			logger.info("FacebookAuthenticationDataHandler:48: " + e.getMessage());
			
			return false; 
		}
		
		return true; 
	}
	
	public boolean userExists(int facebookId) {
		logger.info("userExists: " + facebookId);
		try {
			User user = jdbc.queryForObject("SELECT * FROM USER WHERE facebook_id = ?",  new UserRowMapper(), facebookId); 
		}
		catch(EmptyResultDataAccessException e) {
			logger.error("User did not exist");
			return false; 
		}
		
		logger.info("Existing user: " + facebookId);
		return true; 
	}
}
