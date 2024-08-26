package org.personal.SimpleDBViewer.CRUDTests;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.personal.SimpleDBViewer.CRUDTests.Providers.CPUListEntityTestProviders;

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
		testCPUs.add(cpuRepo.createCPU("i3-8100"));
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
	@ParameterizedTest
	@MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPUListEntityTestProviders#testGetCPUProvider")
	void testGetCPU(CPUListEntity cpu) {
		// get cpu from the database
		CPUListEntity cpuInDB = null;
		try {
			cpuInDB = this.cpuRepo.getCPU(cpu);
		} catch(IllegalArgumentException e) {
			// is the edge cases. throw correct exception. terminate test
			return;
		}

		// ensure that cpu retrieved matches the correct values for non-null entries
		if(cpu.getId() != null) {
			Assertions.assertThat(cpuInDB.getId())
					.as("CPU id %d does not equal name %d", cpuInDB.getId(), cpu.getId())
					.isEqualTo(cpu.getName());
		}
		if(!cpu.getName().isEmpty()) {
			Assertions.assertThat(cpuInDB.getName())
					.as("CPU name %s does not equal name %s", cpuInDB.getName(), cpu.getName())
					.isEqualTo(cpu.getName());
		}
	}

	/**
	 * Unit test that tests the method `getCPU(String cpuName)`
	 * @param cpuName CPU name used to search the database
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"i7-11700KF",
			"i3-8100",
			""
	})
	void testGetCPU(String cpuName) {
		// get cpu from the database
		CPUListEntity cpuInDB = null;
		try {
			cpuInDB = this.cpuRepo.getCPU(cpuName);
		} catch(IllegalArgumentException e) {
			// is the edge cases. throw correct exception. terminate test
			return;
		}

		// ensure that cpu retrieved matches the correct values for non-null entries
		Assertions.assertThat(cpuInDB.getName())
				.as("CPU name %s does not equal name %s", cpuInDB.getName(), cpuName)
				.isEqualTo(cpuName);
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

	/**
	 * Unit test for method createCPU(CPUListEntity)
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 * @param cpu CPUListEntity used for the test
	 */
	@ParameterizedTest
	@MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPUListEntityTestProviders#testCreateCPUProvider")
	void testCreateCPU(CPUListEntity cpu) {
		try {
			cpuRepo.createCPU(cpu);
		} catch(IllegalArgumentException e) {
			return;
		}

		// get cpu
		CPUListEntity cpuInDB = this.cpuRepo.getCPU(cpu);

		// assert if the correct cpu was returned
		if(cpu.getId() != null) {
			Assertions.assertThat(cpuInDB.getId())
					.as("CPU returned has id %d, but it should have id %d.", cpuInDB.getId(), cpu.getId())
					.isEqualTo(cpu.getId());
		}
		if(cpu.getName() != null) {
			Assertions.assertThat(cpuInDB.getName())
					.as("CPU returned has name %s, but it should have name %s.", cpuInDB.getName(), cpu.getName())
					.isEqualTo(cpu.getName());
		}
	}

	/**
	 * Unit test for method createCPU(CPUListEntity)
	 * FOR NOW: assume that get methods function correctly
	 * 		NOTE TO SELF: try to fix this later
	 * @param cpuName String of the CPU used for the test
	 */
	@ParameterizedTest
	@ValueSource(strings = {
		"i7-11700KF",
		"i3-8100",
		"i3-10105F",
		"Ryzen 5 5600X",
		""
	})
	void testCreateCPU(String cpuName) {
		try {
			cpuRepo.createCPU(cpuName);
		} catch(IllegalArgumentException e) {
			return;
		}

		// get cpu
		CPUListEntity cpuInDB = this.cpuRepo.getCPU(cpuName);

		// assert if the correct cpu was returned
		Assertions.assertThat(cpuInDB.getName())
				.as("CPU returned has name %s, but it should have name %s.", cpuInDB.getName(), cpuName)
				.isEqualTo(cpuName);
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