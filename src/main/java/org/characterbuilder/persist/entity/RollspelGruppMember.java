package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "rollspel_grupp_member")
public class RollspelGruppMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ROLLSPEL_GRUPP_MEMBER_ID_GENERATOR", sequenceName = "ROLLSPEL_GRUPP_MEMBER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_GRUPP_MEMBER_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(name = "group_id", nullable = false)
    private Integer groupId;
    //bi-directional many-to-one association to RollspelGruppRole
    @ManyToOne
    @JoinColumn(name = "group_role_id", nullable = false)
    private RollspelGruppRole rollspelGruppRole;
    //bi-directional many-to-one association to RollspelUser
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RollspelUser rollspelUser;

    public RollspelGruppMember() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public RollspelGruppRole getRollspelGruppRole() {
        return this.rollspelGruppRole;
    }

    public void setRollspelGruppRole(RollspelGruppRole rollspelGruppRole) {
        this.rollspelGruppRole = rollspelGruppRole;
    }

    public RollspelUser getRollspelUser() {
        return this.rollspelUser;
    }

    public void setRollspelUser(RollspelUser rollspelUser) {
        this.rollspelUser = rollspelUser;
    }

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 37 * hash + Objects.hashCode(this.id);
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
		final RollspelGruppMember other = (RollspelGruppMember) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}