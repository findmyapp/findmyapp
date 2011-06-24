package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import no.uka.findmyapp.model.Position;

/**
 * 
 * @author Cecilie Haugstvedt
 * @author Audun Sørheim
 */
public class PositionRowMapper implements RowMapper<Position> {

	/**
	 * maps each row in the result set to a result object of type Position 
	 * sets name and ssid of the position object
	 */
	public Position mapRow(ResultSet rs, int arg1) throws SQLException {
		Position pos = new Position();
		pos.setName(rs.getString("name"));
		pos.setSSID(rs.getString("ssid"));
		return pos;
	}
}
