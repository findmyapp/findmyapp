package no.uka.findmyapp.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.AppDetailedRowMapper;
import no.uka.findmyapp.datasource.mapper.DeveloperRowMapper;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppDetailed;
import no.uka.findmyapp.model.appstore.Developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class DeveloperRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory
	.getLogger(DeveloperRepository.class);
	
	public Developer getDeveloperForWpId(int wpId) {
		Developer dev = jdbcTemplate.queryForObject("SELECT * " +
				"FROM APPSTORE_DEVELOPER " +
				"WHERE wp_id = ? ", new DeveloperRowMapper(), wpId);
		return dev;
	}
	
	public int registerDeveloper(Developer developer) {
		return jdbcTemplate.update("INSERT INTO APPSTORE_DEVELOPER " +
				"(fullname, email, wp_id, user_id) " +
				"VALUES (?, ?, ?, ?)",
				developer.getFullName(), developer.getEmail(), developer.getWpId(), developer.getUserId());
	}

	public List<AppDetailed> getAppsFromDeveloperId(int developer_id) {
		return jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE appstore_developer_id = ?",
				new AppDetailedRowMapper(), developer_id);
	}
	
	public AppDetailed getDetailedApp(int developerId, int appId) {
		return jdbcTemplate.queryForObject("SELECT * FROM APPSTORE_APPLICATION WHERE appstore_developer_id = ? AND appstore_application_id = ?",
				new AppDetailedRowMapper(), developerId, appId);
	}
	
	public int registerApp(final int developer_id, final App app, final String consumer_key, final String consumer_secret) {
		
		try {
			final String sql = "INSERT INTO APPSTORE_APPLICATION " +
			"(name, " +
			"platform, " +
			"description, " +
			"market_identifier, " +
			"appstore_developer_id, " +
			"publish_date, " +
			"facebook_app_id, " +
			"thumb_image, " +
			"consumer_secret, " +
			"consumer_key, " +
			"facebook_secret) VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?)";
			
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps =
			                connection.prepareStatement(sql, new String[] {"custom_parameter_id"});
			            ps.setString(1, app.getName());
			            ps.setString(2, app.getPlatform());
			            ps.setString(3, app.getDescription());
			            ps.setString(4, app.getMarketID());
			            ps.setInt(5, developer_id);
			            ps.setString(6, app.getFacebookAppID());
			            ps.setString(7, app.getThumbImage().toString());
			            ps.setString(8, consumer_secret);
			            ps.setString(9, consumer_key);
			            ps.setString(10, app.getFacebookSecret());
			            return ps;
			        }

			    },
			    keyHolder);
			Number n = keyHolder.getKey();
			return (Integer) n.intValue();
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("DataIntegrityViolationException" + e);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			logger.error("DataIntegrityViolationException " + e);
		} catch (DataAccessException e) {
			logger.error("DataAccessException" + e);
			throw e;
		}
		return -1;
	}
	
	public int updateApp(int developerId, App app) {
		String sql = "UPDATE APPSTORE_APPLICATION SET " +
		"name = ?, " +
		"platform = ?, " +
		"description = ?, " +
		"market_identifier = ?, " +
		"facebook_app_id = ?, " +
		"facebook_secret = ?, " +
		"thumb_image = ? " +
		"WHERE appstore_developer_id = ? " +
		"AND appstore_application_id = ?";
		logger.info("running sql : " + sql);
		int res = 0;
		try {
			res = jdbcTemplate.update(sql, app.getName(), app.getPlatform(), app.getDescription(), app.getMarketID(), 
					app.getFacebookAppID(), app.getFacebookSecret(), app.getThumbImage().toString(), developerId, app.getId());
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return res;
	}
	
	public int updateAppActivation(int developerId, int appId, boolean activated) {

		String sql = "UPDATE APPSTORE_APPLICATION SET " +
		"activated = ? " +
		"WHERE appstore_developer_id = ? " +
		"AND appstore_application_id = ?";
		logger.info("running sql : " + sql);
		int res = 0;
		try {
			String act;
			if(!activated) {
				act = "false";
			} else {
				act = "true";
			}
			res = jdbcTemplate.update(sql, act, developerId, appId);
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return res;
	}
	
	public Developer getDeveloperForConsumerKey(String consumerKey) {
		Developer dev;
		try {
			dev = jdbcTemplate.queryForObject("SELECT DEV.* "
					+ "FROM APPSTORE_DEVELOPER AS DEV, APPSTORE_APPLICATION AS APP " + "WHERE APP.consumer_key = ? AND DEV.appstore_developer_id = APP.appstore_developer_id ",
					new DeveloperRowMapper(), consumerKey);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		return dev;
	}
}
