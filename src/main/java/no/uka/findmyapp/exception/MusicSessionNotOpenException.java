package no.uka.findmyapp.exception;

public class MusicSessionNotOpenException extends Exception {

	private static final long serialVersionUID = 7421309743536684329L;
	
	public MusicSessionNotOpenException(String msg) {
		super(msg);
	}
}
