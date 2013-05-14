package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "rollspel_grupp")
public class RollspelGrupp implements Serializable {

    @Id
    @SequenceGenerator(name = "ROLLSPEL_GRUPP_ID_GENERATOR", sequenceName = "ROLLSPEL_GRUPP_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_GRUPP_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(length = 2147483647, nullable=false)
    private String description = "";
    @Column(nullable = false, length = 2147483647)
    private String name;
    @Column(nullable = false)
    private Boolean visibillity;
    @OneToMany(mappedBy = "rollspelGrupp")
    private Set<RollspelGruppApplication> rollspelGruppApplications;

    public RollspelGrupp() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVisibillity() {
        return this.visibillity;
    }

    public void setVisibillity(Boolean visibillity) {
        this.visibillity = visibillity;
    }

    public Set<RollspelGruppApplication> getRollspelGruppApplications() {
        return this.rollspelGruppApplications;
    }

    public void setRollspelGruppApplications(Set<RollspelGruppApplication> rollspelGruppApplications) {
        this.rollspelGruppApplications = rollspelGruppApplications;
    }

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 11 * hash + Objects.hashCode(this.id);
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
		final RollspelGrupp other = (RollspelGrupp) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}