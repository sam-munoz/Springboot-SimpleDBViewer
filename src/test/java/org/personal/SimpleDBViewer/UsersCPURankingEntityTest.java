package org.personal.SimpleDBViewer;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsersCPURankingEntityTest {
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
     * Method attempts to fetch Ranking Summary from the database and checks if the retrieved objects are correct
     */
    @Test
    void testCorrectlyGetSummary() {}

    /*
     * Method attempts to create Ranking Summary from the database and checks if object is created and stored the correct values
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyCreateSummary() {}

    /*
     * Method attempts to update Ranking Summary from the database and checks if object is correctly updated
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyUpdateSummary() {}

    /*
     * Method attempts to delete Ranking Summary from the database and checks if object is correctly deleted
     * FOR NOW: assume that get methods function correctly
     * 		NOTE TO SELF: try to fix this later
     */
    @Test
    void testCorrectlyDeleteSummary() {}
}
