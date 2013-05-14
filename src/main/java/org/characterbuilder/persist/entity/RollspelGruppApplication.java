package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "rollspel_grupp_application")
public class RollspelGruppApplication implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "ROLLSPEL_GRUPP_APPLICATION_ID_GENERATOR", sequenceName = "ROLLSPEL_GRUPP_APPLICATION_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_GRUPP_APPLICATION_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "status_id", nullable = false)
	private Integer statusId;
	//bi-directional many-to-one association to RollspelGrupp
	@ManyToOne
	@JoinColumn(name = "grupp_id", nullable = false)
	private RollspelGrupp rollspelGrupp;
	//bi-directional many-to-one association to RollspelUser
	@ManyToOne
	@JoinColumn(name = "usr_id", nullable = false)
	private RollspelUser rollspelUser;

	public RollspelGruppApplication() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public RollspelGrupp getRollspelGrupp() {
		return this.rollspelGrupp;
	}

	public void setRollspelGrupp(RollspelGrupp rollspelGrupp) {
		this.rollspelGrupp = rollspelGrupp;
	}

	public RollspelUser getRollspelUser() {
		return this.rollspelUser;
	}

	public void setRollspelUser(RollspelUser rollspelUser) {
		this.rollspelUser = rollspelUser;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.id);
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
		final RollspelGruppApplication other = (RollspelGruppApplication) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}