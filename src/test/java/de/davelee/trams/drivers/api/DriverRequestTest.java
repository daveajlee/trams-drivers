package de.davelee.trams.drivers.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the DriverRequest class to make sure it works properly.
 * @author Dave Lee
 */
public class DriverRequestTest {

    @Test
    /**
     * Test case: check that the getters and setters work as expected.
     * Expected result: the getters return the values given by the setters.
     */
    public void testGettersAndSetters() {
        DriverRequest driverRequest = new DriverRequest();
        driverRequest.setContractedHours(20);
        assertEquals(driverRequest.getContractedHours(), 20);
        driverRequest.setDateOfBirth("29/02/1988");
        assertEquals(driverRequest.getDateOfBirth(), "29/02/1988");
        driverRequest.setName("Joe Bloggs");
        assertEquals(driverRequest.getName(), "Joe Bloggs");
        driverRequest.setHourlyWage("20");
        assertEquals(driverRequest.getHourlyWage(), "20");
        driverRequest.setSkills("Driving");
        assertEquals(driverRequest.getSkills(), "Driving");
        driverRequest.setStartDate("01/09/2016");
        assertEquals(driverRequest.getStartDate(), "01/09/2016");
        driverRequest.setCompany("Lee Buses");
        assertEquals(driverRequest.getCompany(), "Lee Buses");
    }

}
