package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.FactRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationCountRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleSignalRowMapper;
import no.uka.findmyapp.datasource.mapper.SignalRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationCount;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	private static final Logger logger = LoggerFactory
			.getLogger(LocationRepository.class);

	public Location getLocation(Sample sample) {
		Location location = jdbcTemplate
				.queryForObject(
						"SELECT l.position_location_id, l.string_id FROM POSITION_LOCATION l, POSITION_SAMPLE sa, POSITION_SIGNAL si "
								+ "WHERE l.id = sa.position_location_id AND sa.position_sample_id = ?",
						new LocationRowMapper(), sample.getLocationId());
		return location;
	}

	private Location getLocationByName(String locationName) {

		Location location = jdbcTemplate.queryForObject(
				"SELECT * FROM POSITION_LOCATION WHERE string_id=?",
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
					"INSERT into POSITION_LOCATION(string_id) values (?)",
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
		final Timestamp now = new Timestamp(new Date().getTime());
		try {
			int count = jdbcTemplate
					.queryForInt(
							"SELECT COUNT(*) FROM POSITION_USER_POSITION WHERE position_location_id = ? " +
							"AND TIMESTAMPDIFF(HOUR, registered_time, ?) < 2",
							locationId, now);
			return count;
		} catch (Exception e) {
			logger.info("Something went wrong when fetching data from database");
			return -1;
		}

	}

	public List<LocationCount> getUserCountAtAllLocations() {
		List<LocationCount> locationCounts = jdbcTemplate
				.query("SELECT l.string_id, COUNT(up.position_location_id) AS count "
						+ "FROM POSITION_LOCATION l, POSITION_USER_POSITION up "
						+ "WHERE l.position_location_id = up.position_location_id "
						+ "AND TIMESTAMPDIFF(HOUR, up.registered_time, NOW()) < 2 "		
						+ "GROUP BY l.string_id",
						new LocationCountRowMapper());
		//Christian and Haakon changed this so it dint give 500-error
//		List<LocationCount> locationCounts = jdbcTemplate
//		.query("SELECT l.string_id, COUNT(up.position_location_id) AS count "
//				+ "FROM POSITION_LOCATION l, POSITION_USER_POSITION up "
//				+ "WHERE l.position_location_id = up.position_location_id "
//				+ "AND TIMESTAMPDIFF(HOUR, up.registered_time, ?) < 2 "		
//				+ "GROUP BY l.string_id",
//				new LocationCountRowMapper());
		return locationCounts;
	}

	public List<Location> getAllLocations() {
		List<Location> locations = jdbcTemplate.query(
				"SELECT * FROM POSITION_LOCATION", new LocationRowMapper());
		return locations;
	}

	/*
	 * ************* POSITIONING *************
	 */

	public Location getUserLocation(int userId) {
		try {
			Location location = jdbcTemplate
					.queryForObject(
							"SELECT location.position_location_id, location.string_id "
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
							"SELECT COUNT(position_location_id) FROM POSITION_LOCATION WHERE string_id = ?",
							sample.getLocationName());
			if (numCurrentLocations == 0) {
				jdbcTemplate.update(
						"INSERT INTO POSITION_LOCATION(string_id) VALUES(?)",
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


}
