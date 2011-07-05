package no.uka.findmyapp.datasource;

import java.util.List;

import no.uka.findmyapp.datasource.mapper.AppRowMapper;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppStoreList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppStoreRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreRepository.class);

	public List<App> getAppList(int count, int listType, int platform) {
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
}
