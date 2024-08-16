package org.personal.SimpleDBViewer.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record UsersCPURankingId(Long cpulistId, Long usersId) {}
