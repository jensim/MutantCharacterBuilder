package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TrudvangVitnerType
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "trudvang_vitner_type", catalog = "rollspel", schema = "public")
public class TrudvangVitnerType implements Serializable {
	
	@Id
	@SequenceGenerator(name = "TRUDVANG_VITNER_TYPE_ID_GENERATOR",
	sequenceName = "trudvang_vitner_type_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_VITNER_TYPE_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=true)
	private String description ="";
	@OneToMany(mappedBy = "vitnerType")
	private List<TrudvangVitner> vitners;
	@OneToMany(mappedBy = "vitnerType")
	private List<TrudvangVitnerCharacter> charVitners;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TrudvangVitner> getVitners() {
		return vitners;
	}

	public void setVitners(List<TrudvangVitner> vitners) {
		this.vitners = vitners;
	}

	public List<TrudvangVitnerCharacter> getCharVitners() {
		return charVitners;
	}

	public void setCharVitners(List<TrudvangVitnerCharacter> charVitners) {
		this.charVitners = charVitners;
	}

	@Override
	public int hashCode() {
		int hash = 5;
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
		final TrudvangVitnerType other = (TrudvangVitnerType) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
