package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.LocationCount;

import org.springframework.jdbc.core.RowMapper;

public class LocationCountRowMapper implements RowMapper<LocationCount> {

	public LocationCount mapRow(ResultSet rs, int rowNum) throws SQLException {
		LocationCount locationCount = new LocationCount();
		locationCount.setLocationName(rs.getString("string_id"));
		locationCount.setUserCount(rs.getInt("count"));
		return locationCount;
	}

}