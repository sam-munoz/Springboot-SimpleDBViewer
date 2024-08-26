package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

@Repository
public class CPUListEntityCRUDRepository {
	@Autowired
	private AbstractCRUDRepository repo;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public CPUListEntity createCPU(CPUListEntity cpu) throws IllegalArgumentException {
		// validate input
		if(cpu == null) {
			throw new IllegalArgumentException("Input cpu cannot be empty/null.");
		}

		return (CPUListEntity) this.repo.createEntity(cpu);
	}

	public CPUListEntity createCPU(String cpuName) throws IllegalArgumentException {
		// validate input
		if(cpuName.isEmpty()) {
			throw new IllegalArgumentException("Input cpuName cannot be empty/null.");
		}

		CPUListEntity cpu = new CPUListEntity();
		cpu.setName(cpuName);
		return (CPUListEntity) this.repo.createEntity(cpu);
	}

	public CPUListEntity getCPU(Long cpuId) throws IllegalArgumentException {
		// validate input
		if(cpuId == null) {
			throw new IllegalArgumentException("Input cpuId cannot be empty/null");
		}

		return this.repo.getEntityById(cpuId, CPUListEntity.class);
	}

	public CPUListEntity getCPU(String cpuName) throws IllegalArgumentException {
		// validate input
		if(cpuName.isEmpty()) {
			throw new IllegalArgumentException("Input cpuName cannot be empty/null");
		}

		CPUListEntity rtnCPU = null;
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			rtnCPU = entityManager.createQuery("SELECT c FROM CPUListEntity c WHERE c.name = ?1", CPUListEntity.class)
				.setParameter(1, cpuName)
				.getSingleResult();
            tx.commit();
		} catch(Exception e) {
			if(tx.isActive()) tx.rollback();
			throw e;
		} finally {
			entityManager.close();
		}

		return rtnCPU;
	}

	public CPUListEntity getCPU(CPUListEntity cpu) throws IllegalArgumentException {
		// validate input
		if(cpu == null) {
			throw new IllegalArgumentException("Input CPU cannot be empty/null");
		}

		if(cpu.getId() != null) {
			return getCPU(cpu.getId());
		} else if(cpu.getName() != null && !cpu.getName().isEmpty()) {
			return getCPU(cpu.getName());
		} else {
			throw new IllegalArgumentException("Input CPU cannot have both null id and name");
		}
	}

	public List<CPUListEntity> getAllCPUs() {
		List<CPUListEntity> cpulist = null;
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			cpulist = entityManager.createQuery("SELECT c FROM CPUListEntity c", CPUListEntity.class)
					.getResultList();
			tx.commit();
		} catch(Exception e) {
			if(tx.isActive()) tx.rollback();
			throw e;
		} finally {
			entityManager.close();
		}

		return cpulist;
	}

	public static void updateCPUListEntity(SessionFactory s, CPUListEntity cpu) throws IllegalArgumentException {
		// validate input
		if(cpu == null) {
			throw new IllegalArgumentException("Input CPUListEntity object cannot be null");
		}

		AbstractCRUDRepository.updateEntity(s, cpu);
	}

	public CPUListEntity updateCPU(CPUListEntity cpu) throws IllegalArgumentException {
		// validate input
		if(cpu == null) {
			throw new IllegalArgumentException("Input CPUListEntity object cannot be null");
		}

		return (CPUListEntity) this.repo.updateEntity(cpu);
	}

	public void deleteCPU(CPUListEntity cpu) throws IllegalArgumentException {
		// validate input
		if(cpu == null) {
			throw new IllegalArgumentException("Input CPUListEntity object cannot be null");
		}

		this.repo.deleteEntity(cpu.getId(), CPUListEntity.class);
	}
}