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
import org.personal.SimpleDBViewer.Domain.UsersEntity;
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
		testCPUs = new ArrayList<CPUListEntity>();

		testCPUs.add(cpuRepo.createCPU("i7-11700KF"));
		testCPUs.add(cpuRepo.createCPU("i3-8100"));
	}

	/**
	 * Method that cleans up any code written to prepare for the unit tests
	 */
	@AfterEach
	void cleanup() {
		cpuRepo.deleteAllCPUs();
		testCPUs = null;
	}

	/**
	 * Ensure that all non-null fields in <code>u0</code> have the same value as the corresponding fields in <code>u1</code>
	 * @param c0 The <code>UsersEntity</code> object used in the checking
	 * @param c1 The <code>UsersEntity</code> object used in the checking
	 */
	private void nonNullFieldsEqual(CPUListEntity c0, CPUListEntity c1) {
		if(c1.getId() != null) {
			Assertions.assertThat(c0.getId())
					.as("CPU id %d does not match the expected id, %d.", c0.getId(), c1.getId())
					.isEqualTo(c1.getId());
		}
		if(c1.getName() != null) {
			Assertions.assertThat(c0.getName())
					.as("CPU name %s does not match the expected name, %s.", c0.getName(), c1.getName())
					.isEqualTo(c1.getName());
		}
	}

	/**
	 * Unit test for method <code>getCPU(CPUListEntity)</code>
	 * @param cpu CPUListEntity to query from the database
	 */
	@ParameterizedTest
	@MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPUListEntityTestProviders#testGetCPUProvider")
	void testGetCPU(CPUListEntity cpu) {
		CPUListEntity cpuInDB = null;
		try {
			cpuInDB = this.cpuRepo.getCPU(cpu);
		} catch(IllegalArgumentException e) {
			return;
		}

		nonNullFieldsEqual(cpuInDB, cpu);
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

		if(cpuInDB == null) {
			System.out.println("Passed an id into the database that does not correspond to a CPU. getCPU returned a null value and we caught that null value.");
			return;
		}

//		System.out.println(cpuRepo.getAllCPUs());
//		System.out.println(cpuId);
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
		CPUListEntity cpuInDB = null;
		try {
			cpuInDB = this.cpuRepo.getCPU(cpuName);
		} catch(IllegalArgumentException e) {
			return;
		}

		Assertions.assertThat(cpuInDB.getName())
				.as("CPU name %s does not equal name %s", cpuInDB.getName(), cpuName)
				.isEqualTo(cpuName);
	}

	/**
	 * Unit test for method <code>getAllCPUs()</code>
	 */
	@Test
	void testGetAllCPU() {
		List<CPUListEntity> cpusInDB = this.cpuRepo.getAllCPUs();

		for(CPUListEntity c : cpusInDB) {
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

		CPUListEntity cpuInDB = this.cpuRepo.getCPU(cpu);
		nonNullFieldsEqual(cpuInDB, cpu);
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

		CPUListEntity cpuInDB = this.cpuRepo.getCPU(cpuName);
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

		cpuInDB.setName(newCPUName);
		try {
			this.cpuRepo.updateCPU(cpuInDB);
		} catch(IllegalArgumentException e) {
			System.out.println(cpuInDB + " contains some illegal value");
			return;
		}

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

	/**
	 * Unit test for method <code>deleteAllCPUs()</code>
	 */
	@Test
	void testDeleteAllCPUs() {
		cpuRepo.deleteAllCPUs();

		List<CPUListEntity> allCPUs = cpuRepo.getAllCPUs();
		Assertions.assertThat(allCPUs.size())
			.as("There should zero CPUs in the database, but there are %d CPUs in the database", allCPUs.size())
			.isEqualTo(0);
	}
}