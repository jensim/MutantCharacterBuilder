package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TrudvangSkillInGroup
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "trudvang_skill_in_group", catalog = "rollspel", schema = "public")
public class TrudvangSkillInGroup implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_SKILL_IN_GROUP_ID_GENERATOR",
	sequenceName = "trudvang_skill_in_group_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_SKILL_IN_GROUP_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@ManyToOne
	private TrudvangSkill skill;
	@ManyToOne
	private TrudvangSkillGroup group;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrudvangSkill getSkill() {
		return skill;
	}

	public void setSkill(TrudvangSkill skill) {
		this.skill = skill;
	}

	public TrudvangSkillGroup getGroup() {
		return group;
	}

	public void setGroup(TrudvangSkillGroup group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + Objects.hashCode(this.id);
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
		final TrudvangSkillInGroup other = (TrudvangSkillInGroup) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return skill.toString();
	}
}
