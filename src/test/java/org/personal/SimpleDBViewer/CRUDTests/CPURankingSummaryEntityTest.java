package org.personal.SimpleDBViewer.CRUDTests;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.personal.SimpleDBViewer.CRUDRepository.CPURankingSummaryCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

@SpringBootTest
public class CPURankingSummaryEntityTest {
    @Autowired
    private CPURankingSummaryCRUDRepository summaryRepo;

    public static List<CPURankingSummaryEntity> testSummaries;

    /**
     * Method that sets up the database for testing
     */
    @BeforeEach
    void setup() {
        // initialize test lists
        testSummaries = new ArrayList<CPURankingSummaryEntity>();

        // initialize the database
        CPURankingSummaryEntity s0 = new CPURankingSummaryEntity(new CPUListEntity("i7-11700KF"), 40, 8L);
        CPURankingSummaryEntity s1 = new CPURankingSummaryEntity(new CPUListEntity("i3-8100"), 20, 14L);
        summaryRepo.createSummary(s0);
        summaryRepo.createSummary(s1);
        testSummaries.add(s0);
        testSummaries.add(s1);
    }

    /**
     * Method that removes all <code>CPURankingSummaryEntity</code> from the database
     */
    @AfterEach
    void cleanup() {
        summaryRepo.deleteAllSummaries();
        testSummaries = null;
    }

    /**
     * Unit test for method <code>getSummary(CPURankingSummaryEntity)</code>
     * @param summary New <code>CPURankingSummaryEntity</code> to create in the database
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPURankingSummaryEntityTestProvider#testGetSummaryProvider")
    void testGetSummary(CPURankingSummaryEntity summary) {
        // get a ranking
        CPURankingSummaryEntity summaryInDB = null;
        try {
            summaryInDB = summaryRepo.getSummary(summary);
        } catch(NullPointerException e) {
            System.out.println("Caught a null point exception");
            return;
        } catch(IllegalArgumentException e) {
            System.out.println("Caught an illegal argument exception");
            return;
        }

        // check if the database returned the correct summary
        if(summary.getId() != null) {
            Assertions.assertThat(summaryInDB.getId())
                    .as("Summary id %d does not equal the correct summary id %d", summaryInDB.getId(), summary.getId())
                    .isEqualTo(summary.getId());
        }
        if(summary.getRankSum() != null) {
            Assertions.assertThat(summaryInDB.getRankSum())
                    .as("Summary rank sum %d does not equal the correct summary rank sum %d", summaryInDB.getRankSum(), summary.getRankSum())
                    .isEqualTo(summary.getRankSum());
        }
        if(summary.getCount() != null) {
            Assertions.assertThat(summaryInDB.getCount())
                    .as("Summary rank sum %d does not equal the correct summary rank sum %d", summaryInDB.getRankSum(), summary.getRankSum())
                    .isEqualTo(summary.getRankSum());
        }
        if(summary.getCPU() != null) {
            if(summary.getId() != null) {
                Assertions.assertThat(summaryInDB.getCPU().getId())
                        .as("Summary id %d does not equal the correct id of %d", summaryInDB.getCPU().getId(), summary.getCPU().getId())
                        .isEqualTo(summary.getCPU().getId());
            }
            if(summary.getCPU().getName() != null) {
                Assertions.assertThat(summaryInDB.getCPU().getName())
                        .as("Related CPU name %s does not match the correct name of %s", summaryInDB.getCPU().getName(), summary.getCPU().getName())
                        .isEqualTo(summary.getCPU().getName());
            }
        }
    }

    /**
     * Unit test for method <code>getAllSummaries()</code>
     */
    @Test
    void testGetAllSummaries() {
        // get all summaries from the database
        List<CPURankingSummaryEntity> allSummariesInDB = summaryRepo.getAllSummaries();

        // assert that the correct data was returned
        Assertions.assertThat(allSummariesInDB.size())
                .as("There are %d summaries in the database, but the database should have %d summaries", allSummariesInDB.size(), testSummaries.size())
                .isEqualTo(testSummaries.size());
        for(CPURankingSummaryEntity s : allSummariesInDB) {
            int index = testSummaries.indexOf(s);
            if(index == -1) index = 0;
            Assertions.assertThat(s)
                    .as("Summary %s is not equal to summary %s", s.toString(), testSummaries.get(index).toString())
                    .isEqualTo(testSummaries.get(index));
        }
    }

