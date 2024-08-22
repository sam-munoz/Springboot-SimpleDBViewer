package org.personal.SimpleDBViewer.CRUDTests;

import java.util.List;
import java.util.ArrayList;

import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.SimpleDBViewer.CRUDRepository.UsersEntityCRUDRepository;
import org.personal.SimpleDBViewer.Domain.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersEntityTest {
    @Autowired
    private UsersEntityCRUDRepository userRepo;

    private List<UsersEntity> testUsers;

    /*
     * Method correct sets up session factory for testing
     */
    @BeforeEach
    void setup() {
        // list to users to use for testing
        testUsers = new ArrayList<UsersEntity>();

        // populate the database with users
        testUsers.add(userRepo.createUser("Sam"));
        testUsers.add(userRepo.createUser("Ricardo", "best"));
        testUsers.add(userRepo.createUser("Israel"));
    }

    /*
     * Method correct garbage session factory for testing
     */
    @AfterEach
    void cleanup() {
        // get all users from the database and remove them from the database
        testUsers = userRepo.getAllUsers();
        for(UsersEntity u : testUsers) userRepo.deleteUser(u);

        // garbage collect testUsers
        testUsers = null;
    }

    /*
     * Method attempts to fetch Users from the database and checks if the retrieved objects are correct
     */
    @Test
    void testGetUser() {
        // get user from the database
        UsersEntity userInDB = userRepo.getUser(testUsers.getFirst().getId());

        // check if this user stores the correct values
        Assertions.assertThat(userInDB)
                .as("User %s should be equal to %s, but is not", userInDB.toString(), testUsers.getFirst().toString())
                .isEqualTo(testUsers.getFirst());
    }

    /*
     * Method attempts to fetch Users from the database and checks if the retrieved objects are correct
     */
    @Test
    void testGetAllUser() {
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

    /*
     * Method attempts to create Users from the database and checks if object is created and stored the correct values
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCreateUser() {
        // create a new user
        UsersEntity newUser = userRepo.createUser("Leo", "the_special_one");

        // attempt to get user from the database
        UsersEntity userInDB = userRepo.getUser(newUser.getId());

        // verify that this object matches what we created
        Assertions.assertThat(userInDB)
                .as("User %s does not match the expected user, %s.", userInDB.toString(), newUser.toString())
                .isEqualTo(newUser);
    }

    /*
     * Method attempts to update Users from the database and checks if object is correctly updated
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testUpdateUser() {
        // get a user from the database
        UsersEntity firstUser = userRepo.getUser(testUsers.getFirst().getId());

        // update the user and query changes to the database
        firstUser.setPasswd("first here");
        userRepo.updateUser(firstUser);

        // throw out the user and get the same user from the database
        firstUser = null;
        firstUser = userRepo.getUser(testUsers.getFirst().getId());

        // check to make sure that the changes we made appear
        Assertions.assertThat(firstUser.getName())
                .as("User's name is %s, but should be %s", firstUser.getName(), testUsers.getFirst().getName())
                .isEqualTo(testUsers.getFirst().getName());
        Assertions.assertThat(firstUser.getPasswd())
                .as("User's password is %s, but should be %s", firstUser.getPasswd(), "first here")
                .isEqualTo("first here");
    }

    /*
     * Method attempts to delete Users from the database and checks if object is correctly deleted
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testDeleteUser() {
        // delete first user in the testUsers list
        Long deletedUserId = testUsers.getFirst().getId();
        userRepo.deleteUser(testUsers.getFirst());

        // attempt to get removed user
        UsersEntity userInDB = userRepo.getUser(deletedUserId);
        Assertions.assertThat(userInDB)
                .as("This object should be null, but it is not.")
                .isEqualTo(null);
    }
}
