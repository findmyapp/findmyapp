package com.accenture.findmyapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.findmyapp.entity.TestObject;
import com.accenture.findmyapp.entity.CalcObject;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CalcController {

	private static final Logger logger = LoggerFactory.getLogger(CalcController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * Use this syntax: .../calc/{add,subtract,divide,multiply}?n1={n1}&n2={n2}
	 */
	@RequestMapping(value="/calc/{operator}", method=RequestMethod.GET)
	public String doSomethingWithRest(@PathVariable("operator") String operator, @RequestParam("n1") int n1,@RequestParam("n2") int n2,  ModelMap model) {
		logger.info("input: " + operator + ", " + n1 + ", " + n2);
		CalcObject cal = new CalcObject();
		cal.setOperator(operator);
		cal.setNumber1(n1);
		cal.setNumber2(n2);
		model.addAttribute("CalcObject", cal);
		return "calc"; 
	} 
	
}

