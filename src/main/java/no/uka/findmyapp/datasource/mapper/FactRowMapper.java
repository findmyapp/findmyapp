package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Fact;

import org.springframework.jdbc.core.RowMapper;

public class FactRowMapper implements RowMapper<Fact>{

	public Fact mapRow(ResultSet rs, int rowNum) throws SQLException {
		Fact fact = new Fact();
		fact.setFactId(rs.getInt("location_fact_id"));
		fact.setLocationId(rs.getInt("position_location_id"));
		fact.setText(rs.getString("text"));
	
		return fact;
	}
}
