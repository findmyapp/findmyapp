package no.uka.findmyapp.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.uka.findmyapp.model.Humidity;
import org.springframework.jdbc.core.RowMapper;

public class SensorHumidityRowMapper implements RowMapper<Humidity> {


	public Humidity mapRow(ResultSet rs, int arg1) throws SQLException {
		Humidity humidity =  new Humidity();
		humidity.setId(rs.getInt("sensor_humidity_id"));
		humidity.setDate(rs.getDate("date"));
		humidity.setLocation(rs.getInt("position_location_id"));
		humidity.setValue(rs.getFloat("value"));
		return humidity;
	}
}