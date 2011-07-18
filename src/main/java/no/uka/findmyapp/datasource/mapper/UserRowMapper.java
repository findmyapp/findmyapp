package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserRowMapper.class); 
	
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		logger.info("inside UserRowMapper");
		User user = new User(); 
		user.setLocalUserId(rs.getInt("user_id"));
		user.setFacebookUserId(rs.getInt("facebook_id"));
		user.setUserRegistered(rs.getTimestamp("registered_date"));
		user.setLastLogon(rs.getTimestamp("last_logon"));
		
		return user; 
	}
}