package no.uka.findmyapp.datasource;

import java.util.List;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.SensorHumidityRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorNoiseRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorTemperatureRowMapper;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Enn saa lenge en dummyklasse for aa kunne bygge controlleren
 * 
 * @author John Modin
 * 
 */
@Repository
public class SensorRepository {
	

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory
	.getLogger(SensorRepository.class);
	
	/**
	 * Methods to receive sensor data stored in database.
	 * 
	 * @return
	 */
	
	public List<Temperature> getTemperatureData(String location){
		
		
		
		List<Temperature> temperatureList = jdbcTemplate.query("SELECT * FROM SENSOR_TEMPERATURE WHERE location = ?", new SensorTemperatureRowMapper(), location);
		logger.info("received temperature list");
		
		
		return temperatureList;
	}
	
	public List<Noise> getNoiseData(String location){
		
		
		  
		
		List<Noise> noiseList = jdbcTemplate.query("SELECT * FROM SENSOR_NOISE WHERE location = ?", new SensorNoiseRowMapper(), location);
		logger.info("received noise list");
		
		
		return noiseList;
	}
	public List<Humidity> getHumidityData(String location){
		
	
		  
		
		List<Humidity> humidityList = jdbcTemplate.query("SELECT * FROM SENSOR_HUMIDITY WHERE location = ?", new SensorHumidityRowMapper(), location);
		logger.info("received humidity list");
		
		
		return humidityList;
	}
	
	/**
	 * Methods to save sensor data received from Arduino.
	 * 
	 * @return
	 */
	public Temperature setTemperatureData(String location, float value){ 
		
	
		  
		jdbcTemplate.execute("INSERT INTO SENSOR_TEMPERATURE (location, value) VALUES ('" + location + "', "+value+")");
		
		Temperature temperature = new Temperature();
		temperature.setLocation(location);
		temperature.setValue(value);
		
		logger.info("Data logged: " + temperature.toString());
		
		return temperature;
	}
	
	public Noise setNoiseData(String location, int raw_average, int raw_max, int raw_min, float decibel){ 
		

		  
		jdbcTemplate.execute("INSERT INTO SENSOR_NOISE (location, raw_average, raw_max, raw_min, decibel) VALUES ('" + location + "', "+raw_average+","+raw_max+","+raw_min+","+decibel+")");
		
		Noise noise = new Noise();
		noise.setLocation(location);
		noise.setDecibel(decibel);
		noise.setRawAverage(raw_average);
		noise.setRawMax(raw_max);
		noise.setRawMin(raw_min);
		
		logger.info("Data logged: " + noise.toString());
		
		return noise;
	}

	public Humidity setHumidityData(String location, float value){ 
	

	  
		jdbcTemplate.execute("INSERT INTO SENSOR_HUMIDITY (location, value) VALUES ('" + location + "', "+value+")");
	
		Humidity humidity= new Humidity();
		humidity.setLocation(location);
		humidity.setValue(value);
		
		logger.info("Data logged: " + humidity.toString());
	
		return humidity;
	}
}
