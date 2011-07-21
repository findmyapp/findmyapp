package no.uka.findmyapp.datasource;

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


	public Temperature getLatestTemperatureData(int location) {

		Temperature temp = jdbcTemplate.queryForObject(
				"SELECT * FROM SENSOR_TEMPERATURE WHERE position_location_id  = ? ORDER BY date DESC LIMIT 0,1",
				new SensorTemperatureRowMapper(), location);
		logger.info("received temperature list");
		return temp;
	}
	
	public Noise getLatestNoiseData(int location) {

		Noise noise = jdbcTemplate.queryForObject(
				"SELECT * FROM SENSOR_NOISE WHERE position_location_id  = ? ORDER BY date DESC LIMIT 0,1",
				new SensorNoiseRowMapper(), location);
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
	
	public Humidity getLatestHumidityData(int location) {
		Humidity humi = jdbcTemplate.queryForObject(
				"SELECT * FROM SENSOR_HUMIDITY WHERE position_location_id  = ? ORDER BY date DESC LIMIT 0,1",
				new SensorHumidityRowMapper(), location);
		logger.info("received humidity list");
		return humi;
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

	public void setTemperatureData(Temperature temperature) {
		jdbcTemplate.execute("INSERT INTO SENSOR_TEMPERATURE (position_location_id , value, date) VALUES ("+ temperature.getLocation() +","+ temperature.getValue() +",now())");
		logger.info("Data logged: " + temperature.toString());
		return;
	}

	public void setHumidityData(Humidity humidity) {
		jdbcTemplate.execute("INSERT INTO SENSOR_HUMIDITY (position_location_id , value, date) VALUES ("+ humidity.getLocation() +","+ humidity.getValue() +",now())");		
		logger.info("Data logged: " + humidity.toString());
		return;
	}

	public void setNoiseData(Noise noise) {

		jdbcTemplate.execute("INSERT INTO SENSOR_NOISE (position_location_id , average, max, min, standard_deviation, samples, date) VALUES ("+ noise.getLocation() +","+ noise.getAverage()+","+noise.getMax()+","+noise.getMin()+","+noise.getStandardDeviation()+",'"+noise.getJsonSamples() +"',now())");
		logger.info("Data logged: " + noise.toString());
		return;
	}


	public BeerTap setBeertapData(int location, float value, int tapnr) {

		jdbcTemplate
				.execute("INSERT INTO SENSOR_BEERTAP (position_location_id , value, tapnr) VALUES ('"
						+ location + "', " + value + "," + tapnr + ")");
		
		BeerTap beerTap = new BeerTap();
		beerTap.setLocation(location);
		beerTap.setValue(value);
		beerTap.setTapnr(tapnr);
		logger.info("Data logged: " + beerTap.toString());
		return beerTap;
	}


}
