package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "trudvang_elaboration", catalog = "rollspel", schema = "public")
public class TrudvangElaboration implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_ELABORATION_ID_GENERATOR",
	sequenceName = "trudvang_elaboration_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ELABORATION_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description="";
	@ManyToOne(optional = false)
	@JoinColumn(name = "skill_id")
	private TrudvangSkill skillId;
	@ManyToOne(optional = false)
	@JoinColumn(name = "book_id")
	private TrudvangBook bookId;
	@ManyToOne(optional=false)
	@JoinColumn(name="level_id")
	private TrudvangElaborationLevel elaborationLevel;
	@ManyToOne
	@JoinColumn(name="religion_id")
	private TrudvangReligion religion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elaborationId")
	private List<TrudvangPower> powerList;
	@OneToMany(cascade= CascadeType.ALL, mappedBy = "elaborationId")
	private List<TrudvangMod> modList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elaborationId")
	private List<TrudvangVitner> vitnerList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elaborationId")
	private List<TrudvangElaborationRequire> elabRequireList;
	@OneToMany(cascade= CascadeType.ALL, mappedBy="elaboration")
	private List<TrudvangElaborationFree> freeElabList;
	
	public TrudvangElaboration() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<TrudvangPower> getPowerList() {
		return powerList;
	}

	public void setPowerList(List<TrudvangPower> trudvangPowerList) {
		this.powerList = trudvangPowerList;
	}

	public List<TrudvangMod> getModList() {
		return modList;
	}

	public void setModList(List<TrudvangMod> trudvangModList) {
		this.modList = trudvangModList;
	}
	
	public List<TrudvangElaborationFree> getFreeElabList() {
		return freeElabList;
	}

	public void setFreeElabList(List<TrudvangElaborationFree> freeElaborations) {
		this.freeElabList = freeElaborations;
	}

	public TrudvangSkill getSkillId() {
		return skillId;
	}

	public void setSkillId(TrudvangSkill skillId) {
		this.skillId = skillId;
	}

	public TrudvangBook getBookId() {
		return bookId;
	}

	public void setBookId(TrudvangBook bookId) {
		this.bookId = bookId;
	}

	public List<TrudvangVitner> getTrudvangVitnerList() {
		return vitnerList;
	}

	public void setVitnerList(List<TrudvangVitner> trudvangVitnerList) {
		this.vitnerList = trudvangVitnerList;
	}

	public List<TrudvangElaborationRequire> getElabRequireList() {
		return elabRequireList;
	}

	public void setElabRequireList(List<TrudvangElaborationRequire> elabRequireList) {
		this.elabRequireList = elabRequireList;
	}

	public TrudvangElaborationLevel getSkillLevel() {
		return elaborationLevel;
	}

	public void setSkillLevel(TrudvangElaborationLevel skillLevel) {
		this.elaborationLevel = skillLevel;
	}

	public TrudvangReligion getReligion() {
		return religion;
	}

	public void setReligion(TrudvangReligion religion) {
		this.religion = religion;
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
		if (!(object instanceof TrudvangElaboration)) {
			return false;
		}
		TrudvangElaboration other = (TrudvangElaboration) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
