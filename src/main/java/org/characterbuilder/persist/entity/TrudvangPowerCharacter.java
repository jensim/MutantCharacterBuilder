package org.characterbuilder.persist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "trudvang_power_character", catalog = "rollspel", schema = "public")
public class TrudvangPowerCharacter implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_POWER_CHARACTER_ID_GENERATOR",
	sequenceName = "trudvang_power_character_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_POWER_CHARACTER_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@JoinColumn(name = "elaboration_id")
	@ManyToOne(optional = false)
	private TrudvangElaborationCharacter elaborationId;
	
	@Column(name="point_cost")
	@Basic(optional=false)
	private Integer cost;
	@Column(name="duration")
	@Basic(optional=false)
	private String duration;
	@Column(name="reach")
	@Basic(optional=false)
	private String reach;

	public TrudvangPowerCharacter() {
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

	public TrudvangElaborationCharacter getElaborationId() {
		return elaborationId;
	}

	public void setElaborationId(TrudvangElaborationCharacter elaborationId) {
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
		if (!(object instanceof TrudvangPowerCharacter)) {
			return false;
		}
		TrudvangPowerCharacter other = (TrudvangPowerCharacter) object;
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
