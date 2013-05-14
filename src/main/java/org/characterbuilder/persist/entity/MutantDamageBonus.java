package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_damage_bonus")
public class MutantDamageBonus implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_DAMAGE_BONUS_ID_GENERATOR", sequenceName = "MUTANT_DAMAGE_BONUS_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_DAMAGE_BONUS_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "dice_count", nullable = false)
	private Integer diceCount = 0;
	@Column(name = "dice_size", nullable = false)
	private Integer diceSize = 0;
	@Column(nullable = false)
	private Boolean positive = true;
	@Column(name = "static_damage", nullable = false)
	private Integer staticDamage = 0;
	@Column(name = "\"STY_STO\"", nullable = false)
	private Integer stySto = 0;

	public MutantDamageBonus() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDiceCount() {
		return this.diceCount;
	}

	public void setDiceCount(Integer diceCount) {
		this.diceCount = diceCount;
	}

	public Integer getDiceSize() {
		return this.diceSize;
	}

	public void setDiceSize(Integer diceSize) {
		this.diceSize = diceSize;
	}

	public Boolean getPositive() {
		return this.positive;
	}

	public void setPositive(Boolean positive) {
		this.positive = positive;
	}

	public Integer getStaticDamage() {
		return this.staticDamage;
	}

	public void setStaticDamage(Integer staticDamage) {
		this.staticDamage = staticDamage;
	}

	public Integer getStySto() {
		return this.stySto;
	}

	public void setStySto(Integer stySto) {
		this.stySto = stySto;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + Objects.hashCode(this.id);
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
		final MutantDamageBonus other = (MutantDamageBonus) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if (diceCount == 0) {
			return staticDamage.toString();
		}
		StringBuilder dmg = new StringBuilder();
		dmg.append(diceCount);
		dmg.append("T");
		dmg.append(diceSize);
		if (staticDamage > 0) {
			dmg.append(" +");
			dmg.append(staticDamage);
		} else if (staticDamage < 0) {
			dmg.append(staticDamage);
		}
		return dmg.toString();
	}
}