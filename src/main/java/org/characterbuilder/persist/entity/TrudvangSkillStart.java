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
@Table(name = "trudvang_skill_start", catalog = "rollspel", schema = "public")
public class TrudvangSkillStart implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_START_SKILL_ID_GENERATOR",	sequenceName = "trudvang_skill_start_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRUDVANG_START_SKILL_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	//The beneficiary. Only one may be != null
	@JoinColumn(name = "skill_id")
	@ManyToOne
	private TrudvangSkill skillId;
	@JoinColumn(name = "group_id")
	@ManyToOne
	private TrudvangSkillGroup group;
	//The source. Only one may be != null
	@JoinColumn(name = "upbringing_id")
	@ManyToOne
	private TrudvangUpbringing upbringingId;
	@JoinColumn(name = "people_id")
	@ManyToOne
	private TrudvangPeople peopleId;
	@JoinColumn(name = "arktype_id")
	@ManyToOne
	private TrudvangArktype arktypeId;

	public TrudvangSkillStart() {
	}

	public TrudvangSkillStart(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrudvangUpbringing getUpbringingId() {
		return upbringingId;
	}

	public void setUpbringingId(TrudvangUpbringing upbringingId) {
		this.upbringingId = upbringingId;
	}

	public TrudvangSkill getSkillId() {
		return skillId;
	}

	public void setSkillId(TrudvangSkill skillId) {
		this.skillId = skillId;
	}

	public TrudvangPeople getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(TrudvangPeople peopleId) {
		this.peopleId = peopleId;
	}

	public TrudvangArktype getArktypeId() {
		return arktypeId;
	}

	public void setArktypeId(TrudvangArktype arktypeId) {
		this.arktypeId = arktypeId;
	}

	public TrudvangSkillGroup getGroup() {
		return group;
	}

	public void setGroup(TrudvangSkillGroup group) {
		this.group = group;
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
		if (!(object instanceof TrudvangSkillStart)) {
			return false;
		}
		TrudvangSkillStart other = (TrudvangSkillStart) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if(skillId != null){
			return skillId.toString();
		}else if(group != null){
			return group.toString();
		}else{
			return "";
		}
	}
}
