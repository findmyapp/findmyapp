package no.uka.findmyapp.controller;

import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.UkaEvent;
import no.uka.findmyapp.model.cashless.CashlessCard;
import no.uka.findmyapp.service.CashlessService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		
		long cardNo = 1628620850;
		CashlessCard card = cashlessService.getCardTransactions(cardNo);
		
		
		logger.debug("testing cashless");	
		return new ModelAndView("json", "program", card);
	
	}
}