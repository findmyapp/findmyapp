package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.uka.findmyapp.model.Noise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;

public class SensorNoiseRowMapper implements RowMapper<Noise> {
	private Gson gson;
	
	public SensorNoiseRowMapper(Gson gson) {
		this.gson = gson;
	}
	
	public Noise mapRow(ResultSet rs, int arg1) throws SQLException {
		Noise noise =  new Noise();
		noise.setId(rs.getInt("sensor_noise_id"));
		noise.setDate(rs.getDate("date"));
		noise.setTime(rs.getTime("date"));
		noise.setLocation(rs.getInt("position_location_id"));
		noise.setAverage(rs.getDouble("average"));
		noise.setMax(rs.getInt("max"));
		noise.setMin(rs.getInt("min"));
		noise.setStandardDeviation(rs.getFloat("standard_deviation"));
		
		noise.setSamples(gson.fromJson(rs.getString("samples"), int[].class));
		return noise;
	}

}


