package org.personal.SimpleDBViewer.CRUDRepository;

import org.personal.SimpleDBViewer.domain.CPUListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CPUListCRUDRepositoryInterface extends JpaRepository<CPUListEntity, Long> {}
