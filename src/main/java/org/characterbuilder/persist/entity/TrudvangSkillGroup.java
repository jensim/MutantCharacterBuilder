package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TrudvangSkillGroup
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "trudvang_skill_group", catalog = "rollspel", schema = "public")
public class TrudvangSkillGroup implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_SKILL_GROUP_ID_GENERATOR",
	sequenceName = "trudvang_skill_group_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_SKILL_GROUP_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name = "description", nullable = false)
	private String description = "";
	@OneToMany(mappedBy = "group")
	private List<TrudvangSkillInGroup> groups;
	@OneToMany(mappedBy="group")
	private List<TrudvangSkillStart> startSkills;

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

	public List<TrudvangSkillInGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<TrudvangSkillInGroup> groups) {
		this.groups = groups;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 41 * hash + Objects.hashCode(this.id);
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
		final TrudvangSkillGroup other = (TrudvangSkillGroup) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
