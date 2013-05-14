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
@Table(name = "trudvang_armor_character", catalog = "rollspel", schema = "public")
public class TrudvangArmorCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_ARMOR_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_armor_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ARMOR_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Basic(optional = false)
	private Integer armorvalue;
	@Basic(optional = false)
	private Integer weight;
	@Basic(optional = false)
	private Integer price;
	@JoinColumn(name = "character_id")
	@ManyToOne(optional = false)
	private TrudvangCharacter characterId;
	@JoinColumn(name = "bodypart_id")
	@ManyToOne(optional = false)
	private TrudvangBodypart bodypartId;

	public TrudvangArmorCharacter() {
	}

	public TrudvangArmorCharacter(Integer id) {
		this.id = id;
	}

	public TrudvangArmorCharacter(Integer id, String name, Integer armorvalue,
			  Integer weight, Integer price) {
		this.id = id;
		this.name = name;
		this.armorvalue = armorvalue;
		this.weight = weight;
		this.price = price;
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

	public Integer getArmorvalue() {
		return armorvalue;
	}

	public void setArmorvalue(Integer armorvalue) {
		this.armorvalue = armorvalue;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public TrudvangCharacter getCharacterId() {
		return characterId;
	}

	public void setCharacterId(TrudvangCharacter characterId) {
		this.characterId = characterId;
	}

	public TrudvangBodypart getBodypartId() {
		return bodypartId;
	}

	public void setBodypartId(TrudvangBodypart bodypartId) {
		this.bodypartId = bodypartId;
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
		if (!(object instanceof TrudvangArmorCharacter)) {
			return false;
		}
		TrudvangArmorCharacter other = (TrudvangArmorCharacter) object;
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
