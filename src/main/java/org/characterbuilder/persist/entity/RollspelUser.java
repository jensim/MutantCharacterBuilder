package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "rollspel_user")
public class RollspelUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "ROLLSPEL_USER_ID_GENERATOR", sequenceName = "ROLLSPEL_USER_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_USER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@OneToMany(mappedBy = "rollspelUser")
	private Set<MutantCharacter> mutantCharacters;
	@OneToMany(mappedBy = "rollspelUser")
	private Set<RollspelGruppApplication> rollspelGruppApplications;
	@OneToMany(mappedBy = "rollspelUser")
	private Set<RollspelGruppMember> rollspelGruppMembers;
	@OneToMany(mappedBy = "rollspelUser")
	private Set<RollspelLog> rollspelLogs;
	@ManyToOne
	@JoinColumn(name = "status_id", nullable = false)
	private RollspelStatus rollspelStatus;
	@ManyToOne
	@JoinColumn(name = "user_role_id", nullable = false)
	private RollspelUserRole rollspelUserRole;
	@OneToMany(mappedBy = "rollspelUser")
	private List<RollspelUserRoleApplication> rollspelUserRoleApplications;
	@OneToMany(mappedBy = "user")
	private List<TrudvangCharacter> trudvangCharacters;
	@ManyToOne
	@JoinColumn(name = "oauth_provider_id")
	private OauthProvider oauthProvider;
	@Column(name = "oauth_provider_user_id")
	private String oauthUserID;
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
	private List<RollspelSession> sessionList;

	public RollspelUser() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<MutantCharacter> getMutantCharacters() {
		return this.mutantCharacters;
	}

	public void setMutantCharacters(Set<MutantCharacter> mutantCharacters) {
		this.mutantCharacters = mutantCharacters;
	}

	public List<TrudvangCharacter> getTrudvangCharacters() {
		return this.trudvangCharacters;
	}

	public void setTrudvangCharacters(List<TrudvangCharacter> trudvangCharacter) {
		this.trudvangCharacters = trudvangCharacter;
	}

	public Set<RollspelGruppApplication> getRollspelGruppApplications() {
		return this.rollspelGruppApplications;
	}

	public void setRollspelGruppApplications(Set<RollspelGruppApplication> rollspelGruppApplications) {
		this.rollspelGruppApplications = rollspelGruppApplications;
	}

	public Set<RollspelGruppMember> getRollspelGruppMembers() {
		return this.rollspelGruppMembers;
	}

	public void setRollspelGruppMembers(Set<RollspelGruppMember> rollspelGruppMembers) {
		this.rollspelGruppMembers = rollspelGruppMembers;
	}

	public Set<RollspelLog> getRollspelLogs() {
		return this.rollspelLogs;
	}

	public void setRollspelLogs(Set<RollspelLog> rollspelLogs) {
		this.rollspelLogs = rollspelLogs;
	}

	public RollspelStatus getRollspelStatus() {
		return this.rollspelStatus;
	}

	public void setRollspelStatus(RollspelStatus rollspelStatus) {
		this.rollspelStatus = rollspelStatus;
	}

	public RollspelUserRole getRollspelUserRole() {
		return this.rollspelUserRole;
	}

	public void setRollspelUserRole(RollspelUserRole rollspelUserRole) {
		this.rollspelUserRole = rollspelUserRole;
	}

	public List<RollspelUserRoleApplication> getRollspelUserRoleApplications() {
		return this.rollspelUserRoleApplications;
	}

	public void setRollspelUserRoleApplications(List<RollspelUserRoleApplication> rollspelUserRoleApplications) {
		this.rollspelUserRoleApplications = rollspelUserRoleApplications;
	}

	public OauthProvider getOauthProvider() {
		return oauthProvider;
	}

	public void setOauthProvider(OauthProvider oauthProvider) {
		this.oauthProvider = oauthProvider;
	}

	public String getOauthUserID() {
		return oauthUserID;
	}

	public void setOauthUserID(String oauthUserID) {
		this.oauthUserID = oauthUserID;
	}

	public List<RollspelSession> getSessionList() {
		return sessionList;
	}

	public void setSessionList(List<RollspelSession> sessionList) {
		this.sessionList = sessionList;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RollspelUser other = (RollspelUser) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public String toString() {
		return oauthUserID + "@" + oauthProvider;
	}
}