package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "rollspel_user_role")
public class RollspelUserRole implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "ROLLSPEL_USER_ROLE_ID_GENERATOR", sequenceName = "ROLLSPEL_USER_ROLE_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_USER_ROLE_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(length = 2147483647, nullable=false)
	private String description ="";
	@Column(nullable = false, length = 2147483647)
	private String name;
	//bi-directional many-to-one association to RollspelUser
	@OneToMany(mappedBy = "rollspelUserRole")
	private Set<RollspelUser> rollspelUsers;
	//bi-directional many-to-one association to RollspelUserRoleApplication
	@OneToMany(mappedBy = "rollspelUserRole")
	private Set<RollspelUserRoleApplication> rollspelUserRoleApplications;

	public RollspelUserRole() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<RollspelUser> getRollspelUsers() {
		return this.rollspelUsers;
	}

	public void setRollspelUsers(Set<RollspelUser> rollspelUsers) {
		this.rollspelUsers = rollspelUsers;
	}

	public Set<RollspelUserRoleApplication> getRollspelUserRoleApplications() {
		return this.rollspelUserRoleApplications;
	}

	public void setRollspelUserRoleApplications(Set<RollspelUserRoleApplication> rollspelUserRoleApplications) {
		this.rollspelUserRoleApplications = rollspelUserRoleApplications;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 83 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RollspelUserRole other = (RollspelUserRole) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}