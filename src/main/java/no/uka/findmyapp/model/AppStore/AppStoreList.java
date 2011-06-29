package no.uka.findmyapp.model.AppStore;

import java.util.List;

public class AppStoreList {
	private ListType listType;
	private int requestCount;
	private int listCount;
	private List<App> appList;
	private Platform platform;
	
	public AppStoreList() {
		
	}
	
	public AppStoreList(List<App> appList) {
		this.setAppList(appList);
	}
	
	public ListType getListType() {
		return listType;
	}
	public void setListType(ListType listType) {
		this.listType = listType;
	}
	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public List<App> getAppList() {
		return appList;
	}
	public void setAppList(List<App> appList) {
		this.appList = appList;
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
}
