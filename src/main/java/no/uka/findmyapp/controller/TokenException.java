package no.uka.findmyapp.controller;

public class TokenException extends Exception {

	private static final long serialVersionUID = 8293090029373294669L;

	public TokenException() {
		super();
	}

	public TokenException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TokenException(String message) {
		super(message);
	}
	
}
