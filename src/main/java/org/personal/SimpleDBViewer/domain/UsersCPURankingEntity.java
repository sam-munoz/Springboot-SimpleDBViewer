package org.personal.SimpleDBViewer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="userscpuranking")
public class UsersCPURankingEntity {
	
	@EmbeddedId
	private UsersCPURankingId id;
	
	@Column(name="userscpurankingRanking")
	private Integer ranking;
	
	public UsersCPURankingEntity() {}

	public UsersCPURankingEntity(UsersCPURankingId id, Integer ranking) {
		super();
		this.id = id;
		this.ranking = ranking;
	}

	public UsersCPURankingId getId() {
		return id;
	}

	public void setId(UsersCPURankingId id) {
		this.id = id;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
}
