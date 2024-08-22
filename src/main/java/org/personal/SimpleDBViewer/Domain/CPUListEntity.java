package org.personal.SimpleDBViewer.Domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cpulist")
public class CPUListEntity {
	
	@Column(name="cpulist_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="cpulist_name")
	private String name;
	
//	Cannot get this work to work correctly
//	@OneToMany(mappedBy="cpu", cascade=CascadeType.REMOVE)
//	Set<UsersCPURankingEntity> rankings;
	
//	@OneToOne(mappedBy="cpu")
//	private CPURankingSummaryEntity summary;
	
	public CPUListEntity() {}
	
	public CPUListEntity(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Long setId(Long id) {
		return this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	public Set<UsersCPURankingEntity> getRankings() {
//		return rankings;
//	}

	@Override
	public String toString() {
		return "CPUListEntity [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPUListEntity other = (CPUListEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}