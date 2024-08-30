package org.personal.SimpleDBViewer.CRUDTests;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.NoResultException;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.personal.SimpleDBViewer.CRUDRepository.UsersEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest
public class UsersEntityTest {
    @Autowired
    private UsersEntityCRUDRepository userRepo;

    private List<UsersEntity> testUsers;

    /**
     * Method for setting up a unit test
     */
    @BeforeEach
    void setup() {
        testUsers = new ArrayList<UsersEntity>();

        testUsers.add(userRepo.createUser(new UsersEntity("Sam")));
        testUsers.add(userRepo.createUser(new UsersEntity("Ricardo", "best")));
        testUsers.add(userRepo.createUser(new UsersEntity("Israel")));
    }

    /**
     * Method for deallocating memory created for unit testing
     */
    @AfterEach
    void cleanup() {
        userRepo.deleteAllUsers();
        testUsers = null;
    }

    /**
     * Ensure that all non-null fields in <code>u0</code> have the same value as the corresponding fields in <code>u1</code>
     * @param u0 The <code>UsersEntity</code> object used in the checking
     * @param u1 The <code>UsersEntity</code> object used in the checking
     */
    private void nonNullFieldsEqual(UsersEntity u0, UsersEntity u1) {
        if(u1.getId() != null) {
            Assertions.assertThat(u0.getId())
                    .as("User id %d does not match the expected id, %d.", u0.getId(), u1.getId())
                    .isEqualTo(u1.getId());
        }
        if(u1.getName() != null) {
            Assertions.assertThat(u0.getName())
                    .as("User name %s does not match the expected name, %s.", u0.getName(), u1.getName())
                    .isEqualTo(u1.getName());
        }
        if(u1.getPasswd() != null) {
            Assertions.assertThat(u0.getPasswd())
                    .as("User password %s does not match the expected password, %s.", u0.getPasswd(), u1.getPasswd())
                    .isEqualTo(u1.getPasswd());
        }
    }

    /**
     * Unit test for method <code>createUser(UsersEntity)</code>
     * @param user <code>UsersEntity</code> to create in the database
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.UsersEntityTestsProvider#testCreateUserProvider")
    void testCreateUser(UsersEntity user) {
        UsersEntity newUser = null;
        try {
            userRepo.createUser(user);
        } catch(NullPointerException e) {
           System.out.println("Attempted to pass null into the createUser method. Caught null pointer exception");
           return;
        }

        UsersEntity queryUser = userRepo.getUser(user);
        nonNullFieldsEqual(queryUser, user);
    }

    /**
     * Unit test for method <code>getUser(UsersEntity)</code>
     * @param user <code>UsersEntity</code> to query the database
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.UsersEntityTestsProvider#testGetUserProvider")
    void testGetUser(UsersEntity user) {
        UsersEntity queryUser = null;
        try {
            queryUser = userRepo.getUser(user);
        } catch(NullPointerException e) {
            System.out.println("Attempted to pass null to method getUser. Caught null pointer exception");
            return;
        } catch(IllegalArgumentException e) {
            System.out.println("Attempted to pass a User to method getUser without any data that can identify the User. Caught illegal argument error.");
            return;
        }
        if(queryUser == null) {
            System.out.println("Attempted to pass a User not in the database. Caught no result exception");
            return;
        }
        nonNullFieldsEqual(queryUser, user);
    }

    /**
     * Unit test for method <code>getAllUsers</code>
     */
    @Test
    void testGetAllUsers() {
        // get all users from the database
        List<UsersEntity> allUsersInDB = userRepo.getAllUsers();

        // check if the users found in the database matches what is done the in the setup method
        Assertions.assertThat(allUsersInDB.size())
                .as("The number of users in the database is %d, but hibernate is returning %d.", testUsers.size(), allUsersInDB.size())
                .isEqualTo(testUsers.size());
        for(UsersEntity u : allUsersInDB) {
            int index = testUsers.indexOf(u);
            if(index == -1) index = 0;
            Assertions.assertThat(u)
                    .as("User %s should be equal to %s, but is not.", u.toString(), testUsers.get(index).toString())
                    .isEqualTo(testUsers.get(index));
        }
    }

    /**
     * Unit test for method <code>updateUser(UsersEntity)</code>
     * @param user The <code>UsersEntity</code> object to update in the database
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.UsersEntityTestsProvider#testUpdateUserProvider")
    void testUpdateUser(UsersEntity user) {
        // let always the first element in testUsers list. hence, update user parameter to have the id of the first element in the aforementioned list
        Long updatedUserId = testUsers.getFirst().getId();
        user.setId(updatedUserId);

        // update the user and query changes to the database
        userRepo.updateUser(user);

        // throw out the user and get the same user from the database
        UsersEntity queryUser = userRepo.getUser(testUsers.getFirst().getId());
        nonNullFieldsEqual(queryUser, user);
        System.out.println(queryUser == user);
        System.out.println(queryUser);
        System.out.println(user);
    }

    /**
     * Unit test for method <code>deleteUser(UsersEntity)</code>
     * @param user <code>UsersEntity</code> to delete from the database
     */
    @ParameterizedTest
    @MethodSource("org.personal.SimpleDBViewer.CRUDTests.Providers.UsersEntityTestsProvider#testDeleteUserProvider")
    void testDeleteUser(UsersEntity user) {
        userRepo.deleteUser(user);

        UsersEntity queryUser = userRepo.getUser(user);
        Assertions.assertThat(queryUser)
                .as("This object should be null, but it is not.")
                .isEqualTo(null);
    }

    /**
     * Unit test for method <code>deleteAllUsers()</code>
     */
    @Test
    void testDeleteAllUsers() {
        userRepo.deleteAllUsers();

        List<UsersEntity> allUsersInDB = userRepo.getAllUsers();
        Assertions.assertThat(allUsersInDB.size())
                .as("The database should have no Users, but for reason contains %d Users", allUsersInDB.size())
                .isEqualTo(0);
    }
}