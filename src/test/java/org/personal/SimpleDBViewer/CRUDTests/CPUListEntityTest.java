package org.personal.SimpleDBViewer.CRUDTests;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.NoResultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest
public class CPUListEntityTest {
	@Autowired
	private CPUListEntityCRUDRepository cpuRepo;

	// list of CPUs used for testing
	List<CPUListEntity> testCPUs;

	/**
	 * Method that prepares for the unit tests
	 */
	@BeforeEach
	void setup() {
		// initialize cpu list
		testCPUs = new ArrayList<CPUListEntity>();

		// insert cpu into the database
		testCPUs.add(cpuRepo.createCPU("i7-11700KF"));
		testCPUs.add(cpuRepo.createCPU("i3-8100"));
	}

	/**
	 * Method that cleans up any code written to prepare for the unit tests
	 */
	@AfterEach
	void cleanup() {
		// get all cpus from the database
		testCPUs = this.cpuRepo.getAllCPUs();
		for(CPUListEntity c : testCPUs) this.cpuRepo.deleteCPU(c);

		// garbage collect the cpu list
		testCPUs = null;
	}

	/**
	 * Unit test for method <code>getCPU(CPUListEntity)</code>
	 * @param cpu CPUListEntity to query from the database
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
	 * Unit test that tests the method <code>getCPU(Long)</code>
	 * <br />
	 * 		NOTE: The input IDs are guesses at what is actually in the database. Therefore, it is possible that the first two tests fail because the provided ID are not in the database
	 * @param cpuId CPU id used to query the database
	 */
	@ParameterizedTest
	@CsvSource({
			"45,true",
			"47,true",
			"-10,false",
			"943248729,false"
	})
	void testGetCPU(Long cpuId, Boolean validArgument) {
		// get cpu from the database
		CPUListEntity cpuInDB = null;
		try {
			cpuInDB = this.cpuRepo.getCPU(cpuId);
		} catch(IllegalArgumentException e) {
			System.out.println("Input CPU id is not valid a valid argument");
			return;
		}

		// if the test is suppose to fail, terminate test
		if(!validArgument) {
			if(cpuInDB == null) {
				System.out.println("getCPU correctly returned null");
				return;
			} else {
				System.out.println("ERROR: Test should have failed at this point, but it has not.");
			}
		}

		// ensure that cpu retrieved matches the correct values for non-null entries
		System.out.println(cpuRepo.getAllCPUs());
		System.out.println(cpuId);
		Assertions.assertThat(cpuInDB.getId())
				.as("CPU id %d does not equal id %d", cpuInDB.getId(), cpuId)
				.isEqualTo(cpuId);
	}

	/**
	 * Unit test that tests the method <code>getCPU(String)</code>
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

	/**
	 * Unit test for method <code>getAllCPUs()</code>
	 */
	@Test
	void testGetAllCPU() {
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
	 * Unit test for method <code>createCPU(CPUListEntity)</code>
	 * <br />
	 * FOR NOW: assume that get methods function correctly
	 * <br />
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
	 * Unit test for method <code>createCPU(CPUListEntity)</code>
	 * <br />
	 * FOR NOW: assume that get methods function correctly
	 * <br />
	 * 		NOTE TO SELF: try to fix this later
	 * @param cpuName String of the CPU used for the test
	 */
	@ParameterizedTest
	@ValueSource(strings = {
		"i3-10105F",
		"Ryzen 5 5600X",
		"i7-11700KF",
		"i3-8100",
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

	/**
	 * Unit test for method <code>updateCPU(CPUListEntity)</code>
	 * @param cpu CPUListEntity to be updated
	 * @param newCPUName The new name for the variable <code>cpu</code>
	 */
	@ParameterizedTest
	@MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPUListEntityTestProviders#testUpdateCPUProvider")
	void testUpdateCPU(CPUListEntity cpu, String newCPUName) {
		// get a cpu from the database and store its id
		CPUListEntity cpuInDB;
		try {
			cpuInDB = cpuRepo.getCPU(cpu);
		} catch(EmptyResultDataAccessException | NoResultException e) {
			System.out.println("Input CPU does not exist in the database");
			return;
		} catch(IllegalArgumentException e) {
			System.out.println("Input CPU is not a valid argument.");
			return;
		}
		Long cpuId = cpuInDB.getId();

		// update the cpu
		cpuInDB.setName(newCPUName);
		try {
			this.cpuRepo.updateCPU(cpuInDB);
		} catch(IllegalArgumentException e) {
			System.out.println(cpuInDB + " contains some illegal value");
			return;
		}

		// get that cpu from the database and assert that the cpu name has changed
		cpuInDB = this.cpuRepo.getCPU(cpuId);
		Assertions.assertThat(cpuInDB.getName())
				.as("CPU %s should equal to CPU %s", cpuInDB.toString(), newCPUName)
				.isEqualTo(newCPUName);
	}

	/**
	 * Unit test for method <code>deleteCPU(CPUListEntity)</code>
	 * @param cpu CPUListEntity that is removed from the database
	 */
	@ParameterizedTest
	@MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPUListEntityTestProviders#testDeleteCPUProvider")
	void testDeleteCPU(CPUListEntity cpu) {
		// get cpu from database
		CPUListEntity cpuInDB;
		try {
			cpuInDB = this.cpuRepo.getCPU(cpu);
		} catch(EmptyResultDataAccessException | NoResultException | IllegalArgumentException e) {
			cpuInDB = null;
		}

		// delete cpu from database
		try {
			this.cpuRepo.deleteCPU(cpuInDB);
		} catch(IllegalArgumentException e) {
			return;
		}

		// ensure that the cpu was deleted
		try {
			cpuInDB = this.cpuRepo.getCPU(cpu);
		} catch(EmptyResultDataAccessException | NoResultException e) {
			System.out.println("CPU not found in the database");
			return;
		} catch(IllegalArgumentException e) {
			System.out.println("Illegal argument error");
			return;
		}
		Assertions.assertThat(cpuInDB)
				.as("CPU must be null and is not.")
				.isEqualTo(null);
	}
}