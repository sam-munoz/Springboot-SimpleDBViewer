package org.personal.SimpleDBViewer.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;

import java.util.Objects;

@Entity
@Table(name="userscpuranking")
public class UsersCPURankingEntity {
	@EmbeddedId
	private UsersCPURankingId id;

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@MapsId("cpuId")
	@Transient
	private CPUListEntity cpu;

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@MapsId("userId")
	@Transient
	private UsersEntity user;

	@Column(name="userscpurankingRanking")
	private Integer ranking;

	public UsersCPURankingEntity() {}

	public UsersCPURankingEntity(CPUListEntity cpu, UsersEntity user, Integer ranking) throws IllegalArgumentException {
		this.id = new UsersCPURankingId();
		if(!validCPU(cpu)) {
			throw new IllegalArgumentException("Input CPU is not valid");
		} else {
			this.id.setCpuId(cpu.getId());
			this.cpu = cpu;
		}
		if(!validUser(user)) {
			throw new IllegalArgumentException("Input User is not valid");
		} else {
			this.id.setUserId(user.getId());
			this.user = user;
		}
		this.ranking = ranking;
	}

	public UsersCPURankingEntity(UsersCPURankingId id, CPUListEntity cpu, UsersEntity user, Integer ranking) throws IllegalArgumentException {
		this.id = id;
		if(!validCPU(cpu)) {
			throw new IllegalArgumentException("Input CPU is not valid");
		} else {
			this.cpu = cpu;
		}
		if(!validUser(user)) {
			throw new IllegalArgumentException("Input User is not valid");
		} else {
			this.user = user;
		}
		this.ranking = ranking;
	}

	public UsersCPURankingId getId() {
		return id;
	}

	public void setId(UsersCPURankingId id) {
		this.id = id;
	}

	public CPUListEntity getCpu() {
		return cpu;
	}

	public void setCpu(CPUListEntity cpu) throws IllegalArgumentException {
		if(this.id == null) this.id = new UsersCPURankingId();
		if(!validCPU(cpu)) {
			throw new IllegalArgumentException("Input CPU is not valid");
		}
		this.cpu = cpu;
		this.id.setCpuId(cpu.getId());
	}

	public boolean validCPU(CPUListEntity cpu) {
		return cpu != null && cpu.getId() != null;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) throws IllegalArgumentException {
		if(this.id == null) this.id = new UsersCPURankingId();
		if(!validUser(user)) {
			throw new IllegalArgumentException("Input User is not valid");
		}
		this.user = user;
		this.id.setUserId(user.getId());
	}

	public boolean validUser(UsersEntity user) {
		return user != null && user.getId() != null;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		String rtnString = "UsersCPURankingEntity{";
		rtnString += "id=" + id;
		if(this.cpu == null) {
			rtnString += ", cpu=null";
		} else {
			rtnString += ", cpu=" + cpu.toString();
		}
		if(this.user == null) {
			rtnString += ", user=null";
		} else {
			rtnString += ", user=" + user.toString();
		}
		rtnString += ", ranking=" + ranking + '}';
		return rtnString;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UsersCPURankingEntity that = (UsersCPURankingEntity) o;
		return Objects.equals(id, that.id) && Objects.equals(cpu, that.cpu) && Objects.equals(user, that.user) && Objects.equals(ranking, that.ranking);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, cpu, user, ranking);
	}
}
