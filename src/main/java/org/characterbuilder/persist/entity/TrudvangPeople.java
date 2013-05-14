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
@Table(name = "trudvang_people", catalog = "rollspel", schema = "public")
public class TrudvangPeople implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_PEOPLE_ID_GENERATOR",
	sequenceName = "trudvang_people_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_PEOPLE_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@OneToMany(mappedBy = "peopleId")
	private List<TrudvangSkillStart> trudvangSkillStartList;
	@JoinColumn(name = "book_id")
	@ManyToOne(optional = false)
	private TrudvangBook bookId;
	@Column(nullable=false, name="weight_min_man")
	private Integer weightMinMale;
	@Column(nullable=false, name="weight_max_man")
	private Integer weightMaxMale;
	@Column(nullable=false, name="weight_min_woman")
	private Integer weightMinFemale;
	@Column(nullable=false, name="weight_max_woman")
	private Integer weightMaxFemale;
	@Column(nullable=false, name="length_min_man")
	private Integer lenghtMinMale;
	@Column(nullable=false, name="length_max_man")
	private Integer lenghtMaxMale;
	@Column(nullable=false, name="length_min_woman")
	private Integer lenghtMinFemale;
	@Column(nullable=false, name="length_max_woman")
	private Integer lenghtMaxFemale;
	@Column(nullable=false, name="agemax_man")
	private Integer ageMaxMale;
	@Column(nullable=false, name="agemax_woman")
	private Integer ageMaxFemale;
	@Column(nullable=false, name="tkp_man")
	private Integer tkpMale;
	@Column(nullable=false, name="tkp_woman")
	private Integer tkpFemale;
	@Column(nullable=false, name="movement")
	private Integer movement;
	@ManyToOne
	@JoinColumn(name="religion_id")
	private TrudvangReligion religion;

	public TrudvangPeople() {
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

	public List<TrudvangSkillStart> getTrudvangSkillStartList() {
		return trudvangSkillStartList;
	}

	public void setTrudvangSkillStartList(List<TrudvangSkillStart> trudvangSkillStartList) {
		this.trudvangSkillStartList = trudvangSkillStartList;
	}

	public TrudvangBook getBookId() {
		return bookId;
	}

	public void setBookId(TrudvangBook bookId) {
		this.bookId = bookId;
	}

	public Integer getWeightMinMale() {
		return weightMinMale;
	}

	public void setWeightMinMale(Integer weightMinMale) {
		this.weightMinMale = weightMinMale;
	}

	public Integer getWeightMaxMale() {
		return weightMaxMale;
	}

	public void setWeightMaxMale(Integer weightMaxMale) {
		this.weightMaxMale = weightMaxMale;
	}

	public Integer getWeightMinFemale() {
		return weightMinFemale;
	}

	public void setWeightMinFemale(Integer weightMinFemale) {
		this.weightMinFemale = weightMinFemale;
	}

	public Integer getWeightMaxFemale() {
		return weightMaxFemale;
	}

	public void setWeightMaxFemale(Integer weightMaxFemale) {
		this.weightMaxFemale = weightMaxFemale;
	}

	public Integer getLenghtMinMale() {
		return lenghtMinMale;
	}

	public void setLenghtMinMale(Integer lenghtMinMale) {
		this.lenghtMinMale = lenghtMinMale;
	}

	public Integer getLenghtMaxMale() {
		return lenghtMaxMale;
	}

	public void setLenghtMaxMale(Integer lenghtMaxMale) {
		this.lenghtMaxMale = lenghtMaxMale;
	}

	public Integer getLenghtMinFemale() {
		return lenghtMinFemale;
	}

	public void setLenghtMinFemale(Integer lenghtMinFemale) {
		this.lenghtMinFemale = lenghtMinFemale;
	}

	public Integer getLenghtMaxFemale() {
		return lenghtMaxFemale;
	}

	public void setLenghtMaxFemale(Integer lenghtMaxFemale) {
		this.lenghtMaxFemale = lenghtMaxFemale;
	}

	public Integer getAgeMaxMale() {
		return ageMaxMale;
	}

	public void setAgeMaxMale(Integer ageMaxMale) {
		this.ageMaxMale = ageMaxMale;
	}

	public Integer getAgeMaxFemale() {
		return ageMaxFemale;
	}

	public void setAgeMaxFemale(Integer ageMaxFemale) {
		this.ageMaxFemale = ageMaxFemale;
	}

	public Integer getTkpMale() {
		return tkpMale;
	}

	public void setTkpMale(Integer tkpMale) {
		this.tkpMale = tkpMale;
	}

	public Integer getTkpFemale() {
		return tkpFemale;
	}

	public void setTkpFemale(Integer tkpFemale) {
		this.tkpFemale = tkpFemale;
	}

	public Integer getMovement() {
		return movement;
	}

	public void setMovement(Integer movement) {
		this.movement = movement;
	}

	public TrudvangReligion getReligion() {
		return religion;
	}

	public void setReligion(TrudvangReligion religion) {
		this.religion = religion;
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
		if (!(object instanceof TrudvangPeople)) {
			return false;
		}
		TrudvangPeople other = (TrudvangPeople) object;
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
