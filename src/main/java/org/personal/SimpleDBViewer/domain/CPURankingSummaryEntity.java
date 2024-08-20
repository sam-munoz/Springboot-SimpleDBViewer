package org.personal.SimpleDBViewer.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="cpurankingsummary")
public class CPURankingSummaryEntity {
	@Id
	@OneToOne(optional=false, fetch=FetchType.EAGER)
	@MapsId
	private CPUListEntity cpu;
	
	@Column(name="cpurankingsummaryRanksum")
	private Integer rankSum;

	@Column(name="cpurankingsummaryCount")
	private Long count;
	
	public CPURankingSummaryEntity() {}

	public CPURankingSummaryEntity(CPUListEntity cpu, Integer rankSum, Long count) {
		super();
		this.cpu = cpu;
		this.rankSum = rankSum;
		this.count = count;
	}

	public CPUListEntity getCpuId() {
		return cpu;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	public Integer getRankSum() {
		return rankSum;
	}

	public void setRankSum(Integer rankSum) {
		this.rankSum = rankSum;
	}

	@Override
	public String toString() {
		return "CPURankingSummaryEntity [cpu=" + cpu + ", rankSum=" + rankSum + ", count=" + count + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(count, cpu, rankSum);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPURankingSummaryEntity other = (CPURankingSummaryEntity) obj;
		return Objects.equals(count, other.count) && Objects.equals(cpu, other.cpu)
				&& Objects.equals(rankSum, other.rankSum);
	}
}
