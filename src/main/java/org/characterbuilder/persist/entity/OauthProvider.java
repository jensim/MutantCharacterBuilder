package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "oauth_provider", schema = "public")
public class OauthProvider implements Serializable {

	@Id
	@SequenceGenerator(name = "OAUTH_PROVIDER_ID_GENERATOR", sequenceName = "oauth_provider_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OAUTH_PROVIDER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "name", nullable = false, length = 255, unique = true)
	private String name;
	@Column(name = "client_id", nullable = false, length = 255)
	private String clientID;
	@Column(name = "client_secret", nullable = false, length = 255)
	private String clientSecret;
	@Column(name = "authorize_url", nullable = false, length = 255)
	private String authorizeURL;
	@Column(name = "access_token_url", nullable = false, length = 255)
	private String accessTokenURL;
	@Column(name = "user_url", nullable = false, length = 255)
	private String userURL;
	@Column(name = "logout_url", length = 255)
	private String logoutURL;
	@Column(name = "login_icon_url", length = 255)
	private String loginIconURL;
	@Column(name = "active")
	private Boolean active = false;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getClient_id() {
		return clientID;
	}

	public String getClient_secret() {
		return clientSecret;
	}

	public String getAuthorize_url() {
		return authorizeURL;
	}

	public String getAccess_token_url() {
		return accessTokenURL;
	}

	public String getUser_url() {
		return userURL;
	}

	public String getLogout_url() {
		return logoutURL;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClient_id(String client_id) {
		this.clientID = client_id;
	}

	public void setClient_secret(String client_secret) {
		this.clientSecret = client_secret;
	}

	public void setAuthorize_url(String authorize_url) {
		this.authorizeURL = authorize_url;
	}

	public void setAccess_token_url(String access_token_url) {
		this.accessTokenURL = access_token_url;
	}

	public void setUser_url(String user_url) {
		this.userURL = user_url;
	}

	public void setLogout_url(String logout_url) {
		this.logoutURL = logout_url;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAuthorizeURL() {
		return authorizeURL;
	}

	public void setAuthorizeURL(String authorizeURL) {
		this.authorizeURL = authorizeURL;
	}

	public String getAccessTokenURL() {
		return accessTokenURL;
	}

	public void setAccessTokenURL(String accessTokenURL) {
		this.accessTokenURL = accessTokenURL;
	}

	public String getUserURL() {
		return userURL;
	}

	public void setUserURL(String userURL) {
		this.userURL = userURL;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}

	public String getLoginIconURL() {
		return loginIconURL;
	}

	public void setLoginIconURL(String loginIconURL) {
		this.loginIconURL = loginIconURL;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OauthProvider other = (OauthProvider) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public String toString() {
		return name;
	}
}
