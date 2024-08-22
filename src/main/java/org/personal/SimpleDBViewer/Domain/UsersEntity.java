package org.personal.SimpleDBViewer.Domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class UsersEntity {
	
	@Column(name="usersId")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="usersName")
	private String name;

	@Column(name="usersPasswd")
	private String passwd;
	
//	Cannot get this to work correctly
//	@OneToMany(mappedBy="user")
//	Set<UsersCPURankingEntity> rankings;
	
	public UsersEntity() {}

	public UsersEntity(Long id, String name, String passwd) {
		super();
		this.id = id;
		this.name = name;
		this.passwd = passwd;
	}
	
	public Long getId() {
		return id;
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
