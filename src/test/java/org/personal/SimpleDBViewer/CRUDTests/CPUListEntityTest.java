package org.personal.SimpleDBViewer.CRUDTests;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CPUListEntityTest {
	@Autowired
	private CPUListEntityCRUDRepository cpuRepo;

	// list of CPUs used for testing
	List<CPUListEntity> testCPUs;

	/*
	 * Method correct sets up the testing environment and add cpus to the database
	 */
	@BeforeEach
	void setup() {
		// initialize cpu list
		testCPUs = new ArrayList<CPUListEntity>();

		// insert cpu into the database
		testCPUs.add(cpuRepo.createCPU("i7-11700KF"));
	}

	/*
	 * Method correct garbage session factory for testing and remove any cpus added in the setup method
	 */
	@AfterEach
	void cleanup() {
		// get all cpus from the database
		testCPUs = this.cpuRepo.getAllCPUs();
		for(CPUListEntity c : testCPUs) this.cpuRepo.deleteCPU(c);

		// garbage collect the cpu list
		testCPUs = null;
	}

	/*
	 * Method attempts to create CPULists from the database and checks if object is created and stored the correct values
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyGetCPU() {
		// get cpu from the database
		CPUListEntity cpuInDB = this.cpuRepo.getCPU("i7-11700KF");

		// ensure that cpu retrieved matches the correct values
		Assertions.assertThat(cpuInDB.getName())
				.as("CPU name %s does not equal %s", cpuInDB.getName(), "i7-11700KF")
				.isEqualTo("i7-11700KF");

		// get second cpu from the database and check if we get the right entry back
		cpuInDB = this.cpuRepo.getCPU(testCPUs.getFirst().getId());
		Assertions.assertThat(cpuInDB)
				.as("CPU %s does not equal %s", cpuInDB.toString(), testCPUs.getFirst().toString())
				.isEqualTo(testCPUs.getFirst());
	}

	/*
	 * Method attempts to fetch CPULists from the database and checks if the retrieved objects are correct
	 * Ensure that the state of the database is unchanged after the test executed
	 * NOT WORKING CORRECT CURRENTLY
	 */
	@Test
	void testCorrectlyGetAllCPU() {
		// get the list of cpus on the database
		List<CPUListEntity> cpusInDB = this.cpuRepo.getAllCPUs();

		// assert the cpus retrieved are the cpus that we are suppose to get
		for(CPUListEntity c : cpusInDB) {
			// find cpu in the testCPUs. if not found, then we can fail the test by showing that any element in the list will not match CPUListEntity c
			int index = testCPUs.indexOf(c);
			if(index == -1) {
				index = 0;
			}

			Assertions.assertThat(c)
					.as("CPU %s does not match CPU %s", c.toString(), testCPUs.get(index).toString())
					.isEqualTo(testCPUs.get(index));
		}
	}

	/*
	 * Method attempts to create CPULists from the database and checks if object is created and stored the correct values
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyCreateCPU() {
		// create cpu
//		CPUListEntity c = CPUListEntityCRUDRepository.createCPUListEntity(sessionFactory, "i3-10105F");
		CPUListEntity newCPU = this.cpuRepo.createCPU("i3-10105F");

		// get cpu
//		CPUListEntity cpuInDB = CPUListEntityCRUDRepository.getCPU(sessionFactory, c);
		CPUListEntity cpuInDB = this.cpuRepo.getCPU(newCPU);

		// assert if the correct cpu was returned
		Assertions.assertThat(cpuInDB)
				.as("CPU entity was not created because the database returns null when we try to get back our newly created entity")
				.isNotEqualTo(null);
	}

	/*
	 * Method attempts to update CPULists from the database and checks if object is correctly updated
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyUpdateCPU() {
		// get a cpu from the database and store its id
		CPUListEntity cpu = this.cpuRepo.getCPU("i7-11700KF");
		Long cpuId = cpu.getId();

		// update the cpu
		cpu.setName("i7-10700F");
		this.cpuRepo.updateCPU(cpu);

		// get that cpu from the database
		CPUListEntity cpuInDB = this.cpuRepo.getCPU(cpuId);

		// assert that the cpu name has changed
		Assertions.assertThat(cpuInDB)
				.as("CPU %s should equal to CPU %s", cpuInDB.toString(), cpu.toString())
				.isEqualTo(cpu);
	}

	/*
	 * Method attempts to delete CPULists from the database and checks if object is correctly deleted
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 */
	@Test
	void testCorrectlyDeleteCPU() {
		// get cpu from database
		CPUListEntity cpuInDB = this.cpuRepo.getCPU(testCPUs.getFirst());

		// delete cpu from database
		this.cpuRepo.deleteCPU(cpuInDB);

		// ensure that the cpu was deleted
		cpuInDB = this.cpuRepo.getCPU(testCPUs.getFirst());
		Assertions.assertThat(cpuInDB)
				.as("CPU must be null and is not.")
				.isEqualTo(null);
	}
}