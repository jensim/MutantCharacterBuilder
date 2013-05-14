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
@Table(name = "trudvang_weapon_melee_character", catalog = "rollspel", schema = "public")
public class TrudvangWeaponMeleeCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_WEAPON_MELEE_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_weapon_melee_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_WEAPON_MELEE_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@Basic(optional = false)
	private Integer sh;
	@Basic(optional = false)
	private Integer im;
	@Basic(optional = false)
	private Integer bv;
	@Basic(optional = false)
	private Integer ps;
	@Basic(optional = false)
	private Integer weight;
	@Basic(optional = false)
	private Integer price;
	@Column(name = "damage_die_open")
	private Integer damageDieOpen;
	@JoinColumn(name = "character_id")
	@ManyToOne(optional = false)
	private TrudvangCharacter characterId;
	@Basic(optional = false)
	@Column(name = "damage_static")
	private Integer damageStatic;
	@ManyToOne(optional=false)
	@JoinColumn(name="damage_dice_id")
	private TrudvangDamageDice damageDice;
	@ManyToOne(optional=false)
	@JoinColumn(name="type_id")
	private TrudvangWeaponType weaponType;

	public TrudvangWeaponMeleeCharacter() {
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

	public Integer getSh() {
		return sh;
	}

	public void setSh(Integer sh) {
		this.sh = sh;
	}

	public Integer getIm() {
		return im;
	}

	public void setIm(Integer im) {
		this.im = im;
	}

	public Integer getBv() {
		return bv;
	}

	public void setBv(Integer bv) {
		this.bv = bv;
	}

	public Integer getPs() {
		return ps;
	}

	public void setPs(Integer ps) {
		this.ps = ps;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getDamageStatic() {
		return damageStatic;
	}

	public void setDamageStatic(Integer damageStatic) {
		this.damageStatic = damageStatic;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getDamageDieOpen() {
		return damageDieOpen;
	}

	public void setDamageDieOpen(Integer damageDieOpen) {
		this.damageDieOpen = damageDieOpen;
	}

	public TrudvangCharacter getCharacterId() {
		return characterId;
	}

	public void setCharacterId(TrudvangCharacter characterId) {
		this.characterId = characterId;
	}

	public TrudvangDamageDice getDamageDice() {
		return damageDice;
	}

	public void setDamageDice(TrudvangDamageDice damageDice) {
		this.damageDice = damageDice;
	}

	public TrudvangWeaponType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(TrudvangWeaponType weaponType) {
		this.weaponType = weaponType;
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
		if (!(object instanceof TrudvangWeaponMeleeCharacter)) {
			return false;
		}
		TrudvangWeaponMeleeCharacter other = (TrudvangWeaponMeleeCharacter) object;
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
