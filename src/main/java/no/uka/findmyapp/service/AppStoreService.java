package no.uka.findmyapp.service;

import java.util.List;

import no.uka.findmyapp.datasource.AppStoreRepository;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppStoreList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppStoreService {
	@Autowired
	private AppStoreRepository data;
	private String default_category = "none";

	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreService.class);

	public AppStoreList getAppStoreListForPlatform(int from, int to, int listType, String platform, String category) {
		List<App> appList = data.getAppList(from, to, listType, platform, category);
		
		AppStoreList appStoreList = new AppStoreList();
		appStoreList.setListType(listType);
		appStoreList.setPlatform(platform);
		appStoreList.setListCount(appList.size());
		appStoreList.setRequestCount(((to-from)>0?(to-from):0)); //if to-from is greater than 0, the count is returned. If the count is below 0, 0 is returned.
		appStoreList.setAppList(appList);
		
		return appStoreList;
	}
	public AppStoreList getAppStoreListForPlatform(int from, int to, int listType, String platform){
		return getAppStoreListForPlatform(from, to, listType, platform, default_category);
	}
	
	public App getAppDetails(int appId) {
		return data.getAppDetails(appId);
	}
	
	public App getAppFromMarketID(String marketID){
		return data.getAppFromMarketID(marketID);
	}
	public boolean registerApp(App app){
		return data.registerApp(app);
	}
	
	public boolean setNewFeaturedApp(App app){
		return data.setNewFeaturedApp(app);
	}
	
}
