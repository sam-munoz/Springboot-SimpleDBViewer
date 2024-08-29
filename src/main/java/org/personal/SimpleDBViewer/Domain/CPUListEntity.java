package org.personal.SimpleDBViewer.Domain;

import java.util.Objects;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="cpulist")
public class CPUListEntity {
	@Column(name="cpulist_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="cpulist_name")
//	@NaturalId
	private String name;

	public CPUListEntity() {}

	public CPUListEntity(Long id) {
		this.id = id;
	}

	public CPUListEntity(String name) {
		this.name = name;
	}

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