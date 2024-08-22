package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AbstractCRUDRepository {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private interface TransactionOperation {
		public void op(EntityManager em);
	}

	private void inTransaction(TransactionOperation function) {
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			function.op(entityManager);
			tx.commit();
		} catch(Exception e) {
			if(tx.isActive()) tx.rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	protected void createEntity(Object obj) {
		this.inTransaction(entityManager -> {
			entityManager.persist(obj);
		});

//		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
//		EntityTransaction tx = entityManager.getTransaction();
//		try {
//			tx.begin();
//			entityManager.persist(obj);
//			tx.commit();
//		} catch(Exception e) {
//			if(tx.isActive()) tx.rollback();
//			throw e;
//		} finally {
//			entityManager.close();
//		}
	}


	protected static void createEntity(SessionFactory s, Object obj) {
		s.inTransaction(session -> {
			session.persist(obj);
		});
	}
	
	protected static void updateEntity(SessionFactory s, Object obj) {
		s.inTransaction(session -> {
			session.merge(obj);
		});
	}
	
	protected static void deleteEntity(SessionFactory s, Object obj) {
		s.inTransaction(session -> {
			session.remove(obj);
		});
	}
}