    /**
     * Unit test for method <code>createSummary(CPURankingSummaryEntity)</code>
     * @param summary <code>CPURankingSummaryEntity</code> to create
     * @param passNull Parameter that determines whether or not to pass null to the method <code>createSummary(CPURankingSummaryEntity)</code>
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPURankingSummaryEntityTestProvider#testCreateSummaryProvider")
    void testCreateSummary(CPURankingSummaryEntity summary, boolean passNull) {
        // pass null into the test function if passNull parameter is true
        if(passNull) summary = null;

        // store into the database
        try {
            summaryRepo.createSummary(summary);
        } catch(NullPointerException e) {
            System.out.println("Caught a null pointer exception");
            return;
        } catch(IllegalArgumentException e) {
            System.out.println("Caught an illegal argument exception");
            return;
        }

        // ensure that the summary has a valid primary key
        Assertions.assertThat(summary.getId())
            .as("Summary id is null, which implies that the summary has not been persisted yet.")
            .isNotEqualTo(null);
        System.out.println(summary);
    }

    /**
     * Unit test for method <code>updateSummary(CPURankingSummmaryEntity)</code>
     * @param summary <code>CPURankingSummaryEntity</code> to be updated
     * @param deletedOldSummary Indictaes whether method <code>updateSummary(CPURankingSummaryEntity)</code> will delete summary <code>summary</code>
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPURankingSummaryEntityTestProvider#testUpdateSummaryProvider")
    void testUpdateSummary(CPURankingSummaryEntity summary, boolean deletedOldSummary) {
        // copy input summary data to check
        Long summaryId = summary.getId();
        Integer summaryRankSum = summary.getRankSum();
        Long summaryCPUId = summary.getCPU().getId();
        String summaryCPUName = summary.getCPU().getName();

        // update summary
        CPURankingSummaryEntity updatedSummary = summaryRepo.updateSummary(summary);

        // ensure all fields of the summary are properly updated
        if(!deletedOldSummary && summaryId != null) {
            Assertions.assertThat(updatedSummary.getId())
                    .as("Summary id %d does not match id %d", updatedSummary.getId(), summaryId)
                    .isEqualTo(summaryId);
        }
        if(summaryRankSum != null) {
            Assertions.assertThat(updatedSummary.getRankSum())
                    .as("Summary rank sum %d does not match sum %d", updatedSummary.getRankSum(), summaryRankSum)
                    .isEqualTo(summaryRankSum);
        }
        if(!deletedOldSummary && summaryCPUId != null) {
            Assertions.assertThat(updatedSummary.getCPU().getId())
                    .as("Summary cpu id %d does not match id %d", updatedSummary.getCPU().getId(), summaryCPUId)
                    .isEqualTo(summaryCPUId);
        }
        if(summaryCPUName != null) {
            Assertions.assertThat(updatedSummary.getCPU().getName())
                    .as("Summary cpu name %s does not match name %s", updatedSummary.getCPU().getName(), summaryCPUName)
                    .isEqualTo(summaryCPUName);
        }
    }

    /**
     * Unit test for method <code>deleteSummary(CPURankingSummaryEntity)</code>
     * @param summary <code>CPURankingSummaryEntity</code> to delete
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.CPURankingSummaryEntityTestProvider#testDeleteSummaryProvider")
    void testDeleteSummary(CPURankingSummaryEntity summary) {
        // delete a summary
        try {
            summaryRepo.deleteSummary(summary);
        } catch(NullPointerException e) {
            System.out.println("Caught null pointer exception");
            return;
        } catch(IllegalArgumentException e) {
            System.out.println("Caught illegal argument exception");
            return;
        } catch(JpaObjectRetrievalFailureException e) {
            System.out.println("Exception occurs only when attempting to fetch a summary that does not exist in the database.");
            return;
        }

        // ensure that the summary has been deleted
        CPURankingSummaryEntity summaryInDB = summaryRepo.getSummary(summary);
        Assertions.assertThat(summaryInDB)
                .as("Database should return a null values for this CPU, but it has not")
                .isEqualTo(null);
    }

    /**
     * Unit test for method <code>allDeleteSummaries()</code>
     */
    @Test
    void testAllDeleteSummaries() {
        // delete all summaries in the database
        summaryRepo.deleteAllSummaries();

        // ensure that no summaries persist in the database
        List<CPURankingSummaryEntity> allSummariesInDB = summaryRepo.getAllSummaries();
        Assertions.assertThat(allSummariesInDB.size())
                .as("There should be no summaries in the database, but there are %d summaries in the database", allSummariesInDB.size())
                .isEqualTo(0);
    }
}