package no.uka.findmyapp.datasource;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.EventRowMapper;
import no.uka.findmyapp.model.Event;
import no.uka.findmyapp.model.UkaProgram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UkaProgramRepository {

	@Autowired
	private DataSource ds;

	private static final Logger logger = LoggerFactory
	.getLogger(UkaProgramRepository.class);


	public List<Event> getUkaProgram(Date day) {
		Date endDate = new Date(day.getTime()+86400000);// endDate =  (day+24h)
		return getUkaProgram(day, endDate);
	}

	public List<Event> getUkaProgram(Date day, String place) {
		Date endDate = new Date(day.getTime()+86400000);// endDate =  (day+24h)
		return getUkaProgram(day, endDate, place);
	}
	
	
	public List<Event> getUkaProgram(Date startDate, Date endDate) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND showing_time>=? AND showing_time<=?",
				new EventRowMapper(),startDate, endDate  );

		return eventList;
	}
	public List<Event> getUkaProgram(Date startDate, Date endDate, String place) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND showing_time>=? AND showing_time<=? AND place =?",
				new EventRowMapper(),startDate, endDate , place );

		return eventList;
	}

	public List<Event> getUkaProgram(){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id",
				new EventRowMapper());
		return eventList;
	}

	public List<Event> getUkaProgram(String place){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND place = ?",
				new EventRowMapper(), place);
		return eventList;
	}
	
	
	public List<String> getUkaPlaces(){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		List<String> places;
		places = jdbcTemplate.queryForList("SELECT DISTINCT place FROM event_showing_real", String.class);
		return places;
	}
	
	public Event getUkaEventById(int id){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Event event;
		logger.info("logH 1");
		event = jdbcTemplate.queryForObject ("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND s.id=?", new EventRowMapper(),id);
		logger.info("logH 2");
		return event;
	}
}
