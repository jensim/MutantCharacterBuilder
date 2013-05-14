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
@Table(name = "mutant_ability_group")
public class MutantAbilityGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_ABILITY_GROUP_ID_GENERATOR", sequenceName = "MUTANT_ABILITY_GROUP_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_ABILITY_GROUP_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(nullable = false)
	private Integer combineable;
	@Column(length = 2147483647, nullable = false)
	private String description = "";
	@Column(nullable = false, length = 2147483647)
	private String name;
	@OneToMany(mappedBy = "mutantAbilityGroup")
	private Set<MutantAbility> mutantAbilities;

	public MutantAbilityGroup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCombineable() {
		return this.combineable;
	}

	public void setCombineable(Integer combineable) {
		this.combineable = combineable;
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

	public Set<MutantAbility> getMutantAbilities() {
		return this.mutantAbilities;
	}

	public void setMutantAbilities(Set<MutantAbility> mutantAbilities) {
		this.mutantAbilities = mutantAbilities;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 37 * hash + Objects.hashCode(this.id);
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
		final MutantAbilityGroup other = (MutantAbilityGroup) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MutantAbilityGroup{" + "name=" + name + '}';
	}
}