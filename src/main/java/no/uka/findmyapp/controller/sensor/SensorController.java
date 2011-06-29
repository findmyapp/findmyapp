/**package no.uka.findmyapp.controller.sensor;

import java.util.Date;

import no.uka.findmyapp.datasource.SensorRepository;
import no.uka.findmyapp.model.Arduino;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class SensorController {

	@Autowired
	private  SensorRepository data; //Remove later?? Kopierte dette fra UkaProgram, der stod det remove later.

	@Autowired
	private SensorService sensorservice;
	
	private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
	
	@RequestMapping(value="location/{locationName}/pull",method = RequestMethod.GET)
	public ModelAndView getSensorData(
			@PathVariable String locationName,
			@RequestParam("sensor") String sensor,//sensortype
			@RequestParam(required=false) boolean stats,// historisk oversikt over data
			@RequestParam(required=false) boolean soundprofile,//lydprofil, feks: stille, konsert, mingling
			@RequestParam(required=false) Boolean all)
	{
		logger.info("SKRIV HER");//Skjønte ikke helt hva som skulle skrives i loggeren
		Arduino arduino = new Arduino();
		
		//Sjekker hva slags sensor vi er intressert i, bare å legge til flere typer etterhvert(Vet ikke hva feilen er)
		if (sensor = "termometer"){
			
		}
		else if(sensor = "desibel" ){
			
		}
		else if(sensor = "humitity"){
			
		}
		else{
			logger.info("unhandled exception 624358123478623784. Should return 400");
			return null;
		}
		
		
		
		Gson g = new Gson(); //Bare kopiert det under, håper det gir mening!
		return new ModelAndView("sensor","arduino",g.toJson(arduino));
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	
	@ExceptionHandler
		private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}	
	

*/
