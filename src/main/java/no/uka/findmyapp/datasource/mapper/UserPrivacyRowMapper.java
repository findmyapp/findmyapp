package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.PrivacySetting;
import no.uka.findmyapp.model.UserPrivacy;
import org.springframework.jdbc.core.RowMapper;

public class UserPrivacyRowMapper implements RowMapper<UserPrivacy> {
	 
	public UserPrivacy mapRow(ResultSet rs, int arg1)throws SQLException {
		UserPrivacy userPrivacy = new UserPrivacy(); 
		userPrivacy.setId(rs.getInt("user_privacy_id"));
		userPrivacy.setPositionPrivacySetting(PrivacySetting.getSetting( rs.getInt("position") ));
		userPrivacy.setEventsPrivacySetting(PrivacySetting.getSetting(rs.getInt("events")));
		userPrivacy.setMoneyPrivacySetting(PrivacySetting.getSetting(rs.getInt("money")));
		userPrivacy.setMediaPrivacySetting(PrivacySetting.getSetting(rs.getInt("media")));
		return userPrivacy;
		
	}

}
