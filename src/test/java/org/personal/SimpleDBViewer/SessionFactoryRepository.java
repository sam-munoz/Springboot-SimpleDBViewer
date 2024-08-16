package org.personal.SimpleDBViewer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.personal.SimpleDBViewer.domain.CPUListEntity;


public class SessionFactoryRepository {
	protected static SessionFactory buildSessionFactory() {
		SessionFactory sf = new Configuration()
				.addAnnotatedClass(CPUListEntity.class)
				.buildSessionFactory();
		return sf;
	}
}
