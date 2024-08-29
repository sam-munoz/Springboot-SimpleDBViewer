package org.personal.SimpleDBViewer.CRUDTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.personal.SimpleDBViewer.CRUDRepository.CPUListEntityCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersCPURankingCRUDRepository;
import org.personal.SimpleDBViewer.CRUDRepository.UsersEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest
public class UsersCPURankingEntityTests {
    @Autowired
    private UsersCPURankingCRUDRepository rankingRepo;

    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    @Autowired
    private UsersEntityCRUDRepository userRepo;

    private List<UsersCPURankingEntity> testRanking;

    @BeforeEach
    void setup() {
        testRanking = new ArrayList<UsersCPURankingEntity>();

        CPUListEntity c0 = cpuRepo.createCPU("i7-11700KF");
        CPUListEntity c1 = cpuRepo.createCPU("i3-8100");
        UsersEntity u0 = userRepo.createUser("User 1");
        UsersEntity u1 = userRepo.createUser("User 2");
        UsersEntity u2 = userRepo.createUser("User 3");

        UsersCPURankingEntity r0 = new UsersCPURankingEntity(c0, u0, 7);
        UsersCPURankingEntity r1 = new UsersCPURankingEntity(c0, u1, 6);
        UsersCPURankingEntity r2 = new UsersCPURankingEntity(c0, u2, 8);
        UsersCPURankingEntity r3 = new UsersCPURankingEntity(c1, u0, 4);
        UsersCPURankingEntity r4 = new UsersCPURankingEntity(c1, u1, 9);
        UsersCPURankingEntity r5 = new UsersCPURankingEntity(c1, u2, 10);
        System.out.println(c0);
        System.out.println(u0);
        testRanking.add(rankingRepo.createRanking(r0));
        testRanking.add(rankingRepo.createRanking(r1));
        testRanking.add(rankingRepo.createRanking(r2));
        testRanking.add(rankingRepo.createRanking(r3));
        testRanking.add(rankingRepo.createRanking(r4));
        testRanking.add(rankingRepo.createRanking(r5));
        for(UsersCPURankingEntity r : testRanking) System.out.println(r);
    }

    @AfterEach
    void cleanup() {
//        for(UsersCPURankingEntity r : testRanking) rankingRepo.deleteRanking(r);
        testRanking = null;
    }

    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.UsersCPURankingEntityTestsProvider#testCreateRankingProvider")
    void testCreateRanking(UsersCPURankingEntity ranking) {
        UsersCPURankingEntity newlyCreatedRanking = null;
        try {
            newlyCreatedRanking = rankingRepo.createRanking(ranking);
        } catch(NullPointerException e) {
            System.out.println("You passed null into createRanking method and caught a null point exception");
            return;
        } catch(IllegalArgumentException e) {
            System.out.println("You passed a ranking into createRanking with one or more null fields");
        }

        Assertions.assertThat(newlyCreatedRanking)
                .as("Newly created ranking is null")
                .isNotEqualTo(null);
    }

    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.UsersCPURankingEntityTestsProvider#testGetRankingProvider")
    void testGetRanking(UsersCPURankingEntity ranking) {
        UsersCPURankingEntity queryRanking = null;
        try {
            queryRanking = rankingRepo.getRanking(ranking);
        } catch(NullPointerException e) {
            System.out.println("Passed null to the getRanking method. Caught the null pointer exception");
            return;
        }

//        System.out.println(ranking);
//        System.out.println(ranking.getCpu());
//        System.out.println(ranking.getUser());
//        System.out.println(queryRanking);
//        System.out.println(queryRanking.getCpu());
//        System.out.println(queryRanking.getUser());
        if(ranking.getCpu().getId() != null) {
            Assertions.assertThat(queryRanking.getCpu().getId())
                    .as("Ranking cpu id %d is not equal to the correct ranking cpu id %d", queryRanking.getCpu().getId(), ranking.getCpu().getId())
                    .isEqualTo(ranking.getCpu().getId());
        }
        if(ranking.getCpu().getName() != null) {
            Assertions.assertThat(queryRanking.getCpu().getName())
                    .as("Ranking cpu name %s is not equal to the correct ranking cpu name %s", queryRanking.getCpu().getName(), ranking.getCpu().getName())
                    .isEqualTo(ranking.getCpu().getName());
        }
        if(ranking.getUser().getId() != null) {
            Assertions.assertThat(queryRanking.getUser().getId())
                    .as("Ranking user id %d is not equal to the correct ranking user id %d", queryRanking.getUser().getId(), ranking.getUser().getId())
                    .isEqualTo(ranking.getUser().getId());
        }
        if(ranking.getUser().getName() != null) {
            Assertions.assertThat(queryRanking.getUser().getName())
                    .as("Ranking user name %s is not equal to the correct ranking user name %s", queryRanking.getUser().getName(), ranking.getUser().getName())
                    .isEqualTo(ranking.getUser().getName());
        }
        if(ranking.getUser().getPasswd() != null) {
            Assertions.assertThat(queryRanking.getUser().getPasswd())
                    .as("Ranking user password %s is not equal to the correct ranking user password %s", queryRanking.getUser().getPasswd(), ranking.getUser().getPasswd())
                    .isEqualTo(ranking.getUser().getPasswd());
        }
        if(ranking.getRanking() != null) {
            Assertions.assertThat(queryRanking.getRanking())
                    .as("Ranking score %d is not equal to the correct ranking score %d", queryRanking.getRanking(), ranking.getRanking())
                    .isEqualTo(ranking.getRanking());

        }
    }

    @Test
    void testGetAllRankings() {
        // get all rankings
        List<UsersCPURankingEntity> allRankingsInDB = rankingRepo.getAllRankings();

        // ensure that all rankings are fetched
        Assertions.assertThat(allRankingsInDB.size())
                .as("Query returned %d number of rankings from the database, but there should be %d rankings", allRankingsInDB.size(), testRanking.size())
                .isEqualTo(testRanking.size());
        for(UsersCPURankingEntity r : allRankingsInDB) {
            int index = testRanking.indexOf(r);
            if(index == -1) index = 0;
            Assertions.assertThat(r)
                    .as("Ranking object %s should have value %s", r.toString(), testRanking.get(index).toString())
                    .isEqualTo(testRanking.get(index));
        }
    }

    // NOT REALLY WORKING
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.UsersCPURankingEntityTestsProvider#testDeleteRankingProvider")
    void testDeleteRanking(UsersCPURankingEntity ranking) {
        // HUGELY PROBLEMATIC LINE OF CODE. DON'T CARE FOR FIXING THIS
        ranking = rankingRepo.getRanking(ranking);
        try {
            rankingRepo.deleteRanking(ranking);
        } catch(NullPointerException e) {
            System.out.println("Passed null to the getRanking method. Caught the null pointer exception");
            return;
        }

        UsersCPURankingEntity queryRanking = rankingRepo.getRanking(ranking);
        Assertions.assertThat(queryRanking)
                .as("Ranking should be null, but it is not")
                .isEqualTo(null);
    }

    @Test
    void testDeleteAllRankings() {
        // remove all rankings from the database
        rankingRepo.deleteAllRankings();

        // ensure that no rankings remain in the database
        List<UsersCPURankingEntity> allRankingsInDB = rankingRepo.getAllRankings();
        Assertions.assertThat(allRankingsInDB.size())
                .as("There should be no rankings left in the database, but the database has %d rankings.", allRankingsInDB.size())
                .isEqualTo(0);
    }
}
