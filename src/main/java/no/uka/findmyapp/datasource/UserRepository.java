package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.UserRowMapper;
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

	public List<User> getAllFriends(int userId) {//Needs some change, ie. get friendlist from Facebook.
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

	public List<User> getUsersOnEvent(String sqlFriendList) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public List<User> getFriendsOnEvent(String facebookFriends, int eventId) {
		return jdbcTemplate.query("SELECT USER.* FROM USER_EVENT, USER WHERE USER_EVENT.user_id = USER.user_id" +
				"AND USER_EVENT.event_id =? AND USER.facebook_id IN?",
				new UserRowMapper(), eventId,facebookFriends);
		}
}