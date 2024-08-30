package org.personal.SimpleDBViewer.CRUDTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import java.util.stream.Stream;

@SpringBootTest
public class UsersCPURankingEntityTests {
    @Autowired
    private UsersCPURankingCRUDRepository rankingRepo;

    @Autowired
    private CPUListEntityCRUDRepository cpuRepo;

    @Autowired
    private UsersEntityCRUDRepository userRepo;

    private List<UsersCPURankingEntity> testRanking;

    private List<CPUListEntity> testCPUs;

    private List<UsersEntity> testUsers;

    @BeforeEach
    void setup() {
        testRanking = new ArrayList<UsersCPURankingEntity>();
        testCPUs = new ArrayList<CPUListEntity>();
        testUsers = new ArrayList<UsersEntity>();

        CPUListEntity c0 = cpuRepo.createCPU(new CPUListEntity("i7-11700KF"));
        CPUListEntity c1 = cpuRepo.createCPU(new CPUListEntity("i3-8100"));
        testCPUs.add(c0);
        testCPUs.add(c1);
        UsersEntity u0 = userRepo.createUser(new UsersEntity("User 1"));
        UsersEntity u1 = userRepo.createUser(new UsersEntity("User 2"));
        UsersEntity u2 = userRepo.createUser(new UsersEntity("User 3"));
        testUsers.add(u0);
        testUsers.add(u1);
        testUsers.add(u2);

        UsersCPURankingEntity r0 = new UsersCPURankingEntity(c0, u0, 7);
        UsersCPURankingEntity r1 = new UsersCPURankingEntity(c0, u1, 6);
        UsersCPURankingEntity r2 = new UsersCPURankingEntity(c0, u2, 9);
        UsersCPURankingEntity r3 = new UsersCPURankingEntity(c1, u0, 2);
        UsersCPURankingEntity r4 = new UsersCPURankingEntity(c1, u1, 5);
        UsersCPURankingEntity r5 = new UsersCPURankingEntity(c1, u2, 10);
        testRanking.add(rankingRepo.createRanking(r0));
        testRanking.add(rankingRepo.createRanking(r1));
        testRanking.add(rankingRepo.createRanking(r2));
        testRanking.add(rankingRepo.createRanking(r3));
        testRanking.add(rankingRepo.createRanking(r4));
        testRanking.add(rankingRepo.createRanking(r5));
    }

    @AfterEach
    void cleanup() {
        rankingRepo.deleteAllRankings();
        cpuRepo.deleteAllCPUs();
        userRepo.deleteAllUsers();
        testRanking = null;
        testCPUs = null;
        testUsers = null;
    }

    private void nonNullFieldsEqual(UsersCPURankingEntity r0, UsersCPURankingEntity r1) {
        Assertions.assertThat(r0)
                .as("r0 is null")
                .isNotEqualTo(null);
        Assertions.assertThat(r1)
                .as("r1 is null")
                .isNotEqualTo(null);
        Assertions.assertThat(r0.getCpu())
                .as("r0 cpu is null")
                .isNotEqualTo(null);
        Assertions.assertThat(r0.getUser())
                .as("r0 user is null")
                .isNotEqualTo(null);
        Assertions.assertThat(r1.getCpu())
                .as("r1 cpu is null")
                .isNotEqualTo(null);
        Assertions.assertThat(r1.getUser())
                .as("r1 user is null")
                .isNotEqualTo(null);
        if(r1.getCpu().getId() != null) {
            Assertions.assertThat(r0.getCpu().getId())
                    .as("Ranking cpu id %d is not equal to the correct ranking cpu id %d", r0.getCpu().getId(), r1.getCpu().getId())
                    .isEqualTo(r1.getCpu().getId());
        }
        if(r1.getCpu().getName() != null) {
            Assertions.assertThat(r0.getCpu().getName())
                    .as("Ranking cpu name %s is not equal to the correct ranking cpu name %s", r0.getCpu().getName(), r1.getCpu().getName())
                    .isEqualTo(r1.getCpu().getName());
        }
        if(r1.getUser().getId() != null) {
            Assertions.assertThat(r0.getUser().getId())
                    .as("Ranking user id %d is not equal to the correct ranking user id %d", r0.getUser().getId(), r1.getUser().getId())
                    .isEqualTo(r1.getUser().getId());
        }
        if(r1.getUser().getName() != null) {
            Assertions.assertThat(r0.getUser().getName())
                    .as("Ranking user name %s is not equal to the correct ranking user name %s", r0.getUser().getName(), r1.getUser().getName())
                    .isEqualTo(r1.getUser().getName());
        }
        if(r1.getUser().getPasswd() != null) {
            Assertions.assertThat(r0.getUser().getPasswd())
                    .as("Ranking user password %s is not equal to the correct ranking user password %s", r0.getUser().getPasswd(), r1.getUser().getPasswd())
                    .isEqualTo(r1.getUser().getPasswd());
        }
        if(r1.getRanking() != null) {
            Assertions.assertThat(r0.getRanking())
                    .as("Ranking score %d is not equal to the correct ranking score %d", r0.getRanking(), r1.getRanking())
                    .isEqualTo(r1.getRanking());

        }
    }

    @ParameterizedTest
    @CsvSource({
            "1,0,9",
            "1,1,9",
            "1,2,9",
    })
    void testCreateRanking(int cpuIndex, int userIndex, Integer rank) {
        UsersCPURankingEntity ranking = new UsersCPURankingEntity(testCPUs.get(cpuIndex), testUsers.get(userIndex), rank);

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
    @CsvSource({
            "0",
            "3",
            "2",
            "5"
    })
    void testGetRanking(int rankingIndex) {
        UsersCPURankingEntity ranking = testRanking.get(rankingIndex);
        UsersCPURankingEntity queryRanking = null;
        try {
            queryRanking = rankingRepo.getRanking(ranking);
        } catch(NullPointerException e) {
            System.out.println("Passed null to the getRanking method. Caught the null pointer exception");
            return;
        }

        Assertions.assertThat(queryRanking)
                .as("Query ranking equals the original ranking")
                .isNotEqualTo(ranking);
        Assertions.assertThat(queryRanking.getId())
                .as("The queried id %s does not equal the correct value of %s", queryRanking.getId(), ranking.getId())
                .isEqualTo(ranking.getId());
        Assertions.assertThat(queryRanking.getRanking())
                .as("The query ranking %d does not equal the correct value of %d", queryRanking.getRanking(), ranking.getRanking())
                .isEqualTo(ranking.getRanking());
    }

    @Test
    void testGetAllRankings() {
        // get all rankings
        List<UsersCPURankingEntity> allRankingsInDB = rankingRepo.getAllRankings();

        // ensure that all rankings are fetched (kind of)
        Assertions.assertThat(allRankingsInDB.size())
            .as("Query returned %d number of rankings from the database, but there should be %d rankings", allRankingsInDB.size(), testRanking.size())
            .isEqualTo(testRanking.size());
    }

    // NOT REALLY WORKING
    @ParameterizedTest
    @CsvSource({
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "-1",
    })
    void testDeleteRanking(int rankingIndex) {
        UsersCPURankingEntity ranking = null;
        if(rankingIndex >= 0) ranking = testRanking.get(rankingIndex);
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