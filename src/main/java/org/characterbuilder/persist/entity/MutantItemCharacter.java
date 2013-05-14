package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "mutant_item_character")
public class MutantItemCharacter implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "MUTANT_ITEM_CHARACTER_ID_GENERATOR", sequenceName = "MUTANT_ITEM_CHARACTER_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUTANT_ITEM_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	private Integer ammount = 1;
	@Column(nullable = false, length = 50)
	private String name;
	private Integer price = 0;
	private Integer weight = 0;
	@ManyToOne
	@JoinColumn(name = "character_id", nullable = false)
	private MutantCharacter mutantCharacter;

	public MutantItemCharacter() {
	}

	public MutantItemCharacter(MutantCharacter mutantCharacter, MutantItem item) {
		this.mutantCharacter = mutantCharacter;
		this.name = item.getName();
		this.price = item.getPrice();
		this.weight = item.getWeight();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAmmount() {
		return this.ammount;
	}

	public void setAmmount(Integer ammount) {
		this.ammount = ammount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public MutantCharacter getMutantCharacter() {
		return this.mutantCharacter;
	}

	public void setMutantCharacter(MutantCharacter mutantCharacter) {
		this.mutantCharacter = mutantCharacter;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 47 * hash + Objects.hashCode(this.id);
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
		final MutantItemCharacter other = (MutantItemCharacter) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MutantItemCharacter{" + "name=" + name + '}';
	}
}