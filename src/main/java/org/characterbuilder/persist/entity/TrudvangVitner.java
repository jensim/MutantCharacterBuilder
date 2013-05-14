package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
@Entity
@Table(name = "trudvang_vitner", catalog = "rollspel", schema = "public")
public class TrudvangVitner implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_VITNER_ID_GENERATOR",
	sequenceName = "trudvang_vitner_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_VITNER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@JoinColumn(name = "elaboration_id")
	@ManyToOne(optional = false)
	private TrudvangElaboration elaborationId;
	@ManyToOne(optional=false)
	@JoinColumn(name="type_id")
	private TrudvangVitnerType vitnerType;
	@OneToMany(mappedBy = "vitner")
	private List<TrudvangVitnerPowerlevel> powerlevels;
	@Column(name="manatime")
	@Basic(optional=false)
	private String manatime;
	@Column(name="duration")
	@Basic(optional=false)
	private String duration;
	@Column(name="reach")
	@Basic(optional=false)
	private String reach;
	

	public TrudvangVitner() {
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

	public TrudvangElaboration getElaborationId() {
		return elaborationId;
	}

	public void setElaborationId(TrudvangElaboration elaborationId) {
		this.elaborationId = elaborationId;
	}

	public TrudvangVitnerType getVitnerType() {
		return vitnerType;
	}

	public void setVitnerType(TrudvangVitnerType vitnerType) {
		this.vitnerType = vitnerType;
	}

	public List<TrudvangVitnerPowerlevel> getPowerlevels() {
		return powerlevels;
	}

	public void setPowerlevels(List<TrudvangVitnerPowerlevel> powerlevels) {
		this.powerlevels = powerlevels;
	}

	public String getManatime() {
		return manatime;
	}

	public void setManatime(String manatime) {
		this.manatime = manatime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getReach() {
		return reach;
	}

	public void setReach(String reach) {
		this.reach = reach;
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
		if (!(object instanceof TrudvangVitner)) {
			return false;
		}
		TrudvangVitner other = (TrudvangVitner) object;
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
