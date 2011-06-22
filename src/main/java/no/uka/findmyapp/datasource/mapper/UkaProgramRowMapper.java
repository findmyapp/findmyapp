package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.UkaProgram;

import org.springframework.jdbc.core.RowMapper;

public class UkaProgramRowMapper implements RowMapper<UkaProgram> {

	public UkaProgram mapRow(ResultSet rs, int arg1) throws SQLException {
		UkaProgram program = new UkaProgram();
		program.setDay(rs.getDate("day"));
		return program;
	}

}
