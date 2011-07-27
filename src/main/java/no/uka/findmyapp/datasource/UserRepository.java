package no.uka.findmyapp.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.EventRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationRowMapper;
import no.uka.findmyapp.datasource.mapper.UserPositionRowMapper;
import no.uka.findmyapp.datasource.mapper.UserPrivacyRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.exception.InvalidUserIdOrAccessTokenException;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.PrivacySetting;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;
import no.uka.findmyapp.model.UserPrivacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory
			.getLogger(UserRepository.class);

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	DataSource dataSourceMSSQL;

	public boolean addEvent(int userId, long eventId) {
		try {
			jdbcTemplate.update(
					"INSERT into USER_EVENT(user_id, event_id) values (?, ?)", userId, eventId);
			return true;
		} catch (Exception e) {
			logger.error("Could not register event to user: " + e);
			return false;
		}
	}
	public boolean removeEvent(int userId, long eventId) {
		int n = jdbcTemplate.update("DELETE from USER_EVENT WHERE user_id=? AND event_id=?", userId, eventId);
		return n > 0;
	}

	public List<UkaEvent> getEvents(int userId) {
		List<UkaEvent> events = jdbcTemplate.query("SELECT * FROM UKA_EVENTS e, USER_EVENT ue WHERE e.id = ue.event_id AND ue.user_id = ?", 
				new EventRowMapper(), userId);
		return events;
	}

	public User getUserByTokenIssued(String token) {

		User user = jdbcTemplate.queryForObject(
				"SELECT * FROM USER WHERE token_issued=?", new UserRowMapper(), token);
		return user;
	}
	
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
	
	public UserPrivacy getUserPrivacyForUserId(int userId) {
		// fetch UserPrivacy by user id;

		UserPrivacy privacy = jdbcTemplate
				.queryForObject(
						"SELECT PRIV.* FROM USER_PRIVACY_SETTINGS AS PRIV JOIN USER ON user_privacy_id WHERE USER.user_id = ?",
						new UserPrivacyRowMapper(), userId);
		return privacy;
	}

	// Retrieving data
	public UserPrivacy retrievePrivacy(int privacyId) {
			UserPrivacy privacy = jdbcTemplate
					.queryForObject(
							"SELECT USER_PRIVACY_SETTINGS.* FROM USER_PRIVACY_SETTINGS "
									+ "WHERE USER_PRIVACY_SETTINGS.user_privacy_id = ? ",
							new UserPrivacyRowMapper(), privacyId);
			return privacy;
		}

	// Updating data
	public void updatePrivacy(int userPrivacyId, PrivacySetting newPosition,
			PrivacySetting newEvents, PrivacySetting newMoney,
			PrivacySetting newMedia) {
		logger.info("before db call with" + userPrivacyId + " and "
				+ newPosition.toString() + newEvents.toString());
		jdbcTemplate.update("UPDATE USER_PRIVACY_SETTINGS "
				+ "SET USER_PRIVACY_SETTINGS.position = ? ,"
				+ "USER_PRIVACY_SETTINGS.events = ? ,"
				+ "USER_PRIVACY_SETTINGS.money = ? ,"
				+ "USER_PRIVACY_SETTINGS.media = ? "
				+ "WHERE USER_PRIVACY_SETTINGS.user_privacy_id = ? ",
				PrivacySetting.toInt(newPosition),
				PrivacySetting.toInt(newEvents),
				PrivacySetting.toInt(newMoney), PrivacySetting.toInt(newMedia),
				userPrivacyId);
		logger.info("after db call");
	}

	// Create defaults settings
	public int createDefaultPrivacySettingsEntry() {
		jdbcTemplate.execute("INSERT INTO USER_PRIVACY_SETTINGS "
				+ "(position, events, money, media) VALUES (2, 2, 2, 2)");
		int user_privacy_id = jdbcTemplate
				.queryForInt("SELECT user_privacy_id FROM USER_PRIVACY_SETTINGS ORDER BY user_privacy_id DESC LIMIT 1");
		return user_privacy_id;
	}

	public List<User> getRegisteredFacebookFriends(List<String> friendIds) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		List<User> users = namedParameterJdbcTemplate
				.query("SELECT * FROM USER WHERE facebook_id IN (:ids)",
						Collections.singletonMap("ids", friendIds),
						new UserRowMapper());
		return users;
	}

	public List<User> getFacebookFriendsAtEvent(int eventId,
			List<String> friendIds) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("eventid", eventId);
		namedParameters.put("ids", friendIds);
		// Original code:
		// List<User> users =
		// namedParameterJdbcTemplate.query("SELECT u.* FROM USER u, USER_EVENT e WHERE u.user_id=e.user_id AND e.event_id=:eventid AND u.facebook_id IN (:ids)",
		// namedParameters, new UserRowMapper());
		// Code taking privacy settings into account: (author: Haakon Bakka)
		// (SQL has been tested manually)
		List<User> users = namedParameterJdbcTemplate
				.query("SELECT u.* FROM USER u, USER_EVENT e, USER_PRIVACY_SETTINGS p"
						+ " WHERE u.user_id=e.user_id AND e.event_id=:eventid AND u.facebook_id IN (:ids)"
						+ " AND u.user_privacy_id = p.user_privacy_id AND p.events != 3",
						namedParameters, new UserRowMapper());
		return users;
	}

	/**
	 * Adds a user with a Facebook Id. This is the only information added to DB.
	 * 
	 * @param facebookId
	 *            id of user in Facebook
	 */
	public int addUserWithFacebookId(final String facebookId) {
		
		final String INSERT_SQL = "INSERT INTO USER (facebook_id) VALUES (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(INSERT_SQL, new String[] {"user_id"});
		            ps.setString(1, facebookId);
		            return ps;
		        }

		    },
		    keyHolder);
		Number n = keyHolder.getKey();
		return (Integer) n.intValue();
	}

	public int getUserIdByFacebookId(String facebookId) {
		int userId;
		try {
			userId = jdbcTemplate.queryForInt(
					"SELECT user_id FROM USER WHERE facebook_id=?", facebookId);
		} catch (DataAccessException e) {
			userId = -1;
		}
		return userId;
	}
	
	public String getFacebookIdByUserId(int userId) {
		String facebookId;
		try {
			facebookId = jdbcTemplate.queryForObject(
					"SELECT facebook_id FROM USER WHERE user_id=?", String.class, userId);
		} catch (DataAccessException e) {
			facebookId = null;
		}
		return facebookId;
	}

	public int findUserPrivacyId(int userId) {
		int userPrivacyId = jdbcTemplate.queryForInt(
					"SELECT USER.USER_PRIVACY_ID FROM USER "
							+ "WHERE USER.user_id = ? ", userId);
		return userPrivacyId;
	}

	public int updateUserTokenIssueTime(long tokenIssued, int userId) {
		return jdbcTemplate.update(
				"UPDATE USER SET token_issued=? WHERE user_id=?",
				tokenIssued, userId);
	}

	public long getUserTokenIssued(int userId) {
		return jdbcTemplate.queryForLong("SELECT token_issued FROM USER WHERE user_id=?", userId);
	}

	public List<UserPosition> getLocationOfAllUsers() {
		return jdbcTemplate.query("SELECT * FROM POSITION_USER_POSITION", new UserPositionRowMapper());
	}

}
