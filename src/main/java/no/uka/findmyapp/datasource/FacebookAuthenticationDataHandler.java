package no.uka.findmyapp.datasource;

import javax.sql.DataSource;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository 
public class FacebookAuthenticationDataHandler {
	
	@Autowired 
	private DataSource ds; 
	
	private static final Logger logger = LoggerFactory.getLogger(UkaProgramRepository.class);
	
	public User getUser(String userId) {
		String sql = "SELECT * FROM USER WHERE user_id=" + userId;
		JdbcTemplate jdbc = new JdbcTemplate(ds); 
		User user = jdbc.queryForObject(sql, new UserRowMapper()); 
		logger.info(user.toString());
		
		return user; 
	}
}
