package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import no.uka.findmyapp.model.LocationReport;

import org.springframework.jdbc.core.RowMapper;

public class LocationReportRowMapper implements RowMapper<LocationReport> {





	
	public LocationReport mapRow(ResultSet rs, int arg1) throws SQLException {
		LocationReport localeNow = new LocationReport();
		localeNow.setDanceFactor(rs.getInt("dance_factor"));
		localeNow.setChatFactor(rs.getInt("chat_factor"));
		localeNow.setFlirtFactor(rs.getInt("flirt_factor"));
		//localeNow.setFunFactor(rs.getInt("fun_factor"));
		localeNow.setComment(rs.getString("comment"));
		localeNow.setHeadCount(rs.getInt("headcount"));
		return localeNow;
		}

}





