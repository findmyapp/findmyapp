package no.uka.findmyapp.datasource;

import java.util.List;

import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

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

}
