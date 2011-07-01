package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.APRowMapper;
import no.uka.findmyapp.datasource.mapper.PositionRowMapper;
import no.uka.findmyapp.datasource.mapper.RoomRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleRowMapper;
import no.uka.findmyapp.datasource.mapper.SignalRowMapper;
import no.uka.findmyapp.model.Accesspoint;
import no.uka.findmyapp.model.Room;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

/**
 * @author Cecilie Haugstvedt
 * @author Audun Sorheim
 */
@Repository
public class PositionDataRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * @param SSID
	 * @return position associated with this SSID
	 */
	public Room getPosition(Sample sample) {
		
		Room pos = jdbcTemplate
				.queryForObject(
						"SELECT r.position_room_id, r.name FROM POSITION_ROOM r, POSITION_SAMPLE sa, POSITION_SIGNAL si " +
						"WHERE r.id = sa.position_room_id AND sa.position_sample_id = ?",
						new PositionRowMapper(), sample.getRoomId());
		return pos;
	}
	
	public boolean registerRoom(String roomName) {
		try {
			final String fRoomName = roomName;
			
			jdbcTemplate.update("INSERT into POSITION_ROOM(name) values (?)", 
				new PreparedStatementSetter() {
			      public void setValues(PreparedStatement ps) throws SQLException {
			        ps.setString(1, fRoomName);
			      }
			    }
			);
			return true;
		}
		catch(Exception e) {
			System.out.println("Could not register given room: "+e);
			return false;
		}
	}
	
	public int totalNumOfAccesspoints() {
		try {
			int totalNumOfAccesspoints = jdbcTemplate.queryForInt("SELECT COUNT(bssid) FROM POSITION_ACCESSPOINT");
			return totalNumOfAccesspoints;
		}
		catch(Exception e) {
			System.out.println("Could not get totalNumOfAccesspoints: "+e);
			return -1;
		}
	}
	
	/** Registers all given signals, and assigns -120 signalstrength to unregistered bssid's.
	 * @return True if the signals of the given sample are successfully inserted into the database
	 */ 
	private boolean registerSignalsOfSample(Sample sample) {
		try {
			// Insert the signals to the given sample into the database
			final Sample fSample = sample;
			for (final Signal signal : fSample.getSignalList()) {
				jdbcTemplate.update("INSERT into POSITION_SIGNAL(position_accesspoint_bssid, signal_strength, position_sample_id) values (?, ?, ?)", 
					new PreparedStatementSetter() {
				      public void setValues(PreparedStatement ps) throws SQLException {
				        ps.setString(1, signal.getBssid());
				        ps.setInt(2, signal.getSignalStrength());
				        ps.setInt(3, fSample.getId());
				      }
				    }
				);
			}
			/* If the sample do not have signals from all bssid's, insert signals 
			 * from the one's missing with signalstrength -120 */
			/*List<Accesspoint> accesspoints = jdbcTemplate.query("SELECT * FROM POSITION_ACCESSPOINT",
					new APRowMapper());
			for (final Accesspoint AP : accesspoints) {
				boolean apMissing = true;
				for(int i = 0; i < fSample.getSignalList().size(); i++) {
					if(AP.getBssid().equalsIgnoreCase(sample.getSignalList().get(i).getBssid())) {
						apMissing = false; 
						break;
					}
					else continue;
				}
				if(apMissing) {
					jdbcTemplate.update("INSERT into POSITION_SIGNAL(position_accesspoint_bssid, signal_strength, position_sample_id) values (?, ?, ?)", 
						new PreparedStatementSetter() {
					      public void setValues(PreparedStatement ps) throws SQLException {
					        ps.setString(1, AP.getBssid());
					        ps.setInt(2, -120);
					        ps.setInt(3, fSample.getId());
					      }
					    }
					);
				}
			}*/
			return true;
		}
		catch(Exception e) {
			System.out.println("Could not register signals in database: "+e);
			return false;
		}
	}
	/**
	 * @return True if the given sample is successfully inserted into the database
	 */
	public boolean registerSample(Sample sample) {
		try {
			// Insert the current room into the database, but only if it do not already exist
			for(final Signal signal : sample.getSignalList()) {
				jdbcTemplate.update("INSERT IGNORE INTO POSITION_ACCESSPOINT(bssid) VALUES(?)",
					new PreparedStatementSetter() {
				      public void setValues(PreparedStatement ps) throws SQLException {
				        ps.setString(1, signal.getBssid());
				      }
				    }
				);	
			}
			final Sample fSample = sample;
			int numCurrentRooms = jdbcTemplate.queryForInt("SELECT COUNT(position_room_id) FROM POSITION_ROOM WHERE name = ?", sample.getRoomName());
			if(numCurrentRooms == 0) {
				jdbcTemplate.update("INSERT INTO POSITION_ROOM(name) VALUES(?)",
					new PreparedStatementSetter() {
				      public void setValues(PreparedStatement ps) throws SQLException {
				        ps.setString(1, fSample.getRoomName());
				      }
				    }
				);	
			}
			final int roomId = getRoomByName(fSample.getRoomName()).getRoomId();
			// Insert the sample into the database
			jdbcTemplate.update("INSERT into POSITION_SAMPLE(position_room_id) values (?)", 
				new PreparedStatementSetter() {
			      public void setValues(PreparedStatement ps) throws SQLException {
			        ps.setInt(1, roomId);
			      }
			    }
			);
			int rowCount = jdbcTemplate.queryForInt("SELECT COUNT(position_sample_id) FROM POSITION_SAMPLE");
			sample.setId(rowCount);
			// Insert the signals of the sample into the database
			registerSignalsOfSample(sample);
			return true;
		}
		catch(Exception e) {
			System.out.println("Could not register given sample: "+e);
			return false;
		}
	}

	/**
	 * 
	 * @return A list of all test points in the database
	 */
	public List<Sample> getAllSamples() {
		
		List<Sample> samples = jdbcTemplate.query("SELECT * FROM POSITION_SAMPLE",
				new SampleRowMapper());

		for (Sample sample : samples) {
			sample.setSignalList(getSignalsFromSample(sample.getId()));
		}
		return samples;
	}

	public Room getRoom(int roomId) {
		
		Room room = jdbcTemplate.queryForObject(
				"SELECT * FROM POSITION_ROOM WHERE position_room_id=?", new RoomRowMapper(), roomId);
		return room;
	}
	
	private Room getRoomByName(String roomName) {
		
		Room room = jdbcTemplate.queryForObject(
				"SELECT * FROM POSITION_ROOM WHERE name=?", new RoomRowMapper(), roomName);
		return room;
	}

	private List<Signal> getSignalsFromSample(int sampleId) {
		
		List<Signal> signals = jdbcTemplate.query(
				"SELECT * FROM POSITION_SIGNAL WHERE position_sample_id=?",
				new SignalRowMapper(), sampleId);
		
		return signals;
	}

}
