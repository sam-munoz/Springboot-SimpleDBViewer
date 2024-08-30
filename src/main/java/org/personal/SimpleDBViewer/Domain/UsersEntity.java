package org.personal.SimpleDBViewer.Domain;

import java.util.Objects;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="voter")
public class UsersEntity {
	
	@Column(name="voterId")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="voterName")
	private String name;

	@Column(name="voterPasswd")
	private String passwd;
	
//	Cannot get this to work correctly
//	@OneToMany(mappedBy="user")
//	Set<UsersCPURankingEntity> rankings;
	
	public UsersEntity() {}

	public UsersEntity(String name) {
		this.name = name;
	}

	public UsersEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public UsersEntity(String name, String passwd) {
		this.name = name;
		this.passwd = passwd;
	}

	public UsersEntity(Long id, String name, String passwd) {
		super();
		this.id = id;
		this.name = name;
		this.passwd = passwd;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
//	public Set<UsersCPURankingEntity> getRankings() {
//		return this.rankings;
//	}

	@Override
	public String toString() {
		return "UsersEntity [id=" + id + ", name=" + name + ", passwd=" + passwd + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, passwd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersEntity other = (UsersEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(passwd, other.passwd);
	}
}
