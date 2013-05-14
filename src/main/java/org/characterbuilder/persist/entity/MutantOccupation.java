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
@Table(name = "mutant_occupation")
public class MutantOccupation implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_OCCUPATION_ID_GENERATOR", sequenceName = "MUTANT_OCCUPATION_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_OCCUPATION_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(length = 2147483647, nullable = false)
	private String description = "";
	@Column(nullable = false, length = 50)
	private String name;
	@Column(name = "start_capital", nullable = false)
	private Integer startCapital;
	@OneToMany(mappedBy = "mutantOccupation")
	private Set<MutantSkillOccupation> mutantSkillOccupations;

	public MutantOccupation() {
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

	public Integer getStartCapital() {
		return this.startCapital;
	}

	public void setStartCapital(Integer startCapital) {
		this.startCapital = startCapital;
	}

	public Set<MutantSkillOccupation> getMutantSkillOccupations() {
		return this.mutantSkillOccupations;
	}

	public void setMutantSkillOccupations(Set<MutantSkillOccupation> mutantSkillOccupations) {
		this.mutantSkillOccupations = mutantSkillOccupations;
	}

	@Override
	public int hashCode() {
		int hash = 7;
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
		final MutantOccupation other = (MutantOccupation) obj;
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