package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.exception.LocationNotFoundException;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppDetailed;
import no.uka.findmyapp.model.appstore.Developer;
import no.uka.findmyapp.service.DeveloperService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/developer")
public class DeveloperController {

	@Autowired
	private DeveloperService service;
	

	private static final Logger logger = LoggerFactory
			.getLogger(DeveloperController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getDeveloperForWpId(@RequestParam(required = true) int wpId) {
		Developer developer = service.getDeveloperForWpId(wpId);
		return new ModelAndView("json", "developer", developer);
	}

	@RequestMapping(value = "/register", method = RequestMethod.PUT)
	public ModelAndView registerDeveloper(@RequestBody Developer developer) {
		//TODO FIX EXCEPTION IF INSERT FAILED
		logger.debug("Registering developer: " + developer.toString());
		int result = service.registerDeveloper(developer);
		return new ModelAndView("json", "result", result);
	}

	@RequestMapping(value = "/{developer_id}/update", method = RequestMethod.POST)
	public ModelAndView updateDeveloper(@PathVariable int developer_id, @RequestBody Developer developer) {
		//TODO IMPLEMENT
		int result = -1;
		return new ModelAndView("json", "result", result);
	}
	

	@RequestMapping(value = "/{developer_id}/apps", method = RequestMethod.GET)
	public ModelAndView getAppsFromDeveloperId(@PathVariable int developer_id) {
		List<AppDetailed> list =  service.getAppsFromDeveloperId(developer_id);
		return new ModelAndView("json", "list", list);
	}

	@RequestMapping(value = "/{developer_id}/apps/{appId}", method = RequestMethod.GET)
	public ModelAndView getDetailedApp(@PathVariable int developerId, @PathVariable int appId) {
		AppDetailed app =  service.getDetailedApp(developerId, appId);
		return new ModelAndView("json", "app", app);
	}
	

	@RequestMapping(value = "/{developer_id}/apps/add", method = RequestMethod.PUT)
	public ModelAndView registerApp(@PathVariable int developer_id, @RequestBody App app) {
		//TODO FIX EXCEPTION IF INSERT FAILED
		int result = service.registerApp(developer_id, app);
		return new ModelAndView("json", "result", result);
	}

	@RequestMapping(value = "/{developer_id}/apps/{appId}/update", method = RequestMethod.POST)
	public ModelAndView updateApp(@PathVariable int developer_id, @PathVariable int appId, @RequestBody App app) {
		
		logger.info("updating app: " + developer_id + " app: " + app.toString());
		app.setId(appId);
		int result = service.updateApp(developer_id, app);
		return new ModelAndView("json", "result", result);
	}

	@RequestMapping(value = "/{developer_id}/apps/{appId}/activation/update", method = RequestMethod.POST)
	public ModelAndView updateAppActivation(@PathVariable int developer_id, @PathVariable int appId, @RequestParam(required=true) boolean activated) {
		
		int result = service.updateAppActivation(developer_id, appId, activated);
		return new ModelAndView("json", "result", result);
	}
	
	@RequestMapping(value = "/demo", method = RequestMethod.POST)
	public ModelAndView registerApp(@RequestBody Developer developer) {
		
		logger.info(developer.toString());
		
		return new ModelAndView("json", "result", "res");
	}

	@RequestMapping(value = "/demo2", method = RequestMethod.PUT)
	public ModelAndView registerApp1(@RequestBody String str) {
		
		logger.info(str);
		
		return new ModelAndView("json", "registerApp", "res");
	}
	
	@RequestMapping(value = "/demo3", method = RequestMethod.PUT)
	public ModelAndView registerApp2(@RequestBody String str) {
		Developer dev = new GsonBuilder().create().fromJson(str, Developer.class);
		logger.info(str);

		logger.info(dev.toString());
		
		return new ModelAndView("json", "registerApp", "res");
	}

	@RequestMapping(value = "/demo4", method = RequestMethod.PUT)
	public ModelAndView registerApp3(@RequestBody Developer dev) {
		
		logger.info(dev.toString());
		
		return new ModelAndView("json", "registerApp", dev);
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