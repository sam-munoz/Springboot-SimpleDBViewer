package org.personal.SimpleDBViewer.CRUDTests;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.SimpleDbViewerApplication;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;

public class CPUListEntityTest {
	SessionFactory sessionFactory;

	// CPUListEntity used for testing across various tests
	List<CPUListEntity> cpulist;

	/*
	 * Method correct sets up session factory for testing and add cpus to the database
	 */
	@BeforeEach
	void setup() {
		sessionFactory = SimpleDbViewerApplication.buildSessionFactory();
		cpulist = new ArrayList<CPUListEntity>();

		// add cpus to the database
		cpulist.add(CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i7-11700KF"));
		cpulist.add(CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i3-8100"));
	}

	/*
	 * Method correct garbage session factory for testing and remove any cpus added in the setup method
	 */
	@AfterEach
	void cleanup() {
		// remove all cpus from the database
		cpulist = CPUListEntityCRUDRepository.getAllCPUs(sessionFactory);
		for (CPUListEntity c : cpulist) CPUListEntityCRUDRepository.deleteCPUListEntity(sessionFactory, c);

		// garbage collect the remaining data
		sessionFactory = null;
		cpulist = null;
	}

	/*
	 * Method attempts to fetch CPULists from the database and checks if the retrieved objects are correct
	 * Ensure that the state of the database is unchanged after the test executed
	 * NOT WORKING CORRECT CURRENTLY
	 */
	@Test
	void testCorrectlyGetAllCPU() {
		// get the list of cpus on the database
		List<CPUListEntity> cpusInDB = CPUListEntityCRUDRepository.getAllCPUs(sessionFactory);

		// assert the cpus retrieved are the cpus that we are suppose to get
		for(CPUListEntity c : cpusInDB) {
			// find cpu in the cpulist. if not found, then we can fail the test by showing that any element in the list will not match CPUListEntity c
			int index = cpulist.indexOf(c);
			if(index == -1) {
				index = 0;
			}

			Assertions.assertThat(c)
					.as("CPU $s does not match CPU %s", c.toString(), cpulist.get(index).toString())
					.isEqualTo(cpulist.get(index));
		}
	}

	/*
	 * Method attempts to create CPULists from the database and checks if object is created and stored the correct values
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyGetCPU() {
		// get cpu
		CPUListEntity cpuInDB = CPUListEntityCRUDRepository.getCPU(sessionFactory, "i3-8100");

		// assert if the correct cpu was returned
		Assertions.assertThat(cpuInDB.getName())
				.as("Wrong CPU name. %s should be i3-8100", cpuInDB.getName())
				.isEqualTo("i3-8100");
	}

	/*
	 * Method attempts to create CPULists from the database and checks if object is created and stored the correct values
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyCreateCPU() {
		// create cpu
		CPUListEntity c = CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i3-10105F");

		// get cpu
		CPUListEntity cpuInDB = CPUListEntityCRUDRepository.getCPU(sessionFactory, c);

		// assert if the correct cpu was returned
		Assertions.assertThat(cpuInDB.getId())
				.as("Wrong CPU id. Id %d should be %d", cpuInDB.getId(), c.getId())
				.isEqualTo(c.getId());
		Assertions.assertThat(cpuInDB.getName())
				.as("Wrong CPU name. %s should be %s", cpuInDB.getName(), c.getName())
				.isEqualTo(c.getName());
	}

	/*
	 * Method attempts to update CPULists from the database and checks if object is correctly updated
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyUpdateCPU() {
		// get a cpu from the database and store its id
		CPUListEntity cpu = CPUListEntityCRUDRepository.getCPU(sessionFactory, "i7-11700KF");
		Long cpuId = cpu.getId();

		// update the cpu
		cpu.setName("i7-10700F");
		CPUListEntityCRUDRepository.updateCPUListEntity(sessionFactory, cpu);

		// get that cpu from the database
		CPUListEntity cpuInDB = CPUListEntityCRUDRepository.getCPU(sessionFactory, cpuId);

		// assert that the cpu name has changed
		Assertions.assertThat(cpuInDB.getId())
				.as("Wrong CPU id. Id %d should be %d", cpuInDB.getId(), cpuId)
				.isEqualTo(cpuId);
		Assertions.assertThat(cpuInDB.getName())
				.as("Wrong CPU name. %s should be i7-10700F", cpuInDB.getName())
				.isEqualTo("i7-10700F");
	}

	/*
	 * Method attempts to delete CPULists from the database and checks if object is correctly deleted
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyDeleteCPU() {
		// get cpu from database
		CPUListEntity cpuInDB = CPUListEntityCRUDRepository.getCPU(sessionFactory, "i3-8100");

		// delete cpu from database
		CPUListEntityCRUDRepository.deleteCPUListEntity(sessionFactory, cpuInDB);

		// ensure that the cpu was deleted
		CPUListEntity newCPU = CPUListEntityCRUDRepository.getCPU(sessionFactory, "i3-8100");
		Assertions.assertThat(newCPU)
				.as("CPU must be null and is not.")
				.isEqualTo(null);
	}
}