package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_ability_character")
public class MutantAbilityCharacter implements Serializable {

    @Id
    @SequenceGenerator(name = "MUTANT_ABILITY_CHARACTER_ID_GENERATOR", sequenceName = "MUTANT_ABILITY_CHARACTER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_ABILITY_CHARACTER_ID_GENERATOR")
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
    
    @Column(nullable = false, length = 2147483647)
    private String name;
    
    @Column(length = 2147483647)
    private String reach;
    
    //bi-directional many-to-one association to MutantCharacter
    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private MutantCharacter mutantCharacter;
    
    //bi-directional many-to-one association to MutantSkillCharacter
    @ManyToOne
    @JoinColumn(name = "skill_char_id")
    private MutantSkillCharacter mutantSkillCharacter;

    public MutantAbilityCharacter() {
    }

    /*public MutantAbilityCharacter(MutantAbility mutantAbility, MutantCharacter mutantCharacter) {
        this.mutantCharacter = mutantCharacter;
        this.activation = mutantAbility.getActivation();
        this.cost = mutantAbility.getCost();
        this.description = mutantAbility.getDescription();
        this.duration = mutantAbility.getDuration();
        this.effect = mutantAbility.getEffect();
        this.name = mutantAbility.getName();
        this.reach = mutantAbility.getReach();
        if (mutantAbility.getMutantSkill() != null) {
            this.mutantSkillCharacter = new MutantSkillCharacter(mutantCharacter, mutantAbility.getMutantSkill());
        }
    }*/

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

    public MutantCharacter getMutantCharacter() {
        return this.mutantCharacter;
    }

    public void setMutantCharacter(MutantCharacter mutantCharacter) {
        this.mutantCharacter = mutantCharacter;
    }

    public MutantSkillCharacter getMutantSkillCharacter() {
        return this.mutantSkillCharacter;
    }

    public void setMutantSkillCharacter(MutantSkillCharacter mutantSkillCharacter) {
        this.mutantSkillCharacter = mutantSkillCharacter;
    }

	@Override
	public int hashCode() {
		int hash = 7;
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
		final MutantAbilityCharacter other = (MutantAbilityCharacter) obj;
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