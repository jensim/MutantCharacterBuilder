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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_item_character", catalog = "rollspel", schema = "public")
@NamedQueries({
	@NamedQuery(name = "TrudvangItemCharacter.findAll", query = "SELECT t FROM TrudvangItemCharacter t")})
public class TrudvangItemCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_ITEM_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_item_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ITEM_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@Basic(optional = false)
	private Integer weight;
	@Basic(optional = false)
	private Integer ammount;
	@Basic(optional = false)
	@Column(name = "value_hv")
	private Integer valueHv;
	@JoinColumn(name = "character_id")
	@ManyToOne(optional = false)
	private TrudvangCharacter characterId;

	public TrudvangItemCharacter() {
	}

	public TrudvangItemCharacter(Integer id) {
		this.id = id;
	}

	public TrudvangItemCharacter(Integer id, String name, Integer weight,
			  Integer ammount, Integer valueHv) {
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.ammount = ammount;
		this.valueHv = valueHv;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getAmmount() {
		return ammount;
	}

	public void setAmmount(Integer ammount) {
		this.ammount = ammount;
	}

	public Integer getValueHv() {
		return valueHv;
	}

	public void setValueHv(Integer valueHv) {
		this.valueHv = valueHv;
	}

	public TrudvangCharacter getCharacterId() {
		return characterId;
	}

	public void setCharacterId(TrudvangCharacter characterId) {
		this.characterId = characterId;
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
		if (!(object instanceof TrudvangItemCharacter)) {
			return false;
		}
		TrudvangItemCharacter other = (TrudvangItemCharacter) object;
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
