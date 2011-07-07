package no.uka.findmyapp.datasource;

import java.util.List;

import no.uka.findmyapp.datasource.mapper.FactRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> getUsersAtLocation(int locationId) {
		List<User> users = jdbcTemplate.query(
				"SELECT u.* " + "FROM USER u, POSITION_USER_POSITION p "
						+ "WHERE u.user_id=p.user_id "
						+ "AND p.position_location_id=?", new UserRowMapper(),
				locationId);
		return users;
	}

	public List<Location> getAllLocations() {
		List<Location> locations = jdbcTemplate.query(
				"SELECT * FROM POSITION_LOCATION", new LocationRowMapper());
		return locations;
	}
	
	public List<Fact> getAllFacts(int locationId) {
		List<Fact> facts = jdbcTemplate.query(
				"SELECT * FROM POSITION_LOCATION_FACT WHERE position_location_id = ?", 
				new FactRowMapper(), locationId);
		return facts;
	}

	public Fact getRandomFact(int locationId) {
		return jdbcTemplate.queryForObject("SELECT * FROM POSITION_LOCATION_FACT " +
				"WHERE position_location_id = ? ORDER BY rand() limit 1;", new FactRowMapper(), locationId);
	}

}
