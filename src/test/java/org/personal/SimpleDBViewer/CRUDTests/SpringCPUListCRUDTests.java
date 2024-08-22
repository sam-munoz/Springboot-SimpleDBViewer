package org.personal.SimpleDBViewer.CRUDTests;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SpringCPUListCRUDTests {
    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    private List<CPUListEntity> testCPUs;

    // Populate if needed
    @BeforeEach
    void setup() {
        // initialize cpu list
        testCPUs = new ArrayList<CPUListEntity>();

        // insert cpu into the database
        CPUListEntity c0 = new CPUListEntity();
        c0.setName("i7-11700KF");
        cpuRepo.createCPUListEntity("i7-11700K");
        testCPUs.add(c0);
    }

    // Populate if needed
    @AfterEach
    void cleanup() {
        // remove all cpus from the database
        testCPUs = null;
        testCPUs = cpuService.getAllCPUs();
        for(CPUListEntity c : testCPUs) cpuService.deleteCPU(c);

    }

    @Test
    void testThatEntityManagerFactoryFunctions() {
        Assertions.assertThat(cpuService)
                .as("If SpringBoot did its job, cpuService should not be null")
                .isNotEqualTo(null);
    }

    @Test
    void testCreateCPU() {
        // get cpu from the database and check if the returned cpu matches what we inserted
        CPUListEntity cpuInDB = cpuService.getCPU(testCPUs.getFirst());
        Assertions.assertThat(cpuInDB)
                .as("cpuInDB should not be null")
                .isNotEqualTo(null);

        Assertions.assertThat(cpuInDB.getName())
                .as("CPU name %s is not equal to %s", cpuInDB.getName(), testCPUs.getFirst().getName())
                .isEqualTo(testCPUs.getFirst().getName());

    }

    @Test
    void testGetAllCPUs() {
        // all cpus in the database
        List<CPUListEntity> cpulist = cpuService.getAllCPUs();
        Assertions.assertThat(cpulist.size())
                .as("List length should equal %d, not %d", testCPUs.size(), cpulist.size())
                .isEqualTo(testCPUs.size());
        for(CPUListEntity c : cpulist) {
            int index = testCPUs.indexOf(c);
            if(index == -1) index = 0;
            Assertions.assertThat(c)
                    .as("CPU %s is not among the test CPUs", c.toString())
                    .isEqualTo(testCPUs.get(index));
        }

        // remove all cpus in the database
        for(CPUListEntity c : testCPUs) cpuService.deleteCPU(c);
    }

    @Test
    void testDeleteCPU() {
        // get all cpus in the database at this time
        List<CPUListEntity> allCPUSInDB = cpuService.getAllCPUs();
        int cpuCountBeforeDeletion = allCPUSInDB.size();

        Assertions.assertThat(allCPUSInDB.size())
                .as("Setup method failed. CPU list size should be greater than zero, not %d", allCPUSInDB.size())
                .isGreaterThan(0);

        // delete a CPU
        cpuService.deleteCPU(allCPUSInDB.getFirst());

        // get all cpus in the database again and check the cpu count against the old value
        allCPUSInDB = null; // garbage collect
        allCPUSInDB = cpuService.getAllCPUs();
        Assertions.assertThat(allCPUSInDB.size())
                .as("CPU count should be equal to %d, not %d", cpuCountBeforeDeletion, allCPUSInDB.size())
                .isEqualTo(cpuCountBeforeDeletion-1);
    }

    @Test
    void testUpdateCPU() {
        // get cpu from the db
        CPUListEntity cpu = cpuService.getCPU(testCPUs.getFirst());
        long cpuId = cpu.getId();

        // update the cpu
        cpu.setName("i3-8100");
        cpuService.updateCPU(cpu);

        // get the same cpu again from the database and check if it matches our updates
        cpu = new CPUListEntity(); // garbage collect
        cpu.setId(cpuId);
        cpu = cpuService.getCPU(cpu);
        Assertions.assertThat(cpu.getName())
                .as("CPU %s should have name 'i3-8100'", cpu.getName());
    }
}
