package no.uka.findmyapp.exception;

public class InvalidUserIdOrAccessTokenException extends Exception{
	
	private static final long serialVersionUID = 2L;
	
	public InvalidUserIdOrAccessTokenException(String s) {
		super(s);
	}
}
