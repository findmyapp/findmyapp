package no.uka.findmyapp.datasource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.mapper.SensorBeertapRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorHumidityRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorNoiseRowMapper;
import no.uka.findmyapp.datasource.mapper.SensorTemperatureRowMapper;
import no.uka.findmyapp.model.BeerTap;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
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


	public List<Temperature> getLatestTemperatureData(int location, int limit) {

		List<Temperature> temp = jdbcTemplate.query(
				"SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id  = ? ORDER BY date DESC LIMIT 0,?",
				new SensorTemperatureRowMapper(), location, limit);
		logger.info("received temperature list");
		return temp;
	}
	
	public List<Noise> getLatestNoiseData(int location, int limit) {

		List<Noise> noise = jdbcTemplate.query(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ? ORDER BY date DESC LIMIT 0,?",
				new SensorNoiseRowMapper(), location, limit);
		logger.info("received noise list");
		return noise;
	}
	

	public List<Temperature> getTemperatureData(int location) {

		List<Temperature> temperatureList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id  = ? ORDER BY date DESC",
				new SensorTemperatureRowMapper(), location);
		logger.info("received temperature list");
		return temperatureList;
	}

	public List<Temperature> getTemperatureData(Date from, int location) {

		List<Temperature> temperatureList = jdbcTemplate
				.query("SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id = ? AND date>=? ORDER BY date DESC",
						new SensorTemperatureRowMapper(), location, from);
		logger.info("received temperature list");
		return temperatureList;
	}

	public List<Temperature> getTemperatureDataTo(Date to, int location) {

		List<Temperature> temperatureList = jdbcTemplate
				.query("SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id = ? AND date<=? ORDER BY date DESC",
						new SensorTemperatureRowMapper(), location, to);
		logger.info("received temperature list");
		return temperatureList;
	}

	public List<Temperature> getTemperatureData(Date from, Date to,
			int location) {
		List<Temperature> temperatureList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_TEMPERATURE WHERE date>=? AND date<=? AND position_location_id =? ORDER BY date DESC",
				new SensorTemperatureRowMapper(), from, to, location);
		return temperatureList;
	}


	public List<Noise> getNoiseData(int location) {

		List<Noise> noiseList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ? ORDER BY date DESC",
				new SensorNoiseRowMapper(), location);
		logger.info("received noise list");
		return noiseList;
	}

	public List<Noise> getNoiseData(Date from, int location) {

		List<Noise> noiseList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ? AND date >=? ORDER BY date DESC",
				new SensorNoiseRowMapper(), location, from);
		logger.info("received noise list");
		return noiseList;
	}

	public List<Noise> getNoiseDataTo(Date to, int location) {

		List<Noise> noiseList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  =? AND date <=? ORDER BY date DESC",
				new SensorNoiseRowMapper(), location, to);
		logger.info("received noise list");
		return noiseList;
	}

	public List<Noise> getNoiseData(Date from, Date to, int location) {

		List<Noise> noiseList = jdbcTemplate
				.query("SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ? AND date <=? AND date >=? ORDER BY date DESC",
						new SensorNoiseRowMapper(), location, from, to);
		logger.info("received noise list");
		return noiseList;
	}
	
	
	
	public List<Humidity> getHumidityData(int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? ORDER BY date DESC",
				new SensorHumidityRowMapper(), location);
		logger.info("received humidity list");
		return humidityList;
	}

	public List<Humidity> getHumidityData(Date from, int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? AND date >=? ORDER BY date DESC",
				new SensorHumidityRowMapper(), location, from);
		logger.info("received humidity list");
		return humidityList;
	}

	public List<Humidity> getHumidityDataTo(Date to, int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? AND date <=? ORDER BY date DESC",
				new SensorHumidityRowMapper(), location, to);
		logger.info("received humidity list");
		return humidityList;
	}

	public List<Humidity> getHumidityData(Date from, Date to, int location) {

		List<Humidity> humidityList = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? AND date >=? AND date <=? ORDER BY date DESC",
				new SensorHumidityRowMapper(), location, from, to);
		logger.info("received humidity list");
		return humidityList;
	}
	
	public List<Humidity> getLatestHumidityData(int location, int limit) {
		List<Humidity> humidity = jdbcTemplate.query(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? ORDER BY date DESC LIMIT 0,?",
				new SensorHumidityRowMapper(), location, limit);
		logger.info("received humidity list");
		return humidity;
	}


	public List<BeerTap> getBeertapData(int location) {
		List<BeerTap> beertapList = jdbcTemplate
				.query("SELECT * FROM SENSOR_BEERTAP WHERE position_location_id = ? ORDER BY date DESC",
						new SensorBeertapRowMapper(), location);
		logger.info("received beertap list");
		return beertapList;
	}
	
	public List<BeerTap> getBeertapData(int location, int tapnr) {
		List<BeerTap> beertapList = jdbcTemplate
				.query("SELECT * FROM SENSOR_BEERTAP WHERE position_location_id = ? AND tapnr = ? ORDER BY date DESC",
						new SensorBeertapRowMapper(), location, tapnr);
		logger.info("received beertap list");
		return beertapList;
	}
	
	public List<BeerTap> getBeertapData(int location, int tapnr, Date from, Date to) {
		List<BeerTap> beertapList = jdbcTemplate
				.query("SELECT * FROM SENSOR_BEERTAP WHERE position_location_id = ? AND tapnr = ? AND date >=? AND date <=? ORDER BY date DESC",
						new SensorBeertapRowMapper(), location, tapnr, from, to);
		logger.info("received beertap list");
		return beertapList;
	}
	
	public List<BeerTap> getLatestBeerTapData(int location, int tapnr,  int limit) {
		List<BeerTap> humidity = jdbcTemplate.query(
				"SELECT * FROM SENSOR_BEERTAP WHERE position_location_id  = ? AND tapnr = ? ORDER BY date DESC LIMIT 0,?",
				new SensorBeertapRowMapper(), location, tapnr, limit);
		logger.info("received beertap list");
		return humidity;
	}

	public int getBeertapSum(int location) {
		int sum = jdbcTemplate.queryForInt("SELECT SUM(value) FROM SENSOR_BEERTAP WHERE position_location_id = ?", location);
		logger.info("received beertap sum: " + sum);
		return sum;
	}
	
	public int getBeertapSum(int location, int tapnr) {
		int sum = jdbcTemplate.queryForInt("SELECT SUM(value) FROM SENSOR_BEERTAP WHERE position_location_id = ? AND tapnr = ?", location, tapnr);
		logger.info("received beertap sum: " + sum);
		return sum;
	}
	
	public int getBeertapSum(int location, int tapnr, Date from, Date to) {
		int sum = jdbcTemplate.queryForInt("SELECT SUM(value) FROM SENSOR_BEERTAP WHERE position_location_id = ? AND tapnr = ? AND date >=? AND date <=?", location, tapnr, from, to);
		logger.info("received beertap sum: " + sum);
		return sum;
	}

	/**
	 * Methods to save sensor data received from Arduino.
	 * 
	 * @return
	 */

	public boolean setTemperatureData(final int locationId, final float value) {
		//jdbcTemplate.execute("INSERT INTO SENSOR_TEMPERATURE (position_location_id , value, date) VALUES (?,?,now())");
		//logger.info("Temperature data logged for: " + locationId + ". Value: " + value);
		
		jdbcTemplate.update("INSERT INTO SENSOR_TEMPERATURE (position_location_id , value, date) VALUES (?,?,now())",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, locationId);
						ps.setFloat(2, value);
					}
				});
		return true;
	}

	public boolean setHumidityData(final int locationId, final float value) {
		//jdbcTemplate.execute("INSERT INTO SENSOR_HUMIDITY (position_location_id , value, date) VALUES ("+ humidity.getLocation() +","+ humidity.getValue() +",now())");		
		//logger.info("Humidity data logged for: " + locationId + ". Value: " + value);
		
		jdbcTemplate.update("INSERT INTO SENSOR_HUMIDITY (position_location_id , value, date) VALUES (?,?,now())",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, locationId);
						ps.setFloat(2, value);
					}
				});
		return true;
	}

	public boolean setNoiseData(final Noise noise) {

		//jdbcTemplate.execute("INSERT INTO SENSOR_NOISE (position_location_id , average, max, min, standard_deviation, samples, date) VALUES ("+ noise.getLocation() +","+ noise.getAverage()+","+noise.getMax()+","+noise.getMin()+","+noise.getStandardDeviation()+",'"+noise.getJsonSamples() +"',now())");
		//logger.info("Data logged: " + noise.toString());
		
		jdbcTemplate.update("INSERT INTO SENSOR_NOISE (position_location_id , average, max, min, standard_deviation, samples, date) VALUES (?,?,?,?,?,?,now())",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, noise.getLocation());
						ps.setDouble(2, noise.getAverage());
						ps.setInt(3, noise.getMax());
						ps.setInt(4, noise.getMin());
						ps.setDouble(5, noise.getStandardDeviation());
						ps.setString(6, noise.getJsonSamples());
					}
				});
		
		return true;
	}


	public boolean setBeertapData(final int locationId, final float value, final int tapnr) {

		//jdbcTemplate
		//		.execute("INSERT INTO SENSOR_BEERTAP (position_location_id , value, tapnr) VALUES ('"
		//				+ location + "', " + value + "," + tapnr + ")");
		
		//logger.info("Beertap data logged for: " + locationId + ". TapNr: " + tapnr + ". Value: " + value);
		
		jdbcTemplate.update("INSERT INTO SENSOR_BEERTAP (position_location_id , value, tapnr, date) VALUES (?,?,?,now())",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, locationId);
						ps.setFloat(2, value);
						ps.setInt(3, tapnr);
					}
				});
		return true;
	}


}
