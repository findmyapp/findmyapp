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
	public User getUser(String userId) {
		
		return new User(); 
	}
}
