package org.personal.SimpleDBViewer.Domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsersCPURankingId implements Serializable {
    private Long cpuId;
    private Long userId;

    public UsersCPURankingId() {}

    public UsersCPURankingId(Long cpuId, Long userId) {
        this.cpuId = cpuId;
        this.userId = userId;
    }

    public Long getCpuId() {
        return cpuId;
    }

    public void setCpuId(Long cpuId) {
        this.cpuId = cpuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean hasNullId() {
        return this.cpuId == null || this.userId == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersCPURankingId that = (UsersCPURankingId) o;
        return Objects.equals(cpuId, that.cpuId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuId, userId);
    }
}