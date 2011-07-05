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

	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreService.class);
	
	public AppStoreList getAppStoreListForPlatform(int count, int listType, int platform) {
		List<App> appList = data.getAppList(count, listType, platform);
		
		AppStoreList appStoreList = new AppStoreList();
		appStoreList.setListType(listType);
		appStoreList.setPlatform(platform);
		appStoreList.setListCount(appList.size());
		appStoreList.setRequestCount(count);
		appStoreList.setAppList(appList);
		
		return appStoreList;
	}
	
	public App getAppDetails(int appId) {
		return data.getAppDetails(appId);
	}
}
