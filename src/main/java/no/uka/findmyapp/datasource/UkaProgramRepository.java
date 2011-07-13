package no.uka.findmyapp.datasource;

import java.util.Date;
import java.util.List;

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
	private JdbcTemplate jdbcTemplate;

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
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND showing_time>=? AND showing_time<=?",
				new EventRowMapper(),startDate, endDate  );

		return eventList;
	}
	public List<Event> getUkaProgram(Date startDate, Date endDate, String place) {
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND showing_time>=? AND showing_time<=? AND place =?",
				new EventRowMapper(),startDate, endDate , place );

		return eventList;
	}

	public List<Event> getUkaProgram(){
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id",
				new EventRowMapper());
		return eventList;
	}

	public List<Event> getUkaProgram(String place){
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND place = ?",
				new EventRowMapper(), place);
		return eventList;
	}
	
	
	public List<String> getUkaPlaces(){
		List<String> places;
		places = jdbcTemplate.queryForList("SELECT DISTINCT place FROM event_showing_real", String.class);
		return places;
	}
	
	public Event getUkaEventById(int id){
		Event event;
		logger.info("logH 1");
		event = jdbcTemplate.queryForObject ("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND s.id=?", new EventRowMapper(),id);
		logger.info("logH 2");
		return event;
	}
	
	public Event getNextUkaEvent(String place) {
		return jdbcTemplate.queryForObject("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id " +
				"AND s.place = ? AND s.showing_time > (now()) ORDER BY s.showing_time LIMIT 1", new EventRowMapper(), place);
	}

	public List<Event> getEventsOnUser(int userId) {
		return jdbcTemplate.query("SELECT event_showing_real.*,events_event.* FROM USER_EVENT, event_showing_real , events_event WHERE USER_EVENT.user_id = ? AND event_showing_real.event_id=events_event.id", new EventRowMapper(),userId);
	}
}
