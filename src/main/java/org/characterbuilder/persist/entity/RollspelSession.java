/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "rollspel_session", schema = "public")
public class RollspelSession implements Serializable {

	@Id
	@SequenceGenerator(name = "ROLLSPEL_SESSION_ID_GENERATOR", sequenceName = "rollspel_session_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_SESSION_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "session_id", nullable = false, length = 255, unique = true)
	private String sessionID;
	@JoinColumn(name = "user_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	private RollspelUser user;
	@Column(name = "ip_addr")
	private String ipAddr;
	@Column(name = "session_start_timestamp", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date sessionStartTimestamp = new Date();
	@Column(name = "session_activity_timestamp", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date sessionActivityTimestamp = new Date();
	@Column(name = "oauth_access_token", nullable = false)
	private String oauthAccessToken;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public RollspelUser getUser() {
		return user;
	}

	public void setUser(RollspelUser user) {
		this.user = user;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Date getSessionStartTimestamp() {
		return sessionStartTimestamp;
	}

	public void setSessionStartTimestamp(Date sessionStartTimestamp) {
		this.sessionStartTimestamp = sessionStartTimestamp;
	}

	public Date getSessionActivityTimestamp() {
		return sessionActivityTimestamp;
	}

	public void setSessionActivityTimestamp(Date sessionActivityTimestamp) {
		this.sessionActivityTimestamp = sessionActivityTimestamp;
	}

	public String getOauthAccessToken() {
		return oauthAccessToken;
	}

	public void setOauthAccessToken(String oauthAccessToken) {
		this.oauthAccessToken = oauthAccessToken;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RollspelSession other = (RollspelSession) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 41 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public String toString() {
		return sessionID;
	}
}
