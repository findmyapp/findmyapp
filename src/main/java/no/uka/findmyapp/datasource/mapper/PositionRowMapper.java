package no.uka.findmyapp.datasource.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import no.uka.findmyapp.model.*;

import org.springframework.jdbc.core.RowMapper;

import no.uka.findmyapp.model.Location;

/**
 * 
 * @author Cecilie Haugstvedt
 * @author Audun Sørheim
 */
public class PositionRowMapper implements RowMapper<Location> {

	/**
	 * maps each row in the result set to a result object of type Position 
	 * sets name and ssid of the position object
	 */
	public Location mapRow(ResultSet rs, int arg1) throws SQLException {
		Location pos = new Location();
		pos.setLocationName(rs.getString("name"));
		return pos;
	}
}
