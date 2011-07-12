package no.uka.findmyapp.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PingController {
	
	@Secured("ROLE_PING")
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public void ping() {
	}
	
}