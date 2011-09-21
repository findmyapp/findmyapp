package no.uka.findmyapp.model.spotify;

import java.sql.Timestamp;

/**
 * Our song model
 */
public class Track {
	private String spotifyId;
	private String title;
	private String artist;
	private Long length;
	private Timestamp lastPlayed;
	private int timesPlayed;
	private int activeRequests;
	private int totalRequests;
	private boolean banned;
	
	public String getSpotifyId() {
		return spotifyId;
	}
	public void setSpotifyId(String spotifyId) {
		this.spotifyId = spotifyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public int getTimesPlayed() {
		return timesPlayed;
	}
	public void setTimesPlayed(int timesPlayed) {
		this.timesPlayed = timesPlayed;
	}
	public int getActiveRequests() {
		return activeRequests;
	}
	public void setActiveRequests(int activeRequests) {
		this.activeRequests = activeRequests;
	}
	public int getTotalRequests() {
		return totalRequests;
	}
	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}
	public Timestamp getLastPlayed() {
		return lastPlayed;
	}
	public void setLastPlayed(Timestamp lastPlayed) {
		this.lastPlayed = lastPlayed;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
}
