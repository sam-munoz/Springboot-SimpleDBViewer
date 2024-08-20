package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.personal.SimpleDBViewer.domain.CPUListEntity;
import org.personal.SimpleDBViewer.domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.domain.UsersCPURankingId;
import org.personal.SimpleDBViewer.domain.UsersEntity;

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
		session.beginTransaction();
		rtnList = session.createSelectionQuery("from UsersCPURankingEntity", UsersCPURankingEntity.class).getResultList();
		session.getTransaction().commit();
		session.close();
		return rtnList;
	}

	public static void deleteRankingEntity(SessionFactory s, UsersCPURankingEntity ranking) {
		AbstractCRUDRepository.deleteEntity(s, ranking);
	}
	
	public static UsersCPURankingEntity getRanking(SessionFactory s, UsersCPURankingEntity ranking) {
		Session session = s.openSession();
		session.beginTransaction();
		UsersCPURankingEntity context = session.getReference(UsersCPURankingEntity.class, ranking.getId());
		session.getTransaction().commit();
		session.close();
		return context;
	}

	public static UsersCPURankingEntity getRankingEagerly(SessionFactory s, UsersCPURankingEntity ranking) {
		Session session = s.openSession();
		session.beginTransaction();
		UsersCPURankingEntity context = session.getReference(UsersCPURankingEntity.class, ranking.getId());
		context.getId().getCpu();
		context.getId().getUser();
		session.getTransaction().commit();
		session.close();
		return context;
	}
}
