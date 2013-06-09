package org.characterbuilder.persist.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
@Entity
@Table(name = "front_page_news")
public class FrontPageNews implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "FRONT_PAGE_NEWS_ID_GENERATOR", sequenceName = "FRONT_PAGE_NEWS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FRONT_PAGE_NEWS_ID_GENERATOR")
    @Column(updatable = false, unique = true, nullable = false)
    private Integer id;
    
    @Column(length = 2147483647) 
    private String content;
    
    @Column(name = "death_time")
    private Timestamp deathTime;
    
    @Column(length = 100)
    private String header;
    
    @Column(name = "post_time", nullable = false)
    private Timestamp postTime;
    
    private Boolean visible;

    public FrontPageNews() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDeathTime() {
        return this.deathTime;
    }

    public void setDeathTime(Timestamp deathTime) {
        this.deathTime = deathTime;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Timestamp getPostTime() {
        return this.postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public Boolean getVisible() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
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
		final FrontPageNews other = (FrontPageNews) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "<h1>"+header+"</h1><p>"+content+"</p>";
	}
}