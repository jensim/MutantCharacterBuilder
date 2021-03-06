package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TrudvangVitnerPowerlevelCharacter
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name="trudvang_vitner_powerlevel_character", catalog = "rollspel", schema = "public")
public class TrudvangVitnerPowerlevelCharacter implements Serializable {
	
	@Id
	@SequenceGenerator(name = "TRUDVANG_VITNER_POWERLEVEL_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_vitner_powerlevel_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_VITNER_POWERLEVEL_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(nullable=false,name="cost")
	private Integer cost;
	@Column(nullable=false,name="description")
	private String description;
	@ManyToOne(optional=false,cascade= CascadeType.ALL)
	@JoinColumn(name="vitner_id")
	private TrudvangVitnerCharacter vitner;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TrudvangVitnerCharacter getVitner() {
		return vitner;
	}

	public void setVitner(TrudvangVitnerCharacter vitner) {
		this.vitner = vitner;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + Objects.hashCode(this.id);
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
		final TrudvangVitnerPowerlevelCharacter other = (TrudvangVitnerPowerlevelCharacter) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
