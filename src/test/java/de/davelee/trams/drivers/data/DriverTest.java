package de.davelee.trams.drivers.data;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This class tests the Driver class to make sure it works properly.
 * @author Dave Lee
 */
public class DriverTest {

    @Test
    /**
     * Test case: check that the getters and setters work as expected.
     * Expected result: the getters return the values given by the setters.
     */
    public void testGettersAndSetters() {
        Driver driver = new Driver();
        driver.setContractedHours(20);
        assertEquals(driver.getContractedHours(), 20);
        driver.setDateOfBirth(LocalDate.of(1988, 2, 29));
        assertEquals(driver.getDateOfBirth(), LocalDate.of(1988,2,29));
        driver.setName("Joe Bloggs");
        assertEquals(driver.getName(), "Joe Bloggs");
        driver.setHourlyWage(BigDecimal.valueOf(20.0));
        assertEquals(driver.getHourlyWage(), BigDecimal.valueOf(20.0));
        driver.setSkills("Driving");
        assertEquals(driver.getSkills(), "Driving");
        driver.setStartDate(LocalDate.of(2016, 9, 1));
        assertEquals(driver.getStartDate(), LocalDate.of(2016, 9, 1));
        driver.setCompany("Lee Buses");
        assertEquals(driver.getCompany(), "Lee Buses");
        driver.setStatus(DriverStatus.HIRED);
        assertEquals(driver.getStatus().getText(), "Hired");
        DriverHistory driverHistory = new DriverHistory();
        driverHistory.setComment("Hired!");
        driverHistory.setDate(LocalDate.of(2016,8,15));
        driverHistory.setStatus(DriverStatus.HIRED);
        driver.addHistory(driverHistory);
        assertEquals(driver.getDriverHistoryList().size(), 1);
        driver.incrementDriverHours(LocalDate.of(2016, 10, 4), 5);
        assertEquals(driver.getHoursWorkedForDate(LocalDate.of(2016, 10, 4)), new Integer(5));
        assertNull(driver.getHoursWorkedForDate(LocalDate.of(2016, 10, 3)));
    }

}
