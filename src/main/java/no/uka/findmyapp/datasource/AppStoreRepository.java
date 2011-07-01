package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.AppRowMapper;
import no.uka.findmyapp.datasource.mapper.EventRowMapper;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.Sample;
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
		//TODO
		List<App> appList = jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE platform=? LIMIT 0,?",
				new AppRowMapper(), platform, count);
		
		logger.info(appList + "");
		return appList;
	}
	
	public App getAppDetails(int appId) {
		/*
		List<App> appList = jdbcTemplate.query("SELECT * FROM APPSTORE_APPLICATION WHERE appstore_app_id=?",
				new AppRowMapper(), appId);
		*/
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
	
	
	/*
	public UkaProgram getUkaProgram(Date startDate, Date endDate) {
		List<Sample> samples = new ArrayList<Sample>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT * FROM sample");
		for(Map row : rows) {
			Sample s = new Sample();
			s.setRoomID((Integer)(row.get("room_id")));
			s.setSignalList(getSignalsFromSample(s.getRoomID()));
			samples.add(s);
		}
		return samples;
	}
	*/
}
