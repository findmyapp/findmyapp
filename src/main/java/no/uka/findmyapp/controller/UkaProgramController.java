package no.uka.findmyapp.controller;

import java.io.IOException;
import java.util.Date;

import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.model.UkaProgram;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonSerializer;
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
	private UkaProgramRepository data;

	private static final Logger logger = LoggerFactory
			.getLogger(UkaProgramController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/program/{aar}/dato", method = RequestMethod.GET)
	// We do not use aar
	public ModelAndView getUkaProgramForDate(
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date dato,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date fra,
	        @RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date til){
		UkaProgram program;
		if (dato==null) {
			// Use fra til
			logger.info("getUkaProgramForFra ( " + fra + " ) og Til ( " + til + " )");
			program = data.getUkaProgram(fra, til);	
		}
		else {
			// Use dato
			logger.info("getUkaProgramForDate ( " + dato + " )");
			program = data.getUkaProgram(dato);			
		}
		
		Gson g = new Gson();
		return new ModelAndView("home", "program", g.toJson(program));
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
