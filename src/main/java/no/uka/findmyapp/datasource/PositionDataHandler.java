package no.uka.findmyapp.datasource;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.PositionRowMapper;
import no.uka.findmyapp.model.Position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sørheim
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

}
