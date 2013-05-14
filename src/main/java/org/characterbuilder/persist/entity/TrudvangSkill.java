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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_skill", catalog = "rollspel", schema = "public")
public class TrudvangSkill implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_SKILL_ID_GENERATOR",
	sequenceName = "trudvang_skill_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_SKILL_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
	private List<TrudvangSkillCharacter> trudvangSkillCharacterList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
	private List<TrudvangSkillStart> trudvangSkillStartList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
	private List<TrudvangElaboration> trudvangElaborationList;
	@OneToMany(mappedBy = "skill")
	private List<TrudvangSkillInGroup> inGroups;
	@OneToMany(mappedBy="skill")
	private List<TrudvangMod> modList;

	public TrudvangSkill() {
	}

	public List<TrudvangMod> getModList() {
		return modList;
	}

	public void setModList(List<TrudvangMod> modList) {
		this.modList = modList;
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

	public List<TrudvangSkillCharacter> getTrudvangSkillCharacterList() {
		return trudvangSkillCharacterList;
	}

	public void setTrudvangSkillCharacterList(List<TrudvangSkillCharacter> trudvangSkillCharacterList) {
		this.trudvangSkillCharacterList = trudvangSkillCharacterList;
	}

	public List<TrudvangSkillStart> getTrudvangSkillStartList() {
		return trudvangSkillStartList;
	}

	public void setTrudvangSkillStartList(List<TrudvangSkillStart> trudvangSkillStartList) {
		this.trudvangSkillStartList = trudvangSkillStartList;
	}

	public List<TrudvangElaboration> getTrudvangElaborationList() {
		return trudvangElaborationList;
	}

	public void setTrudvangElaborationList(List<TrudvangElaboration> trudvangElaborationList) {
		this.trudvangElaborationList = trudvangElaborationList;
	}

	public List<TrudvangSkillInGroup> getInGroups() {
		return inGroups;
	}

	public void setInGroups(List<TrudvangSkillInGroup> inGroups) {
		this.inGroups = inGroups;
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
		if (!(object instanceof TrudvangSkill)) {
			return false;
		}
		TrudvangSkill other = (TrudvangSkill) object;
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
