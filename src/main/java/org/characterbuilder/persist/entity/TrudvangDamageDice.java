package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TrudvangDamageDice
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "trudvang_damage_dice", catalog = "rollspel", schema = "public")
public class TrudvangDamageDice implements Serializable{
	
	@Id
	@Column(unique=true, nullable=false)
	private Integer id;
	@Column(name="size", nullable=false)
	private Integer size;
	@Column(name="open", nullable=true)
	private Integer open = null;
	
	@OneToMany(mappedBy = "damageDice")
	private List<TrudvangWeaponProjectileCharacter> charProjectileWeapons;
	@OneToMany(mappedBy = "damageDice")
	private List<TrudvangWeaponProjectile> projectileWeapons;
	@OneToMany(mappedBy = "damageDice")
	private List<TrudvangWeaponMeleeCharacter> charMeleeWeapons;
	@OneToMany(mappedBy = "damageDice")
	private List<TrudvangWeaponMelee> meleeWeapons;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public List<TrudvangWeaponProjectileCharacter> getCharProjectileWeapons() {
		return charProjectileWeapons;
	}

	public void setCharProjectileWeapons(List<TrudvangWeaponProjectileCharacter> charProjectileWeapons) {
		this.charProjectileWeapons = charProjectileWeapons;
	}

	public List<TrudvangWeaponProjectile> getProjectileWeapons() {
		return projectileWeapons;
	}

	public void setProjectileWeapons(List<TrudvangWeaponProjectile> projectileWeapons) {
		this.projectileWeapons = projectileWeapons;
	}

	public List<TrudvangWeaponMeleeCharacter> getCharMeleeWeapons() {
		return charMeleeWeapons;
	}

	public void setCharMeleeWeapons(List<TrudvangWeaponMeleeCharacter> charMeleeWeapons) {
		this.charMeleeWeapons = charMeleeWeapons;
	}

	public List<TrudvangWeaponMelee> getMeleeWeapons() {
		return meleeWeapons;
	}

	public void setMeleeWeapons(List<TrudvangWeaponMelee> meleeWeapons) {
		this.meleeWeapons = meleeWeapons;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TrudvangDamageDice other = (TrudvangDamageDice) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
