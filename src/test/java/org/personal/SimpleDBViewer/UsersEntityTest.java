package org.personal.SimpleDBViewer;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Test
public class UsersEntityTest {
    SessionFactory sessionFactory;

    /*
     * Method correct sets up session factory for testing
     */
    @BeforeEach
    void setup() {}

    /*
     * Method correct garbage session factory for testing
     */
    @AfterEach
    void cleanup() {}

    /*
     * Method attempts to fetch Users from the database and checks if the retrieved objects are correct
     */
    @Test
    void testCorrectlyGetUser() {}

    /*
     * Method attempts to create Users from the database and checks if object is created and stored the correct values
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyCreateUser() {}

    /*
     * Method attempts to update Users from the database and checks if object is correctly updated
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyUpdateUser() {}

    /*
     * Method attempts to delete Users from the database and checks if object is correctly deleted
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyDeleteUser() {}
}
