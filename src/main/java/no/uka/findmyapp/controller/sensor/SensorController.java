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
	@Autowired
	private Gson gson;

	//@Autowired
	//private SensorService sensorservice;

	List <Temperature> temperatureList;
	List<Noise> noiseList;
	List<Humidity> humidityList;

	private static final Logger logger = LoggerFactory.getLogger(SensorController.class);

	@RequestMapping(value="location/{locationName}/temperature/pull", method = RequestMethod.GET)
	public ModelAndView getTemperatureData(
			@PathVariable String locationName){

		logger.info("Temperature data request received for location: " + locationName);


		logger.info("Trying to fetch temperature data");
		temperatureList = data.getTemperatureData(locationName);
		logger.info("Got temperature data");

		Gson g = new Gson(); 
		return new ModelAndView("sensor","sensor",g.toJson(temperatureList));
	}
	

	@RequestMapping(value="location/{locationName}/noise/pull",method = RequestMethod.GET)
	public ModelAndView getNoiseData(
			@PathVariable String locationName){

		logger.info("Noise data request received for location: " + locationName);
		logger.info("Trying to fetch noise data");
		noiseList = data.getNoiseData(locationName);
		logger.info("Got noise data");

		Gson g = new Gson(); 
		return new ModelAndView("sensor","sensor",g.toJson(noiseList));
	}
	
	
	@RequestMapping(value="location/{locationName}/humidity/pull",method = RequestMethod.GET)
	public ModelAndView getHumidityData(
			@PathVariable String locationName){

		logger.info("Humidity data request received for location: " + locationName);
		logger.info("Trying to fetch humidity data");
		humidityList = data.getHumidityData(locationName);
		logger.info("Got humidity data");

		Gson g = new Gson(); 
		return new ModelAndView("sensor","sensor",g.toJson(humidityList));
	}

	//logger.info("unhandled exception 624358123478623784. Should return 400");
	//return null;




	/**
	 * Simply selects the sensor view to return a confirmation.
	 */
	
	
	@RequestMapping(value = "/location/{locationName}/temperature/push", method = RequestMethod.GET)
	public ModelAndView setTemperatureData(
			@PathVariable String locationName,
			@RequestParam float value) {


		logger.info("Temperature data logged for location: " + locationName  + ", Value: "+ value  );

		Temperature temperature = data.setTemperatureData(locationName, value);


		Gson g = new Gson();
		return new ModelAndView("sensor", "sensor", g.toJson(temperature));

	}
	
	
	@RequestMapping(value = "/location/{locationName}/noise", method = RequestMethod.GET)
	public ModelAndView setNoiseData(
			@PathVariable String locationName,
			@RequestParam int raw_average,
			@RequestParam int raw_max,
			@RequestParam int raw_min){

		float decibel = service.toDecibel(raw_average); 
		logger.info("Noise data logged for location: " + locationName + ", Decibel: "+ decibel );

		Noise noise = data.setNoiseData(locationName,raw_average, raw_max, raw_min, decibel );


		Gson g = new Gson();
		return new ModelAndView("sensor", "sensor", g.toJson(noise));
	}
	
	
	@RequestMapping(value = "/location/{locationName}/humidity/push", method = RequestMethod.GET)
	public ModelAndView setHumidityData(
			@PathVariable String locationName,
			@RequestParam float value){

		logger.info("Humidity data logged for location: " + locationName + ", Value: "+ value  );

		Humidity humidity = data.setHumidityData(locationName, value);


		Gson g = new Gson();
		return new ModelAndView("sensor", "sensor", g.toJson(humidity));


		//logger.info("unhandled exception 624358123478623784. Should return 400");
		//return null;
	}


	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}	

