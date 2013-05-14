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
@Table(name = "rollspel_log_type")
public class RollspelLogType implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "ROLLSPEL_LOG_TYPE_ID_GENERATOR", sequenceName = "ROLLSPEL_LOG_TYPE_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_LOG_TYPE_ID_GENERATOR")
	@Column(unique = true, nullable = false)
	private Integer id;
	@Column(nullable = false, length = 255)
	private String name;
	//bi-directional many-to-one association to RollspelLog
	@OneToMany(mappedBy = "rollspelLogType")
	private Set<RollspelLog> rollspelLogs;

	public RollspelLogType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<RollspelLog> getRollspelLogs() {
		return this.rollspelLogs;
	}

	public void setRollspelLogs(Set<RollspelLog> rollspelLogs) {
		this.rollspelLogs = rollspelLogs;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(this.id);
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
		final RollspelLogType other = (RollspelLogType) obj;
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