package no.uka.findmyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

@Controller
public class EchoController {
	
	@Autowired
	private Gson gson;
	
	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public void echo(ModelMap model) {
		model.addAttribute("echo", gson.toJson("echo"));
	}
	
}