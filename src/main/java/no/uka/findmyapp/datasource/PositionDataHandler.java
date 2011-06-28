package no.uka.findmyapp.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.PositionRowMapper;
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
	public List<Sample> getAllSamples() {
		List<Sample> samples = new ArrayList<Sample>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT * FROM sample");
		for(Map row : rows) {
			Sample s = new Sample();
			s.setRoomID((Integer)(row.get("room_id")));
			s.setSignalList(getSignalsFromSample(s.getRoomID()));
			samples.add(s);
		}
		return samples;
	}
	
	public List<Signal> getSignalsFromSample(int sampleID) {
		List<Signal> signals = new ArrayList<Signal>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
		"SELECT * FROM signal WHERE sample_id = '"+sampleID+"'");
		for(Map row : rows) {
			Signal s = new Signal();
			s.setBssid((String)(row.get("bssid")));
			s.setLevel((Integer)(row.get("signalstrength")));
			signals.add(s);
		}
		return signals;
	}	
}
