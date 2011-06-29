package no.uka.findmyapp.controller;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.service.UkaProgramService;

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
public class UkaProgramController {

	@Autowired
	private UkaProgramService ukaProgramService;


	private static final Logger logger = LoggerFactory
	.getLogger(UkaProgramController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/program/{ukaYear}/events", method = RequestMethod.GET)
	// We do not use ukaYear
	public ModelAndView getUkaProgramForDate(
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date date,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date from,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date to,
			@RequestParam(required=false) Boolean all){
		UkaProgram program = new UkaProgram();
		
		if (date!=null) {			
			// Use date
			logger.info("getUkaProgramForDate ( " + date + " )");
			program = ukaProgramService.getUkaProgram(date);		

		}else if(from != null && to != null) {
			// Use from to
			logger.info("getUkaProgramForFrom ( " + from + " ) and to ( " + to + " )");
			program = ukaProgramService.getUkaProgram(from, to);	

		}else if(all != null && all) {
			logger.info("getUkaProgram");
			
			program = ukaProgramService.getUkaProgram();	
			
		}else{
			logger.info("unhandled exception 624358123478623784. Should return 400");
			return null;
		}

		Gson g = new Gson();
		return new ModelAndView("home", "program", g.toJson(program));
	}

	@RequestMapping(value = "/program/{ukaYear}/places", method = RequestMethod.GET)
	// We do not use ukaYear
	public ModelAndView getUkaProgramPlaces(){
		List<String> places;
		logger.info("getUkaProgramPlaces");
		places = ukaProgramService.getUkaPlaces();

		Gson g = new Gson();
		return new ModelAndView("places", "places", g.toJson(places));
	}



	@RequestMapping(value = "/program/{date}", method = RequestMethod.PUT)
	public void insertUkaProgramForDate(
			@PathVariable @DateTimeFormat(iso = ISO.DATE) Date date) {
		logger.info("insertUkaProgramForDate ( " + date + " )");

		//data.insertUkaProgram(date);
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}

}
