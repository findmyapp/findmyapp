package no.uka.findmyapp.model.spotify;

public class RequestResponse {
	
	private String message;
	private boolean requestOK;
	private int qrValidationCode;
	
	
	public RequestResponse(String message, boolean requestOK, int qrValidationCode){
		setMessage(message);
		setRequestOK(requestOK);
		setQrValidationCode(qrValidationCode);
	}
	
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
	
	public int getQrValidationCode() {
		return qrValidationCode;
	}
	
	public void setQrValidationCode(int qrValidationCode) {
		this.qrValidationCode = qrValidationCode;
	}

	
	
}
