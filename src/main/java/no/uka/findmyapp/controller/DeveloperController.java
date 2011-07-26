package no.uka.findmyapp.controller;

import java.util.List;

import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.Developer;
import no.uka.findmyapp.service.DeveloperService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
		List<App> list =  service.getAppsFromDeveloperId(developer_id);
		return new ModelAndView("json", "list", list);
	}

	@RequestMapping(value = "/{developer_id}/apps/add", method = RequestMethod.PUT)
	public ModelAndView registerApp(@PathVariable int developer_id, @RequestBody App app) {
		//TODO FIX EXCEPTION IF INSERT FAILED
		int result = service.registerApp(developer_id, app);
		return new ModelAndView("json", "result", result);
	}
	
	@RequestMapping(value = "/{developer_id}/apps/{appId}/update", method = RequestMethod.POST)
	public ModelAndView updateApp(@PathVariable int developer_id, @PathVariable int appId, @RequestBody App app) {
		//TODO IMPLEMENT
		int result = -1;
		return new ModelAndView("json", "result", result);
	}
	
	
	@RequestMapping(value = "/demo", method = RequestMethod.POST)
	public ModelAndView registerApp(@RequestBody Developer developer) {
		
		logger.info(developer.toString());
		
		return new ModelAndView("json", "result", "res");
	}

	@RequestMapping(value = "/demo2", method = RequestMethod.POST)
	public ModelAndView registerApp(@RequestBody String str) {
		
		logger.info(str);
		
		return new ModelAndView("json", "registerApp", "res");
	}
}
