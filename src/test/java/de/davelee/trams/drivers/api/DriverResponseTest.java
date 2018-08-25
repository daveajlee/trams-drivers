package de.davelee.trams.drivers.api;

import de.davelee.trams.drivers.data.DriverStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the DriverResponse class to make sure it works properly.
 * @author Dave Lee
 */
public class DriverResponseTest {

    @Test
    /**
     * Test class: check that the getters and setters work as expected.
     * Expected result: the getters return the values given by setters.
     */
    public void testGettersAndSetters() {
        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setContractedHours(40);
        driverResponse.setName("Max Mustermann");
        driverResponse.setDateOfBirth("22-09-1996");
        driverResponse.setHourlyWage("20.0");
        driverResponse.setSkills("Driving");
        driverResponse.setStartDate("01-10-2016");
        driverResponse.setCompany("Lee Buses");
        driverResponse.setStatus(DriverStatus.HIRED.getText());
        assertEquals(driverResponse.getContractedHours(), 40);
        assertEquals(driverResponse.getName(), "Max Mustermann");
        assertEquals(driverResponse.getDateOfBirth(), "22-09-1996");
        assertEquals(driverResponse.getHourlyWage(), "20.0");
        assertEquals(driverResponse.getSkills(), "Driving");
        assertEquals(driverResponse.getStartDate(), "01-10-2016");
        assertEquals(driverResponse.getCompany(), "Lee Buses");
        assertEquals(driverResponse.getStatus(), "Hired");
        //Test other status.
        driverResponse.setStatus(DriverStatus.WORKING.getText());
        assertEquals(driverResponse.getStatus(), "In Employment");
        driverResponse.setStatus(DriverStatus.DISMISSED.getText());
        assertEquals(driverResponse.getStatus(), "Dismissed");
        //Test history.
        DriverHistoryResponse driverHistoryResponse = new DriverHistoryResponse();
        driverHistoryResponse.setComment("Hired");
        driverHistoryResponse.setDate("01-09-2016");
        driverHistoryResponse.setStatus(DriverStatus.HIRED.getText());
        List<DriverHistoryResponse> driverHistoryResponseList = new ArrayList<>();
        driverHistoryResponseList.add(driverHistoryResponse);
        driverResponse.setDriverHistoryResponseList(driverHistoryResponseList);
        assertEquals(driverResponse.getDriverHistoryResponseList().size(), 1);
    }

}
