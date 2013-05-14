package org.characterbuilder.persist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_armor_mod", catalog = "rollspel", schema = "public")
public class TrudvangArmorMod implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_ARMOR_MOD_ID_GENERATOR",
	sequenceName = "trudvang_armor_mod_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_ARMOR_MOD_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "top_rv")
	private Integer topRv;
	@Basic(optional = false)
	private Integer bm;
	@Basic(optional = false)
	private Integer fm;
	@Basic(optional = false)
	private Integer im;
	@Basic(optional = false)
	private Integer rm;

	public TrudvangArmorMod() {
	}

	public TrudvangArmorMod(Integer id) {
		this.id = id;
	}

	public TrudvangArmorMod(Integer id, Integer topRv, Integer bm, Integer fm,
			  Integer im, Integer rm) {
		this.id = id;
		this.topRv = topRv;
		this.bm = bm;
		this.fm = fm;
		this.im = im;
		this.rm = rm;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getTopRv() {
		return topRv;
	}

	public void setTopRv(int topRv) {
		this.topRv = topRv;
	}

	public int getBm() {
		return bm;
	}

	public void setBm(int bm) {
		this.bm = bm;
	}

	public int getFm() {
		return fm;
	}

	public void setFm(int fm) {
		this.fm = fm;
	}

	public int getIm() {
		return im;
	}

	public void setIm(int im) {
		this.im = im;
	}

	public int getRm() {
		return rm;
	}

	public void setRm(int rm) {
		this.rm = rm;
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
		if (!(object instanceof TrudvangArmorMod)) {
			return false;
		}
		TrudvangArmorMod other = (TrudvangArmorMod) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
}
