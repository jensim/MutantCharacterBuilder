package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
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
 * TrudvangElaborationFree
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "trudvang_elaboration_free", catalog = "rollspel", schema = "public")
public class TrudvangElaborationFree implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_ELAB_FREE_ID_GENERATOR",
	sequenceName = "trudvang_elaboration_free_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ELAB_FREE_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@JoinColumn(name="elaboration_id")
	@ManyToOne
	private TrudvangElaboration elaboration;
	@JoinColumn(name="exceptional_id")
	@ManyToOne
	private TrudvangExceptionalLevel exceptional;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrudvangElaboration getElaboration() {
		return elaboration;
	}

	public void setElaboration(TrudvangElaboration elaboration) {
		this.elaboration = elaboration;
	}

	public TrudvangExceptionalLevel getExceptional() {
		return exceptional;
	}

	public void setExceptional(TrudvangExceptionalLevel exceptional) {
		this.exceptional = exceptional;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.id);
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
		final TrudvangElaborationFree other = (TrudvangElaborationFree) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return elaboration.toString();
	}
}
