package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import no.uka.findmyapp.model.Fact;

import org.springframework.jdbc.core.RowMapper;


public class FactRowMapper implements RowMapper<Fact>{

private Map<Integer, Fact> facts;
	
	public FactRowMapper(Map<Integer, Fact> facts) {
		this.facts = facts;
	}
	
	@Override
	public Fact mapRow(ResultSet rs, int rowNum) throws SQLException {
		Fact fact = new Fact();
		fact.setFactId(rs.getInt("position_location_fact_id"));
		fact.setLocationId(rs.getInt("position_location_id"));
		fact.setText(rs.getString("text"));
		
		if (!facts.containsKey(fact.getFactId())) {
			facts.put(fact.getFactId(), fact);
		}
	
		return fact;
	}
}
