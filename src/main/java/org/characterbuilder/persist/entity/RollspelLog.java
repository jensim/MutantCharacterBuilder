package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "rollspel_log")
public class RollspelLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ROLLSPEL_LOG_ID_GENERATOR", sequenceName = "ROLLSPEL_LOG_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLLSPEL_LOG_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Integer id;
    @Column(name = "log_info", nullable = false, length = 2147483647)
    private String logInfo;
    @Column(name = "stack_trace", nullable = true, length = 2147483647)
    private String stackTrace;
    @Column(nullable = false)
    private Timestamp logtime = new Timestamp(new Date().getTime());
    //bi-directional many-to-one association to RollspelLogType
    @ManyToOne
    @JoinColumn(name = "log_type_id", nullable = false)
    private RollspelLogType rollspelLogType;
    //bi-directional many-to-one association to RollspelUser
    @ManyToOne
    @JoinColumn(name = "user_id")
    private RollspelUser rollspelUser;

    public RollspelLog() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogInfo() {
        return this.logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Timestamp getLogtime() {
        return this.logtime;
    }

    public void setLogtime(Timestamp logtime) {
        this.logtime = logtime;
    }

    public RollspelLogType getRollspelLogType() {
        return this.rollspelLogType;
    }

    public void setRollspelLogType(RollspelLogType rollspelLogType) {
        this.rollspelLogType = rollspelLogType;
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
		hash = 97 * hash + Objects.hashCode(this.id);
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
		final RollspelLog other = (RollspelLog) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return logInfo;
	}
}