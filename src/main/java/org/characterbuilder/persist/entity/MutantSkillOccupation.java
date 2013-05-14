package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_skill_occupation")
public class MutantSkillOccupation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "MUTANT_SKILL_OCCUPATION_ID_GENERATOR", sequenceName = "MUTANT_SKILL_OCCUPATION_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_SKILL_OCCUPATION_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(length = 2147483647)
    private String notes;
    //bi-directional many-to-one association to MutantOccupation
    @ManyToOne()
    @JoinColumn(name = "occupation_id", nullable = false)
    private MutantOccupation mutantOccupation;
    //bi-directional many-to-one association to MutantSkill
    @ManyToOne()
    @JoinColumn(name = "skill_id", nullable = false)
    private MutantSkill mutantSkill;

    public MutantSkillOccupation() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public MutantOccupation getMutantOccupation() {
        return this.mutantOccupation;
    }

    public void setMutantOccupation(MutantOccupation mutantOccupation) {
        this.mutantOccupation = mutantOccupation;
    }

    public MutantSkill getMutantSkill() {
        return this.mutantSkill;
    }

    public void setMutantSkill(MutantSkill mutantSkill) {
        this.mutantSkill = mutantSkill;
    }

	@Override
	public String toString() {
		return "MutantSkillOccupation{" + "mutantOccupation=" + mutantOccupation + ", mutantSkill=" + mutantSkill + '}';
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.id);
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
		final MutantSkillOccupation other = (MutantSkillOccupation) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}