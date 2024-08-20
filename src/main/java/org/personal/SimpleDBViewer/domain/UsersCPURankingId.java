package org.personal.SimpleDBViewer.domain;


import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class UsersCPURankingId {
	@ManyToOne(fetch=FetchType.LAZY)
	private CPUListEntity cpu;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private UsersEntity user;
	
	public UsersCPURankingId() {}
	
	public UsersCPURankingId(CPUListEntity cpu, UsersEntity user) {
		this.cpu = cpu;
		this.user = user;
	}

	public CPUListEntity getCpu() {
		return cpu;
	}

	public void setCpu(CPUListEntity cpu) {
		this.cpu = cpu;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UsersCPURankingId [cpu=" + cpu + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpu, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersCPURankingId other = (UsersCPURankingId) obj;
		return Objects.equals(cpu, other.cpu) && Objects.equals(user, other.user);
	}
}