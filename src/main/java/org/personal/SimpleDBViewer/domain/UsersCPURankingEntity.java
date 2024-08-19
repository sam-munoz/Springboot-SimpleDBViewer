package org.personal.SimpleDBViewer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="userscpuranking")
public class UsersCPURankingEntity {
	
	@EmbeddedId
	private UsersCPURankingId ucrid;
	
	@Column(name="userscpurankingRanking")
	private Integer ranking;
	
	public UsersCPURankingEntity() {}

	public UsersCPURankingEntity(UsersCPURankingId id, Integer ranking) {
		super();
		this.ucrid = id;
		this.ranking = ranking;
	}

	public UsersCPURankingId getId() {
		return ucrid;
	}

	public void setId(UsersCPURankingId id) {
		this.ucrid = id;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		return "UsersCPURankingEntity [id=" + ucrid + ", ranking=" + ranking + "]";
	}
}
