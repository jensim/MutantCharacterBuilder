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
@Table(name = "mutant_armor_bodypart")
public class MutantArmorBodypart implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_ARMOR_BODYPART_ID_GENERATOR", sequenceName = "MUTANT_ARMOR_BODYPART_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_ARMOR_BODYPART_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(length = 2147483647, nullable = false)
	private String description = "";
	@Column(nullable = false, length = 50)
	private String name;
	@OneToMany(mappedBy = "mutantArmorBodypart")
	private Set<MutantArmor> mutantArmors;
	@OneToMany(mappedBy = "mutantArmorBodypart")
	private Set<MutantArmorCharacter> mutantArmorCharacters;

	public MutantArmorBodypart() {
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

	public Set<MutantArmor> getMutantArmors() {
		return this.mutantArmors;
	}

	public void setMutantArmors(Set<MutantArmor> mutantArmors) {
		this.mutantArmors = mutantArmors;
	}

	public Set<MutantArmorCharacter> getMutantArmorCharacters() {
		return this.mutantArmorCharacters;
	}

	public void setMutantArmorCharacters(Set<MutantArmorCharacter> mutantArmorCharacters) {
		this.mutantArmorCharacters = mutantArmorCharacters;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 23 * hash + Objects.hashCode(this.id);
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
		final MutantArmorBodypart other = (MutantArmorBodypart) obj;
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