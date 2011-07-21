package no.uka.findmyapp.service.auth;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.AppAuthInfoRowMapper;
import no.uka.findmyapp.model.auth.AppAuthInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	@Autowired
	DataSource ds;

	public AppAuthInfo getAppAuthInfoByConsumerKey(String consumerKey)
			throws ConsumerKeyNotFoundException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		AppAuthInfo appAuthInfo;
		try {
			appAuthInfo = jdbcTemplate
					.queryForObject(
							"SELECT appstore_application_id, consumer_secret, consumer_key " +
							"FROM APPSTORE_APPLICATION " +
							"WHERE consumer_key=?",
							new AppAuthInfoRowMapper(),
							consumerKey);
		} catch (DataAccessException e) {
			throw new ConsumerKeyNotFoundException(
					"Consumer key not found. Consumer may not have a key registered, or the wrong key is provided");
		}
		return appAuthInfo;
	}

}
