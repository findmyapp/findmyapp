package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.EventRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory
			.getLogger(UserRepository.class);

	@Autowired
	DataSource dataSource;

	public boolean areFriends(int userId1, int userId2) {
		final int id1 = userId1;
		final int id2 = userId2;
		int friends = jdbcTemplate
				.queryForInt(
						"SELECT COUNT(*) FROM ("
								+ "SELECT * FROM FRIENDS f "
								+ "WHERE f.user1_id = ? AND f.user2_id = ? "
								+ "UNION "
								+ "SELECT * FROM FRIENDS f WHERE f.user1_id = ? AND f.user2_id = ?) AS areFriends",
						new PreparedStatementSetter() {
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setInt(1, id1);
								ps.setInt(2, id2);
								ps.setInt(3, id2);
								ps.setInt(4, id1);
							}
						});
		if (friends > 0)
			return true;
		else
			return false;
	}

	public List<User> getUsersOnEvent(String sqlFriendList) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getFriendsOnEvent(String facebookFriends, int eventId) {
		return jdbcTemplate
				.query("SELECT USER.* FROM USER_EVENT, USER WHERE USER_EVENT.user_id = USER.user_id"
						+ "AND USER_EVENT.event_id =? AND USER.facebook_id IN?",
						new UserRowMapper(), eventId, facebookFriends);
	}

	public boolean addEvent(int userId, long eventId) {
		try {
			final long event_id = eventId;
			final int user_id = userId;
			jdbcTemplate.update(
					"INSERT into USER_EVENT(user_id, event_id) values (?, ?)",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, user_id);
							ps.setLong(2, event_id);
						}
					});
			return true;
		} catch (Exception e) {
			logger.error("Could not register given location: " + e);
			return false;
		}
	}

	public List<Event> getEvents(int userId) {
		List<Event> events = jdbcTemplate
				.query("SELECT * FROM event_showing_real AS s, events_event AS e, USER_EVENT ue "
						+ "WHERE s.event_id=e.id AND e.id = ue.event_id AND ue.user_id = ?",
						new EventRowMapper(), userId);
		return events;
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
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("eventid", eventId);
		namedParameters.put("ids", friendIds);
		
		List<User> users = namedParameterJdbcTemplate.query("SELECT u.* FROM USER u, USER_EVENT e WHERE u.user_id=e.user_id AND e.event_id=:eventid AND u.facebook_id IN (:ids)", namedParameters, new UserRowMapper());
		return users;
	}

}
