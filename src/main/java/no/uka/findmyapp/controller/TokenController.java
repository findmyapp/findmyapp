package no.uka.findmyapp.controller;

import no.uka.findmyapp.service.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/auth")
@Controller
public class TokenController {

	private static final Logger logger = LoggerFactory
			.getLogger(TokenController.class);

	@Autowired
	AuthenticationService service;

	@RequestMapping("/login")
	public ModelAndView login(
			@RequestParam(required = true) String facebookToken)
			throws TokenException {
		ModelAndView mav = new ModelAndView("login");
		String token = service.login(facebookToken);
		if (token == null)
			throw new TokenException("Unable to obtain token for user.");
		mav.addObject("token", token);
		return mav;
	}

	@SuppressWarnings("unused")
	@ExceptionHandler(TokenException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	private void handleTokenException(Exception e) {
		logger.debug("Error message: \"" + e.getMessage() + 
				"\" Error status: " + HttpStatus.UNAUTHORIZED);
	}
}
