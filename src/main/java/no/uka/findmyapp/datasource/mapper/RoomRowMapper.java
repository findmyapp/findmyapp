package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Room;

import org.springframework.jdbc.core.RowMapper;

public class RoomRowMapper implements RowMapper<Room> {

	public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
		Room room = new Room();
		room.setRoomId(rs.getInt("id"));
		room.setRoomName(rs.getString("name"));
		return room;
	}

}
