package org.personal.SimpleDBViewer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cpurankingsummary")
public class CPURankingSummaryEntity {
	
	@Column(name="cpurankingsummaryCpuid")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long cpuId;
	
	@Column(name="cpurankingsummaryCount")
	private Long count;
	
	public CPURankingSummaryEntity() {}

	public CPURankingSummaryEntity(Long cpuId, Long count) {
		super();
		this.cpuId = cpuId;
		this.count = count;
	}

	public Long getCpuId() {
		return cpuId;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "CPURankingSummaryEntity [cpuId=" + cpuId + ", count=" + count + "]";
	}
}
