package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TrudvangElaborationLevel
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "trudvang_elaboration_level", catalog = "rollspel", schema = "public")
public class TrudvangElaborationLevel implements Serializable{
	@Id
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description="";
	private Integer cost = 10;
	@Basic(optional=false)
	@Column(name="required_skill_level")
	private Integer requiredFv;
	@Basic(optional=false)
	@Column(name="vitner_mod")
	private Integer vitnerMod;
	@Basic(optional=false)
	@Column(name="vitner_cost")
	private Integer vitnerCost;
	@Basic(optional=false)
	@Column(name="vitner_fail_cost")
	private Integer vitnerFailCost;
	@Basic(optional=false)
	@Column(name="vitner_learn_time")
	private String vitnerLearnTime;
	@Basic(optional=false)
	@Column(name="religion_segment")
	private Integer religionSegment;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elaborationLevel")
	private List<TrudvangElaboration> elaborationList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "elaborationLevel")
	private List<TrudvangElaborationCharacter> charElaborationList;

	public TrudvangElaborationLevel() {
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

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public List<TrudvangElaboration> getElabList() {
		return elaborationList;
	}

	public void setElabList(List<TrudvangElaboration> elaborationList) {
		this.elaborationList = elaborationList;
	}

	public Integer getRequiredFv() {
		return requiredFv;
	}

	public void setRequiredFv(Integer requiredFv) {
		this.requiredFv = requiredFv;
	}

	public Integer getVitnerMod() {
		return vitnerMod;
	}

	public void setVitnerMod(Integer vitnerMod) {
		this.vitnerMod = vitnerMod;
	}

	public Integer getVitnerCost() {
		return vitnerCost;
	}

	public void setVitnerCost(Integer vitnerCost) {
		this.vitnerCost = vitnerCost;
	}

	public Integer getVitnerFailCost() {
		return vitnerFailCost;
	}

	public void setVitnerFailCost(Integer vitnerFailCost) {
		this.vitnerFailCost = vitnerFailCost;
	}

	public String getVitnerLearnTime() {
		return vitnerLearnTime;
	}

	public void setVitnerLearnTime(String vitnerLearnTime) {
		this.vitnerLearnTime = vitnerLearnTime;
	}

	public Integer getReligionSegment() {
		return religionSegment;
	}

	public void setReligionSegment(Integer religionSegment) {
		this.religionSegment = religionSegment;
	}

	public List<TrudvangElaborationCharacter> getCharElabList() {
		return charElaborationList;
	}

	public void setCharElabList(List<TrudvangElaborationCharacter> charElaborationList) {
		this.charElaborationList = charElaborationList;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.id);
		hash = 97 * hash + Objects.hashCode(this.name);
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
		final TrudvangElaborationLevel other = (TrudvangElaborationLevel) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
