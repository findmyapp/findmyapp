package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Location;

import org.springframework.jdbc.core.RowMapper;

public class LocationRowMapper implements RowMapper<Location> {

	public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
		Location location = new Location();
		location.setLocationId(rs.getInt("position_location_id"));
		location.setStringId(rs.getString("string_id"));
		location.setLocationName(rs.getString("name"));
		return location;
	}

}
