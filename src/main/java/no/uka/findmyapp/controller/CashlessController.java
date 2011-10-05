package no.uka.findmyapp.controller;

import java.util.Date;
import java.util.List;

import no.uka.findmyapp.exception.InvalidUserIdOrAccessTokenException;
import no.uka.findmyapp.exception.PrivacyException;
import no.uka.findmyapp.exception.UkaYearNotFoundException;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
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
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService auth;

	private static final Logger logger = LoggerFactory
	.getLogger(CashlessController.class);

	/**
	 * Gets the information from the user's Cashless card
	 * 
	 * @return Cashless card with transaction info
	 */
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/cashless/{ukaYear}/{idOrMe}", method = RequestMethod.GET)
	@ServiceModelMapping(returnType=CashlessCard.class)
	public ModelAndView getMyCashless (
			@PathVariable String ukaYear,
			@PathVariable String idOrMe,
			@RequestParam(required = true) String token,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date date,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date from,
			@RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE) Date to,
			@RequestParam(required=false) String location,
			@RequestParam(required=false) String orderBy) throws ConsumerException, UkaYearNotFoundException, PrivacyException { 
		
		//int userId = auth.verify("b7049efc1ccf2eaf829c5cef9ddd186a8c5f8f50i25t1311839388431");
		int tokenUserId = auth.verify( token );
		logger.debug("UserId: " + tokenUserId);
		
		CashlessCard card;
		if (tokenUserId != -1 && idOrMe.equalsIgnoreCase("me")) {
			card = cashlessService.getCardTransactions(ukaYear, tokenUserId, date, from, to, location);
		} else if (tokenUserId != -1) {
			card = cashlessService.getCardTransactions(ukaYear, tokenUserId, Integer.parseInt(idOrMe), date, from, to, location);
		} else {
			throw new InvalidTokenException("Token not valid");
		}
		
		
		if (!idOrMe.equalsIgnoreCase("me")) {//don't show card number to others
			card.setCardNo(-1);
		} else {
			card.setBalance(cashlessService.getCardBalance(card.getCardNo()));
		}
		return new ModelAndView("json", "card", card);
	}
	/**
	 * Update the users Cashless card number
	 * 
	 * @return
	 */
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/cashless/me/update", method = RequestMethod.POST)//should maybe be changed to post @ /cashless/me or users/me/cashless
	@ServiceModelMapping(returnType=CashlessCard.class)
	public ModelAndView updateMyCashless(
			@RequestParam(required = true) long cardNo,
			@RequestParam(required = true) String token) throws ConsumerException{ 
		
		//int userId = auth.verify("b7049efc1ccf2eaf829c5cef9ddd186a8c5f8f50i25t1311839388431");
		int userId = auth.verify( token );
		
		boolean success = false;
		
		if (userId != -1) {
			success = cashlessService.updateCardNumber(userId, cardNo);
		} else {
			throw new InvalidTokenException("Token not valid");
		}
		
		return new ModelAndView("json", "status", success);
	}
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/cashless/friends", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/cashless/{ukaYear}/locations", method = RequestMethod.GET)
	public ModelAndView getCashlessLocations(@PathVariable String ukaYear) {
		return new ModelAndView("json", "status", cashlessService.getCashlessLocations());
	}
	
	
	
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidUserIdOrAccessTokenException.class)
	private void handleInvalidUserIdOrAccessTokenException(
			InvalidUserIdOrAccessTokenException e) {
		logger.debug(e.getMessage());
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ConsumerException.class)
	private void handleConsumerException(ConsumerException e) {
		logger.debug(e.getMessage());
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	private void handleNumberFormatException(NumberFormatException e) {
		logger.debug(e.getMessage());
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(PrivacyException.class)
	private void handlePrivacyException(PrivacyException e) {
		logger.debug(e.getMessage());
	}
	

}