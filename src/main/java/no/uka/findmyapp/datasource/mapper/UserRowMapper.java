package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.User;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User(); 
		user.setLocalUserId(rs.getString("user_id"));
		user.setFacebookUserId(rs.getString("facebook_id"));
		user.setUserRegistered(rs.getTimestamp("registered_date"));
		user.setLastLogon(rs.getTimestamp("last_logon"));
		
		return user; 
	}
}