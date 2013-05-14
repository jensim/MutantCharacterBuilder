package org.characterbuilder.persist.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_armor_character")
public class MutantArmorCharacter implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_ARMOR_CHARACTER_ID_GENERATOR", sequenceName = "MUTANT_ARMOR_CHARACTER_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_ARMOR_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(name = "abs", nullable = false)
	private Integer absorb;
	@Column(name = "beg", nullable = false)
	private Integer beg;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(length = 2147483647, nullable = false)
	private String description = "";
	@Column(nullable = false)
	private Integer weight;
	@Column(nullable = false)
	private Integer price = 0;
	@ManyToOne()
	@JoinColumn(name = "character_id", nullable = false)
	private MutantCharacter mutantCharacter;
	@ManyToOne()
	@JoinColumn(name = "body_part_id", nullable = false)
	private MutantArmorBodypart mutantArmorBodypart;

	public MutantArmorCharacter() {
	}

	public MutantArmorCharacter(MutantCharacter character, MutantArmor armor) {
		this.mutantCharacter = character;
		this.absorb = armor.getAbs();
		this.beg = armor.getBeg();
		this.name = armor.getName();
		this.weight = armor.getWeight();
		this.mutantArmorBodypart = armor.getMutantArmorBodypart();
		this.description = armor.getDescription();
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

	public MutantCharacter getMutantCharacter() {
		return this.mutantCharacter;
	}

	public void setMutantCharacter(MutantCharacter mutantCharacter) {
		this.mutantCharacter = mutantCharacter;
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
	public String toString() {
		return new String(name);
	}
}