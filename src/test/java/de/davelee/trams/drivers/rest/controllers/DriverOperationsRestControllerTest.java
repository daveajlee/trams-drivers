package de.davelee.trams.drivers.rest.controllers;

import de.davelee.trams.drivers.TramsDriversApplication;
import de.davelee.trams.drivers.api.DriverRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Test the driver operations rest controller and in particular methods the validateInput method.
 * @author Dave Lee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TramsDriversApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverOperationsRestControllerTest {

    @Autowired
    private DriverOperationsRestController driverOperationsRestController;

    @Test
    /**
     * Test case: contracted hours is too low.
     * Expected result: false (input is not valid).
     */
    public void contractedHoursTooLow() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setContractedHours(6);
        validDriverRequest.setDateOfBirth("29-02-1988");
        validDriverRequest.setName("Joe Bloggs");
        validDriverRequest.setHourlyWage("70");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("01-09-2016");
        assertFalse(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: contracted hours is too high.
     * Expected result: false (input is not valid).
     */
    public void contractedHoursTooHigh() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setContractedHours(60);
        validDriverRequest.setDateOfBirth("29-02-1988");
        validDriverRequest.setName("Joe Bloggs");
        validDriverRequest.setHourlyWage("70");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("01-09-2016");
        assertFalse(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: date of birth is invalid.
     * Expected result: false (input is not valid).
     */
     public void invalidDateofBirth() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setContractedHours(20);
        validDriverRequest.setDateOfBirth("30-02-1988");
        validDriverRequest.setName("Joe Bloggs");
        validDriverRequest.setHourlyWage("70");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("01-09-2016");
        assertFalse(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: name is null.
     * Expected result: false (input is not valid).
     */
     public void nameIsNull() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setContractedHours(20);
        validDriverRequest.setDateOfBirth("29-02-1988");
        validDriverRequest.setHourlyWage("70");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("01-09-2016");
        assertFalse(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: salary is too high.
     * Expected result: false (input is not valid).
     */
    public void salaryIsTooHigh() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setName("Joe Bloggs");
        validDriverRequest.setContractedHours(20);
        validDriverRequest.setDateOfBirth("29-02-1988");
        validDriverRequest.setHourlyWage("7000");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("01-09-2016");
        assertFalse(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: salary is too low.
     * Expected result: false (input is not valid).
     */
    public void salaryIsTooLow() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setName("Joe Bloggs");
        validDriverRequest.setContractedHours(20);
        validDriverRequest.setDateOfBirth("29-02-1988");
        validDriverRequest.setHourlyWage("1");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("01-09-2016");
        assertFalse(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: start date is invalid.
     * Expected result: false (input is not valid).
     */
    public void invalidStartDate() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setName("Joe Bloggs");
        validDriverRequest.setContractedHours(20);
        validDriverRequest.setDateOfBirth("29-02-1988");
        validDriverRequest.setHourlyWage("70");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("31-09-2016");
        assertFalse(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: driver request object is valid.
     * Expected result: true (input is valid).
     */
    public void inputIsValid() {
        DriverRequest validDriverRequest = new DriverRequest();
        validDriverRequest.setContractedHours(20);
        validDriverRequest.setDateOfBirth("29-02-1988");
        validDriverRequest.setName("Joe Bloggs");
        validDriverRequest.setHourlyWage("70");
        validDriverRequest.setSkills("Driving");
        validDriverRequest.setStartDate("01-09-2016");
        assertTrue(driverOperationsRestController.validateInput(validDriverRequest));
    }

    @Test
    /**
     * Test case: Retrieve a driver with a name null or date of birth null.
     * Expected result: bad request.
     */
    public void getDriverHasInvalidProperties() {
        assertEquals(driverOperationsRestController.getDriver(null, "20-09-1996", "Lee Buses").getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(driverOperationsRestController.getDriver("Joe Bloggs", null, "Lee Buses").getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
