package no.uka.findmyapp.controller.auth;

import no.uka.findmyapp.service.auth.AuthenticationService;
import no.uka.findmyapp.service.auth.CouldNotRetreiveUserIdException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.social.InternalServerErrorException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {

	@Autowired
	AuthenticationService service;
	
	@Secured("ROLE_CONSUMER")
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(required = true) String facebookToken) throws InternalServerErrorException {
		ModelAndView mav = new ModelAndView("json");
		String token;
		try {
			token = service.login(facebookToken);
		} catch (CouldNotRetreiveUserIdException e) {
			throw new InternalServerErrorException(e.getMessage());
		}
		return mav.addObject("token", token);
	}

	@ExceptionHandler(InternalServerErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private void handleInternalServerErrorException() {
	}
	
}
