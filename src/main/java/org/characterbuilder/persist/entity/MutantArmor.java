package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_armor")
public class MutantArmor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "MUTANT_ARMOR_ID_GENERATOR", sequenceName = "MUTANT_ARMOR_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_ARMOR_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    
    @Column(name="abs", nullable = false)
    private Integer absorb = 1;
    
    @Column(name="beg", nullable = false)
    private Integer beg = 0;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(length = 2147483647, nullable=false)
    private String description = "";
    
    @Column(nullable = false)
    private Integer weight = 0;
    
    @Column(nullable = false)
    private Integer price = 0;
    
    //bi-directional many-to-one association to MutantArmorBodypart
    @ManyToOne()
    @JoinColumn(name = "body_part_id", nullable = false)
    private MutantArmorBodypart mutantArmorBodypart;

    public MutantArmor() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbs() {
        return this.absorb;
    }

    public void setAbs(Integer abs) {
        this.absorb = abs;
    }

    public Integer getBeg() {
        return this.beg;
    }

    public void setBeg(Integer beg) {
        this.beg = beg;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public MutantArmorBodypart getMutantArmorBodypart() {
        return this.mutantArmorBodypart;
    }

    public void setMutantArmorBodypart(MutantArmorBodypart mutantArmorBodypart) {
        this.mutantArmorBodypart = mutantArmorBodypart;
    }

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 11 * hash + Objects.hashCode(this.id);
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
		final MutantArmor other = (MutantArmor) obj;
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