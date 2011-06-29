package no.uka.findmyapp.model.AppStore;

import java.net.URI;
import java.util.List;

public class UkaApp {
	private String name;
	private URI androidMarketUri;
	private URI thumbImage;
	private List<URI> imageList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URI getAndroidMarketUri() {
		return androidMarketUri;
	}
	public void setAndroidMarketUri(URI androidMarketUri) {
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
	
}
