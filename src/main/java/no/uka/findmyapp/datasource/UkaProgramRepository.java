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
				"SELECT * FROM UKA_EVENTS WHERE showing_time>=? AND showing_time<=?",
				new EventRowMapper(),startDate, endDate  );

		return eventList;
	}
	public List<Event> getUkaProgram(Date startDate, Date endDate, String place) {
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM UKA_EVENTS WHERE showing_time>=? AND showing_time<=? AND place =?",
				new EventRowMapper(),startDate, endDate , place );

		return eventList;
	}

	public List<Event> getUkaProgram(){
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM UKA_EVENTS",
				new EventRowMapper());
		return eventList;
	}

	public List<Event> getUkaProgram(String place){
		return jdbcTemplate.query("SELECT * FROM UKA_EVENTS WHERE place = ?", new EventRowMapper(), place);
	}
	
	public List<String> getUkaPlaces(){
		return jdbcTemplate.queryForList("SELECT place FROM UKA_EVENTS", String.class);
	}
	public List<String> getUkaPlaces(Date from, Date to) {
		return jdbcTemplate.queryForList("SELECT DISTINCT place FROM UKA_EVENTS WHERE showing_time >= ? AND showing_time <= ?", String.class, from, to);
	}
	
	public Event getUkaEventById(int id){
		return jdbcTemplate.queryForObject("SELECT * FROM UKA_EVENTS WHERE id=?", new EventRowMapper(),id);
	}
	public Event getUkaEventById(int id, Date from, Date to) {
		return jdbcTemplate.queryForObject("SELECT * FROM UKA_EVENTS WHERE id=? AND showing_time>=? AND showing_time<=?", new EventRowMapper(), id, from, to);
	}
	
	public Event getNextUkaEvent(String place) {
		return jdbcTemplate.queryForObject("SELECT * FROM UKA_EVENTS WHERE " +
				"AND place = ? AND showing_time > (now()) ORDER BY showing_time LIMIT 1", new EventRowMapper(), place);
	}

	public Event getNextUkaEvent(String place, Date from, Date to) {
		return jdbcTemplate.queryForObject("SELECT * FROM UKA_EVENTS WHERE " +
				"place = ? AND showing_time > (now()) AND showing_time >= ? AND showing_time <= ? ORDER BY showing_time LIMIT 1", new EventRowMapper(), place, from, to);
	}
}
