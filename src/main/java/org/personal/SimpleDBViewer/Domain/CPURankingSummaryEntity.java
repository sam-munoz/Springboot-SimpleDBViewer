package org.personal.SimpleDBViewer.Domain;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name="cpurankingsummary")
public class CPURankingSummaryEntity {
	@Id
	@Column(name="cpurankingsummaryId")
	private Long id;

	@OneToOne(optional=false, fetch=FetchType.EAGER)
	@MapsId
	@Transient
	private CPUListEntity cpu;
	
	@Column(name="cpurankingsummaryRanksum")
	private Integer rankSum;

	@Column(name="cpurankingsummaryCount")
	private Long count;
	
	public CPURankingSummaryEntity() {}

	public CPURankingSummaryEntity(Long cpuId) {
		this.id = cpuId;
	}

	public CPURankingSummaryEntity(CPUListEntity cpu, Integer rankSum, Long count) {
		this.cpu = cpu;
		if(cpu != null && cpu.getId() != null) {
			this.id = cpu.getId();
		}
		this.rankSum = rankSum;
		this.count = count;
	}

	public CPURankingSummaryEntity(Long cpuID, CPUListEntity cpu, Integer rankSum, Long count) {
		this.id = cpuID;
		this.cpu = cpu;
		this.rankSum = rankSum;
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CPUListEntity getCPU() {
		return cpu;
	}

	public void setCPU(CPUListEntity cpu) {
		this.cpu = cpu;
		if(cpu != null && cpu.getId() != null) {
			this.id = cpu.getId();
		}
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
		return "CPURankingSummaryEntity{" +
				"id=" + id +
				", cpu=" + cpu +
				", rankSum=" + rankSum +
				", count=" + count +
				'}';
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
