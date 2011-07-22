package no.uka.findmyapp.model.auth;

public class AppAuthInfo {

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getConsumerRole() {
		return consumerRole;
	}
	
	public void setConsumerRole(String consumerRole) {
		this.consumerRole = consumerRole;
	}

	private int appId;
	private String consumerKey;
	private String consumerSecret;
	private String consumerRole;

}
