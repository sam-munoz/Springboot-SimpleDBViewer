package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.personal.SimpleDBViewer.Domain.UsersEntity;

public class UsersEntityCRUDRepository extends AbstractCRUDRepository {
//	public static void createUsersEntity(SessionFactory s, UsersEntity user) throws IllegalArgumentException {
//		// valid input
//		if(user == null) {
//			throw new IllegalArgumentException("Input User object cannot be null");
//		}
//
//		AbstractCRUDRepository.createEntity(s, user);
//	}
//
//	public static UsersEntity createUsersEntity(SessionFactory s, String userName, String userPasswd) throws IllegalArgumentException {
//		// validate user input. userName cannot be empty
//		if(userName.isEmpty()) {
//			throw new IllegalArgumentException("userName cannot be a empty, i.e. length of userName must be >= 1");
//		}
//
//		UsersEntity user = new UsersEntity();
//		user.setName(userName);
//		user.setPasswd(userPasswd);
//		AbstractCRUDRepository.createEntity(s, user);
//		return user;
//	}
//
//	public static UsersEntity createUsersEntity(SessionFactory s, String userName) throws IllegalArgumentException {
//		// validate user input. userName cannot be empty
//		if(userName.isEmpty()) {
//			throw new IllegalArgumentException("userName cannot be a empty, i.e. length of userName must be >= 1");
//		}
//
//		UsersEntity user = new UsersEntity();
//		user.setName(userName);
//		user.setPasswd(null);
//		AbstractCRUDRepository.createEntity(s, user);
//		return user;
//	}
//
//	public static List<UsersEntity> getAllUsersEntities(SessionFactory s) {
//		Session session = s.openSession();
//		List<UsersEntity> rtnList = null;
//		try {
//			session.beginTransaction();
//			rtnList = session.createSelectionQuery("from UsersEntity", UsersEntity.class).getResultList();
//			session.getTransaction().commit();
//		} catch(Exception e) {
//			if(session.getTransaction().isActive()) session.getTransaction().rollback();
//			throw e;
//		} finally {
//			session.close();
//		}
//		return rtnList;
//	}
//
//	public static void updateUsersEntity(SessionFactory s, UsersEntity user) throws IllegalArgumentException {
//		if(user == null) {
//			throw new IllegalArgumentException("Input User object cannot be null");
//		}
//
//		AbstractCRUDRepository.updateEntity(s, user);
//	}
//
//	public static void deleteUsersEntity(SessionFactory s, UsersEntity user) throws IllegalArgumentException {
//		if(user == null) {
//			throw new IllegalArgumentException("Input User object cannot be null");
//		}
//
//		AbstractCRUDRepository.deleteEntity(s, user);
//	}
}