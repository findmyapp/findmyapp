package no.uka.findmyapp.datasource;

import java.util.List;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.PositionRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleRowMapper;
import no.uka.findmyapp.model.Room;
import no.uka.findmyapp.model.Sample;

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
	public Room getPosition(Sample sample) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Room pos = jdbcTemplate.queryForObject(
				"SELECT r.id, r.name FROM room r, sample sa, signal si WHERE r.id = sa.room_id AND sa.id = ?",
				new PositionRowMapper(), sample.roomID);
		return pos;
	}
	
	/**
	 * 
	 * @return A list of all test points in the database
	 */
	public List<Sample> getAllTestPoints() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Room pos = jdbcTemplate.queryForObject(
				"SELECT r.id, r.name FROM room r, sample sa, signal si WHERE r.id = sa.room_id AND sa.id = ?",
				new SampleRowMapper(), sample.roomID);
		
	}

}
