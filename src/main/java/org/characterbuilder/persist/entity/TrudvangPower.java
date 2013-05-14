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
@Table(name = "trudvang_power", catalog = "rollspel", schema = "public")
public class TrudvangPower implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_POWER_ID_GENERATOR",
	sequenceName = "trudvang_power_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_POWER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Basic(optional=false)
	@Column(name = "description", nullable = false)
	private String description = "";
	@JoinColumn(name = "religion_id")
	@ManyToOne(optional = false)
	private TrudvangReligion religionId;
	@ManyToOne(optional=false) 
	@JoinColumn(name = "elaboration_id")
	private TrudvangElaboration elaborationId;
	
	@Column(name="point_cost")
	@Basic(optional=false)
	private Integer cost;
	@Column(name="duration")
	@Basic(optional=false)
	private String duration;
	@Column(name="reach")
	@Basic(optional=false)
	private String reach;

	public TrudvangPower() {
	}

	public TrudvangPower(Integer id) {
		this.id = id;
	}

	public TrudvangPower(Integer id, String name) {
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

	public TrudvangReligion getReligionId() {
		return religionId;
	}

	public void setReligionId(TrudvangReligion religionId) {
		this.religionId = religionId;
	}

	public TrudvangElaboration getElaborationId() {
		return elaborationId;
	}

	public void setElaborationId(TrudvangElaboration elaborationId) {
		this.elaborationId = elaborationId;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
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
		if (!(object instanceof TrudvangPower)) {
			return false;
		}
		TrudvangPower other = (TrudvangPower) object;
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
