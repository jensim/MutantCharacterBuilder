package org.characterbuilder.persist.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_elaboration_require", catalog = "rollspel", schema = "public")
public class TrudvangElaborationRequire implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "TRUDVANG_ELABORATION_REQUIRE_ID_GENERATOR",
	sequenceName = "trudvang_elaboration_require_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ELABORATION_REQUIRE_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@JoinColumn(name = "required_id")
	@ManyToOne(optional = false)
	private TrudvangElaboration requiredId;
	@JoinColumn(name = "elaboration_id")
	@ManyToOne(optional = false)
	private TrudvangElaboration elaborationId;

	public TrudvangElaborationRequire() {
	}

	public TrudvangElaborationRequire(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrudvangElaboration getRequiredId() {
		return requiredId;
	}

	public void setRequiredId(TrudvangElaboration requiredId) {
		this.requiredId = requiredId;
	}

	public TrudvangElaboration getElaborationId() {
		return elaborationId;
	}

	public void setElaborationId(TrudvangElaboration elaborationId) {
		this.elaborationId = elaborationId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TrudvangElaborationRequire)) {
			return false;
		}
		TrudvangElaborationRequire other = (TrudvangElaborationRequire) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return requiredId.toString();
	}
}
