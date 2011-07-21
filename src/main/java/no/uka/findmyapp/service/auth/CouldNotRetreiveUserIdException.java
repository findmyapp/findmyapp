package no.uka.findmyapp.service.auth;

public class CouldNotRetreiveUserIdException extends Exception {

	private static final long serialVersionUID = 8724201558300512758L;

	public CouldNotRetreiveUserIdException(String message) {
		super(message);
	}
	
	public CouldNotRetreiveUserIdException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CouldNotRetreiveUserIdException(Throwable cause) {
		super(cause);
	}
	
}
