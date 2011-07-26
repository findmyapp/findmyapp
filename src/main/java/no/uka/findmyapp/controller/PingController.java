package no.uka.findmyapp.controller;

import no.uka.findmyapp.service.auth.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PingController {

	@Autowired
	AuthenticationService service;

	private static final Logger logger = LoggerFactory
			.getLogger(PingController.class);

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public void ping(@RequestParam String token) {
		int userId = service.verify(token);
		if (userId != -1) {
			logger.debug("Token verified.");
		} else {
			throw new InvalidTokenException("Token is not valid");
		}
	}

	@SuppressWarnings("unused")
	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	private void handleInvalidTokenException(InvalidTokenException e) {
		logger.debug(e.getMessage());
	}

}