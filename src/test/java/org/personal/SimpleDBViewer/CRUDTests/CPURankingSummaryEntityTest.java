package org.personal.SimpleDBViewer.CRUDTests;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.CPURankingSummaryCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CPURankingSummaryEntityTest {
    @Autowired
    private CPURankingSummaryCRUDRepository summaryRepo;

    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    private List<CPUListEntity> testCPUs;
    private List<CPURankingSummaryEntity> testSummaries;

    /*
     * Method correct sets up session factory for testing
     */
    @BeforeEach
    void setup() {
        // initialize test lists
        testCPUs = new ArrayList<CPUListEntity>();
        testSummaries = new ArrayList<CPURankingSummaryEntity>();

        // initialize the database
        testCPUs.add(cpuRepo.createCPU("i7-11700KF"));
        testCPUs.add(cpuRepo.createCPU("i3-8100"));
        testSummaries.add(summaryRepo.createSummary(new CPURankingSummaryEntity(testCPUs.get(0).getId(), 40, 8L)));
        testSummaries.add(summaryRepo.createSummary(new CPURankingSummaryEntity(testCPUs.get(1).getId(), 20, 14L)));

        System.out.println(testSummaries.getFirst());
        System.out.println(testSummaries.getLast());
    }

    /*
     * Method correct garbage session factory for testing
     */
    @AfterEach
    void cleanup() {
        // remove all created entities in the setup method
        testCPUs = cpuRepo.getAllCPUs();
        for(CPUListEntity c : testCPUs) cpuRepo.deleteCPU(c);

        testCPUs = null;
        testSummaries = null;
    }

    /*
     * Method attempts to fetch Ranking from the database and checks if the retrieved objects are correct
     */
    @Test
    void testGetRanking() {
        // get a ranking
        CPURankingSummaryEntity summaryInDB = summaryRepo.getSummary(testSummaries.getFirst());

        // check if the database returned the correct summary
        Assertions.assertThat(summaryInDB)
                .as("Summary %s does not equal the correct summary %s", summaryInDB.toString(), testSummaries.getFirst().toString())
                .isEqualTo(testSummaries.getFirst());
    }

    /*
     * Method attempts to create Ranking from the database and checks if object is created and stored the correct values
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyCreateRanking() {}

    /*
     * Method attempts to update Ranking from the database and checks if object is correctly updated
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyUpdateRanking() {}

    /*
     * Method attempts to delete Ranking from the database and checks if object is correctly deleted
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyDeleteRanking() {}
}
