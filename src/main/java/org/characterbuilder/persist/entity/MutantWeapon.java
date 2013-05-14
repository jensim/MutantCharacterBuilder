package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_weapon")
public class MutantWeapon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "MUTANT_WEAPON_ID_GENERATOR", sequenceName = "MUTANT_WEAPON_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_WEAPON_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(nullable = false)
    private Integer dependability = 100;
    @Column(length = 2147483647, nullable=false)
    private String description = "";
    @Column(nullable = false)
    private Integer durability = 0;
    @Column(nullable = false, length = 50)
    private String grip = "1H";
    @Column(nullable = false)
    private Integer initiative = 0;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String damage;
    @Column(nullable = false)
    private Integer penetration = 0;
    @Column(nullable = false)
    private Integer reach = 0;
    @Column(nullable = false, name = "sty_krav")
    private Integer styKrav = 0;
    @Column(nullable = false)
    private Integer weight = 0;
    @Column(nullable = false)
    private Integer price = 0;

    public MutantWeapon() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDependability() {
        return this.dependability;
    }

    public void setDependability(Integer dependability) {
        this.dependability = dependability;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurability() {
        return this.durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    public String getGrip() {
        return this.grip;
    }

    public void setGrip(String grip) {
        this.grip = grip;
    }

    public Integer getInitiative() {
        return this.initiative;
    }

    public void setInitiative(Integer initiative) {
        this.initiative = initiative;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDamage() {
        return this.damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public Integer getPenetration() {
        return this.penetration;
    }

    public void setPenetration(Integer penetration) {
        this.penetration = penetration;
    }

    public Integer getReach() {
        return this.reach;
    }

    public void setReach(Integer reach) {
        this.reach = reach;
    }

    public Integer getStyKrav() {
        return this.styKrav;
    }

    public void setStyKrav(Integer styKrav) {
        this.styKrav = styKrav;
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

	@Override
	public int hashCode() {
		int hash = 7;
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
		final MutantWeapon other = (MutantWeapon) obj;
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