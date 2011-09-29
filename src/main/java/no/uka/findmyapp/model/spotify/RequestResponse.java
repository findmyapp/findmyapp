package no.uka.findmyapp.model.spotify;

public class RequestResponse {
	
	private String message;
	private boolean requestOK;
	
	public RequestResponse(String message, boolean requestOK){
		setMessage(message);
		setRequestOK(requestOK);
	}
	
	public RequestResponse(){
		
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isRequestOK() {
		return requestOK;
	}
	public void setRequestOK(boolean requestOK) {
		this.requestOK = requestOK;
	}

	
	
}
