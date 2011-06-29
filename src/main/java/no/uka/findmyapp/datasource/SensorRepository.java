package no.uka.findmyapp.datasource;

import java.util.List;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.SensorTemperatureRowMapper;
import no.uka.findmyapp.model.Temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Enn så lenge en dummyklasse for å kunne bygge controlleren
 * 
 * @author John Modin
 * 
 */
@Repository
public class SensorRepository {
	
	@Autowired
	private DataSource ds;

	private static final Logger logger = LoggerFactory
	.getLogger(SensorRepository.class);
	
	/**
	 * Method to receive sensor data stored in database.
	 * 
	 * @return
	 */
	public List<Temperature> getTemperatureData(String location){
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		  
		
		List<Temperature> temperatureList = jdbcTemplate.query("SELECT * FROM SENSOR_TEMPERATURE WHERE location = ?", new SensorTemperatureRowMapper(), location);
		logger.info("received temperature list");
		
		
		return temperatureList;
	}
	
	/**
	 * Method to save sensor data received from Arduino.
	 * 
	 * @return
	 */
	public Temperature setTemperatureData(String location, float value){ 
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		  
		jdbcTemplate.execute("INSERT INTO SENSOR_TEMPERATURE (location, value) VALUES ('" + location + "', "+value+")");
		
		Temperature temperature = new Temperature();
		temperature.setLocation(location);
		temperature.setValue(value);
		
		logger.info("Data logged: " + temperature.toString());
		
		return temperature;
	}
}
