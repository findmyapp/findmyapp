package no.uka.findmyapp.controller;

import java.util.Date;

import no.uka.findmyapp.datasource.UkaProgramDataHandler;
import no.uka.findmyapp.model.UkaProgram;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UkaProgramController {

	@Autowired
	private UkaProgramDataHandler data;

	private static final Logger logger = LoggerFactory
			.getLogger(UkaProgramController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/program/{day}", method = RequestMethod.GET)
	public ModelAndView getUkaProgramForDate(
			@PathVariable @DateTimeFormat(iso = ISO.DATE) Date day) {
		logger.info("getUkaProgramForDate ( " + day + " )");
		
		ModelAndView mav = new ModelAndView("home");
		UkaProgram program = data.getUkaProgram(day);			
		mav.addObject("program", program);

		return mav;
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
}
