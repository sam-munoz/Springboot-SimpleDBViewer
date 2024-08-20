package org.personal.SimpleDBViewer;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.personal.SimpleDBViewer.SimpleDbViewerApplication;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.domain.CPUListEntity;

public class CPUListEntityTest {
	SessionFactory sessionFactory;

	/*
	 * Method correct sets up session factory for testing
	 */
	@BeforeEach
	void setup() {}

	/*
	 * Method correct garbage session factory for testing
	 */
	@AfterEach
	void cleanup() {}

	/*
	 * Method attempts to fetch CPULists from the database and checks if the retrieved objects are correct
	 */
	@Test
	void testCorrectlyGetCPU() {}

	/*
	 * Method attempts to create CPULists from the database and checks if object is created and stored the correct values
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyCreateCPU() {}

	/*
	 * Method attempts to update CPULists from the database and checks if object is correctly updated
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyUpdateCPU() {}

	/*
	 * Method attempts to delete CPULists from the database and checks if object is correctly deleted
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyDeleteCPU() {}


//	public static void test0() {
//
//		// create cpu entities
//		CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i7-11700KF");
//		CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i3-8100");
//		SimpleDbViewerApplication.printTable(sessionFactory, 0);
//
//		// update an entity
//		List<CPUListEntity> l = CPUListEntityCRUDRepository.getAllCPUs(sessionFactory);
//		if(!l.isEmpty()) {
//			l.getFirst().setName("i7-10700F");
//			CPUListEntityCRUDRepository.updateCPUListEntity(sessionFactory, l.getFirst());
//		}
//
//		SimpleDbViewerApplication.printTable(sessionFactory, 0);
//
//		// remove all entries in the database
//		l = CPUListEntityCRUDRepository.getAllCPUs(sessionFactory);
//		while(!l.isEmpty()) {
//			CPUListEntityCRUDRepository.deleteCPUListEntity(sessionFactory, l.getFirst());
//			l.removeFirst();
//		}
//
//		SimpleDbViewerApplication.printTable(sessionFactory, 0);
//	}
}
