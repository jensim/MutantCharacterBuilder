package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_base_stat")
public class MutantBaseStat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "MUTANT_BASE_STAT_ID_GENERATOR", sequenceName = "MUTANT_BASE_STAT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_BASE_STAT_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    
    @Column(length = 2147483647)
    private String descripion;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(name = "short_name", nullable = false, length = 25)
    private String shortName;
    
    //bi-directional many-to-one association to MutantBaseStatCharacter
    @OneToMany(mappedBy = "mutantBaseStat")
    private Set<MutantBaseStatCharacter> mutantBaseStatCharacters;
    
    //bi-directional many-to-one association to MutantSkill
    @OneToMany(mappedBy = "mutantBaseStat")
    private Set<MutantSkill> mutantSkills;
    
    /*
     //bi-directional many-to-one association to MutantSkill
     @OneToMany(mappedBy="mutantBaseStat2")
     private Set<MutantSkill> mutantSkills2;
     */

    public MutantBaseStat() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripion() {
        return this.descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
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

    public Set<MutantBaseStatCharacter> getMutantBaseStatCharacters() {
        return this.mutantBaseStatCharacters;
    }

    public void setMutantBaseStatCharacters(Set<MutantBaseStatCharacter> mutantBaseStatCharacters) {
        this.mutantBaseStatCharacters = mutantBaseStatCharacters;
    }

    public Set<MutantSkill> getMutantSkills() {
        return this.mutantSkills;
    }

    public void setMutantSkills(Set<MutantSkill> mutantSkills) {
        this.mutantSkills = mutantSkills;
    }
    /*
     public Set<MutantSkill> getMutantSkills2() {
     return this.mutantSkills2;
     }

     public void setMutantSkills2(Set<MutantSkill> mutantSkills2) {
     this.mutantSkills2 = mutantSkills2;
     }
     */

    public String toString() {
        return new String(name);
    }
}