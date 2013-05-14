package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "rollspel_user_role_application")
public class RollspelUserRoleApplication implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "ROLLSPEL_USER_ROLE_APPLICATION_ID_GENERATOR", sequenceName = "ROLLSPEL_USER_ROLE_APPLICATION_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_USER_ROLE_APPLICATION_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(length = 2147483647)
	private String message;
	@Column(name = "status_id", nullable = false)
	private Integer statusId;
	//bi-directional many-to-one association to RollspelUser
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private RollspelUser rollspelUser;
	//bi-directional many-to-one association to RollspelUserRole
	@ManyToOne
	@JoinColumn(name = "usr_role_id", nullable = false)
	private RollspelUserRole rollspelUserRole;

	public RollspelUserRoleApplication() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public RollspelUser getRollspelUser() {
		return this.rollspelUser;
	}

	public void setRollspelUser(RollspelUser rollspelUser) {
		this.rollspelUser = rollspelUser;
	}

	public RollspelUserRole getRollspelUserRole() {
		return this.rollspelUserRole;
	}

	public void setRollspelUserRole(RollspelUserRole rollspelUserRole) {
		this.rollspelUserRole = rollspelUserRole;
	}

	@Override
	public String toString() {
		return "RollspelUserRoleApplication{" + "message=" + message + '}';
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 41 * hash + Objects.hashCode(this.id);
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
		final RollspelUserRoleApplication other = (RollspelUserRoleApplication) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}