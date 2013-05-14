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
@Table(name = "trudvang_mod", catalog = "rollspel", schema = "public")
public class TrudvangMod implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_MOD_ID_GENERATOR",
	sequenceName = "trudvang_mod_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_MOD_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private Integer value;
	@JoinColumn(name = "type_id")
	@ManyToOne(optional = false)
	private TrudvangModType typeId;
	@JoinColumn(name = "exceptional_id")
	@ManyToOne
	private TrudvangExceptionalLevel exceptionalId;
	@JoinColumn(name = "elaboration_id")
	@ManyToOne
	private TrudvangElaboration elaborationId;
	@JoinColumn(name = "skill_id")
	@ManyToOne
	private TrudvangSkill skill;

	public TrudvangMod() {
	}

	public TrudvangMod(Integer id) {
		this.id = id;
	}

	public TrudvangSkill getSkill() {
		return skill;
	}

	public void setSkill(TrudvangSkill skill) {
		this.skill = skill;
	}

	public TrudvangMod(Integer id, Integer value) {
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

	public TrudvangExceptionalLevel getExceptionalId() {
		return exceptionalId;
	}

	public void setExceptionalId(TrudvangExceptionalLevel exceptionalId) {
		this.exceptionalId = exceptionalId;
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
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TrudvangMod)) {
			return false;
		}
		TrudvangMod other = (TrudvangMod) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}
