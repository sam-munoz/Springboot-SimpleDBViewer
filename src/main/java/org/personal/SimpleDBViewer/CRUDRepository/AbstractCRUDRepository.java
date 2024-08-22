package org.personal.SimpleDBViewer.CRUDRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;

import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AbstractCRUDRepository {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private interface TransactionOperationVoid {
		public void op(EntityManager em);
	}

	private interface TransactionOperationObject {
		public Object op(EntityManager em);
	}

	private void inTransaction(TransactionOperationVoid function) {
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

	private Object inTransaction(TransactionOperationObject function) {
		Object rtnObj = null;
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			rtnObj = (Object) function.op(entityManager);
			tx.commit();
		} catch(Exception e) {
			if(tx.isActive()) tx.rollback();
			throw e;
		} finally {
			entityManager.close();
		}
		return rtnObj;
	}

	protected Object createEntity(Object obj) {
		TransactionOperationObject function = entityManager -> {
			entityManager.persist(obj);
			return obj;
		};
		return this.inTransaction(function);
	}

	protected <T> T getEntityById(Object id, Class<T> className) {
		TransactionOperationObject function = entityManager -> {
			return entityManager.find(className, id); // this kind of sucks. it would be nice to not have to go into the db for this
		};
		return (T) this.inTransaction(function); // this line of code could be problematic. we will ignore this for now
	}

	protected static void updateEntity(SessionFactory s, Object obj) {
		s.inTransaction(session -> {
			session.merge(obj);
		});
	}

	protected Object updateEntity(Object obj) {
		TransactionOperationObject function = entityManager -> {
			return entityManager.merge(obj);
		};
		return this.inTransaction(function);
	}

	protected <T> void deleteEntity(Object id, Class<T> className) throws IllegalArgumentException {
		TransactionOperationVoid function = entityManager -> {
			Object obj = entityManager.getReference(className, id);
			if(obj == null) {
				throw new IllegalArgumentException("The arguments passed do not correspond to a entity stored in the database.");
			}
			entityManager.remove(obj);
		};
		this.inTransaction(function);
	}
}
