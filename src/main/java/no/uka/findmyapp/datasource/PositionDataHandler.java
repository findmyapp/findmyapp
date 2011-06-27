package no.uka.findmyapp.datasource;

import java.util.List;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.PositionRowMapper;
import no.uka.findmyapp.model.Position;
import no.uka.findmyapp.model.TestPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
@Repository
public class PositionDataHandler {

	@Autowired
	private DataSource ds;

	/**
	 * 
	 * @param SSID
	 * @return position associated with this SSID
	 */
	public Position getPosition(String SSID) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Position pos = jdbcTemplate.queryForObject(
				"SELECT id, name FROM position WHERE ssid=?",
				new PositionRowMapper(), SSID);
		return pos;
	}
	
	/**
	 * 
	 * @return A list of all test points in the database
	 */
	public List<TestPoint> getAllTestPoints() {
		return null;
		
	}

}
