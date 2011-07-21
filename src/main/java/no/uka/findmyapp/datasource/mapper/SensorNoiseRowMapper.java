package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Noise;

import org.springframework.jdbc.core.RowMapper;

public class SensorNoiseRowMapper implements RowMapper<Noise> {


	public Noise mapRow(ResultSet rs, int arg1) throws SQLException {
		Noise noise =  new Noise();
		noise.setId(rs.getInt("sensor_noise_id"));
		noise.setDate(rs.getDate("date"));
		noise.setTime(rs.getTime("date"));
		noise.setLocation(rs.getInt("position_location_id"));
		return noise;
	}

}


