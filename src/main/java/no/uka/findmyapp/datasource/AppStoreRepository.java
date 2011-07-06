package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.AppRowMapper;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppStoreList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class AppStoreRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreRepository.class);


	public List<App> getAppList(int count, int listType, int platform, String app_category) {
		List<App> appList;
		switch (listType) {
		case 1:
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE platform=? LIMIT 0,?",
					new AppRowMapper(), platform, count);
			logger.info(appList+ "");
			logger.info("Listing 10 apps");
			break;

		case 2:
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE platform=? ORDER BY times_downloaded DESC LIMIT 0,? ",
					new AppRowMapper(), platform, count);
			logger.info(appList+ "");
			logger.info("Sorting after most downloaded");
			break;

		case 3:			
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE platform=? ORDER BY publish_time DESC LIMIT 0,?",
					new AppRowMapper(), platform, count);
			logger.info(appList+ "");
			logger.info("Sorting after publish date");
			break;

		case 4:			
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE platform=? AND category=? ORDER BY publish_time DESC LIMIT 0,?",
					new AppRowMapper(), platform, app_category, count);
			logger.info(appList+ "");
			logger.info("Getting apps by category");
			break;


		default:
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE platform=? LIMIT 0,?",
					new AppRowMapper(), platform, count);
			logger.info(appList + "  DEFAULT!");
			break;
		}
		return appList;
	}

	public App getAppDetails(int appId) {

		App app = jdbcTemplate.queryForObject("SELECT * FROM APPSTORE_APPLICATION WHERE appstore_application_id=?",
				new AppRowMapper(), appId);

		logger.info(app.toString());
		return app;
	}


	public AppStoreList getAppStoreList() {

		Object o = jdbcTemplate.queryForInt("SELECT user_id FROM user_table");
		logger.info(o.toString());

		AppStoreList appStoreList = new AppStoreList();
		return appStoreList;
	}

	public App addUkaApp() {
		//TODO
		return null;
	}

	public App updateUkaApp() {
		//TODO
		return null;
	}

	public boolean registerApp(App newApp){
		final App app = newApp;
		try{
		jdbcTemplate.update(
				"INSERT INTO APPSTORE_APPLICASTION(name, market_identifier, platform, description, facebook_app_id, deevloper_id) VALUES(?,?,?,?,?,?)",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
					throws SQLException {
						ps.setString(1, app.getName());
						ps.setString(1, app.getMarketID());
						ps.setInt(1, app.getPlatform());
						ps.setString(1, app.getDescription());
						ps.setString(1, app.getFacebookAppID());
						ps.setString(1, app.getDeveloperID());
					}
				});
		return true;
		}
		catch(Exception e){
			logger.error("Could not register given app: " + e);
			logger.info(" " + newApp);
			return false;
		}
	}
}
