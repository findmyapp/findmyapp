package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import no.uka.findmyapp.model.Sample;

import no.uka.findmyapp.model.Room;

import org.springframework.jdbc.core.RowMapper;

public class SampleRowMapper implements RowMapper<List<Sample>> {

	/**
	 * maps each row in the result set to a result object of type Position 
	 * sets name and ssid of the position object
	 */
	public List<Sample> mapRow(ResultSet rs, int arg1) throws SQLException {
		Room pos = new Room();
		pos.setRoomName(rs.getString("name"));
		return null;
	}
}
