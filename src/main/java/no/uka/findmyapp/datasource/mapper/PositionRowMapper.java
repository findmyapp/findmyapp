package no.uka.findmyapp.datasource.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import no.uka.findmyapp.model.*;

import org.springframework.jdbc.core.RowMapper;

import no.uka.findmyapp.model.Room;

/**
 * 
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
public class PositionRowMapper implements RowMapper<Room> {

	/**
	 * maps each row in the result set to a result object of type Position 
	 * sets name and ssid of the position object
	 */
	public Room mapRow(ResultSet rs, int arg1) throws SQLException {
		Room pos = new Room();
		pos.setRoomName(rs.getString("name"));
		return pos;
	}
}
