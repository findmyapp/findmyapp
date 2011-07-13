package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.UserPrivacy;
import org.springframework.jdbc.core.RowMapper;

public class UserPrivacyRowMapper implements RowMapper<UserPrivacy> {
	 
	public UserPrivacy mapRow(ResultSet rs, int arg1)throws SQLException {
		UserPrivacy userPrivacy = new UserPrivacy(); 
		userPrivacy.setPosition(rs.getInt("position"));
		userPrivacy.setEvents(rs.getInt("events"));
		userPrivacy.setMoney(rs.getInt("money"));
		return userPrivacy;
		
	}

}
