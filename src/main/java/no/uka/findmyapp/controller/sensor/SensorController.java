package no.uka.findmyapp.controller.sensor;


import java.util.List;

import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.Temperature;


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
	
	//@Autowired
	//private SensorService sensorservice;
	
	List <Temperature> temperatureList;
	
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
			logger.info("Trying to fetch temperature data fra db");
			temperatureList = data.getTemperatureData(locationName);
			logger.info("Got temperature data fra db");
		}
		else if(sensor.equals("noise")){
			return null;
		}
		else if(sensor.equals("humidity")){
			return null;
		}
		else{
			logger.info("unhandled exception 624358123478623784. Should return 400");
			return null;
		}
		
		
		
		Gson g = new Gson(); 
		return new ModelAndView("sensor","temperature",g.toJson(temperatureList));
	}
	
	
	/**
	 * Simply selects the sensor view to return a confirmation.
	 */
	@RequestMapping(value = "/location/{locationName}/push", method = RequestMethod.GET)
	public ModelAndView setSensorData(
			@PathVariable String locationName,
			@RequestParam("sensor") String sensor,
			@RequestParam("value") float value) {
		
		logger.info("Data logged for location: " + locationName + ". Sensortype: " + sensor + ", Value: "+ value  );
		
		Temperature temperature = data.setTemperatureData(locationName, value);

		
		Gson g = new Gson();
		return new ModelAndView("sensor", "temperature", g.toJson(temperature));
		
		
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
		private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}	
	
