package no.uka.findmyapp.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.PositionRowMapper;
import no.uka.findmyapp.datasource.mapper.RoomRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleRowMapper;
import no.uka.findmyapp.datasource.mapper.SignalRowMapper;
import no.uka.findmyapp.model.Room;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
@Repository
public class PositionDataRepository {

	@Autowired
	private DataSource ds;

	/**
	 * 
	 * @param SSID
	 * @return position associated with this SSID
	 */
	public Room getPosition(Sample sample) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Room pos = jdbcTemplate
				.queryForObject(
						"SELECT r.id, r.name FROM room r, sample sa, signal si WHERE r.id = sa.room_id AND sa.id = ?",
						new PositionRowMapper(), sample.getRoomId());
		return pos;
	}

	/**
	 * 
	 * @return A list of all test points in the database
	 */
	public List<Sample> getAllSamples() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Sample> samples = jdbcTemplate.query("SELECT * FROM sample",
				new SampleRowMapper());
		for (Sample sample : samples) {
			this.getSignalsFromSample(sample.getId());
		}
		return samples;
	}

	public Room getRoom(int roomId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Room room = jdbcTemplate.queryForObject("SELECT * FROM room WHERE id=?",
				new RoomRowMapper(), roomId);
		return room;
	}

	private List<Signal> getSignalsFromSample(int sampleId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Signal> signals = jdbcTemplate.query(
				"SELECT * FROM signal WHERE sample_id=?",
				new SignalRowMapper(), sampleId);
		return signals;
	}

}
