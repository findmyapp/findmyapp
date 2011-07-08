package no.uka.findmyapp.controller;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppStoreList;
import no.uka.findmyapp.service.AppStoreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class AppStoreController {

	//AppStoreService service;
	@Autowired
	private AppStoreService appStoreService;
	@Autowired
	private Gson gson;
	private static final String APP_OF_THE_DAY = "app_of_the_day";

	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreController.class);

	/**
	 * Returns a list of avaliable apps
	 * @throws URISyntaxException 
	 */
	@RequestMapping(value = "/appstore/{platform}", method = RequestMethod.GET)
	public String getAppStoreListForPlatform(
			@PathVariable int platform,
			@RequestParam(required=true) int listType,
			@RequestParam(required=false) String category,
			@RequestParam(required=true) int count, Model model) throws URISyntaxException {

		//TODO check values, throw exception

		logger.info("AppStoreList requsted: " + platform + ". ListType: " + listType + ". Count: " + count);
		AppStoreList appStoreList = appStoreService.getAppStoreListForPlatform(
				count, 
				listType, 
				platform, category);

		model.addAttribute("appstore", gson.toJson(appStoreList));
		return "appstore";
	}

	/**
	 * Returns detailed info about an app
	 * @throws URISyntaxException /get.json
	 */
	@RequestMapping(value = "/appstore/app/{appId}", method = RequestMethod.GET)
	public String  getDetailedAppInfoFromId(
			@PathVariable int appId, Model model) throws URISyntaxException {

		logger.info("AppStore App details requsted: " + appId);

		App app = appStoreService.getAppDetails(appId);
		model.addAttribute("appstore", gson.toJson(app));
		System.out.println(gson.toJson(app));
		return "appstore";
		//return new ModelAndView("appstore", "appstore", gson.toJson(app));
	}
	
	@RequestMapping(value = "/appstore/SetFeaturedApp/selectedAppIs", method = RequestMethod.GET)
	public ModelAndView  setNewFeaturedAppById(
			@RequestParam String marketID, Model model) throws URISyntaxException {
		
		System.out.println(marketID);
		App app = appStoreService.getAppFromMarketID(marketID);
		System.out.println(app);
		logger.info("The new featured app is:  ---------->    " + app.getName());
		appStoreService.setNewFeaturedApp(app);
		
		AppStoreList androidList = appStoreService.getAppStoreListForPlatform(
				10, 
				4, 
				2, APP_OF_THE_DAY);
		AppStoreList iosList = appStoreService.getAppStoreListForPlatform(
				10, 
				4, 
				1, APP_OF_THE_DAY);

		List<AppStoreList> fullList = new LinkedList<AppStoreList>();
		fullList.add(androidList);
		fullList.add(iosList);
		return new ModelAndView("appstoreweb", "appstoreweb", fullList);
	}

	/**
	 * Returns a list of avaliable apps
	 * @throws URISyntaxException 
	 */
	@RequestMapping(value = "/appstore/list", method = RequestMethod.GET)
	public ModelAndView getAppStoreListForPlatformOnWeb() throws URISyntaxException {

		AppStoreList androidList = appStoreService.getAppStoreListForPlatform(
				10, 
				1, 
				2);
		AppStoreList iosList = appStoreService.getAppStoreListForPlatform(
				10, 
				1, 
				1);

		List<AppStoreList> fullList = new LinkedList<AppStoreList>();
		fullList.add(androidList);
		fullList.add(iosList);

		//TODO check values, throw exception
		return new ModelAndView("appstoreweb", "appstoreweb", fullList);
	}



	@RequestMapping(value = "/appstore/latest/", method = RequestMethod.GET)
	public ModelAndView getAppStoreLatestList() throws URISyntaxException {

		AppStoreList androidList = appStoreService.getAppStoreListForPlatform(
				10, 
				3, 
				2);
		AppStoreList iosList = appStoreService.getAppStoreListForPlatform(
				10, 
				3, 
				1);
		List<AppStoreList> fullList = new LinkedList<AppStoreList>();
		fullList.add(androidList);
		fullList.add(iosList);

		//TODO check values, throw exception
		return new ModelAndView("appstoreweb", "appstoreweb", fullList);
	}

	@RequestMapping(value = "/appstore/mostPopular/", method = RequestMethod.GET)
	public ModelAndView getAppStoreMostPopularList() throws URISyntaxException {

		AppStoreList androidList = appStoreService.getAppStoreListForPlatform(
				10, 
				2, 
				2);
		AppStoreList iosList = appStoreService.getAppStoreListForPlatform(
				10, 
				2, 
				1);

		List<AppStoreList> fullList = new LinkedList<AppStoreList>();
		fullList.add(androidList);
		fullList.add(iosList);

		//TODO check values, throw exception
		return new ModelAndView("appstoreweb", "appstoreweb", fullList);
	}

	@RequestMapping(value = "/appstore/SetFeaturedApp/", method = RequestMethod.GET)
	public ModelAndView setFeaturedApp() throws URISyntaxException {

		AppStoreList androidList = appStoreService.getAppStoreListForPlatform(
				10, 
				2, 
				2, APP_OF_THE_DAY);
		AppStoreList iosList = appStoreService.getAppStoreListForPlatform(
				10, 
				2, 
				1, APP_OF_THE_DAY);

		List<AppStoreList> fullList = new LinkedList<AppStoreList>();
		fullList.add(androidList);
		fullList.add(iosList);

		//TODO check values, throw exception
		//return new ModelAndView("appstoreweb", "appstoreweb", fullList);
		return new ModelAndView("setFeaturedApp", "appstoreweb", fullList);
	}
	
	@RequestMapping(value = "/appstore/appOfTheDay/", method = RequestMethod.GET)
	public ModelAndView getFeaturedApp() throws URISyntaxException {

		AppStoreList androidList = appStoreService.getAppStoreListForPlatform(
				10, 
				4, 
				2, APP_OF_THE_DAY);
		AppStoreList iosList = appStoreService.getAppStoreListForPlatform(
				10, 
				4, 
				1, APP_OF_THE_DAY);

		List<AppStoreList> fullList = new LinkedList<AppStoreList>();
		fullList.add(androidList);
		fullList.add(iosList);

		//TODO check values, throw exception
		return new ModelAndView("appstoreweb", "appstoreweb", fullList);
	}

	@RequestMapping(value = "/appstore/form/", method = RequestMethod.POST)
	public ModelAndView registerApp(@RequestBody App newApp) {
		ModelAndView mav = new ModelAndView("registerApp");
		System.out.print(newApp);
		boolean regApp = appStoreService.registerApp(newApp);
		logger.info("APP IS REGISTERED:   ------>   "+   regApp);
		mav.addObject("appIsRegistered", regApp); // model name, model object
		return mav;
	}

	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}



	private App generateDemoApp() throws URISyntaxException {
		App ukaApp = new App();

		ukaApp.setName("demoApp");
		//ukaApp.setAndroidMarketUri(new URI("https://market.android.com/details?id=com.playcreek.DeathWorm&feature=featured-apps"));

		return ukaApp;
	}


}
