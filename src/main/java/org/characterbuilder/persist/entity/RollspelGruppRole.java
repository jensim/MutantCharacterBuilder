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
@Table(name = "rollspel_grupp_role")
public class RollspelGruppRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ROLLSPEL_GRUPP_ROLE_ID_GENERATOR", sequenceName = "ROLLSPEL_GRUPP_ROLE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_GRUPP_ROLE_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(length = 2147483647, nullable=false)
    private String description = "";
    @Column(nullable = false, length = 255)
    private String name;
    //bi-directional many-to-one association to RollspelGruppMember
    @OneToMany(mappedBy = "rollspelGruppRole")
    private Set<RollspelGruppMember> rollspelGruppMembers;

    public RollspelGruppRole() {
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

    public Set<RollspelGruppMember> getRollspelGruppMembers() {
        return this.rollspelGruppMembers;
    }

    public void setRollspelGruppMembers(Set<RollspelGruppMember> rollspelGruppMembers) {
        this.rollspelGruppMembers = rollspelGruppMembers;
    }

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + Objects.hashCode(this.id);
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
		final RollspelGruppRole other = (RollspelGruppRole) obj;
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