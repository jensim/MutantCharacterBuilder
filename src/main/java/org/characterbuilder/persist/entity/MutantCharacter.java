package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_character")
public class MutantCharacter implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_CHARACTER_ID_GENERATOR", sequenceName = "MUTANT_CHARACTER_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "age")
	private Integer age = 20;
	@Column(name = "creation_points", nullable = false)
	private Integer creationPoints = 30;
	@Column(name = "ff_springa")
	private Integer ffSpringa = 0;
	@Column(name = "ff_sprint")
	private Integer ffSprint = 0;
	@Column(name = "ff_strid")
	private Integer ffStrid = 0;
	@Column(name = "health_points")
	private Integer healthPoints = 0;
	@Column(length = 50)
	private String home = "";
	@Column(name = "initiativbonus")
	private Integer initiativbonus = 0;
	@Column(name = "length")
	private Integer length = 170;
	@Column(length = 2147483647, name = "looks")
	private String looks = "";
	@Column(name = "money")
	private Integer money = 0;
	@Column(nullable = false, length = 50, name = "name")
	private String name;
	@Column(name = "other_text", length = 2147483647)
	private String otherText = "";
	@Column(name = "prior_occupation", length = 50)
	private String priorOccupation;
	@Column(name = "rumor_points")
	private Integer rumorPoints = 0;
	@Column(length = 50)
	private String sex = "";
	@Column(name = "status_points")
	private Integer statusPoints = 0;
	@Column(name = "trauma_points")
	private Integer traumaPoints = 0;
	@Column(name = "weight")
	private Integer weight = 70;
	@Column(name = "damage_bonus", length = 25)
	private String damageBonus = "0";
	@Column(name = "carry_cap")
	private Integer carryCap = 0;
	@Column(name = "reaktion_value")
	private Integer reactionValue = 0;
	@OneToMany(mappedBy = "mutantCharacter")
	private Set<MutantAbilityCharacter> mutantAbilityCharacters;
	@OneToMany(mappedBy = "mutantCharacter")
	private Set<MutantBaseStatCharacter> mutantBaseStatCharacters;
	@ManyToOne
	@JoinColumn(name = "class_id", nullable = false)
	private MutantClass mutantClass;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private RollspelUser rollspelUser;
	@OneToMany(mappedBy = "mutantCharacter")
	private Set<MutantItemCharacter> mutantItemCharacters;
	@OneToMany(mappedBy = "mutantCharacter")
	private Set<MutantSkillCharacter> mutantSkillCharacters;
	@OneToMany(mappedBy = "mutantCharacter")
	private Set<MutantWeaponCharacter> mutantWeaponCharacters;
	@OneToMany(mappedBy = "mutantCharacter")
	private Set<MutantArmorCharacter> mutantArmorCharacters;

	public MutantCharacter() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCreationPoints() {
		return this.creationPoints;
	}

	public void setCreationPoints(Integer creationPoints) {
		this.creationPoints = creationPoints;
	}

	public Integer getFfSpringa() {
		return this.ffSpringa;
	}

	public void setFfSpringa(Integer ffSpringa) {
		this.ffSpringa = ffSpringa;
	}

	public Integer getFfSprint() {
		return this.ffSprint;
	}

	public void setFfSprint(Integer ffSprint) {
		this.ffSprint = ffSprint;
	}

	public Integer getFfStrid() {
		return this.ffStrid;
	}

	public void setFfStrid(Integer ffStrid) {
		this.ffStrid = ffStrid;
	}

	public Integer getHealthPoints() {
		return this.healthPoints;
	}

	public void setHealthPoints(Integer healthPoints) {
		this.healthPoints = healthPoints;
	}

	public String getHome() {
		return this.home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public Integer getInitiativbonus() {
		return this.initiativbonus;
	}

	public void setInitiativbonus(Integer initiativbonus) {
		this.initiativbonus = initiativbonus;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getLooks() {
		return this.looks;
	}

	public void setLooks(String looks) {
		this.looks = looks;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherText() {
		return this.otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public String getPriorOccupation() {
		return this.priorOccupation;
	}

	public void setPriorOccupation(String priorOccupation) {
		this.priorOccupation = priorOccupation;
	}

	public Integer getRumorPoints() {
		return this.rumorPoints;
	}

	public void setRumorPoints(Integer rumorPoints) {
		this.rumorPoints = rumorPoints;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getStatusPoints() {
		return this.statusPoints;
	}

	public void setStatusPoints(Integer statusPoints) {
		this.statusPoints = statusPoints;
	}

	public Integer getTraumaPoints() {
		return this.traumaPoints;
	}

	public void setTraumaPoints(Integer traumaPoints) {
		this.traumaPoints = traumaPoints;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getDamageBonus() {
		return this.damageBonus;
	}

	public void setDamageBonus(String damageBonus) {
		this.damageBonus = damageBonus;
	}

	public Integer getCarryCap() {
		return this.carryCap;
	}

	public void setCarryCap(Integer carryCap) {
		this.carryCap = carryCap;
	}

	public Integer getReactionValue() {
		return this.reactionValue;
	}

	public void setReactionValue(Integer reactionValue) {
		this.reactionValue = reactionValue;
	}

	public Set<MutantAbilityCharacter> getMutantAbilityCharacters() {
		return this.mutantAbilityCharacters;
	}

	public void setMutantAbilityCharacters(Set<MutantAbilityCharacter> mutantAbilityCharacters) {
		this.mutantAbilityCharacters = mutantAbilityCharacters;
	}

	public Set<MutantBaseStatCharacter> getMutantBaseStatCharacters() {
		return this.mutantBaseStatCharacters;
	}

	public void setMutantBaseStatCharacters(Set<MutantBaseStatCharacter> mutantBaseStatCharacters) {
		this.mutantBaseStatCharacters = mutantBaseStatCharacters;
	}

	public MutantClass getMutantClass() {
		return this.mutantClass;
	}

	public void setMutantClass(MutantClass mutantClass) {
		this.mutantClass = mutantClass;
	}

	public RollspelUser getRollspelUser() {
		return this.rollspelUser;
	}

	public void setRollspelUser(RollspelUser rollspelUser) {
		this.rollspelUser = rollspelUser;
	}

	public Set<MutantItemCharacter> getMutantItemCharacters() {
		return this.mutantItemCharacters;
	}

	public void setMutantItemCharacters(Set<MutantItemCharacter> mutantItemCharacters) {
		this.mutantItemCharacters = mutantItemCharacters;
	}

	public Set<MutantSkillCharacter> getMutantSkillCharacters() {
		return this.mutantSkillCharacters;
	}

	public void setMutantSkillCharacters(Set<MutantSkillCharacter> mutantSkillCharacters) {
		this.mutantSkillCharacters = mutantSkillCharacters;
	}

	public Set<MutantWeaponCharacter> getMutantWeaponCharacters() {
		return this.mutantWeaponCharacters;
	}

	public void setMutantWeaponCharacters(Set<MutantWeaponCharacter> mutantWeaponCharacters) {
		this.mutantWeaponCharacters = mutantWeaponCharacters;
	}

	public Set<MutantArmorCharacter> getMutantArmorCharacters() {
		return this.mutantArmorCharacters;
	}

	public void setMutantArmorCharacters(Set<MutantArmorCharacter> mutantArmorCharacters) {
		this.mutantArmorCharacters = mutantArmorCharacters;
	}

	/**
	 * Get the amount of SkapelsePo√§ng spent on Abilities and baseValues for the
	 * skills.
	 *
	 * @return SkapelsePo√§ng sum
	 */
	public Integer getSpSpent() {
		//TODO: Implement
		Integer sum = 0;
		for (MutantAbilityCharacter mac : mutantAbilityCharacters) {
			sum += mac.getCost();
		}
		for (MutantSkillCharacter msc : mutantSkillCharacters) {
			sum += msc.getValueBaseCost();
		}
		return sum;
	}

	/**
	 * Get the amount of ErfarenhetsPo‰ng spent on Skill Training and GE Training
	 *
	 * @return ErfarenhetsPo‰ng sum
	 */
	public Integer getErfSpent() {
		//TODO: Implement
		Integer sum = 0;
		// BASE STATS
		for (MutantBaseStatCharacter mbsc : mutantBaseStatCharacters) {
			sum += mbsc.getValueTrainingCost();
		}
		//SKILLS
		for (MutantSkillCharacter msc : mutantSkillCharacters) {
			sum += msc.getValueTrainingCost();
		}

		return sum;
	}

	/**
	 * Combines the price of all owned wealth.
	 *
	 * @return
	 */
	public Integer getItemCost() {
		Integer sum = 0;
		for (MutantItemCharacter item : mutantItemCharacters) {
			sum += item.getPrice() * item.getAmmount();
		}
		for (MutantWeaponCharacter weapon : mutantWeaponCharacters) {
			sum += weapon.getPrice();
		}
		for (MutantArmorCharacter armor : mutantArmorCharacters) {
			sum += armor.getPrice();
		}
		return sum;
	}

	/**
	 * Return combined item weight measured in gram. Does not account for armor
	 * or weapon that is worn on the body.
	 *
	 * @return combined weight in gram
	 */
	public Integer getItemWeight() {
		Integer sum = 0;
		for (MutantItemCharacter item : mutantItemCharacters) {
			sum += item.getWeight() + item.getAmmount();
		}
		for (MutantWeaponCharacter weapon : mutantWeaponCharacters) {
			sum += weapon.getWeight();
		}
		for (MutantArmorCharacter armor : mutantArmorCharacters) {
			sum += armor.getWeight();
		}
		return sum;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.id);
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
		final MutantCharacter other = (MutantCharacter) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}