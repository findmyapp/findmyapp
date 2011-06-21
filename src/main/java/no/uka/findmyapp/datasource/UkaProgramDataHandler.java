package no.uka.findmyapp.datasource;

import java.util.Date;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.UkaProgramRowMapper;
import no.uka.findmyapp.model.UkaProgram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UkaProgramDataHandler {

	@Autowired
	private DataSource ds;

	public UkaProgram getUkaProgram(Date day) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		UkaProgram program = jdbcTemplate.queryForObject(
				"SELECT id, day FROM program WHERE day=?",
				new UkaProgramRowMapper(), day);
		return program;
	}

}
