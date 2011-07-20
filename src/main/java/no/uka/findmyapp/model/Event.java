package no.uka.findmyapp.model;

import java.sql.Timestamp;
import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Event {
	private int id;
	private Timestamp showingTime;
	private String place;
	private int billigId;
	private boolean free;
	private boolean canceled;
	private String title;
	private String lead;
	private String text;
	private String eventType;
	private String image;
	private String thumbnail;
	private int ageLimit;
	//private Timestamp publishTime;
	//private long eventId;
	//private Timestamp netsaleFrom;
	//private Timestamp netsaleTo;
	//private int entranceId;
	//private Boolean hiddenFromListing;
	//private String slug;
	//private int detailPhotoId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getShowingTime() {
		return showingTime;
	}
	public void setShowingTime(Timestamp showingTime) {
		this.showingTime = showingTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getBilligId() {
		return billigId;
	}
	public void setBilligId(int billigId) {
		this.billigId = billigId;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLead() {
		return lead;
	}
	public void setLead(String lead) {
		this.lead = lead;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getAgeLimit() {
		return ageLimit;
	}
	public void setAgeLimit(int ageLimit) {
		this.ageLimit = ageLimit;
	}
	/*
	public Timestamp getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
	public long getEventId() {
		return eventId;
	}	
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public Timestamp getNetsaleFrom() {
		return netsaleFrom;
	}
	public void setNetsaleFrom(Timestamp netsaleFrom) {
		this.netsaleFrom = netsaleFrom;
	}
	public Timestamp getNetsaleTo() {
		return netsaleTo;
	}
	public void setNetsaleTo(Timestamp netsaleTo) {
		this.netsaleTo = netsaleTo;
	}
	public int getEntranceId() {
		return entranceId;
	}
	public void setEntranceId(int entranceId) {
		this.entranceId = entranceId;
	}
	public Boolean getHiddenFromListing() {
		return hiddenFromListing;
	}
	public void setHiddenFromListing(Boolean hiddenFromListing) {
		this.hiddenFromListing = hiddenFromListing;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public int getDetailPhotoId() {
		return detailPhotoId;
	}
	public void setDetailPhotoId(int detailPhotoId) {
		this.detailPhotoId = detailPhotoId;
	}
	*/
}
