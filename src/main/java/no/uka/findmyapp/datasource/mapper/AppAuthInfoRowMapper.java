package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.auth.AppAuthInfo;

import org.springframework.jdbc.core.RowMapper;

public class AppAuthInfoRowMapper implements RowMapper<AppAuthInfo>{

	public AppAuthInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		AppAuthInfo authInfo = new AppAuthInfo();
		authInfo.setAppId(rs.getInt("appstore_application_id"));
		authInfo.setConsumerKey(rs.getString("consumer_key"));
		authInfo.setConsumerSecret(rs.getString("consumer_secret"));
		authInfo.setConsumerRole(rs.getString("consumer_role"));
		return authInfo;
	}
	
}
