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

	public UkaProgram getUkaProgram(Date day) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Object args[] = new Object[1];
		args[0] = day;
		List<Event> eventList = jdbcTemplate.query(
				"SELECT * FROM event_showing_real AS s, events_event AS e WHERE s.event_id=e.id AND showing_time=?", args,
				new EventRowMapper());
		
		UkaProgram ukaProgram = new UkaProgram(eventList);
		return ukaProgram;
	}

}