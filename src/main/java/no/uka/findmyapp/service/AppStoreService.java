package no.uka.findmyapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.uka.findmyapp.datasource.AppStoreRepository;
import no.uka.findmyapp.model.appstore.App;
import no.uka.findmyapp.model.appstore.AppStoreList;
import no.uka.findmyapp.model.appstore.ListType;
import no.uka.findmyapp.model.appstore.Platform;

@Service
public class AppStoreService {
	@Autowired
	private AppStoreRepository data;

	private static final Logger logger = LoggerFactory
	.getLogger(AppStoreService.class);
	
	public AppStoreList getAppStoreListForPlatform(int count, ListType listType, Platform platform) {
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
