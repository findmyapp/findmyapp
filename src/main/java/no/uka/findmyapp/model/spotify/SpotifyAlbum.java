package no.uka.findmyapp.model.spotify;

public class SpotifyAlbum {
	private int released;
	private String name;
	private SpotifyAvailability availability;
	
	public int getReleased() {
		return released;
	}
	public void setReleased(int released) {
		this.released = released;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SpotifyAvailability getAvailability() {
		return availability;
	}
	public void setAvailability(SpotifyAvailability availability) {
		this.availability = availability;
	}
}
