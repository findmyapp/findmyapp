package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.Temperature;
import no.uka.findmyapp.model.appstore.AppDetailed;
import no.uka.findmyapp.model.appstore.Developer;
import no.uka.findmyapp.service.DeveloperService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/developer")
public class DeveloperController {

	@Autowired
	private DeveloperService service;
	

	private static final Logger logger = LoggerFactory
			.getLogger(DeveloperController.class);

	@Secured("ROLE_WORDPRESS")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getDeveloperForWpId(@RequestParam(required = true) int wpId) {
		Developer developer = service.getDeveloperForWpId(wpId);
		return new ModelAndView("json", "developer", developer);
	}

	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/register", method = RequestMethod.PUT)
	public ModelAndView registerDeveloper(@RequestBody Developer developer) {
		//TODO FIX EXCEPTION IF INSERT FAILED
		logger.debug("Registering developer: " + developer.toString());
		int result = service.registerDeveloper(developer);
		return new ModelAndView("json", "result", result);
	}

	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/{developerId}/update", method = RequestMethod.POST)
	public ModelAndView updateDeveloper(@PathVariable int developerId, @RequestBody Developer developer) {
		//TODO IMPLEMENT
		int result = -1;
		return new ModelAndView("json", "result", result);
	}

	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/{developerId}/apps", method = RequestMethod.GET)
	public ModelAndView getAppsFromDeveloperId(@PathVariable int developerId) {
		List<AppDetailed> list =  service.getAppsFromDeveloperId(developerId);
		return new ModelAndView("json", "list", list);
	}

	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/{developerId}/apps/{appId}", method = RequestMethod.GET)
	public ModelAndView getDetailedApp(@PathVariable int developerId, @PathVariable int appId) {
		AppDetailed app =  service.getDetailedApp(developerId, appId);
		return new ModelAndView("json", "app", app);
	}	
	
	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/{developerId}/apps/add", method = RequestMethod.PUT)
	public ModelAndView registerApp(@PathVariable int developerId, @RequestBody AppDetailed app) {
		
		//TODO FIX EXCEPTION IF INSERT FAILED
		int result = service.registerApp(developerId, app);
		return new ModelAndView("json", "result", result);
	}
	
	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/{developerId}/apps/{appId}/update", method = RequestMethod.POST)
	public ModelAndView updateApp(@PathVariable int developerId, @PathVariable int appId, @RequestBody AppDetailed app) {
		
		logger.info("updating app: " + developerId + " app: " + app.toString());
		app.setId(appId);
		int result = service.updateApp(developerId, app);
		return new ModelAndView("json", "result", result);
	}
	
	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/{developerId}/apps/{appId}/activation/update", method = RequestMethod.POST)
	public ModelAndView updateAppActivation(@PathVariable int developerId, @PathVariable int appId, @RequestParam(required=true) boolean activated) {

		logger.info("updating app activation: " + developerId + " app: " + appId + " act: " + activated);
		int result = service.updateAppActivation(developerId, appId, activated);
		return new ModelAndView("json", "result", result);
	}

	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/demo4", method = RequestMethod.PUT)
	public ModelAndView demo4(@RequestBody Temperature temp) {
		
		logger.info(temp.toString());
		
		return new ModelAndView("json", "registerApp", temp);
	}

	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/demo3", method = RequestMethod.POST)
	public ModelAndView demo3(@RequestBody Temperature temp) {
		
		logger.info(temp.toString());
		
		return new ModelAndView("json", "registerApp", temp);
	}
	
	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/demo2", method = RequestMethod.GET)
	public ModelAndView demo2() {
		
		logger.info("JSON TEST");
		
		return new ModelAndView("json", "registerApp", "JSON TEST");
	}
	
	@Secured("ROLE_WORDPRESS")
	@RequestMapping(value = "/demo1", method = RequestMethod.GET)
	public ModelAndView demo1() {
		boolean b = true;
		logger.info("JSON TEST");
		
		return new ModelAndView("json", "registerApp", b);
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	private void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	private void handleIncorrectResultSizeDataAccessException(
			IncorrectResultSizeDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( "
				+ ex.getLocalizedMessage() + " )");
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(LocationNotFoundException.class)
	private void handleLocationNotFoundException(LocationNotFoundException ex) {
		logger.info("handleLocationNotFoundException ( "
				+ ex.getLocalizedMessage() + " )");
	}
}
