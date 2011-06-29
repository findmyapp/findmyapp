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
						"SELECT r.id, r.name FROM room r, sample sa, signal si " +
						"WHERE r.id = sa.room_id AND sa.id = ?",
						new PositionRowMapper(), sample.getRoomId());
		return pos;
	}
	
	public boolean registerRoom(String roomName) {
		try {
			final String fRoomName = roomName;
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			jdbcTemplate.update("INSERT into room(name) values (?)", 
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
	
	/** Registers all given signals, and assigns -120 signalstrength to unregistered bssid's.
	 * @return True if the signals of the given sample are successfully inserted into the database
	 */ 
	private boolean registerSignalsOfSample(Sample sample) {
		try {
			// Insert the signals to the given sample into the database
			final Sample fSample = sample;
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			for (final Signal signal : fSample.getSignalList()) {
				System.out.println("Signal registrert: BSSID - "+signal.getBssid()+", SampleID - "+fSample.getId());
				jdbcTemplate.update("INSERT into signal(bssid, signalstrength, sample_id) values (?, ?, ?)", 
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
			List<String> accesspoints = jdbcTemplate.query("SELECT * FROM accesspoint",
					new APRowMapper());
			System.out.println("NumOfAccesspoints: "+accesspoints.size());
			for (final String AP : accesspoints) {
				for(int i = 0; i < fSample.getSignalList().size(); i++) {
					if(AP.equalsIgnoreCase(sample.getSignalList().get(i).getBssid())) break;
					else continue;
				}
				jdbcTemplate.update("INSERT into signal(bssid, signalstrength, sample_id) values (?, ?, ?)", 
					new PreparedStatementSetter() {
				      public void setValues(PreparedStatement ps) throws SQLException {
				        ps.setString(1, AP);
				        ps.setInt(2, -120);
				        ps.setInt(3, fSample.getId());
				      }
				    }
				);
			}
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
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			for(final Signal signal : sample.getSignalList()) {
				jdbcTemplate.update("MERGE INTO accesspoint USING (VALUES(CAST(? AS VARCHAR(255)))) AS vals(bssid) ON accesspoint.id = vals.bssid " +
						"WHEN MATCHED THEN UPDATE SET accesspoint.id = vals.bssid " +
						"WHEN NOT MATCHED THEN INSERT VALUES vals.bssid",
					new PreparedStatementSetter() {
				      public void setValues(PreparedStatement ps) throws SQLException {
				        ps.setString(1, signal.getBssid());
				      }
				    }
				);	
			}
			// Insert the sample into the database
			final Sample fSample = sample;			
			jdbcTemplate.update("INSERT into sample(room_id) values (?)", 
				new PreparedStatementSetter() {
			      public void setValues(PreparedStatement ps) throws SQLException {
			        ps.setInt(1, fSample.getRoomId());
			      }
			    }
			);
			int rowCount = jdbcTemplate.queryForInt("SELECT COUNT(id) FROM sample");
			sample.setId(rowCount-1);
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Sample> samples = jdbcTemplate.query("SELECT * FROM sample",
				new SampleRowMapper());

		for (Sample sample : samples) {
			sample.setSignalList(getSignalsFromSample(sample.getId()));
		}
		return samples;
	}

	public Room getRoom(int roomId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Room room = jdbcTemplate.queryForObject(
				"SELECT * FROM room WHERE id=?", new RoomRowMapper(), roomId);
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
