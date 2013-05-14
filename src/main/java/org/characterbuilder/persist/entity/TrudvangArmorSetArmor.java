package org.characterbuilder.persist.entity;

import java.io.Serializable;
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
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_armor_set_armor", catalog = "rollspel", schema = "public")
public class TrudvangArmorSetArmor implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_ARMOR_SET_ARMOR_ID_GENERATOR",
	sequenceName = "trudvang_armor_set_armor_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ARMOR_SET_ARMOR_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@JoinColumn(name = "set_id")
	@ManyToOne(optional = false)
	private TrudvangArmorSet setId;
	@JoinColumn(name = "armor_id")
	@ManyToOne(optional = false)
	private TrudvangArmor armorId;

	public TrudvangArmorSetArmor() {
	}

	public TrudvangArmorSetArmor(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrudvangArmorSet getSetId() {
		return setId;
	}

	public void setSetId(TrudvangArmorSet setId) {
		this.setId = setId;
	}

	public TrudvangArmor getArmorId() {
		return armorId;
	}

	public void setArmorId(TrudvangArmor armorId) {
		this.armorId = armorId;
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
		if (!(object instanceof TrudvangArmorSetArmor)) {
			return false;
		}
		TrudvangArmorSetArmor other = (TrudvangArmorSetArmor) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.characterbuilder.persist.entity.TrudvangArmorSetArmor[ id=" + id + " ]";
	}
}
