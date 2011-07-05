package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.UserPosition;

import org.springframework.jdbc.core.RowMapper;

public class UserPositionRowMapper implements RowMapper<UserPosition> {

	public UserPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserPosition userPosition = new UserPosition();
		userPosition.setUser("/findmyapp/position/user/" + rs.getString("user_id"));
		userPosition.setRoom("/findmyapp/position/location/" + rs.getString("position_room_id"));
		
		return userPosition; 
	}

}
