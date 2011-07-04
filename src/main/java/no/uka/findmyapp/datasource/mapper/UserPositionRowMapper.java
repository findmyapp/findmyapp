package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.UserPosition;

import org.springframework.jdbc.core.RowMapper;

public class UserPositionRowMapper implements RowMapper<UserPosition> {

	public UserPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserPosition userPosition = new UserPosition();
		userPosition.setUserId(rs.getString("user_id"));
		userPosition.setRoom(new RoomRowMapper().mapRow(rs, rowNum));
		
		return userPosition; 
	}

}
