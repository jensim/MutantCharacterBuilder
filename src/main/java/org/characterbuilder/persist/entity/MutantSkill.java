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
@Table(name = "mutant_skill")
public class MutantSkill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "MUTANT_SKILL_ID_GENERATOR", sequenceName = "MUTANT_SKILL_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_SKILL_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(length = 2147483647, nullable=false)
    private String description = "";
    @Column(nullable = false, length = 50)
    private String name;
    @Column(name = "natural_skill", nullable = false)
    private Boolean naturalSkill = false;
    //bi-directional many-to-one association to MutantAbility
    @OneToMany(mappedBy = "mutantSkill")
    private Set<MutantAbility> mutantAbilities;
    //bi-directional many-to-one association to MutantBaseStat
    @ManyToOne()
    @JoinColumn(name = "base_stat_id", nullable = false)
    private MutantBaseStat mutantBaseStat;
    /*
     //bi-directional many-to-one association to MutantBaseStat
     @ManyToOne
     @JoinColumn(name="base_stat_two_id")
     private MutantBaseStat mutantBaseStat2;

     //bi-directional many-to-one association to MutantSkillCharacter
     @OneToMany(mappedBy="mutantSkill")
     private Set<MutantSkillCharacter> mutantSkillCharacters;
     */
    //bi-directional many-to-one association to MutantSkillOccupation
    @OneToMany(mappedBy = "mutantSkill")
    private Set<MutantSkillOccupation> mutantSkillOccupations;

    public MutantSkill() {
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

    public Boolean getNaturalSkill() {
        return this.naturalSkill;
    }

    public void setNaturalSkill(Boolean naturalSkill) {
        this.naturalSkill = naturalSkill;
    }

    public Set<MutantAbility> getMutantAbilities() {
        return this.mutantAbilities;
    }

    public void setMutantAbilities(Set<MutantAbility> mutantAbilities) {
        this.mutantAbilities = mutantAbilities;
    }

    public MutantBaseStat getMutantBaseStat() {
        return this.mutantBaseStat;
    }

    public void setMutantBaseStat(MutantBaseStat mutantBaseStat) {
        this.mutantBaseStat = mutantBaseStat;
    }
    /*
     public MutantBaseStat getMutantBaseStat2() {
     return this.mutantBaseStat2;
     }

     public void setMutantBaseStat2(MutantBaseStat mutantBaseStat2) {
     this.mutantBaseStat2 = mutantBaseStat2;
     }

     public Set<MutantSkillCharacter> getMutantSkillCharacters() {
     return this.mutantSkillCharacters;
     }

     public void setMutantSkillCharacters(Set<MutantSkillCharacter> mutantSkillCharacters) {
     this.mutantSkillCharacters = mutantSkillCharacters;
     }
     */

    public Set<MutantSkillOccupation> getMutantSkillOccupations() {
        return this.mutantSkillOccupations;
    }

    public void setMutantSkillOccupations(Set<MutantSkillOccupation> mutantSkillOccupations) {
        this.mutantSkillOccupations = mutantSkillOccupations;
    }

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 83 * hash + Objects.hashCode(this.id);
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
		final MutantSkill other = (MutantSkill) obj;
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