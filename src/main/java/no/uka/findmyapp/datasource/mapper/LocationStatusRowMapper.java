package no.uka.findmyapp.datasource.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;


import no.uka.findmyapp.model.LocationStatus;

import org.springframework.jdbc.core.RowMapper;

public class LocationStatusRowMapper implements RowMapper<LocationStatus> {
	
	public LocationStatus mapRow(ResultSet rs, int arg1) throws SQLException {
		LocationStatus localeNow = new LocationStatus();
		localeNow.setDanceFactor(rs.getInt("dance_factor"));
		localeNow.setChatFactor(rs.getInt("chat_factor"));
		localeNow.setFlirtFactor(rs.getInt("flirt_factor"));
		localeNow.setFunFactor(rs.getInt("fun_factor"));
		return localeNow;
		//ADD MORE METHODS,HARDER SINCE THEY ARE OBJECTS OR LISTS
	}

}





