package no.uka.findmyapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EchoController {
	
	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public void echo(ModelMap model) {
		model.addAttribute("echo", "echo");
	}
	
}