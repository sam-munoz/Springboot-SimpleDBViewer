package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.personal.SimpleDBViewer.domain.CPUListEntity;
import org.personal.SimpleDBViewer.domain.CPURankingSummaryEntity;

public class CPURankingSummaryCRUDRepository {
	public static void createCPUSummaryEntity(SessionFactory s, CPURankingSummaryEntity summary) {
		AbstractCRUDRepository.createEntity(s, summary);
	}

	public static CPURankingSummaryEntity createCPUSummaryEntity(SessionFactory s, CPUListEntity cpu, Integer rankSum, Long count) {
		CPURankingSummaryEntity summary = new CPURankingSummaryEntity(cpu, rankSum, count);
		AbstractCRUDRepository.createEntity(s, summary);
		return summary;
	}

	public static CPURankingSummaryEntity createCPUSummaryEntity(SessionFactory s, CPUListEntity cpu, Integer rankSum) {
		return createCPUSummaryEntity(s, cpu, rankSum, 1L);
	}
	
	public static List<CPURankingSummaryEntity> getAllCPUSummaryEntities(SessionFactory s) {
		Session session = s.openSession();
		List<CPURankingSummaryEntity> rtnList = null;
		session.beginTransaction();
		rtnList = session.createSelectionQuery("from CPURankingSummaryEntity", CPURankingSummaryEntity.class).getResultList();
		session.getTransaction().commit();
		session.close();
		return rtnList;
	}

	public static CPURankingSummaryEntity getCPUSummaryEntity(SessionFactory s, CPURankingSummaryEntity summary) {
		Session session = s.openSession();
		session.beginTransaction();
		CPURankingSummaryEntity context = session.find(CPURankingSummaryEntity.class, summary);
		session.getTransaction().commit();
		session.close();
		return context;
	}
	
	public static void updateCPUSummaryEntity(SessionFactory s, CPURankingSummaryEntity summary) {
		AbstractCRUDRepository.updateEntity(s, summary);
	}

	public static void deleteCPUSummaryEntity(SessionFactory s, CPURankingSummaryEntity summary) {
		AbstractCRUDRepository.deleteEntity(s, summary);
	}
}
