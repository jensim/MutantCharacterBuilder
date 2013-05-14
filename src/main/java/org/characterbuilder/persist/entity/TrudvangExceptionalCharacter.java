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
@Table(name = "trudvang_exceptional_character", catalog = "rollspel", schema = "public")
public class TrudvangExceptionalCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_EXCEPTIONAL_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_exceptional_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_EXCEPTIONAL_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Basic(optional = false)
	private Integer value;
	@JoinColumn(name = "character_id")
	@ManyToOne(optional = false)
	private TrudvangCharacter characterId;
	
	@OneToMany(mappedBy = "exeptionalId")
	private List<TrudvangModCharacter> trudvangModCharacterList;

	public TrudvangExceptionalCharacter() {
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public TrudvangCharacter getCharacterId() {
		return characterId;
	}

	public void setCharacterId(TrudvangCharacter characterId) {
		this.characterId = characterId;
	}

	public List<TrudvangModCharacter> getTrudvangModCharacterList() {
		return trudvangModCharacterList;
	}

	public void setTrudvangModCharacterList(List<TrudvangModCharacter> trudvangModCharacterList) {
		this.trudvangModCharacterList = trudvangModCharacterList;
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
		if (!(object instanceof TrudvangExceptionalCharacter)) {
			return false;
		}
		TrudvangExceptionalCharacter other = (TrudvangExceptionalCharacter) object;
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
