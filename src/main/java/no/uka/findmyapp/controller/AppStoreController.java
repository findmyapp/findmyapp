package no.uka.findmyapp.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import no.uka.findmyapp.controller.sensor.ArduinoController;
import no.uka.findmyapp.datasource.AppStoreRepository;
import no.uka.findmyapp.datasource.UkaProgramRepository;
import no.uka.findmyapp.model.Arduino;
import no.uka.findmyapp.model.AppStore.AppStoreList;
import no.uka.findmyapp.model.AppStore.ListType;
import no.uka.findmyapp.model.AppStore.Platform;
import no.uka.findmyapp.model.AppStore.UkaApp;

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
	private AppStoreRepository data;
	
	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreController.class);


	/**
	* Simply returns a list bla bla
	 * @throws URISyntaxException 
	*/
	@RequestMapping(value = "/appstore/{platform}", method = RequestMethod.GET)
	public ModelAndView getAppStoreForPlatform(
		@PathVariable String platform,
		@RequestParam(required=true) String listType,
		@RequestParam(required=true) int count) throws URISyntaxException {
	
		logger.info("AppStoreList requsted: " + platform + ". ListType: " + listType + ". Count: " + count);
		
		List<UkaApp> appList = new LinkedList<UkaApp>();
		
		appList.add(generateDemoApp());
		
		//TODO check values, throw exception
		AppStoreList appStoreList = data.getAppStoreList(count, ListType.valueOf(listType.toUpperCase()), Platform.valueOf(platform.toUpperCase()));
		appStoreList.setListCount(count);
		appStoreList.setListType(ListType.valueOf(listType.toUpperCase()));
		appStoreList.setPlatform(Platform.valueOf(platform.toUpperCase()));
		appStoreList.setRequestCount(count);
		
		Gson g = new Gson();
		return new ModelAndView("appstore", "appstore", g.toJson(appStoreList));
	}
	
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler
	private void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		logger.info("handleEmptyResultDataAccessException ( " + ex.getLocalizedMessage() + " )");
	}
	
	private UkaApp generateDemoApp() throws URISyntaxException {
		UkaApp ukaApp = new UkaApp();
		ukaApp.setName("demoApp");
		ukaApp.setAndroidMarketUri(new URI("https://market.android.com/details?id=com.playcreek.DeathWorm&feature=featured-apps"));
		
		return ukaApp;
	}


}
