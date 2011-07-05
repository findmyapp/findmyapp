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

	public AppStoreList getAppStoreListForPlatform(int count, int listType, int platform, String category) {
		List<App> appList = data.getAppList(count, listType, platform, category);
		
		AppStoreList appStoreList = new AppStoreList();
		appStoreList.setListType(listType);
		appStoreList.setPlatform(platform);
		appStoreList.setListCount(appList.size());
		appStoreList.setRequestCount(count);
		appStoreList.setAppList(appList);
		
		return appStoreList;
	}
	public AppStoreList getAppStoreListForPlatform(int count, int listType, int platform){
		return getAppStoreListForPlatform(count, listType, platform, default_category);
	}
	
	public App getAppDetails(int appId) {
		return data.getAppDetails(appId);
	}
}
