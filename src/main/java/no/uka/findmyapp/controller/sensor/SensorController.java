package no.uka.findmyapp.controller.sensor;



import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.Beertap;
import no.uka.findmyapp.model.Humidity;
import no.uka.findmyapp.model.Noise;
import no.uka.findmyapp.model.Temperature;
import no.uka.findmyapp.service.SensorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	List<Beertap> beertapList;

	private static final Logger logger = LoggerFactory.getLogger(SensorController.class);

	@RequestMapping(value="location/{locationId}/temperature",method = RequestMethod.GET)
	public ModelAndView getTemperatureData(
			@PathVariable int locationId,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date from,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date to){
		
	
		temperatureList = service.getTemperatureData(from, to, locationId);
		
		if(temperatureList.isEmpty()){
			return new ModelAndView("fail_respons");
		}
		else{
			return new ModelAndView("sensor","sensor",temperatureList);
		}
		
		
		
	}
	

	@RequestMapping(value="location/{locationId}/noise",method = RequestMethod.GET)
	public ModelAndView getNoiseData(
			@PathVariable int locationId,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date from,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date to){

		
		noiseList = service.getNoiseData(from, to, locationId);
		return new ModelAndView("sensor","sensor",noiseList);
	}
	
	
	@RequestMapping(value="location/{locationId}/humidity",method = RequestMethod.GET)
	public ModelAndView getHumidityData(
			@PathVariable int locationId,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date from,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date to){

		logger.info("Humidity data request received for location: " + locationId);
		logger.info("Trying to fetch humidity data");
		humidityList = service.getHumidityData(from, to, locationId);
		logger.info("Got humidity data");

		
		return new ModelAndView("sensor","sensor",humidityList);
	}

	@RequestMapping(value="location/{locationId}/beertap",method = RequestMethod.GET)
	public ModelAndView getBeertapData(
			@PathVariable int locationId,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date from,
			@RequestParam (required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date to,
			@RequestParam int tapnr){

		logger.info("Beertap data request received for location: " + locationId+"tapnr" +tapnr);
		logger.info("Trying to fetch beertap data");
		beertapList = data.getBeertapData(locationId,tapnr);
		logger.info("Got beertap data");

		 
		return new ModelAndView("sensor","sensor",humidityList);
	}




	/**
	 * Simply selects the sensor view to return a confirmation.
	 */
	
	
	@RequestMapping(value = "/location/{locationId}/temperature", method = RequestMethod.POST)
	public ModelAndView setTemperatureData(
			@PathVariable int locationId,
			@RequestBody float value) {


		logger.info("Temperature data logged for location: " + locationId  + ", Value: "+ value  );

		Temperature temperature = data.setTemperatureData(locationId, value);
		
		if (temperature == null){//Data not stored properly
			return new ModelAndView("fail_respons");
		}
		else{ //Success
			return new ModelAndView("ok_respons");
		}
	}
	
	
	@RequestMapping(value = "/location/{locationId}/noise", method = RequestMethod.POST)
	public ModelAndView setNoiseData(@RequestBody int[] samples,
			@PathVariable int locationId){

		//float decibel = service.toDecibel(raw_average); 
		//logger.info("Noise data logged for location: " + locationId + ", Decibel: "+ decibel );

		//Noise noise = data.setNoiseData(locationId,raw_average, raw_max, raw_min, decibel );


		
		return new ModelAndView("ok_respons");
	}
	
	
	@RequestMapping(value = "/location/{locationId}/humidity", method = RequestMethod.POST)
	public ModelAndView setHumidityData(
			@PathVariable int locationId,
			@RequestBody float value){

		logger.info("Humidity data logged for location: " + locationId + ", Value: "+ value  );

		Humidity humidity = data.setHumidityData(locationId, value);


	
		return new ModelAndView("ok_respons");


	}

	@RequestMapping(value = "/location/{locationId}/beertap", method = RequestMethod.POST)
	public ModelAndView setBeertapData(
			@PathVariable int  locationId,
			@RequestBody int tapnr, float value){
		logger.info("Beertap data logged for location: " + locationId + ", Value: "+ value +",tap nr: "+tapnr  );
		Beertap beertap = data.setBeertapData(locationId, value, tapnr);
		
		return new ModelAndView("ok_respons");


	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}

}	

