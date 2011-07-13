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
		return jdbcTemplate.query("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND place = ?", new EventRowMapper(), place);
	}
	
	public List<String> getUkaPlaces(){
		return jdbcTemplate.queryForList("SELECT DISTINCT place FROM event_showing_real", String.class);
	}
	public List<String> getUkaPlaces(Date from, Date to) {
		return jdbcTemplate.queryForList("SELECT DISTINCT place FROM event_showing_real WHERE showing_time >= ? AND showing_time <= ?", String.class, from, to);
	}
	
	public Event getUkaEventById(int id){
		return jdbcTemplate.queryForObject("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND s.id=?", new EventRowMapper(),id);
	}
	public Event getUkaEventById(int id, Date from, Date to) {
		return jdbcTemplate.queryForObject("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND s.id=? AND showing_time>=? AND showing_time<=?", new EventRowMapper(), id, from, to);
	}
	
	public Event getNextUkaEvent(String place) {
		return jdbcTemplate.queryForObject("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id " +
				"AND s.place = ? AND s.showing_time > (now()) ORDER BY s.showing_time LIMIT 1", new EventRowMapper(), place);
	}

	public List<Event> getEventsOnUser(int userId) {
		return jdbcTemplate.query("SELECT event_showing_real.*,events_event.* FROM USER_EVENT, event_showing_real ," +
				" events_event WHERE USER_EVENT.user_id = ? AND event_showing_real.event_id=events_event.id", 
				new EventRowMapper(),userId);
	}
	public Event getNextUkaEvent(String place, Date from, Date to) {
		return jdbcTemplate.queryForObject("SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id " +
				"AND s.place = ? AND s.showing_time > (now()) AND s.showing_time >= ? AND s.showing_time <= ? ORDER BY s.showing_time LIMIT 1", new EventRowMapper(), place, from, to);
	}
}
