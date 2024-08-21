package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.personal.SimpleDBViewer.domain.CPUListEntity;

public class CPUListEntityCRUDRepository {
	public static void createCPUListEntity(SessionFactory s, CPUListEntity cpu) {
		AbstractCRUDRepository.createEntity(s, cpu);
	}

	public static CPUListEntity createCPUListEntity(SessionFactory s, String cpuName) {
		CPUListEntity cpu = new CPUListEntity();
		cpu.setName(cpuName);
		AbstractCRUDRepository.createEntity(s, cpu);
		return cpu;
	}

	public static CPUListEntity getCPU(SessionFactory s, CPUListEntity cpu) {
		return getCPU(s, cpu.getName());
	}

	public static CPUListEntity getCPU(SessionFactory s, Long cpuId) {
		Session session = s.openSession();
		CPUListEntity rtnCPU = null;
		try {
			session.beginTransaction();
			rtnCPU = session.createSelectionQuery("from CPUListEntity where id = ?1", CPUListEntity.class)
					.setParameter(1, cpuId)
					.getSingleResult();
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session.getTransaction().isActive()) session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
		return rtnCPU;
	}

	public static CPUListEntity getCPU(SessionFactory s, String cpuName) {
		Session session = s.openSession();
		CPUListEntity rtnCPU = null;
		try {
			session.beginTransaction();
			rtnCPU = session.createSelectionQuery("from CPUListEntity where name = ?1", CPUListEntity.class)
							.setParameter(1, cpuName)
							.getSingleResultOrNull();
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session.getTransaction().isActive()) session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
		return rtnCPU;
	}

	public static List<CPUListEntity> getAllCPUs(SessionFactory s) {
		Session session = s.openSession();
		List<CPUListEntity> rtnList = null;
		session.beginTransaction();
		rtnList = session.createSelectionQuery("from CPUListEntity", CPUListEntity.class).getResultList();
		session.getTransaction().commit();
		session.close();
		return rtnList;
	}

	public static void updateCPUListEntity(SessionFactory s, CPUListEntity cpu) {
		AbstractCRUDRepository.updateEntity(s, cpu);
	}

	public static void deleteCPUListEntity(SessionFactory s, CPUListEntity cpu) {
		AbstractCRUDRepository.deleteEntity(s, cpu);
	}
}