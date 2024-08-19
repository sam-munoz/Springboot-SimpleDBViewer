package org.personal.SimpleDBViewer.CRUDRepository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractCRUDRepository {
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
