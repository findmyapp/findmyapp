package no.uka.findmyapp.model.appstore;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;

public class App {
	private String name;
	private int id;
	private String marketID;
	private int platform;
	private String description;
	private String facebookAppID;
	private String developerID;
	private URI thumbImage;
	private List<URI> imageList;
	private DateTime publishDate;
	private int timesDownloaded;
	private double ranking;
	
	public String getMarketID() {
		return marketID;
	}
	public void setMarketID(String marketID) {
		this.marketID = marketID;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFacebookAppID() {
		return facebookAppID;
	}
	public void setFacebookAppID(String facebookAppID) {
		this.facebookAppID = facebookAppID;
	}
	public String getDeveloperID() {
		return developerID;
	}
	public void setDeveloperID(String developerID) {
		this.developerID = developerID;
	}
	
	public DateTime getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(DateTime publishDate) {
		this.publishDate = publishDate;
	}
	public int getTimesDownloaded() {
		return timesDownloaded;
	}
	public void setTimesDownloaded(int timesDownloaded) {
		this.timesDownloaded = timesDownloaded;
	}
	public double getRanking() {
		return ranking;
	}
	public void setRanking(double ranking) {
		this.ranking = ranking;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAndroidMarketUri() {
		return marketID;
	}
	public void setAndroidMarketUri(String androidMarketUri) {
		this.marketID = androidMarketUri;
	}
	public URI getThumbImage() {
		return thumbImage;
	}
	public void setThumbImage(URI thumbImage) {
		this.thumbImage = thumbImage;
	}
	public List<URI> getImageList() {
		return imageList;
	}
	public void setImageList(List<URI> imageList) {
		this.imageList = imageList;
	}
	@Override
	public String toString() {
		return "App [name=" + name + ", androidMarketUri=" + marketID
				+ ", thumbImage=" + thumbImage + ", imageList=" + imageList
				+ "]";
	}
	
}
