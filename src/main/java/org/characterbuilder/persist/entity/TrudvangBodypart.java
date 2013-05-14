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
@Table(name = "trudvang_bodypart", catalog = "rollspel", schema = "public")
public class TrudvangBodypart implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_BODYPART_ID_GENERATOR",
	sequenceName = "trudvang_bodypart_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_BODYPART_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bodypartId")
	private List<TrudvangArmor> trudvangArmorList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bodypartId")
	private List<TrudvangArmorCharacter> trudvangArmorCharacterList;

	public TrudvangBodypart() {
	}

	public TrudvangBodypart(Integer id) {
		this.id = id;
	}

	public TrudvangBodypart(Integer id, String name) {
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

	public List<TrudvangArmor> getTrudvangArmorList() {
		return trudvangArmorList;
	}

	public void setTrudvangArmorList(List<TrudvangArmor> trudvangArmorList) {
		this.trudvangArmorList = trudvangArmorList;
	}

	public List<TrudvangArmorCharacter> getTrudvangArmorCharacterList() {
		return trudvangArmorCharacterList;
	}

	public void setTrudvangArmorCharacterList(List<TrudvangArmorCharacter> trudvangArmorCharacterList) {
		this.trudvangArmorCharacterList = trudvangArmorCharacterList;
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
		if (!(object instanceof TrudvangBodypart)) {
			return false;
		}
		TrudvangBodypart other = (TrudvangBodypart) object;
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
