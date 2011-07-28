package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.UserPosition;

import org.springframework.jdbc.core.RowMapper;

public class UserPositionRowMapper implements RowMapper<UserPosition> {

	public UserPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserPosition userPosition = new UserPosition();
		userPosition.setUserId(rs.getInt("user_id"));
		userPosition.setLocationId(rs.getInt("position_location_id"));
		userPosition.setTimestamp(rs.getTimestamp("registered_time"));
		return userPosition; 
	}
}
