package no.uka.findmyapp.model.serviceinfo;

public enum ServiceDataFormat {
	JSON("application/json");
	String value;
	
	private ServiceDataFormat(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
