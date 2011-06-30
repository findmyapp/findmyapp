package no.uka.findmyapp.controller.sensor;


import java.util.List;

import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Temperature;
import no.uka.findmyapp.service.SensorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class SensorController {

	@Autowired
	private  SensorRepository data; 
	@Autowired
	private SensorService service;
	
	//@Autowired
	//private SensorService sensorservice;
	
	List <Temperature> temperatureList;
	List<Noise> noiseList;
	List<Humidity> humidityList;
	
	private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
	
	@RequestMapping(value="location/{locationName}/pull",method = RequestMethod.GET)
	public ModelAndView getSensorData(
			@PathVariable String locationName,
			@RequestParam(required=true) String sensor) 
			//@RequestParam(required=false) boolean stats,// historisk oversikt over data
			//@RequestParam(required=false) boolean soundprofile,//lydprofil, feks: stille, konsert, mingling
			//@RequestParam(required=false) Boolean all)
	{
		logger.info("Data request received for location: " + locationName+". Type: " + sensor);
		
		if (sensor.equals("temperature")){
			logger.info("Trying to fetch temperature data");
			temperatureList = data.getTemperatureData(locationName);
			logger.info("Got temperature data");
			
			Gson g = new Gson(); 
			return new ModelAndView("sensor","sensor",g.toJson(temperatureList));
		}
		else if(sensor.equals("noise")){
			logger.info("Trying to fetch noise data");
			noiseList = data.getNoiseData(locationName);
			logger.info("Got noise data");
			
			Gson g = new Gson(); 
			return new ModelAndView("sensor","sensor",g.toJson(noiseList));
		}
		else if(sensor.equals("humidity")){
			logger.info("Trying to fetch humidity data");
			humidityList = data.getHumidityData(locationName);
			logger.info("Got humidity data");
			
			Gson g = new Gson(); 
			return new ModelAndView("sensor","sensor",g.toJson(humidityList));
		}
		else{
			logger.info("unhandled exception 624358123478623784. Should return 400");
			return null;
		}
}
	
	
	/**
	 * Simply selects the sensor view to return a confirmation.
	 */
	@RequestMapping(value = "/location/{locationName}/temperature/push", method = RequestMethod.GET)
	public ModelAndView setTemperatureData(
			@PathVariable String locationName,
			@RequestParam(required=false) Float value) {
		
		
		
			logger.info("Data logged for location: " + locationName + ". Sensortype: " + sensor + ", Value: "+ value  );
			
			Temperature temperature = data.setTemperatureData(locationName, value);

			
			Gson g = new Gson();
			return new ModelAndView("sensor", "sensor", g.toJson(temperature));
			
	}
		@RequestMapping(value = "/location/{locationName}/noise/push", method = RequestMethod.GET)
		public ModelAndView setNoiseData(
				@PathVariable String locationName,
				@RequestParam(required=false) Integer raw_average,
				@RequestParam(required=false) Integer raw_max,
				@RequestParam(required=false) Integer raw_min){
		
			float decibel = service.toDecibel(raw_average); 
			logger.info("Data logged for location: " + locationName + ". Sensortype: " + sensor + ", Decibel: "+ decibel );
			
			Noise noise = data.setNoiseData(locationName,raw_average, raw_max, raw_min, decibel );

			
			Gson g = new Gson();
			return new ModelAndView("sensor", "sensor", g.toJson(noise));
		}
		
		@RequestMapping(value = "/location/{locationName}/humidity/push", method = RequestMethod.GET)
		public ModelAndView setHumidityData(
				@PathVariable String locationName,
				@RequestParam(required=false) Float value){
		
			logger.info("Data logged for location: " + locationName + ". Sensortype: " + sensor + ", Value: "+ value  );
			
			Humidity humidity = data.setHumidityData(locationName, value);

			
			Gson g = new Gson();
			return new ModelAndView("sensor", "sensor", g.toJson(humidity));
		
		
			logger.info("unhandled exception 624358123478623784. Should return 400");
			return null;
		
		
		}
	
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}	
	
