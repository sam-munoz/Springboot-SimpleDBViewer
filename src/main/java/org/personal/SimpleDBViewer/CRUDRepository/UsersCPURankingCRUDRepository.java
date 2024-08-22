package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingId;
import org.personal.SimpleDBViewer.Domain.UsersEntity;

public class UsersCPURankingCRUDRepository {
	public static void createRankingEntity(SessionFactory s, UsersCPURankingEntity ranking) {
		AbstractCRUDRepository.createEntity(s, ranking);
	}

	public static UsersCPURankingEntity createRankingEntity(SessionFactory s, CPUListEntity cpu, UsersEntity user, Integer ranking) {
		UsersCPURankingEntity r = new UsersCPURankingEntity(new UsersCPURankingId(cpu, user), ranking);
		AbstractCRUDRepository.createEntity(s, r);
		return r;
	}

	public static List<UsersCPURankingEntity> getAllUsersEntities(SessionFactory s) {
		Session session = s.openSession();
		List<UsersCPURankingEntity> rtnList = null;
		try {
			session.beginTransaction();
			rtnList = session.createSelectionQuery("from UsersCPURankingEntity", UsersCPURankingEntity.class).getResultList();
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session.getTransaction().isActive()) session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
		return rtnList;
	}

	public static void deleteRankingEntity(SessionFactory s, UsersCPURankingEntity ranking) {
		AbstractCRUDRepository.deleteEntity(s, ranking);
	}
	
	public static UsersCPURankingEntity getRanking(SessionFactory s, UsersCPURankingEntity ranking) {
		UsersCPURankingEntity context = null;
		Session session = s.openSession();
		try {
			session.beginTransaction();
			context = session.getReference(UsersCPURankingEntity.class, ranking.getId());
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session.getTransaction().isActive()) session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
		return context;
	}

	public static UsersCPURankingEntity getRankingEagerly(SessionFactory s, UsersCPURankingEntity ranking) {
		UsersCPURankingEntity context = null;
		Session session = s.openSession();
		try {
			session.beginTransaction();
			context = session.getReference(UsersCPURankingEntity.class, ranking.getId());
			context.getId().getCpu();
			context.getId().getUser();
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session.getTransaction().isActive())	 session.getTransaction().rollback();
			throw e;
		} finally {
			session.close();
		}
		return context;
	}
}
