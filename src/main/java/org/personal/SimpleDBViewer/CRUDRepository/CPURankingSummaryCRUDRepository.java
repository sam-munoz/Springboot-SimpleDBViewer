package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import jakarta.persistence.EntityManagerFactory;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CPURankingSummaryCRUDRepository {
    @Autowired
    private AbstractCRUDRepository repo;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public CPURankingSummaryEntity createSummary(CPURankingSummaryEntity summary) throws IllegalArgumentException {
        if(summary == null) {
            throw new IllegalArgumentException("Summary input cannot be null");
        }

        return (CPURankingSummaryEntity) this.repo.createEntity(summary);
    }

    public CPURankingSummaryEntity createSummary(CPUListEntity cpu, Integer rankSum) throws IllegalArgumentException {
        if(cpu == null) {
            throw new IllegalArgumentException("CPU input cannot be null");
        } else if(rankSum == null) {
            throw new IllegalArgumentException("Rank sum input cannot be null");
        }

        CPURankingSummaryEntity summary = new CPURankingSummaryEntity(cpu.getId());
        summary.setRankSum(rankSum);
        return (CPURankingSummaryEntity) this.repo.createEntity(summary);
    }

    public CPURankingSummaryEntity getSummary(CPURankingSummaryEntity summary) throws IllegalArgumentException {
        if(summary == null) {
            throw new IllegalArgumentException("Summary object cannot be null");
        }

        return this.repo.getEntityById(summary.getId(), CPURankingSummaryEntity.class);
    }

//	public static List<CPURankingSummaryEntity> getAllCPUSummaryEntities(SessionFactory s) {
//		Session session = s.openSession();
//		List<CPURankingSummaryEntity> rtnList = null;
//		try {
//			session.beginTransaction();
//			rtnList = session.createSelectionQuery("from CPURankingSummaryEntity", CPURankingSummaryEntity.class).getResultList();
//			session.getTransaction().commit();
//		} catch (Exception e) {
//			if(session.getTransaction().isActive())	session.getTransaction().rollback();
//			throw e;
//		} finally {
//			session.close();
//		}
//		return rtnList;
//	}
//
//	public static CPURankingSummaryEntity getCPUSummaryEntity(SessionFactory s, CPURankingSummaryEntity summary) {
//		CPURankingSummaryEntity context = null;
//		Session session = s.openSession();
//		try {
//			session.beginTransaction();
//			context = session.find(CPURankingSummaryEntity.class, summary);
//			session.getTransaction().commit();
//		} catch(Exception e) {
//			if(session.getTransaction().isActive())	session.getTransaction().rollback();
//			throw e;
//		} finally {
//			session.close();
//		}
//		return context;
//	}
//
//	public static void updateCPUSummaryEntity(SessionFactory s, CPURankingSummaryEntity summary) {
//		AbstractCRUDRepository.updateEntity(s, summary);
//	}

    public void deleteSummary(CPURankingSummaryEntity summary) throws IllegalArgumentException {
        if(summary == null) {
            throw new IllegalArgumentException("Summary object cannot be null");
        }

        this.repo.deleteEntity(summary.getId(), CPURankingSummaryEntity.class);
    }
}
