package de.davelee.trams.drivers;

import com.jayway.restassured.RestAssured;
import de.davelee.trams.drivers.api.*;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDate;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TramsDriversApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
/**
 * Test the Spring Boot application to make sure it starts and swagger can be called.
 * @author Dave Lee
 */
public class TramsDriversApplicationTest {

    @LocalServerPort
    private int port;

    @Before
    /**
     * Set up the test by setting the port correctly.
     */
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    /**
     * Test case: make sure that the swagger can be called.
     * Expected result: 200 status code OK.
     */
    public void testSwagger() {
        when().
                get("/swagger-ui.html").
                then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    /**
     * Test case: make sure that the vaadin ui can be called.
     * Expected result: 200 status code OK.
     */
    public void testVaadinUi() {
        when().
                get("/admin").
                then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    /**
     * Retrieve a driver who does not yet exist.
     * Expected result: Internal Server Error.
     */
    public void testDriver() {
        when().
                get("/driver/getDriver?name=Max Mustermann&dateOfBirth=20-09-1998&company=Lee Buses").
                then().
                statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    /**
     * Test case: Hire a permanent driver then retrieve the driver, pay him and assign him a duty.
     * Expected result: All operations work as appropriate.
     */
    public void testPermanentAndThenDriverAndThenDismiss() {
        //First of all test adding permanent driver.
        DriverRequest driverRequest = new DriverRequest();
        driverRequest.setContractedHours(20);
        driverRequest.setDateOfBirth("20-09-1996");
        driverRequest.setName("Max Mustermann");
        driverRequest.setHourlyWage("20.0");
        driverRequest.setSkills("Driving");
        driverRequest.setStartDate("01-10-2016");
        driverRequest.setCompany("Lee Buses");
        given()
                .contentType("application/json")
                .body(driverRequest)
                .when()
                .post("/driver/hirePermanent")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
        //Now test retrieve driver.
        when().
                get("/driver/getDriver?name=Max Mustermann&dateOfBirth=20-09-1996&company=Lee Buses").
                then().
                statusCode(HttpStatus.SC_OK);
        //Now check that driver can still work -> should be possible.
        RetrieveDriverRequest retrieveDriverRequest = new RetrieveDriverRequest();
        retrieveDriverRequest.setCompany("Lee Buses");
        retrieveDriverRequest.setDateOfBirth("20-09-1996");
        retrieveDriverRequest.setName("Max Mustermann");
        given()
                .contentType("application/json")
                .body(retrieveDriverRequest)
                .when()
                .post("/driver/checkHours")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("furtherHoursAllowed", equalTo(true))
                .body("remainingHours", equalTo(10));
        //Now attempt to pay the driver but he hasn't work.
        PayDriversRequest payDriversRequest = new PayDriversRequest();
        payDriversRequest.setCompany("Lee Buses");
        LocalDate currentDate = LocalDate.now();
        payDriversRequest.setFromDate(currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-" + currentDate.getYear());
        payDriversRequest.setToDate(currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-" + currentDate.getYear());
        given()
                .contentType("application/json")
                .body(payDriversRequest)
                .when()
                .post("/driver/payDrivers")
                .then()
                .statusCode(HttpStatus.SC_OK);
        //Bad request to pay the driver.
        PayDriversRequest payDriversRequest2 = new PayDriversRequest();
        payDriversRequest2.setCompany("Lee Buses");
        payDriversRequest2.setFromDate(currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-" + currentDate.getYear());
        given()
                .contentType("application/json")
                .body(payDriversRequest2)
                .when()
                .post("/driver/payDrivers")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        //Now assign the driver a duty.
        AssignDriverRequest assignDriverRequest = new AssignDriverRequest();
        assignDriverRequest.setCompany("Lee Buses");
        assignDriverRequest.setDateOfBirth("20-09-1996");
        assignDriverRequest.setName("Max Mustermann");
        assignDriverRequest.setAssignedRouteSchedule("1/1");
        given()
                .contentType("application/json")
                .body(assignDriverRequest)
                .when()
                .post("/driver/assignRoute")
                .then()
                .statusCode(HttpStatus.SC_OK);
        when().
                get("/driver/getDriver?name=Max Mustermann&dateOfBirth=20-09-1996&company=Lee Buses").
                then().
                statusCode(HttpStatus.SC_OK)
                .body("assignedRouteSchedule", equalTo("1/1"));
        //Now test adding hours to the driver.
        DriverHoursRequest driverHoursRequest = new DriverHoursRequest();
        driverHoursRequest.setCompany("Lee Buses");
        driverHoursRequest.setDateOfBirth("20-09-1996");
        driverHoursRequest.setName("Max Mustermann");
        driverHoursRequest.setHours(5);
        given()
                .contentType("application/json")
                .body(driverHoursRequest)
                .when()
                .post("/driver/trackHours")
                .then()
                .statusCode(HttpStatus.SC_OK);
        //Now check hours again.
        given()
                .contentType("application/json")
                .body(retrieveDriverRequest)
                .when()
                .post("/driver/checkHours")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("furtherHoursAllowed", equalTo(true))
                .body("remainingHours", equalTo(5));
        //Now add hours to the driver so that he has worked maximum.
        given()
                .contentType("application/json")
                .body(driverHoursRequest)
                .when()
                .post("/driver/trackHours")
                .then()
                .statusCode(HttpStatus.SC_OK);
        //Now check hours again.
        given()
                .contentType("application/json")
                .body(retrieveDriverRequest)
                .when()
                .post("/driver/checkHours")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("furtherHoursAllowed", equalTo(false))
                .body("remainingHours", equalTo(0));
        //Now pay the driver.
        given()
                .contentType("application/json")
                .body(payDriversRequest)
                .when()
                .post("/driver/payDrivers")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("totalPayout", equalTo("200.00"));
        //Now dismiss the driver.
        DismissDriverRequest dismissDriverRequest = new DismissDriverRequest();
        dismissDriverRequest.setCompany("Lee Buses");
        dismissDriverRequest.setDateOfBirth("20-09-1996");
        dismissDriverRequest.setName("Max Mustermann");
        dismissDriverRequest.setReasonForDismissal("Poor Performance");
        given()
                .contentType("application/json")
                .body(dismissDriverRequest)
                .when()
                .post("/driver/dismiss")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}