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

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getFacebookSecret() {
		return facebookSecret;
	}

	public void setFacebookSecret(String facebookSecret) {
		this.facebookSecret = facebookSecret;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	private int appId;
	private String appName;
	private String facebookId;
	private String facebookSecret;
	private String consumerKey;
	private String consumerSecret;
	private String consumerRole;

}
