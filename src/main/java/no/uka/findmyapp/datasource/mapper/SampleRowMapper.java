package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Sample;

import org.springframework.jdbc.core.RowMapper;

public class SampleRowMapper implements RowMapper<Sample> {

	public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
		Sample sample = new Sample();
		sample.setId(rs.getInt("position_sample_id"));
		sample.setRoomId(rs.getInt("position_room_id"));
		return sample;
	}

}
