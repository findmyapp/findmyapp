package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.EventRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory
			.getLogger(UserRepository.class);

	public List<User> getAllFriends(int userId) {
		return jdbcTemplate.query("SELECT u.* " + "FROM USER u, FRIENDS f "
				+ "WHERE u.user_id=f.user1_id AND f.user2_id = ? " + "UNION "
				+ "SELECT u.* " + "FROM USER u, FRIENDS f "
				+ "WHERE u.user_id=f.user2_id AND f.user1_id = ?",
				new UserRowMapper(), userId, userId);
	}
	
	public boolean areFriends(int userId1, int userId2) {
		final int id1 = userId1;
		final int id2 = userId2;
		int friends = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("
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
		if(friends > 0) return true;
		else return false;
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
		List<Event> events = jdbcTemplate.query("SELECT * FROM event_showing_real AS s, events_event AS e, USER_EVENT ue "
				+ "WHERE s.event_id=e.id AND e.id = ue.event_id AND ue.user_id = ?", new EventRowMapper(), userId);
		return events;
	}
	
}
