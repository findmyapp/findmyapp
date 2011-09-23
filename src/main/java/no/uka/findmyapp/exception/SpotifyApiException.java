package no.uka.findmyapp.exception;

public class SpotifyApiException extends Exception {
	private static final long serialVersionUID = 3L;
	
	public SpotifyApiException(String msg) {
		super(msg);
	}
}
