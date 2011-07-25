package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import no.uka.findmyapp.model.LocationReport;

import org.springframework.jdbc.core.RowMapper;

public class LocationReportRowMapper implements RowMapper<LocationReport> {
	
	public LocationReport mapRow(ResultSet rs, int arg1) throws SQLException {
		LocationReport localeNow = new LocationReport();
		localeNow.setParameterName(rs.getString("parameter_name"));
		localeNow.setParameterTextValue(rs.getString("string_value"));
		localeNow.setParameterNumberValue(rs.getFloat("float_value"));
		return localeNow;
		}

}





