package no.uka.findmyapp.model.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecret;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;

@SuppressWarnings("serial")
public class UKAppsConsumerDetails implements ExtraTrustConsumerDetails {
	
	private int consumerId;
	private String consumerKey;
	private String consumerName;
	private String secret;
	private String authority;
	private boolean requiredToObtainAuthenticatedToken;
	
	public boolean isRequiredToObtainAuthenticatedToken() {
		return requiredToObtainAuthenticatedToken;
	}

	public UKAppsConsumerDetails() {
		requiredToObtainAuthenticatedToken = false;
	}
	
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
		return new UKAppsSignatureSecret(secret);
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return authority;
			}
		});
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

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	private class UKAppsSignatureSecret extends SharedConsumerSecret {
		
		public UKAppsSignatureSecret(String consumerSecret) {
			super(consumerSecret);
		}
		
	}

}
