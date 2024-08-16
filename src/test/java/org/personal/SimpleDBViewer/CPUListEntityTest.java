package org.personal.SimpleDBViewer;

import java.util.List;

import org.hibernate.SessionFactory;
import org.personal.SimpleDBViwer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.SimpleDbViewerApplication;
import org.personal.SimpleDBViewer.domain.CPUListEntity;

public class CPUListEntityTest {
	public static void test0() {
		// create SessionFactory
		SessionFactory sessionFactory = SessionFactoryRepository.buildSessionFactory();
		
		// create cpu entities
		CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i7-11700KF");
		CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i3-8100");
		SimpleDbViewerApplication.printTable(sessionFactory, 0);
		
		// update an entity
		List<CPUListEntity> l = CPUListEntityCRUDRepository.getAllCPUs(sessionFactory);
		if(!l.isEmpty()) {
			l.getFirst().setName("i7-10700F");
			CPUListEntityCRUDRepository.updateCPUListEntity(sessionFactory, l.getFirst());
		}
		
		SimpleDbViewerApplication.printTable(sessionFactory, 0);
		
		// remove all entries in the database
		l = CPUListEntityCRUDRepository.getAllCPUs(sessionFactory);
		while(!l.isEmpty()) {
			CPUListEntityCRUDRepository.deleteCPUListEntity(sessionFactory, l.getFirst());
			l.removeFirst();
		}

		SimpleDbViewerApplication.printTable(sessionFactory, 0);
	}
}
