package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.User;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User(); 
		user.setFacebookUserId(rs.getString("facebookUserId"));
		user.setLocalUserId(rs.getString("localUserId")); 
		
		return user; 
	}
}