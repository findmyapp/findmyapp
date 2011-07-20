package no.uka.findmyapp.configuration;

public class AuthenticationConfiguration {
	
	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	private String tokenSecret;
	
}
