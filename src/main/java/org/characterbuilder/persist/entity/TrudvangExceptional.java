package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "trudvang_exceptional", catalog = "rollspel", schema = "public")
public class TrudvangExceptional implements Serializable {

	@Id
	@SequenceGenerator(name = "TRUDVANG_EXCEPTIONAL_ID_GENERATOR",
	sequenceName = "trudvang_exceptional_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "TRUDVANG_EXCEPTIONAL_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Basic(optional = false)
	private String name;
	@Column(name="description", nullable=false)
	private String description ="";
	@JoinColumn(name = "book_id")
	@ManyToOne(optional = false)
	private TrudvangBook bookId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exceptionalId")
	private List<TrudvangExceptionalLevel> trudvangExceptionalLevelList;
	

	public TrudvangExceptional() {
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

	public TrudvangBook getBookId() {
		return bookId;
	}

	public void setBookId(TrudvangBook bookId) {
		this.bookId = bookId;
	}

	public List<TrudvangExceptionalLevel> getTrudvangExceptionalLevelList() {
		return trudvangExceptionalLevelList;
	}

	public void setTrudvangExceptionalLevelList(List<TrudvangExceptionalLevel> trudvangExceptionalLevelList) {
		this.trudvangExceptionalLevelList = trudvangExceptionalLevelList;
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
		if (!(object instanceof TrudvangExceptional)) {
			return false;
		}
		TrudvangExceptional other = (TrudvangExceptional) object;
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
