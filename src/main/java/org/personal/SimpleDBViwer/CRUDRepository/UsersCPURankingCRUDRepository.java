package org.personal.SimpleDBViwer.CRUDRepository;

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

	public static UsersCPURankingEntity createRankingEntity(SessionFactory s, UsersEntity user, CPUListEntity cpu, Integer ranking) {
		UsersCPURankingEntity r = new UsersCPURankingEntity(new UsersCPURankingId(user.getId(), cpu.getId()), ranking);
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
}
