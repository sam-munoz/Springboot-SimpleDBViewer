package org.personal.SimpleDBViewer.domain;

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

	@Override
	public String toString() {
		return "CPUListEntity [id=" + id + ", name=" + name + "]";
	}
}