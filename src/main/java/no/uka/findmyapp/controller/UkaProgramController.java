package no.uka.findmyapp.controller;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.model.Event;
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
	@Autowired
	private Gson gson;

	private static final Logger logger = LoggerFactory
	.getLogger(UkaProgramController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/program/{ukaYear}/events", method = RequestMethod.GET)
	// We do not use ukaYear
	public ModelAndView getUkaProgramForDate(
			@PathVariable String ukaYear,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date date,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date from,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date to,
			@RequestParam(required=false) Boolean all,
			@RequestParam(required=false) String place){ 
		UkaProgram program = new UkaProgram();
		

			logger.info("getUkaProgram - new");
			program = ukaProgramService.getUkaProgram(ukaYear, date, from, to, all, place);	
			
			return new ModelAndView("home", "program", gson.toJson(program));
	
	}
	
	@RequestMapping(value = "/program/{ukaYear}/events/search", method = RequestMethod.GET)
	// We do not use ukaYear
	public ModelAndView searchForUkaProgramByName(
			@PathVariable String ukaYear,
			@RequestParam(required=true) String eventName){
		UkaProgram program = new UkaProgram();
		
			logger.info("searchForUkaProgramByName");
			program = ukaProgramService.titleSearch(ukaYear, eventName);	
	
			return new ModelAndView("home", "program", gson.toJson(program));
	}

	@RequestMapping(value = "/program/{ukaYear}/places", method = RequestMethod.GET)
	// We do not use ukaYear
	public ModelAndView getUkaProgramPlaces(
			@PathVariable String ukaYear){
		List<String> places;
		logger.info("getUkaProgramPlaces");
		places = ukaProgramService.getUkaPlaces(ukaYear);

		return new ModelAndView("places", "places", places);
	}
	
	@RequestMapping(value = "/program/{ukaYear}/{place}/next", method = RequestMethod.GET)
	// We do not use ukaYear
	public ModelAndView getNextUkaEvent(
			@PathVariable String ukaYear, @PathVariable String place){
		logger.info("getNextUkaEvent");
		Event event = ukaProgramService.getNextUkaEvent(ukaYear, place);

		return new ModelAndView("event", "event", event);
	}
	
	@RequestMapping(value = "/program/{ukaYear}/beginningAndEndDates", method = RequestMethod.GET)
	// We do use ukaYear
	public ModelAndView getUkaProgramStartEndDate(
			@PathVariable String ukaYear){

		List<Date> dates;
		logger.info("getUkaProgramStartEndDate using a config file");
		dates = ukaProgramService.getUkaProgramStartEndDate(ukaYear);

		return new ModelAndView("beginningAndEndDates", "beginningAndEndDates", gson.toJson(dates));
	}


	@RequestMapping(value = "/program/{date}", method = RequestMethod.PUT)
	public void insertUkaProgramForDate(
			@PathVariable @DateTimeFormat(iso = ISO.DATE) Date date) {
		logger.info("insertUkaProgramForDate ( " + date + " )");

		//data.insertUkaProgram(date);
	}

	@RequestMapping(value = "/program/{ukaYear}/event/{id}", method = RequestMethod.GET)
	// We do not use ukaYear
	public ModelAndView getUkaEventById(
			@PathVariable int id, @PathVariable String ukaYear){
		Event event;
		logger.info("getUkaEventById");
		event = ukaProgramService.getUkaEventById(ukaYear, id);

		return new ModelAndView("event", "event", gson.toJson(event));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public void handleIllegalArgumentException(
			IllegalArgumentException ex) {
		logger.info("handleIllegalArgumentException ( "
				+ ex.getLocalizedMessage() + " )");
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

