package de.davelee.trams.drivers.data;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the DriverHistory class to make sure it works properly.
 * @author Dave Lee
 */
public class DriverHistoryTest {

    @Test
    /**
     * Test case: check that the getters and setters work as expected.
     * Expected result: the getters return the values given by the setters.
     */
    public void testGettersAndSetters() {
        DriverHistory driverHistory = new DriverHistory();
        driverHistory.setDate(LocalDate.of(2016, 10, 1));
        driverHistory.setComment("Hired!");
        driverHistory.setStatus(DriverStatus.HIRED);
        assertEquals(driverHistory.getDate(), LocalDate.of(2016, 10, 1));
        assertEquals(driverHistory.getComment(), "Hired!");
        assertEquals(driverHistory.getStatus(), DriverStatus.HIRED);
    }

}
