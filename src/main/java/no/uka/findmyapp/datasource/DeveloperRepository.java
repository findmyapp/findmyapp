package no.uka.findmyapp.datasource;

import java.util.List;

import no.uka.findmyapp.datasource.mapper.AppRowMapper;
import no.uka.findmyapp.datasource.mapper.DeveloperRowMapper;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.Developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

	public List<App> getAppsFromDeveloperId(int developer_id) {
		return jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE appstore_developer_id = ?",
				new AppRowMapper(), developer_id);
	}
	
	public int registerApp(int developer_id, App app, String consumer_key, String consumer_secret) {
		return jdbcTemplate.update("INSERT INTO APPSTORE_APPLICATION " +
				"(name, " +
				"platform, " +
				"description, " +
				"market_identifier, " +
				"appstore_developer_id, " +
				"category, " +
				"publish_date, " +
				"ranking, " +
				"times_downloaded, " +
				"facebook_app_id, " +
				"thumb_image, " +
				"consumer_secret, " +
				"consumer_key, " +
				"facebook_secret) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?);",
				app.getName(), app.getPlatform(), app.getDescription(), app.getMarketID(), developer_id, app.getCategory(),
				app.getRanking(), app.getTimesDownloaded(), app.getFacebookAppID(), app.getThumbImage(), consumer_secret, consumer_key, app.getFacebookSecret());
	}
	
	public int updateApp(int developer_id, App app) {
		String sql = "UPDATE APPSTORE_APPLICATION SET " +
		"name = ?, " +
		"platform = ?, " +
		"description = ?, " +
		"market_identifier = ?, " +
		"facebook_app_id = ?, " +
		"thumb_image = ? " +
		"WHERE appstore_developer_id = ? " +
		"AND appstore_application_id = ?";
		logger.info("running sql : " + sql);
		int res = 0;
		try {
			Object[] params = {app.getName(), app.getPlatform(), app.getDescription(), app.getMarketID(), app.getFacebookAppID(), app.getThumbImage(), developer_id, app.getId()};
			
			res = jdbcTemplate.update(sql, params);
			
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
