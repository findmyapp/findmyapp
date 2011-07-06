package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import no.uka.findmyapp.model.User;
import org.springframework.jdbc.core.RowMapper;

public class UserLocationRowMapper implements RowMapper<Integer> {
	
	private Map<Integer,Integer> userPositions;
	
	public UserLocationRowMapper(Map<Integer,Integer> userPositions) {
		this.userPositions = userPositions;
	}
	
	@Override
	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		int userId = (rs.getInt("user_id"));
		int locationId = (rs.getInt("position_location_id"));
		userPositions.put(userId, locationId);
		return userId;
	}
	

}
