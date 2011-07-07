package no.uka.findmyapp.datasource;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.SensorBeertapRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorHumidityRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorNoiseRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorTemperatureRowMapper;
import no.uka.findmyapp.model.Beertap;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

	public List<Temperature> getTemperatureData(int location) {

		List<Temperature> temperatureList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id  = ?",
				new SensorTemperatureRowMapper(), location);
		logger.info("received temperature list");
		return temperatureList;
	}

	public List<Temperature> getTemperatureData(Date from, int location) {

		List<Temperature> temperatureList = jdbcTemplate
				.query("SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id = ? AND date>=?",
						new SensorTemperatureRowMapper(), location, from);
		logger.info("received temperature list");
		return temperatureList;
	}

	public List<Temperature> getTemperatureDataTo(Date to, int location) {

		List<Temperature> temperatureList = jdbcTemplate
				.query("SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id = ? AND date<=?",
						new SensorTemperatureRowMapper(), location, to);
		logger.info("received temperature list");
		return temperatureList;
	}

	public List<Temperature> getTemperatureData(Date from, Date to,
			int location) {
		List<Temperature> temperatureList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_TEMPERATURE WHERE date>=? AND date<=? AND position_location_id =?",
				new SensorTemperatureRowMapper(), from, to, location);
		return temperatureList;
	}

	public List<Noise> getNoiseData(int location) {

		List<Noise> noiseList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ?",
				new SensorNoiseRowMapper(), location);
		logger.info("received noise list");
		return noiseList;
	}

	public List<Noise> getNoiseData(Date from, int location) {

		List<Noise> noiseList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ? AND date <=?",
				new SensorNoiseRowMapper(), location, from);
		logger.info("received noise list");
		return noiseList;
	}

	public List<Noise> getNoiseDataTo(Date to, int location) {

		List<Noise> noiseList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  =? AND date >=?",
				new SensorNoiseRowMapper(), location, to);
		logger.info("received noise list");
		return noiseList;
	}

	public List<Noise> getNoiseData(Date from, Date to, int location) {

		List<Noise> noiseList = jdbcTemplate
				.query("SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ? AND date <=? AND date >=?",
						new SensorNoiseRowMapper(), location, from, to);
		logger.info("received noise list");
		return noiseList;
	}

	public List<Humidity> getHumidityData(int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ?",
				new SensorHumidityRowMapper(), location);
		logger.info("received humidity list");
		return humidityList;
	}

	public List<Humidity> getHumidityData(Date from, int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? AND date >=?",
				new SensorHumidityRowMapper(), location, from);
		logger.info("received humidity list");
		return humidityList;
	}

	public List<Humidity> getHumidityDataTo(Date to, int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? AND date <=?",
				new SensorHumidityRowMapper(), location, to);
		logger.info("received humidity list");
		return humidityList;
	}

	public List<Humidity> getHumidityData(Date from, Date to, int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? AND date >=? AND date <=?",
				new SensorHumidityRowMapper(), location, from, to);
		logger.info("received humidity list");
		return humidityList;
	}

	public List<Beertap> getBeertapData(int location, int tapnr) {
		logger.info("called get beertap");
		List<Beertap> beertapList = jdbcTemplate
				.query("SELECT * FROM SENSOR_BEERTAP WHERE position_location_id = ? AND tapnr = ?",
						new SensorBeertapRowMapper(), location, tapnr);
		logger.info("received beertap list");
		return beertapList;
	}
	
	public List<Beertap> getBeertapData(int location, int tapnr, Date from, Date to) {

		List<Beertap> beertapList = jdbcTemplate
				.query("SELECT * FROM SENSOR_BEERTAP WHERE position_location_id = ? AND tapnr = ? AND date >=? AND date <=?",
						new SensorBeertapRowMapper(), location, tapnr, from, to);
		logger.info("received beertap list");
		return beertapList;
	}

	/**
	 * Methods to save sensor data received from Arduino.
	 * 
	 * @return
	 */

	public Temperature setTemperatureData(int location, float value) {
		
		try{
		jdbcTemplate
				.execute("INSERT INTO SENSOR_TEMPERATURE (position_location_id , value) VALUES ('"
						+ location + "', " + value + ")");
		}
		catch (DataAccessException d){
			return null;
		}
		Temperature temperature = new Temperature();
		temperature.setLocation(location);
		temperature.setValue(value);
		logger.info("Data logged: " + temperature.toString());
		return temperature;
	}

	public Noise setNoiseData(int location, int[] sample) {

		jdbcTemplate
				.execute("INSERT INTO SENSOR_NOISE (position_location_id , noise_sample) VALUES ('"
						+ location +","+sample+ ")");
		
		Noise noise = new Noise();
		noise.setLocation(location);
		noise.setSample(sample);
		logger.info("Data logged: " + noise.toString());
		return noise;
	}

	public Humidity setHumidityData(int location, float value) {

		jdbcTemplate
				.execute("INSERT INTO SENSOR_HUMIDITY (position_location_id , value) VALUES ('"
						+ location + "', " + value + ")");
		
		Humidity humidity = new Humidity();
		humidity.setLocation(location);
		humidity.setValue(value);
		logger.info("Data logged: " + humidity.toString());
		return humidity;
	}

	public Beertap setBeertapData(int location, float value, int tapnr) {

		jdbcTemplate
				.execute("INSERT INTO SENSOR_BEERTAP (position_location_id , value, tapnr) VALUES ('"
						+ location + "', " + value + "," + tapnr + ")");
		
		Beertap beertap = new Beertap();
		beertap.setLocation(location);
		beertap.setValue(value);
		beertap.setTapnr(tapnr);
		logger.info("Data logged: " + beertap.toString());
		return beertap;
	}

}
