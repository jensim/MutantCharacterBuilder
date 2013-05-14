package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_mod_type", catalog = "rollspel", schema = "public")
public class TrudvangModType implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_MOD_TYPE_ID_GENERATOR",
	sequenceName = "trudvang_mod_type_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_MOD_TYPE_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description="";
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "typeId")
	private List<TrudvangMod> trudvangModList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "typeId")
	private List<TrudvangModCharacter> trudvangModCharacterList;

	public TrudvangModType() {
	}

	public TrudvangModType(Integer id) {
		this.id = id;
	}

	public TrudvangModType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

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

	public List<TrudvangMod> getTrudvangModList() {
		return trudvangModList;
	}

	public void setTrudvangModList(List<TrudvangMod> trudvangModList) {
		this.trudvangModList = trudvangModList;
	}

	public List<TrudvangModCharacter> getTrudvangModCharacterList() {
		return trudvangModCharacterList;
	}

	public void setTrudvangModCharacterList(List<TrudvangModCharacter> trudvangModCharacterList) {
		this.trudvangModCharacterList = trudvangModCharacterList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TrudvangModType)) {
			return false;
		}
		TrudvangModType other = (TrudvangModType) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
