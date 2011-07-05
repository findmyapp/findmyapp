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
		noise.setLocation(rs.getString("location"));
		noise.setDecibel(rs.getFloat("decibel"));
		noise.setRawAverage(rs.getInt("raw_average"));
		noise.setRawMax(rs.getInt("raw_max"));
		noise.setRawMin(rs.getInt("raw_min"));
		return noise;
	}

}


