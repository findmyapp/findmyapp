package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.Temperature;

import org.springframework.jdbc.core.RowMapper;

public class SensorTemperatureRowMapper implements RowMapper<Temperature> {


	public Temperature mapRow(ResultSet rs, int arg1) throws SQLException {
		Temperature temperature =  new Temperature();
		temperature.setId(rs.getInt("sensor_temperature_id"));
		temperature.setTime(rs.getTimestamp("date"));
		temperature.setLocation(rs.getString("location"));
		temperature.setValue(rs.getFloat("value"));
		
		return temperature;
	}

}