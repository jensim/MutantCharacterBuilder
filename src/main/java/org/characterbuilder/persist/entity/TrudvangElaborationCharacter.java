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
import javax.validation.constraints.NotNull;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_elaboration_character", catalog = "rollspel", schema = "public")
public class TrudvangElaborationCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_ELABORATION_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_elaboration_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ELABORATION_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "character_id")
	private TrudvangCharacter characterId;
	@Column(name="name", nullable = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@ManyToOne(optional = false)
	@JoinColumn(name = "skill_id")
	private TrudvangSkillCharacter skillId;
	@JoinColumn(name="level_id")
	private TrudvangElaborationLevel elaborationLevel;
	
	@OneToMany(mappedBy = "elaborationId")
	private List<TrudvangModCharacter> modList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elaborationId")
	private List<TrudvangVitnerCharacter> vitnerList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elaborationId")
	private List<TrudvangPowerCharacter> powerList;

	public TrudvangElaborationCharacter() {
	}

	public TrudvangElaborationCharacter(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrudvangCharacter getCharacterId() {
		return characterId;
	}

	public void setCharacterId(TrudvangCharacter characterId) {
		this.characterId = characterId;
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

	public List<TrudvangVitnerCharacter> getVitnerList() {
		return vitnerList;
	}

	public void setVitnerList(List<TrudvangVitnerCharacter> trudvangVitnerCharacterList) {
		this.vitnerList = trudvangVitnerCharacterList;
	}

	public List<TrudvangPowerCharacter> getPowerList() {
		return powerList;
	}

	public void setPowerList(List<TrudvangPowerCharacter> trudvangPowerCharacterList) {
		this.powerList = trudvangPowerCharacterList;
	}

	public TrudvangSkillCharacter getSkillId() {
		return skillId;
	}

	public void setSkillId(TrudvangSkillCharacter skillId) {
		this.skillId = skillId;
	}

	public List<TrudvangModCharacter> getModList() {
		return modList;
	}

	public void setModList(List<TrudvangModCharacter> trudvangModCharacterList) {
		this.modList = trudvangModCharacterList;
	}

	public TrudvangElaborationLevel getElaborationLevel() {
		return elaborationLevel;
	}

	public void setElaborationLevel(TrudvangElaborationLevel elaborationLevel) {
		this.elaborationLevel = elaborationLevel;
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
		if (!(object instanceof TrudvangElaborationCharacter)) {
			return false;
		}
		TrudvangElaborationCharacter other = (TrudvangElaborationCharacter) object;
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
