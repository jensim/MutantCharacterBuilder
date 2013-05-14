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
@Table(name = "trudvang_religion", catalog = "rollspel", schema = "public")
public class TrudvangReligion implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_RELIGION_ID_GENERATOR",
	sequenceName = "trudvang_religion_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_RELIGION_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description="";
	@OneToMany(mappedBy = "religionId")
	private List<TrudvangPower> trudvangPowerList;
	@JoinColumn(name = "book_id")
	@ManyToOne(optional = false)
	private TrudvangBook bookId;
	@OneToMany(mappedBy = "religion")
	private List<TrudvangPeople> trudvangPeoples;
	@OneToMany(mappedBy = "religion")
	private List<TrudvangElaboration> elaborations;

	public TrudvangReligion() {
	}

	public TrudvangReligion(Integer id) {
		this.id = id;
	}

	public TrudvangReligion(Integer id, String name) {
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

	public List<TrudvangPower> getTrudvangPowerList() {
		return trudvangPowerList;
	}

	public void setTrudvangPowerList(List<TrudvangPower> trudvangPowerList) {
		this.trudvangPowerList = trudvangPowerList;
	}

	public TrudvangBook getBookId() {
		return bookId;
	}

	public void setBookId(TrudvangBook bookId) {
		this.bookId = bookId;
	}

	public List<TrudvangPeople> getTrudvangPeoples() {
		return trudvangPeoples;
	}

	public void setTrudvangPeoples(List<TrudvangPeople> trudvangPeoples) {
		this.trudvangPeoples = trudvangPeoples;
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
		if (!(object instanceof TrudvangReligion)) {
			return false;
		}
		TrudvangReligion other = (TrudvangReligion) object;
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
