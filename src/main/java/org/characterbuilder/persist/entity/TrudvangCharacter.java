package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_character", catalog = "rollspel", schema = "public")
@NamedQueries({
	@NamedQuery(name = "TrudvangCharacter.findAll", query = "SELECT t FROM TrudvangCharacter t")})
public class TrudvangCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	private String notes;
	private String hometown;
	private String homeland;
	private String race;
	private String people;
	private String religion;
	private String upbringing;
	private String arketype;
	private String weaponhand;
	private String sex;
	@Basic(optional = false)
	private Integer age;
	@Basic(optional = false)
	private Integer weight;
	@Basic(optional = false)
	private Integer length;
	@Basic(optional = false)
	private Integer movement;
	@Basic(optional = false)
	private Integer experience;
	@Column(name = "background_story")
	private String backgroundStory;
	@Basic(optional = false)
	@Column(name = "health_total")
	private Integer healthTotal;
	@Basic(optional = false)
	@Column(name = "vitner_points_extra")
	private Integer vitnerPointsExtra;
	@Basic(optional = false)
	@Column(name = "religion_points_extra")
	private Integer religionPointsExtra;
	@Basic(optional = false)
	@Column(name = "health_atm")
	private Integer healthAtm;
	@Basic(optional = false)
	private Integer projectiles;
	@Basic(optional = false)
	@Column(name = "money_carried_hv")
	private Integer moneyCarriedHv;
	@Basic(optional = false)
	@Column(name = "money_banked_hv")
	private Integer moneyBankedHv;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "characterId")
	private List<TrudvangSkillCharacter> skillList = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "characterId")
	private List<TrudvangExceptionalCharacter> exceptionalList = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "characterId")
	private List<TrudvangWeaponProjectileCharacter> projectileWeaponList = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "characterId")
	private List<TrudvangWeaponMeleeCharacter> meleeWeaponList = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "characterId")
	private List<TrudvangItemCharacter> itemList = new ArrayList<>();
	@JoinColumn(name = "user_id")
	@ManyToOne(optional = false)
	private RollspelUser user;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "characterId")
	private List<TrudvangArmorCharacter> armorList = new ArrayList<>();
	@OneToMany(mappedBy = "characterId")
	private List<TrudvangElaborationCharacter> elaborations = new ArrayList<>();

	public TrudvangCharacter() {
	}

	public TrudvangCharacter(Integer id, String name, Integer age, Integer weight,
			  Integer length, Integer movement, Integer experience, Integer healthTotal,
			  Integer vitnerPointsExtra, Integer religionPointsExtra, Integer healthAtm,
			  Integer projectiles, Integer moneyCarriedHv, Integer moneyBankedHv) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.length = length;
		this.movement = movement;
		this.experience = experience;
		this.healthTotal = healthTotal;
		this.vitnerPointsExtra = vitnerPointsExtra;
		this.religionPointsExtra = religionPointsExtra;
		this.healthAtm = healthAtm;
		this.projectiles = projectiles;
		this.moneyCarriedHv = moneyCarriedHv;
		this.moneyBankedHv = moneyBankedHv;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getHomeland() {
		return homeland;
	}

	public void setHomeland(String homeland) {
		this.homeland = homeland;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getUpbringing() {
		return upbringing;
	}

	public void setUpbringing(String upbringing) {
		this.upbringing = upbringing;
	}

	public String getArketype() {
		return arketype;
	}

	public void setArketype(String arketype) {
		this.arketype = arketype;
	}

	public String getWeaponhand() {
		return weaponhand;
	}

	public void setWeaponhand(String weaponhand) {
		this.weaponhand = weaponhand;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getMovement() {
		return movement;
	}

	public void setMovement(Integer movement) {
		this.movement = movement;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getBackgroundStory() {
		return backgroundStory;
	}

	public void setBackgroundStory(String backgroundStory) {
		this.backgroundStory = backgroundStory;
	}

	public Integer getHealthTotal() {
		return healthTotal;
	}

	public void setHealthTotal(Integer healthTotal) {
		this.healthTotal = healthTotal;
	}

	public Integer getVitnerPointsExtra() {
		return vitnerPointsExtra;
	}

	public void setVitnerPointsExtra(Integer vitnerPointsExtra) {
		this.vitnerPointsExtra = vitnerPointsExtra;
	}

	public Integer getReligionPointsExtra() {
		return religionPointsExtra;
	}

	public void setReligionPointsExtra(Integer religionPointsExtra) {
		this.religionPointsExtra = religionPointsExtra;
	}

	public Integer getHealthAtm() {
		return healthAtm;
	}

	public void setHealthAtm(Integer healthAtm) {
		this.healthAtm = healthAtm;
	}

	public Integer getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(Integer projectiles) {
		this.projectiles = projectiles;
	}

	public Integer getMoneyCarriedHv() {
		return moneyCarriedHv;
	}

	public void setMoneyCarriedHv(Integer moneyCarriedHv) {
		this.moneyCarriedHv = moneyCarriedHv;
	}

	public Integer getMoneyBankedHv() {
		return moneyBankedHv;
	}

	public void setMoneyBankedHv(Integer moneyBankedHv) {
		this.moneyBankedHv = moneyBankedHv;
	}

	public List<TrudvangSkillCharacter> getTrudvangSkillList() {
		return skillList;
	}

	public void setTrudvangSkillList(List<TrudvangSkillCharacter> trudvangSkillList) {
		this.skillList = trudvangSkillList;
	}

	public List<TrudvangExceptionalCharacter> getTrudvangExceptionalList() {
		return exceptionalList;
	}

	public void setTrudvangExceptionalList(List<TrudvangExceptionalCharacter> trudvangExceptionalList) {
		this.exceptionalList = trudvangExceptionalList;
	}

	public List<TrudvangWeaponProjectileCharacter> getTrudvangWeaponProjectileList() {
		return projectileWeaponList;
	}

	public void setTrudvangWeaponProjectileList(List<TrudvangWeaponProjectileCharacter> trudvangWeaponProjectileCharacterList) {
		this.projectileWeaponList = trudvangWeaponProjectileCharacterList;
	}

	public List<TrudvangWeaponMeleeCharacter> getTrudvangWeaponMeleeList() {
		return meleeWeaponList;
	}

	public void setTrudvangWeaponMeleeList(List<TrudvangWeaponMeleeCharacter> trudvangWeaponMeleeCharacterList) {
		this.meleeWeaponList = trudvangWeaponMeleeCharacterList;
	}

	public List<TrudvangItemCharacter> getTrudvangItemList() {
		return itemList;
	}

	public void setTrudvangItemList(List<TrudvangItemCharacter> trudvangItemCharacterList) {
		this.itemList = trudvangItemCharacterList;
	}

	public RollspelUser getUser() {
		return user;
	}

	public void setUser(RollspelUser user) {
		this.user = user;
	}

	public List<TrudvangArmorCharacter> getTrudvangArmorList() {
		return armorList;
	}

	public void setTrudvangArmorList(List<TrudvangArmorCharacter> trudvangArmorCharacterList) {
		this.armorList = trudvangArmorCharacterList;
	}

	public List<TrudvangSkillCharacter> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<TrudvangSkillCharacter> skillList) {
		this.skillList = skillList;
	}

	public List<TrudvangExceptionalCharacter> getExceptionalList() {
		return exceptionalList;
	}

	public void setExceptionalList(List<TrudvangExceptionalCharacter> exceptionalList) {
		this.exceptionalList = exceptionalList;
	}

	public List<TrudvangWeaponProjectileCharacter> getProjectileWeaponList() {
		return projectileWeaponList;
	}

	public void setProjectileWeaponList(List<TrudvangWeaponProjectileCharacter> projectileWeaponList) {
		this.projectileWeaponList = projectileWeaponList;
	}

	public List<TrudvangWeaponMeleeCharacter> getMeleeWeaponList() {
		return meleeWeaponList;
	}

	public void setMeleeWeaponList(List<TrudvangWeaponMeleeCharacter> meleeWeaponList) {
		this.meleeWeaponList = meleeWeaponList;
	}

	public List<TrudvangItemCharacter> getItemList() {
		return itemList;
	}

	public void setItemList(List<TrudvangItemCharacter> itemList) {
		this.itemList = itemList;
	}

	public List<TrudvangArmorCharacter> getArmorList() {
		return armorList;
	}

	public void setArmorList(List<TrudvangArmorCharacter> armorList) {
		this.armorList = armorList;
	}

	public List<TrudvangElaborationCharacter> getElaborations() {
		return elaborations;
	}

	public void setElaborations(List<TrudvangElaborationCharacter> elaborations) {
		this.elaborations = elaborations;
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
		if (!(object instanceof TrudvangCharacter)) {
			return false;
		}
		TrudvangCharacter other = (TrudvangCharacter) object;
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
