package no.uka.findmyapp.model.spotify;

import java.util.List;

/**
 * Spotifys track model
 */
public class SpotifyTrack {
	private double length;//length of song in seconds
	private String href;//URI or id of song
	private List<SpotifyArtist> artists;//artists of song
	private String name;//name of track
	
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public List<SpotifyArtist> getArtists() {
		return artists;
	}
	public void setArtists(List<SpotifyArtist> artists) {
		this.artists = artists;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String concatArtistNames() {
		String name = artists.get(0).getName();
		for (int i = 1; i < artists.size(); i++) {
			name += ", "+artists.get(i).getName();
		}
		return name;
	}
}
