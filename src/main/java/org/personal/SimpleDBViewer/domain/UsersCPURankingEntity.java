package org.personal.SimpleDBViewer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="userscpuranking")
public class UsersCPURankingEntity {
	
	@Column(name="userscpurankingUserid")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	@Column(name="userscpurankingCpuid")
	private Long cpuId;
	
	@Column(name="userscpurankingRanking")
	private Integer ranking;
	
	public UsersCPURankingEntity() {}

	public UsersCPURankingEntity(Long userId, Long cpuId, Integer ranking) {
		super();
		this.userId = userId;
		this.cpuId = cpuId;
		this.ranking = ranking;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCpuId() {
		return cpuId;
	}

	public void setCpuId(Long cpuId) {
		this.cpuId = cpuId;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
}
