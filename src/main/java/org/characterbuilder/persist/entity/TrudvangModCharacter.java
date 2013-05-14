package org.characterbuilder.persist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "trudvang_mod_character", catalog = "rollspel", schema = "public")
public class TrudvangModCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_MOD_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_mod_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_MOD_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private Integer value;
	@JoinColumn(name = "type_id")
	@ManyToOne(optional = false)
	private TrudvangModType typeId;
	@JoinColumn(name = "exeptional_id")
	@ManyToOne
	private TrudvangExceptionalCharacter exeptionalId;
	@JoinColumn(name = "elaboration_id")
	@ManyToOne
	private TrudvangElaborationCharacter elaborationId;

	public TrudvangModCharacter() {
	}

	public TrudvangModCharacter(Integer id) {
		this.id = id;
	}

	public TrudvangModCharacter(Integer id, int value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public TrudvangModType getTypeId() {
		return typeId;
	}

	public void setTypeId(TrudvangModType typeId) {
		this.typeId = typeId;
	}

	public TrudvangExceptionalCharacter getExeptionalId() {
		return exeptionalId;
	}

	public void setExeptionalId(TrudvangExceptionalCharacter exeptionalId) {
		this.exeptionalId = exeptionalId;
	}

	public TrudvangElaborationCharacter getElaborationId() {
		return elaborationId;
	}

	public void setElaborationId(TrudvangElaborationCharacter elaborationId) {
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
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TrudvangModCharacter)) {
			return false;
		}
		TrudvangModCharacter other = (TrudvangModCharacter) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}
