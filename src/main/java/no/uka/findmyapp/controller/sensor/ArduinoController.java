package no.uka.findmyapp.controller.sensor;


import no.uka.findmyapp.model.Arduino;
import no.uka.findmyapp.model.UkaProgram;

import org.codehaus.jackson.JsonFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class ArduinoController {


	private static final Logger logger = LoggerFactory
			.getLogger(ArduinoController.class);
	
	/**
	 * Simply selects the sensor view to return a confirmation.
	 */
	@RequestMapping(value = "/location/{locationName}", method = RequestMethod.GET)
	public ModelAndView getUkaProgramForDate(
			@PathVariable String locationName,
			@RequestParam("sensor") String sensor,
			@RequestParam("value") int value) {
		logger.info("Data logged for location: " + locationName + ". Sensortype: " + sensor + ", Value: "+ value  );
		
		Arduino arduino = new Arduino();
		arduino.setLocation(locationName);
		arduino.setSensor(sensor);
		arduino.setValue(value);
		
		Gson g = new Gson();
		return new ModelAndView("sensor", "arduino", g.toJson(arduino));
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}
