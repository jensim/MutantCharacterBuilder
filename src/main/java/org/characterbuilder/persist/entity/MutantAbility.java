package org.characterbuilder.persist.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_ability")
public class MutantAbility implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_ABILITY_ID_GENERATOR", sequenceName = "MUTANT_ABILITY_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_ABILITY_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(length = 2147483647)
	private String activation;
	@Column(nullable = false)
	private Integer cost;
	@Column(length = 2147483647, nullable=false)
	private String description = "";
	@Column(length = 2147483647)
	private String duration;
	@Column(length = 2147483647)
	private String effect;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(length = 2147483647)
	private String reach;
	//bi-directional many-to-one association to MutantAbilityGroup
	@ManyToOne()
	@JoinColumn(name = "ability_group_id")
	private MutantAbilityGroup mutantAbilityGroup;
	//bi-directional many-to-one association to MutantClass
	@ManyToOne()
	@JoinColumn(name = "class_id", nullable = false)
	private MutantClass mutantClass;
	//bi-directional many-to-one association to MutantSkill
	@ManyToOne()
	@JoinColumn(name = "skill_id")
	private MutantSkill mutantSkill;

	public MutantAbility() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivation() {
		return this.activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public Integer getCost() {
		return this.cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEffect() {
		return this.effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReach() {
		return this.reach;
	}

	public void setReach(String reach) {
		this.reach = reach;
	}

	public MutantAbilityGroup getMutantAbilityGroup() {
		return this.mutantAbilityGroup;
	}

	public void setMutantAbilityGroup(MutantAbilityGroup mutantAbilityGroup) {
		this.mutantAbilityGroup = mutantAbilityGroup;
	}

	public MutantClass getMutantClass() {
		return this.mutantClass;
	}

	public void setMutantClass(MutantClass mutantClass) {
		this.mutantClass = mutantClass;
	}

	public MutantSkill getMutantSkill() {
		return this.mutantSkill;
	}

	public void setMutantSkill(MutantSkill mutantSkill) {
		this.mutantSkill = mutantSkill;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
		final MutantAbility other = (MutantAbility) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}