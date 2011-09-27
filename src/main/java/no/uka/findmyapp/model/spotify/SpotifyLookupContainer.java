package no.uka.findmyapp.model.spotify;

/**
 * Spotify's song model (for requests against their api)
 */
public class SpotifyLookupContainer {
	private SpotifyTrack track;

	public SpotifyTrack getTrack() {
		return track;
	}

	public void setTrack(SpotifyTrack track) {
		this.track = track;
	}
}
