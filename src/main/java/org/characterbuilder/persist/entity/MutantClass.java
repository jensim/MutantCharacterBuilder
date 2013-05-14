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
@Table(name = "mutant_class")
public class MutantClass implements Serializable {

	@Id
	@SequenceGenerator(name = "MUTANT_CLASS_ID_GENERATOR",
	sequenceName = "MUTANT_CLASS_ID_SEQ",
	allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "MUTANT_CLASS_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "base_stat_points", nullable = false)
	private Integer baseStatPoints;
	@Column(length = 2147483647, nullable = false)
	private String description = "";
	@Column(nullable = false, length = 50)
	private String name;
	@Column(name = "short_name", nullable = false, length = 25)
	private String shortName;
	@OneToMany(mappedBy = "mutantClass")
	private Set<MutantAbility> mutantAbilities;
	@OneToMany(mappedBy = "mutantClass")
	private Set<MutantCharacter> mutantCharacters;

	public MutantClass() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBaseStatPoints() {
		return this.baseStatPoints;
	}

	public void setBaseStatPoints(Integer baseStatPoints) {
		this.baseStatPoints = baseStatPoints;
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

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Set<MutantAbility> getMutantAbilities() {
		return this.mutantAbilities;
	}

	public void setMutantAbilities(Set<MutantAbility> mutantAbilities) {
		this.mutantAbilities = mutantAbilities;
	}

	public Set<MutantCharacter> getMutantCharacters() {
		return this.mutantCharacters;
	}

	public void setMutantCharacters(Set<MutantCharacter> mutantCharacters) {
		this.mutantCharacters = mutantCharacters;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 13 * hash + Objects.hashCode(this.id);
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
		final MutantClass other = (MutantClass) obj;
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