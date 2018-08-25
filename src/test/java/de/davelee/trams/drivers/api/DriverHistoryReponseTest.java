package de.davelee.trams.drivers.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the DriverHistoryResponse class to make sure it works properly.
 * @author Dave Lee
 */
public class DriverHistoryReponseTest {

    @Test
    /**
     * Test case: check that the getters and setters work as expected.
     * Expected result: the getters return the values given by the setters.
     */
    public void testGettersAndSetters() {
        DriverHistoryResponse response = new DriverHistoryResponse();
        response.setComment("Hired!");
        response.setDate("15-08-2016");
        response.setStatus("Hired");
        assertEquals(response.getComment(), "Hired!");
        assertEquals(response.getDate(), "15-08-2016");
        assertEquals(response.getStatus(), "Hired");
    }

}
