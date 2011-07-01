package no.uka.findmyapp.model.appstore;

import java.net.URI;
import java.util.List;

public class App {
	private int id;
	private String name;
	private String androidMarketUri;
	private URI thumbImage;
	private List<URI> imageList;
	
	
	
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
		return androidMarketUri;
	}
	public void setAndroidMarketUri(String androidMarketUri) {
		this.androidMarketUri = androidMarketUri;
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
		return "App [name=" + name + ", androidMarketUri=" + androidMarketUri
				+ ", thumbImage=" + thumbImage + ", imageList=" + imageList
				+ "]";
	}
	
}
