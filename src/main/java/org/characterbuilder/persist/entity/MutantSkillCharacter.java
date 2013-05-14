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
@Table(name = "mutant_skill_character")
public class MutantSkillCharacter implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_SKILL_CHARACTER_ID_GENERATOR", sequenceName = "MUTANT_SKILL_CHARACTER_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_SKILL_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "base_bonus", nullable = false)
	private Integer baseBonus = 0;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(length = 2147483647, nullable=false)
	private String description = "";
	@Column(name = "base_value", nullable = false)
	private Integer baseValue = 0;
	@Column(name = "natural_skill", nullable = false)
	private Boolean naturalSkill = false;
	@Column(name = "trained_value", nullable = false)
	private Integer trainedValue = 0;
	@Column(name = "creation_trained", nullable = false)
	private Boolean creationTrained = false;
	//bi-directional many-to-one association to MutantAbilityCharacter
	@OneToMany(mappedBy = "mutantSkillCharacter")
	private Set<MutantAbilityCharacter> mutantAbilityCharacters;
	//bi-directional many-to-one association to MutantBaseStatCharacter
	@ManyToOne
	@JoinColumn(name = "base_stat_char_id", nullable = false)
	private MutantBaseStatCharacter mutantBaseStatCharacter;
	//bi-directional many-to-one association to MutantCharacter
	@ManyToOne
	@JoinColumn(name = "character_id", nullable = false)
	private MutantCharacter mutantCharacter;

	public MutantSkillCharacter() {
	}

	public MutantSkillCharacter(MutantCharacter mutantCharacter, MutantSkill mutantSkill) {
		this.mutantCharacter = mutantCharacter;
		this.name = mutantSkill.getName();
		this.description = mutantSkill.getDescription();
		this.naturalSkill = mutantSkill.getNaturalSkill();

		for (MutantBaseStatCharacter baseStat : mutantCharacter.getMutantBaseStatCharacters()) {
			if (baseStat.getMutantBaseStat().getId() == mutantSkill.getMutantBaseStat().getId()) {
				this.mutantBaseStatCharacter = baseStat;
				break;
			}
		}
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBaseBonus() {
		return this.baseBonus;
	}

	public void setBaseBonus(Integer baseBonus) {
		this.baseBonus = baseBonus;
	}

	public Integer getBaseValue() {
		return this.baseValue;
	}

	public void setBaseValue(Integer baseValue) {
		this.baseValue = baseValue;
	}

	public Boolean getNaturalSkill() {
		return this.naturalSkill;
	}

	public void setNaturalSkill(Boolean naturalSkill) {
		this.naturalSkill = naturalSkill;
	}

	/**
	 * This value affects the training cost. If the skill was not natural or part
	 * of the characters prior profession, it costs extra experience to train it.
	 *
	 * @return creationTrained
	 */
	public Boolean getCreationTrained() {
		return this.creationTrained;
	}

	/**
	 * This value affects the training cost. If the skill was not natural or part
	 * of the characters prior profession, it costs extra experience to train it.
	 *
	 * @param creationTrained
	 */
	public void setCreationTrained(Boolean creationTrained) {
		this.creationTrained = creationTrained;
	}

	public Integer getTrainedValue() {
		return this.trainedValue;
	}

	public void setTrainedValue(Integer trainedValue) {
		this.trainedValue = trainedValue;
	}

	public Set<MutantAbilityCharacter> getMutantAbilityCharacters() {
		return this.mutantAbilityCharacters;
	}

	public void setMutantAbilityCharacters(Set<MutantAbilityCharacter> mutantAbilityCharacters) {
		this.mutantAbilityCharacters = mutantAbilityCharacters;
	}

	public MutantBaseStatCharacter getMutantBaseStatCharacter() {
		return this.mutantBaseStatCharacter;
	}

	public void setMutantBaseStatCharacter(MutantBaseStatCharacter mutantBaseStatCharacter) {
		this.mutantBaseStatCharacter = mutantBaseStatCharacter;
	}

	public MutantCharacter getMutantCharacter() {
		return this.mutantCharacter;
	}

	public void setMutantCharacter(MutantCharacter mutantCharacter) {
		this.mutantCharacter = mutantCharacter;
	}

	/**
	 * Get the sum % of the skill, gained from base stats bonuses and training
	 *
	 * @return sum of skill %
	 */
	public Integer getTotal() {
		Integer baseFromStat = mutantBaseStatCharacter.getValueTotal() * (baseBonus + baseValue + 1);
		Integer total = trainedValue + baseFromStat;
		return total;
	}

	/**
	 * Calculate the GE cost for the skill
	 *
	 * @return
	 */
	public Integer getValueBaseCost() {
		if (naturalSkill) {
			return baseValue;
		}
		return baseValue * 2;
	}

	/**
	 * Calculate the Erf cost for training a skill
	 *
	 * @return Erf cost
	 */
	public Integer getValueTrainingCost() {
		//TODO: implement
		Integer sum = 0;
		Integer total = getTotal();
		Integer baseFromStat = mutantBaseStatCharacter.getValueTotal() * (baseBonus + baseValue);
		if (trainedValue > 0) {
			if (total > 100) {
				if (baseFromStat > 100) {
					//all points bought are above 100
					sum += trainedValue * 3;
				} else if (baseFromStat > 85) {
					//some points are trained between 85 and 100 as well as above 100
					sum += (100 - baseFromStat) * 2;
					sum += (total - 100) * 3;
				} else {
					//points are trained from under 85 to above 100
					sum += 85 - baseFromStat;
					sum += 30; //Cost for training 85 -> 100
					sum += (total - 100) * 3;
				}
			} else if (total > 85) {
				if (baseFromStat > 85) {
					//all points trained are between 85 and 100
					sum += trainedValue * 2;
				} else {
					sum += 85 - baseFromStat;
					sum += (total - 85) * 2;
				}
			} else {
				sum += trainedValue;
			}
		}

		//COST FOR LEARNING A SKILL AFTER CREATION
		if (!creationTrained) {
			for (MutantBaseStatCharacter mbsc : mutantCharacter.getMutantBaseStatCharacters()) {
				if (mbsc.getMutantBaseStat().getShortName().equalsIgnoreCase("INT")) {
					Integer intellect = mbsc.getValueTotal();
					if (intellect >= 12) {
						sum += 2;
					} else {
						sum += 14 - intellect;
					}
				}
			}
		}
		return sum;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.id);
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
		final MutantSkillCharacter other = (MutantSkillCharacter) obj;
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