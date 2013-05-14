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
@Table(name = "trudvang_skill_character", catalog = "rollspel", schema = "public")
public class TrudvangSkillCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_SKILL_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_skill_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_SKILL_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private Integer value = 0;
	@Basic(optional = false)
	private Integer basevalue = 0;
	@JoinColumn(name = "skill_id")
	@ManyToOne(optional = false)
	private TrudvangSkill skillId;
	@JoinColumn(name = "character_id")
	@ManyToOne(optional = false)
	private TrudvangCharacter characterId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
	private List<TrudvangElaborationCharacter> trudvangElaborationCharacterList;
	
	public TrudvangSkillCharacter() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getBasevalue() {
		return basevalue;
	}

	public void setBasevalue(int basevalue) {
		this.basevalue = basevalue;
	}

	public TrudvangSkill getSkillId() {
		return skillId;
	}

	public void setSkillId(TrudvangSkill skillId) {
		this.skillId = skillId;
	}

	public TrudvangCharacter getCharacterId() {
		return characterId;
	}

	public void setCharacterId(TrudvangCharacter characterId) {
		this.characterId = characterId;
	}

	public List<TrudvangElaborationCharacter> getTrudvangElaborationCharacterList() {
		return trudvangElaborationCharacterList;
	}

	public void setTrudvangElaborationCharacterList(List<TrudvangElaborationCharacter> trudvangElaborationCharacterList) {
		this.trudvangElaborationCharacterList = trudvangElaborationCharacterList;
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
		if (!(object instanceof TrudvangSkillCharacter)) {
			return false;
		}
		TrudvangSkillCharacter other = (TrudvangSkillCharacter) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}
