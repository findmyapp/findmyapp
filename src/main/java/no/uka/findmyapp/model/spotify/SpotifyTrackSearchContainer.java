package no.uka.findmyapp.model.spotify;

import java.util.List;

/**
 * Model for spotify's search results
 *
 */
public class SpotifyTrackSearchContainer {
	private List<SpotifyTrack> tracks;

	public List<SpotifyTrack> getTracks() {
		return tracks;
	}

	public void setTracks(List<SpotifyTrack> tracks) {
		this.tracks = tracks;
	}
}
