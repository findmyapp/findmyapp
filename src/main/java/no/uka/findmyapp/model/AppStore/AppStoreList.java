package no.uka.findmyapp.model.AppStore;

import java.util.List;

public class AppStoreList {
	private ListType listType;
	private int requestCount;
	private int listCount;
	private List<UkaApp> appList;
	private Platform platform;
	
	public AppStoreList() {
		
	}
	
	public AppStoreList(List<UkaApp> appList) {
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
	public List<UkaApp> getAppList() {
		return appList;
	}
	public void setAppList(List<UkaApp> appList) {
		this.appList = appList;
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
}
