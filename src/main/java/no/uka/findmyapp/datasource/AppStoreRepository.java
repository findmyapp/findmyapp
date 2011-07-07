package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.AppDetailedRowMapper;
import no.uka.findmyapp.datasource.mapper.AppRowMapper;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppDetailed;
import no.uka.findmyapp.model.appstore.AppStoreList;

import org.joda.time.DateTime;
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
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE platform=? LIMIT 0,?",
					new AppRowMapper(), platform, count);
			logger.info(appList+ "");
			logger.info("Listing 10 apps");
			break;

		case 2:
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE platform=? ORDER BY times_downloaded DESC LIMIT 0,? ",
					new AppRowMapper(), platform, count);
			logger.info(appList+ "");
			logger.info("Sorting after most downloaded");
			break;

		case 3:			
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE platform=? ORDER BY publish_date DESC LIMIT 0,?",
					new AppRowMapper(), platform, count);
			logger.info(appList+ "");
			logger.info("Sorting after publish date");
			break;

		case 4:			
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE platform=? AND category=? ORDER BY publish_date DESC LIMIT 0,?",
					new AppRowMapper(), platform, app_category, count);
			logger.info(appList+ "");
			logger.info("Getting apps by category");
			break;


		default:
			appList =  jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE platform=? LIMIT 0,?",
					new AppRowMapper(), platform, count);
			logger.info(appList + "  DEFAULT!");
			break;
		}
		logger.info("Number of apps in list: " +appList.size());
		return appList;
	}

	public App getAppDetails(int appId) {

		App app = jdbcTemplate.queryForObject("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE appstore_application_id=?",
				new AppRowMapper(), appId);

		logger.info(app.toString());
		return app;
	}

	public App getAppFromMarketID(String marketID) {

		App app = jdbcTemplate.queryForObject("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE app.market_identifier = ?",
				new AppRowMapper(), marketID);

		logger.info(app.toString());
		return app;
	}

	public AppDetailed getAppDetailed(App app){
		AppDetailed appDetailed= jdbcTemplate.queryForObject("SELECT * FROM APPSTORE_APPLICATION AS app JOIN APPSTORE_DEVELOPER AS dev ON app.appstore_developer_id = dev.appstore_developer_id WHERE app.market_identifier = ?",
				new AppDetailedRowMapper(), app.getMarketID());
		return appDetailed;
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
					"INSERT INTO APPSTORE_APPLICATION(name, market_identifier, platform, description, facebook_app_id, appstore_developer_id, publish_date) VALUES(?,?,?,?,?,?,NOW())",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
						throws SQLException {
							ps.setString(1, app.getName());
							ps.setString(2, app.getMarketID());
							ps.setInt(3, app.getPlatform());
							ps.setString(4, app.getDescription());
							ps.setString(5, app.getFacebookAppID());
							ps.setString(6, app.getDeveloperID());
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
	public boolean setNewFeaturedApp(App app){
		// Set all categories to "none" in the appropriate list 
		final App featuredApp = app;
		try{
			jdbcTemplate.update(
					"UPDATE APPSTORE_APPLICATION SET category='none' WHERE platform=?",
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
						throws SQLException {
							ps.setInt(1, featuredApp.getPlatform());
						}
					});
			logger.info("Category reset success!");
			try{
				jdbcTemplate.update(
						"UPDATE APPSTORE_APPLICATION SET category='app_of_the_day' WHERE market_identifier=?",
						new PreparedStatementSetter() {
							public void setValues(PreparedStatement ps)
							throws SQLException {
								ps.setString(1, featuredApp.getMarketID());
							}
						});
				logger.info("app with market id = " + featuredApp.getMarketID() + "is now set to featured");
				return true;
			}
			catch(Exception e){
				logger.error("Could not set featured app category " + e);
				logger.info(" " + featuredApp);
				return false;
			}
		}
		catch(Exception e){
			logger.error("Could not reset categories " + e);
			logger.info(" " + featuredApp);
			return false;
		}
	}
}


