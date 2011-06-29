package no.uka.findmyapp.datasource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.EventRowMapper;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.model.AppStore.AppStoreList;
import no.uka.findmyapp.model.AppStore.ListType;
import no.uka.findmyapp.model.AppStore.Platform;
import no.uka.findmyapp.model.AppStore.UkaApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppStoreRepository {
	@Autowired
	private DataSource ds;
	
	private static final Logger logger = LoggerFactory
	.getLogger(UkaProgramRepository.class);

	public AppStoreList getAppStoreList(int count, ListType listType, Platform platform) {
		
		//TODO
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		/*
		  Object args[] = new Object[2];
		  args[0] = startDate;
		  args[1] = endDate;
		  List<UkaApp> eventList = jdbcTemplate.query(
		    "SELECT * FROM uka_app AS ua, events_event AS e WHERE s.event_id=e.id AND showing_time>=? AND showing_time<=?",
		    new EventRowMapper(),startDate, endDate  );
		  
		  UkaProgram ukaProgram = new UkaProgram(eventList);
		  return ukaProgram;
		  */
		AppStoreList appStoreList = new AppStoreList(null);
		return appStoreList;
	}
	
	
	public AppStoreList getAppStoreList() {
		AppStoreList appStoreList = new AppStoreList();
		return appStoreList;
	}
	
	public UkaApp addUkaApp() {
		//TODO
		return null;
	}
	
	public UkaApp updateUkaApp() {
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
