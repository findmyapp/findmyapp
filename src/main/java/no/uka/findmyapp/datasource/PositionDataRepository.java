package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uka.findmyapp.datasource.mapper.LocationRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleSignalRowMapper;
import no.uka.findmyapp.datasource.mapper.SignalRowMapper;
import no.uka.findmyapp.datasource.mapper.UserLocationRowMapper;
import no.uka.findmyapp.datasource.mapper.UserPositionRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory
			.getLogger(SensorRepository.class);

	/**
	 * 
	 * @param SSID
	 * @return position associated with this SSID
	 */
	public Location getPosition(Sample sample) {

		Location pos = jdbcTemplate
				.queryForObject(
						"SELECT l.position_location_id, l.name FROM POSITION_LOCATION l, POSITION_SAMPLE sa, POSITION_SIGNAL si "
								+ "WHERE l.id = sa.position_location_id AND sa.position_sample_id = ?",
						new LocationRowMapper(), sample.getLocationId());
		return pos;
	}

	public boolean registerLocation(String locationName) {
		try {
			final String fLocationName = locationName;

			jdbcTemplate.update(
					"INSERT into POSITION_LOCATION(name) values (?)",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setString(1, fLocationName);
						}
					});
			return true;
		} catch (Exception e) {
			logger.error("Could not register given location: " + e);
			return false;
		}
	}

	public int getTotalNumOfAccesspoints() {
		try {
			int totalNumOfAccesspoints = jdbcTemplate
					.queryForInt("SELECT COUNT(bssid) FROM POSITION_ACCESSPOINT");
			return totalNumOfAccesspoints;
		} catch (Exception e) {
			logger.error("Could not get totalNumOfAccesspoints: " + e);
			return -1;
		}
	}

	/**
	 * Registers all given signals, and assigns -120 signalstrength to
	 * unregistered bssid's.
	 * 
	 * @return True if the signals of the given sample are successfully inserted
	 *         into the database
	 */
	private boolean registerSignalsOfSample(Sample sample) {
		try {
			logger.info("Registrerer signaler for sample " + sample.getId()
					+ "..");
			// Insert the signals to the given sample into the database
			final Sample fSample = sample;
			for (final Signal signal : fSample.getSignalList()) {
				jdbcTemplate
						.update("INSERT into POSITION_SIGNAL(position_accesspoint_bssid, signal_strength, position_sample_id) values (?, ?, ?)",
								new PreparedStatementSetter() {
									public void setValues(PreparedStatement ps)
											throws SQLException {
										ps.setString(1, signal.getBssid());
										ps.setInt(2, signal.getSignalStrength());
										ps.setInt(3, fSample.getId());
									}
								});
			}
			return true;
		} catch (Exception e) {
			logger.error("Could not register signals in database: " + e);
			return false;
		}
	}

	/**
	 * @return True if the given sample is successfully inserted into the
	 *         database
	 */
	public boolean registerSample(Sample sample) {
		try {
			// Insert the current location into the database, but only if it do
			// not
			// already exist
			for (final Signal signal : sample.getSignalList()) {
				jdbcTemplate
						.update("INSERT IGNORE INTO POSITION_ACCESSPOINT(bssid) VALUES(?)",
								new PreparedStatementSetter() {
									public void setValues(PreparedStatement ps)
											throws SQLException {
										ps.setString(1, signal.getBssid());
									}
								});
			}
			final Sample fSample = sample;
			int numCurrentLocations = jdbcTemplate
					.queryForInt(
							"SELECT COUNT(position_location_id) FROM POSITION_LOCATION WHERE name = ?",
							sample.getLocationName());
			if (numCurrentLocations == 0) {
				jdbcTemplate.update(
						"INSERT INTO POSITION_LOCATION(name) VALUES(?)",
						new PreparedStatementSetter() {
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setString(1, fSample.getLocationName());
							}
						});
			}
			final int locationId = getLocationByName(fSample.getLocationName())
					.getLocationId();
			// Insert the sample into the database
			jdbcTemplate
					.update("INSERT into POSITION_SAMPLE(position_location_id) values (?)",
							new PreparedStatementSetter() {
								public void setValues(PreparedStatement ps)
										throws SQLException {
									ps.setInt(1, locationId);
								}
							});
			int lastSampleId = jdbcTemplate
					.queryForInt("SELECT position_sample_id FROM POSITION_SAMPLE ORDER BY position_sample_id DESC LIMIT 1");
			sample.setId(lastSampleId);
			// Insert the signals of the sample into the database
			registerSignalsOfSample(sample);
			return true;
		} catch (Exception e) {
			logger.error("Could not register given sample: " + e);
			return false;
		}
	}

	/**
	 * 
	 * @return A list of all test points in the database
	 */
	public List<Sample> getAllSamples() {

		List<Sample> samples = jdbcTemplate.query(
				"SELECT * FROM POSITION_SAMPLE", new SampleRowMapper());

		for (Sample sample : samples) {
			sample.setSignalList(getSignalsFromSample(sample.getId()));
		}
		return samples;
	}

	public List<Sample> getSamples() {
		Map<Integer, Sample> samples = new HashMap<Integer, Sample>();

		jdbcTemplate
				.query("SELECT sample.position_sample_id, sample.position_location_id, signal.position_signal_id, "
						+ "signal.position_accesspoint_bssid, signal.signal_strength "
						+ "FROM POSITION_SAMPLE AS sample, POSITION_SIGNAL AS signal "
						+ "WHERE sample.position_sample_id = signal.position_sample_id",
						new SampleSignalRowMapper(samples));

		return new ArrayList<Sample>(samples.values());
	}

	public boolean registerUserPosition(int userId, int locationId) {
		try {
			final int fUserId = userId;
			final int fLocationId = locationId;
			final Timestamp now = new Timestamp(new Date().getTime());
			jdbcTemplate
					.update("INSERT INTO POSITION_USER_POSITION(user_id, position_location_id, registered_time) VALUES(?, ?, ?) "
							+ "ON DUPLICATE KEY UPDATE position_location_id = ?, registered_time = ?;",
							new PreparedStatementSetter() {
								public void setValues(PreparedStatement ps)
										throws SQLException {
									ps.setInt(1, fUserId);
									ps.setInt(2, fLocationId);
									ps.setTimestamp(3, now);
									ps.setInt(4, fLocationId);
									ps.setTimestamp(5, now);
								}
							});
			return true;
		} catch (Exception e) {
			logger.error("Could not register user position: " + e);
			return false;
		}
	}

	public Location getUserPosition(int userId) {
		try {
			Location location = jdbcTemplate
					.queryForObject(
							"SELECT location.position_location_id, location.name "
									+ "FROM POSITION_LOCATION location, POSITION_USER_POSITION up "
									+ "WHERE location.position_location_id=up.position_location_id AND up.user_id = ?",
							new LocationRowMapper(), userId);
			return location;
		} catch (Exception e) {
			logger.error("Could not get user position: " + e);
			return null;
		}
	}

	public Location getLocation(int locationId) {

		Location location = jdbcTemplate.queryForObject(
				"SELECT * FROM POSITION_LOCATION WHERE position_location_id=?",
				new LocationRowMapper(), locationId);
		return location;
	}

	private Location getLocationByName(String locationName) {

		Location location = jdbcTemplate.queryForObject(
				"SELECT * FROM POSITION_LOCATION WHERE name=?",
				new LocationRowMapper(), locationName);
		return location;
	}

	private List<Signal> getSignalsFromSample(int sampleId) {

		List<Signal> signals = jdbcTemplate.query(
				"SELECT * FROM POSITION_SIGNAL WHERE position_sample_id=?",
				new SignalRowMapper(), sampleId);

		return signals;
	}

	public List<UserPosition> getPositionOfAllUsers() {
		return jdbcTemplate.query("SELECT * FROM POSITION_USER_POSITION",
				new UserPositionRowMapper());
	}

	public Location getPositionOfFriend(int friendId) {
		Location pos = jdbcTemplate
			.queryForObject(
				"SELECT l.position_location_id, l.name FROM POSITION_LOCATION l, POSITION_USER_POSITION up "
						+ "WHERE l.id = up.position_location_id AND up.user_id = ?",
				new LocationRowMapper(), friendId);
		return pos;
	}
	
	public List<User> getAllFriends(int userId) {
		return jdbcTemplate.query(
			"SELECT u.* " + "FROM USER u, FRIENDS f " + 
			"WHERE u.user_id=f.user1_id AND f.user2_id = ? " +
			"UNION " +
			"SELECT u.* " + "FROM USER u, FRIENDS f " +
			"WHERE u.user_id=f.user2_id AND f.user1_id = ?", 
			new UserRowMapper(), userId, userId);		
	}

	// Returns a hashmap of the positions of the friends of the user, with userId as key and locationId as value
	public Map<Integer,Integer> getPositionOfFriends(int userId) {
		Map<Integer,Integer> userPositions = new HashMap<Integer,Integer>();
		jdbcTemplate.query(
				"SELECT DISTINCT u.user_id, pl.position_location_id " +
				"FROM POSITION_USER_POSITION pup, USER u, FRIENDS f, POSITION_LOCATION pl " +
				"WHERE pup.position_location_id = pl.position_location_id " +
				"AND u.user_id = pup.user_id AND pup.user_id IN (SELECT u.user_id FROM USER u, FRIENDS f " +
				"WHERE u.user_id=f.user1_id AND f.user2_id = ? " +
				"UNION " +
				"SELECT u.user_id FROM USER u, FRIENDS f WHERE u.user_id=f.user2_id AND f.user1_id = ?)",
				new UserLocationRowMapper(userPositions), userId, userId);
		return userPositions;
	}
}
