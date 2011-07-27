package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.helpers.ServiceModelMapping;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.cashless.CashlessCard;
import no.uka.findmyapp.service.CashlessService;
import no.uka.findmyapp.service.UserService;
import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.service.auth.ConsumerException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CashlessController {

	@Autowired
	private CashlessService cashlessService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService auth;

	private static final Logger logger = LoggerFactory
	.getLogger(CashlessController.class);

	/**
	 * Gets the information from the users Cashless card
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cashless/me", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=CashlessCard.class)
	public ModelAndView getMyCashless() { 
		
		long cardNo = 1628620850; // This must come from user object
		
		CashlessCard card = cashlessService.getCardTransactions(cardNo);
		
		return new ModelAndView("json", "program", card);
	}
	
	/**
	 * Update the users Cashless card number
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cashless/me/update", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=CashlessCard.class)
	public ModelAndView updateMyCashless() { 
		
		int userId = 1;
		long cardNo = 1628620850; // This must come from user object
		
		if(cashlessService.updateCardNumber(userId, cardNo)) {
			
		}
		
		return new ModelAndView("json", "program", "hei");
	}
	
	@RequestMapping(value = "/cashless/friends", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=CashlessCard.class)
	public ModelAndView getFriendsWithCashless(
				@RequestParam(required = true) String token) 
				throws ConsumerException{
		
			ModelAndView mav = new ModelAndView("json");
			int userId = auth.verify(token);
			List<User> friends;
			if (userId != -1) {
				friends = userService.getFriendsWithCashless( userId );
			} else {
				throw new InvalidTokenException("Token not valid");
			}
			mav.addObject("users", friends);
			return mav;
	}
}