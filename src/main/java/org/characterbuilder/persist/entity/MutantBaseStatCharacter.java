package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_base_stat_character")
public class MutantBaseStatCharacter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "MUTANT_BASE_STAT_CHARACTER_ID_GENERATOR", sequenceName = "MUTANT_BASE_STAT_CHARACTER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_BASE_STAT_CHARACTER_ID_GENERATOR")
    @Column(name="id", unique = true, nullable = false)
    private Integer id;
    @Column(name="value", nullable = false)
    private Integer valuee = 5;
    @Column(name = "value_bonus", nullable = false)
    private Integer valueBonus = 0;
    @Column(name = "value_trained")
    private Integer valueTrained = 0;
    //bi-directional many-to-one association to MutantBaseStat
    @ManyToOne()
    @JoinColumn(name = "base_stat_id", nullable = false)
    private MutantBaseStat mutantBaseStat;
    //bi-directional many-to-one association to MutantCharacter
    @ManyToOne()
    @JoinColumn(name = "character_id", nullable = false)
    private MutantCharacter mutantCharacter;
    //bi-directional many-to-one association to MutantSkillCharacter
    @OneToMany(mappedBy = "mutantBaseStatCharacter")
    private Set<MutantSkillCharacter> mutantSkillCharacters;

    public MutantBaseStatCharacter() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return this.valuee;
    }

    public void setValue(Integer value) {
        this.valuee = value;
    }

    public Integer getValueBonus() {
        return this.valueBonus;
    }

    public void setValueBonus(Integer valueBonus) {
        this.valueBonus = valueBonus;
    }

    public Integer getValueTrained() {
        return this.valueTrained;
    }

    public void setValueTrained(Integer valueTrained) {
        this.valueTrained = valueTrained;
    }

    public MutantBaseStat getMutantBaseStat() {
        return this.mutantBaseStat;
    }

    public void setMutantBaseStat(MutantBaseStat mutantBaseStat) {
        this.mutantBaseStat = mutantBaseStat;
    }

    public MutantCharacter getMutantCharacter() {
        return this.mutantCharacter;
    }

    public void setMutantCharacter(MutantCharacter mutantCharacter) {
        this.mutantCharacter = mutantCharacter;
    }

    public Set<MutantSkillCharacter> getMutantSkillCharacters() {
        return this.mutantSkillCharacters;
    }

    public void setMutantSkillCharacters(Set<MutantSkillCharacter> mutantSkillCharacters) {
        this.mutantSkillCharacters = mutantSkillCharacters;
    }

    /**
     * Total BaseStat value for a BaseStat and character
     *
     * @return total value of a base stat
     */
    public Integer getValueTotal() {
        return new Integer(valuee + valueBonus + valueTrained);
    }

    /**
     * Get the ERF cost for training the BaseStat
     *
     * @return Erf Cost
     */
    public Integer getValueTrainingCost() {
        //TODO: Implement
        Integer sum = 0;
        for (int i = 1; i <= valueTrained; ++i) {
            sum += (valuee + valueTrained) * 2;
        }
        return sum;
    }

    public String toString() {
        return new String(mutantBaseStat.getName());
    }
}