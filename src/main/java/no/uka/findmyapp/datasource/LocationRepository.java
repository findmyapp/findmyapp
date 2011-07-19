package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uka.findmyapp.datasource.mapper.FactRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationReportRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleSignalRowMapper;
import no.uka.findmyapp.datasource.mapper.SignalRowMapper;
import no.uka.findmyapp.datasource.mapper.UserLocationRowMapper;
import no.uka.findmyapp.datasource.mapper.UserPositionRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationReport;
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

@Repository
public class LocationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory
			.getLogger(LocationRepository.class);

	public Location getLocation(Sample sample) {
		Location location = jdbcTemplate
				.queryForObject(
						"SELECT l.position_location_id, l.name FROM POSITION_LOCATION l, POSITION_SAMPLE sa, POSITION_SIGNAL si "
								+ "WHERE l.id = sa.position_location_id AND sa.position_sample_id = ?",
						new LocationRowMapper(), sample.getLocationId());
		return location;
	}

	private Location getLocationByName(String locationName) {

		Location location = jdbcTemplate.queryForObject(
				"SELECT * FROM POSITION_LOCATION WHERE name=?",
				new LocationRowMapper(), locationName);
		return location;
	}

	public Location getLocation(int locationId) {

		Location location = jdbcTemplate.queryForObject(
				"SELECT * FROM POSITION_LOCATION WHERE position_location_id=?",
				new LocationRowMapper(), locationId);
		return location;
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

	public List<User> getUsersAtLocation(int locationId) {
		List<User> users = jdbcTemplate.query(
				"SELECT u.* " + "FROM USER u, POSITION_USER_POSITION p "
						+ "WHERE u.user_id=p.user_id "
						+ "AND p.position_location_id=?", new UserRowMapper(),
				locationId);
		return users;
	}

	public int getUserCountAtLocation(int locationId) {
		int count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM POSITION_USER_POSITION WHERE position_location_id = ?", locationId);
		return count;
	}

	public List<Location> getAllLocations() {
		List<Location> locations = jdbcTemplate.query(
				"SELECT * FROM POSITION_LOCATION", new LocationRowMapper());
		return locations;
	}

	public Location getLocationOfFriend(int friendId) {
		Location location = jdbcTemplate
				.queryForObject(
						"SELECT l.position_location_id, l.name FROM POSITION_LOCATION l, POSITION_USER_POSITION up "
								+ "WHERE l.position_location_id = up.position_location_id AND up.user_id = ?",
						new LocationRowMapper(), friendId);
		return location;
	}

	public List<UserPosition> getLocationOfAllUsers() {
		return jdbcTemplate.query("SELECT * FROM POSITION_USER_POSITION",
				new UserPositionRowMapper());
	}

	// Returns a hashmap of the positions of the friends of the user, with
	// userId as key and locationId as value
	public Map<Integer, Integer> getLocationOfFriends(int userId) {
		Map<Integer, Integer> userLocations = new HashMap<Integer, Integer>();
		jdbcTemplate
				.query("SELECT DISTINCT u.user_id, pl.position_location_id "
						+ "FROM POSITION_USER_POSITION pup, USER u, FRIENDS f, POSITION_LOCATION pl "
						+ "WHERE pup.position_location_id = pl.position_location_id "
						+ "AND u.user_id = pup.user_id AND pup.user_id IN (SELECT u.user_id FROM USER u, FRIENDS f "
						+ "WHERE u.user_id=f.user1_id AND f.user2_id = ? "
						+ "UNION "
						+ "SELECT u.user_id FROM USER u, FRIENDS f WHERE u.user_id=f.user2_id AND f.user1_id = ?)",
						new UserLocationRowMapper(userLocations), userId,
						userId);
		return userLocations;
	}

	/*
	 * ************* POSITIONING *************
	 */

	public Location getUserLocation(int userId) {
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

	public List<Sample> getAllSamples() {
		List<Sample> samples = jdbcTemplate.query(
				"SELECT * FROM POSITION_SAMPLE", new SampleRowMapper());
		for (Sample sample : samples) {
			sample.setSignalList(getSignalsFromSample(sample.getId()));
		}
		return samples;
	}

	private List<Signal> getSignalsFromSample(int sampleId) {
		List<Signal> signals = jdbcTemplate.query(
				"SELECT * FROM POSITION_SIGNAL WHERE position_sample_id=?",
				new SignalRowMapper(), sampleId);
		return signals;
	}

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

	public boolean registerUserLocation(int userId, int locationId) {
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

	/*
	 * **************** FACT *****************
	 */

	public List<Fact> getAllFacts(int locationId) {
		List<Fact> facts = jdbcTemplate
				.query("SELECT * FROM POSITION_LOCATION_FACT WHERE position_location_id = ?",
						new FactRowMapper(), locationId);
		return facts;
	}

	public Fact getRandomFact(int locationId) {
		return jdbcTemplate
				.queryForObject(
						"SELECT * FROM POSITION_LOCATION_FACT "
								+ "WHERE position_location_id = ? ORDER BY rand() limit 1;",
						new FactRowMapper(), locationId);
	}

	public List<LocationReport> getUserReportedData(int locationId) {
		logger.info("Fetching data ");
		return jdbcTemplate.query("SELECT * FROM POSITION_LOCATION_STATUS" +
				"WHERE position_location_id = ?", new LocationReportRowMapper(),locationId);
	
	}

	public void addData(LocationReport locationReport, int locationId) {
		jdbcTemplate.execute("INSERT INTO POSITION_LOCATION_STATUS (position_location_id ,comment, fun_factor," +
				" dance_factor, chat_factor, flirt_factor, ) VALUES ("+locationId+","+locationReport.getComment()+","
				+locationReport.getFunFactor() +","+ locationReport.getDanceFactor() +", " + locationReport.getChatFactor()+","
				+locationReport.getFlirtFactor());
		logger.info("Data logged: " + locationReport.toString());
	}
}
