package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_exceptional_level", catalog = "rollspel", schema = "public")
public class TrudvangExceptionalLevel implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_EXCEPTIONAL_LEVEL_ID_GENERATOR",
	sequenceName = "trudvang_exceptional_level_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_EXCEPTIONAL_LEVEL_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private Integer value;
	@Basic(optional = false)
	private String name;
	@Column(name = "description", nullable = false)
	private String description = "";
	@OneToMany(mappedBy = "exceptionalId")
	private List<TrudvangMod> trudvangModList;
	@JoinColumn(name = "exceptional_id")
	@ManyToOne(optional = false)
	private TrudvangExceptional exceptionalId;
	@OneToMany(mappedBy = "exceptional")
	private List<TrudvangElaborationFree> freeElaborations;

	public TrudvangExceptionalLevel() {
	}

	public TrudvangExceptionalLevel(Integer id) {
		this.id = id;
	}

	public TrudvangExceptionalLevel(Integer id, Integer value, String name) {
		this.id = id;
		this.value = value;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TrudvangMod> getTrudvangModList() {
		return trudvangModList;
	}

	public void setTrudvangModList(List<TrudvangMod> trudvangModList) {
		this.trudvangModList = trudvangModList;
	}

	public TrudvangExceptional getExceptionalId() {
		return exceptionalId;
	}

	public void setExceptionalId(TrudvangExceptional exceptionalId) {
		this.exceptionalId = exceptionalId;
	}

	public List<TrudvangElaborationFree> getFreeElaborations() {
		return freeElaborations;
	}

	public void setFreeElaborations(List<TrudvangElaborationFree> freeElaborations) {
		this.freeElaborations = freeElaborations;
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
		if (!(object instanceof TrudvangExceptionalLevel)) {
			return false;
		}
		TrudvangExceptionalLevel other = (TrudvangExceptionalLevel) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if (value > 0) {
			return name + " (+" + value + ")";
		} else {
			return name + " (" + value + ")";
		}
	}
}
