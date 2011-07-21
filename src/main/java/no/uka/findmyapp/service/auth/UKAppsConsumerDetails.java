package no.uka.findmyapp.service.auth;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.ConsumerDetails;

@SuppressWarnings("serial")
public class UKAppsConsumerDetails implements ConsumerDetails {
	
	private int consumerId;
	private String consumerKey;
	private String consumerName;
	private String secret;
	private List<GrantedAuthority> authorities;
	
	public int getConsumerId() {
		return consumerId;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public SignatureSecret getSignatureSecret() {
		return new UKAppsSignatureSecret();
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public void setSignatureSecret(String signatureSecret) {
		this.secret = signatureSecret;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	private class UKAppsSignatureSecret implements SignatureSecret {
		public String getSecret() { return secret; }
	}

}
