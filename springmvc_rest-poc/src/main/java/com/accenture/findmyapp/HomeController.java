package com.accenture.findmyapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.findmyapp.entity.TestObject;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/name/{name}", method=RequestMethod.GET)
	public String doSomethingWithRest(@PathVariable("name") String name, ModelMap model) {
		logger.info("input: " + name);
		TestObject to = new TestObject();
		to.setTest1(1);
		to.setTest2(name);
		model.addAttribute("testObject", to);
		return "home"; 
	} 
	
}

