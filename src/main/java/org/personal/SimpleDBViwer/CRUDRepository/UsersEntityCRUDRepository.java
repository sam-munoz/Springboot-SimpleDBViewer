package org.personal.SimpleDBViwer.CRUDRepository;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.personal.SimpleDBViewer.domain.UsersEntity;

public class UsersEntityCRUDRepository extends AbstractCRUDRepository {
	public static void createUsersEntity(SessionFactory s, UsersEntity user) {
		AbstractCRUDRepository.createEntity(s, user);
	}

	public static UsersEntity createUsersEntity(SessionFactory s, String userName, String userPasswd) {
		UsersEntity user = new UsersEntity();
		user.setName(userName);
		user.setPasswd(userPasswd);
		AbstractCRUDRepository.createEntity(s, user);
		return user;
	}

	public static UsersEntity createUsersEntity(SessionFactory s, String userName) {
		UsersEntity user = new UsersEntity();
		user.setName(userName);
		user.setPasswd(null);
		AbstractCRUDRepository.createEntity(s, user);
		return user;
	}
	
	public static List<UsersEntity> getAllUsersEntities(SessionFactory s) {
		Session session = s.openSession();
		List<UsersEntity> rtnList = null;
		session.beginTransaction();
		rtnList = session.createSelectionQuery("from UsersEntity", UsersEntity.class).getResultList();
		session.getTransaction().commit();
		session.close();
		return rtnList;
	}

	public static void updateUsersEntity(SessionFactory s, UsersEntity user) {
		AbstractCRUDRepository.updateEntity(s, user);
	}

	public static void deleteUsersEntity(SessionFactory s, UsersEntity user) {
		AbstractCRUDRepository.deleteEntity(s, user);
	}
}