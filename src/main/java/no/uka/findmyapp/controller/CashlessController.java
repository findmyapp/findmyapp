package no.uka.findmyapp.controller;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.configuration.UkaProgramConfiguration;
import no.uka.findmyapp.exception.UkaYearNotFoundException;
import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.UkaProgram;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.service.CashlessService;
import no.uka.findmyapp.service.UkaProgramService;
import no.uka.findmyapp.service.UserService;

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

@Controller
public class CashlessController {

	@Autowired
	private CashlessService cashlessService;

	private static final Logger logger = LoggerFactory
	.getLogger(CashlessController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */


	@RequestMapping(value = "/cashless", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=UkaEvent.class)
	public ModelAndView testCashless() { 

		logger.debug("testing cashless");

		cashlessService.testCashless();	
		
		return new ModelAndView("json", "program", "TEST");
	
	}
}