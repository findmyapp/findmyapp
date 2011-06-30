package no.uka.findmyapp.controller;

import java.net.URISyntaxException;

import no.uka.findmyapp.model.AppStore.App;
import no.uka.findmyapp.model.AppStore.AppStoreList;
import no.uka.findmyapp.model.AppStore.ListType;
import no.uka.findmyapp.model.AppStore.Platform;
import no.uka.findmyapp.service.AppStoreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class AppStoreController {

	@Autowired
	private AppStoreService appStoreService;
	private Gson gson;
	
	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreController.class);


	/**
	* Returns a list of avaliable apps
	 * @throws URISyntaxException 
	*/
	@RequestMapping(value = "/appstore/{platform}", method = RequestMethod.GET)
	public ModelAndView getAppStoreListForPlatform(
		@PathVariable String platform,
		@RequestParam(required=true) String listType,
		@RequestParam(required=true) int count) throws URISyntaxException {
	 
		//TODO check values, throw exception
		
		logger.info("AppStoreList requsted: " + platform + ". ListType: " + listType + ". Count: " + count);
		AppStoreList appStoreList = appStoreService.getAppStoreListForPlatform(
				count, 
				ListType.valueOf(listType.toUpperCase()), 
				Platform.valueOf(platform.toUpperCase()));
		
		return new ModelAndView("appstore", "appstore", gson.toJson(appStoreList));
	}
	
	/**
	* Returns detailed info about an app
	 * @throws URISyntaxException 
	*/
	@RequestMapping(value = "/appstore/app/{appId}", method = RequestMethod.GET)
	public ModelAndView getDetailedAppInfoFromId(
			@PathVariable int appId) throws URISyntaxException {
	
		logger.info("AppStore App details requsted: " + appId);
		
		App app = appStoreService.getAppDetails(appId);
		
		return new ModelAndView("appstore", "appstore", gson.toJson(app));
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
