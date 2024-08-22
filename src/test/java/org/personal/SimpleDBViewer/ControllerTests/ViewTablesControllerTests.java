package org.personal.SimpleDBViewer.ControllerTests;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.Controllers.ViewTablesController;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ViewTablesControllerTests {
    @Autowired
    private ViewTablesController tableController;

    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    private List<CPUListEntity> testCPUs;

    @BeforeEach
    void setup() {
        // initialize testCPUs list
        testCPUs = new ArrayList<CPUListEntity>();

        // create a bunch of cpus and store it into the database
        testCPUs.add(cpuRepo.createCPU("i7-11700KF"));
        testCPUs.add(cpuRepo.createCPU("i3-8100"));
        testCPUs.add(cpuRepo.createCPU("Ryzen 5 5600X"));
    }

    @AfterEach
    void cleanup() {
        // empty the database of all cpus
        testCPUs = cpuRepo.getAllCPUs();
        for(CPUListEntity c : testCPUs) cpuRepo.deleteCPU(c);

        // garbage collect testCPUs
        testCPUs = null;
    }

    @Test
    void testViewCPUTable() {
        // get all CPUs from the table controller bean
        List<CPUListEntity> cpuTable = tableController.viewCPUTable();

        // validate that the table returns exactly all the cpus created in the setup method and returns no more CPUs
        for(CPUListEntity c : cpuTable) {
            int index = testCPUs.indexOf(c);
            if(index == -1) index = 0;

            Assertions.assertThat(c)
                    .as("CPU %s should be %s", c.toString(), testCPUs.get(index))
                    .isEqualTo(testCPUs.get(index));
        }
        Assertions.assertThat(cpuTable.size())
                .as("Table has the incorrect number of entities. Returned table has %d entries, but should have %d entities", cpuTable.size(), testCPUs.size())
                .isEqualTo(testCPUs.size());
    }
}
