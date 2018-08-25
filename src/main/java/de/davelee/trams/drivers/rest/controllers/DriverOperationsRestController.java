package de.davelee.trams.drivers.rest.controllers;

import de.davelee.trams.drivers.api.*;
import de.davelee.trams.drivers.data.Driver;
import de.davelee.trams.drivers.data.DriverHistory;
import de.davelee.trams.drivers.data.DriverStatus;
import de.davelee.trams.drivers.services.DriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value="driver", description="Driver Operations")
@RequestMapping(value="/driver")
/**
 * This class represents the REST operations in the REST controller.
 * @author Dave Lee
 */
public class DriverOperationsRestController {

    @Autowired
    private DriverService driverService;

    @Value("${driver.permitted.hours.max}")
    private int maxDriverHours;

    @Value("${driver.hourlyWage.min}")
    private int minHourlyWage;

    @Value("${driver.hourlyWage.max}")
    private int maxHourlyWage;

    @Value("${driver.contractedHours.min}")
    private int minContractedHours;

    @Value("${driver.contractedHours.max}")
    private int maxContractedHours;

    @ApiOperation(value = "Get driver", notes="Method to get a driver's details by name and date of birth.")
    @RequestMapping(method = RequestMethod.GET, produces="application/json", value="/getDriver")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully retrieved driver details"), @ApiResponse(code=500,message="Database not available")})
    /**
     * Find a driver by name, date of birth and company.
     * @param name a <code>String</code> containing the name of the driver to find.
     * @param dateOfBirth a <code>String</code> containing the date of the birth for the driver to find.
     * @param company a <code>String</code> containing the company of the driver to find.
     * @return a <code>ResponseEntity</code> object which contains the driver found or bad request if the parameters are
     * invalid or an internal server error if the database is not available.
     */
    public ResponseEntity<DriverResponse> getDriver ( @RequestParam("name") final String name, @RequestParam("dateOfBirth") final String dateOfBirth, @RequestParam("company") final String company ) {
        //If name or date of birth is null then bad request.
        if ( name == null || dateOfBirth == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Driver driver = driverService.findDriverByDateOfBirthAndNameAndCompany(convertStringToDate(dateOfBirth), name, company);
            //Driver being null means that database was not available.
            if ( driver == null ) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            DriverResponse driverResponse = new DriverResponse();
            driverResponse.setContractedHours(driver.getContractedHours());
            driverResponse.setDateOfBirth(convertDateToString(driver.getDateOfBirth()));
            driverResponse.setName(driver.getName());
            driverResponse.setHourlyWage(driver.getHourlyWage().toString());
            driverResponse.setSkills(driver.getSkills());
            driverResponse.setStartDate(convertDateToString(driver.getStartDate()));
            driverResponse.setCompany(driver.getCompany());
            driverResponse.setAssignedRouteSchedule(driver.getAssignedRouteSchedule());
            driverResponse.setStatus(driver.getStatus().getText());
            List<DriverHistory> driverHistoryList = driver.getDriverHistoryList();
            List<DriverHistoryResponse> driverHistoryResponseList = new ArrayList<>(driverHistoryList.size());
            for ( DriverHistory driverHistory : driverHistoryList ) {
                DriverHistoryResponse driverHistoryResponse = new DriverHistoryResponse();
                driverHistoryResponse.setComment(driverHistory.getComment());
                driverHistoryResponse.setDate(convertDateToString(driverHistory.getDate()));
                driverHistoryResponse.setStatus(driverHistory.getStatus().getText());
                driverHistoryResponseList.add(driverHistoryResponse);
            }
            driverResponse.setDriverHistoryResponseList(driverHistoryResponseList);
            return new ResponseEntity<>(driverResponse, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Hire a permanent driver", notes="Method to hire a permanent driver and add it to the database.")
    @RequestMapping(method = RequestMethod.POST, produces="text/plain", value="/hirePermanent")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code=201,message="Successfully hired driver and added to database"), @ApiResponse(code=500,message="Database not available")})
    /**
     * Hire a new driver permanently based on the supplied driver request.
     * @param driverRequest a <code>DriverRequest</code> object containing the driver to be hired.
     * @return a <code>ResponseEntity</code> object which either indicates that the driver was hired successfully,
     * or bad request if the validation was not successful or internal server error if the database was not available.
     */
    public ResponseEntity<Void> hirePermanent ( @RequestBody final DriverRequest driverRequest ) {
        //Validate the request and return bad request if it fails.
        if ( !validateInput(driverRequest) ) {
            System.out.println("Validate input not successful!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Driver driver = new Driver();
            driver.setContractedHours(driverRequest.getContractedHours());
            driver.setDateOfBirth(convertStringToDate(driverRequest.getDateOfBirth()));
            driver.setName(driverRequest.getName());
            driver.setHourlyWage(new BigDecimal(driverRequest.getHourlyWage()));
            driver.setSkills(driverRequest.getSkills());
            driver.setStartDate(convertStringToDate(driverRequest.getStartDate()));
            driver.setCompany(driverRequest.getCompany());
            driver.setStatus(DriverStatus.HIRED);
            DriverHistory driverHistory = new DriverHistory();
            driverHistory.setComment("Hired!");
            driverHistory.setDate(LocalDate.now());
            driverHistory.setStatus(DriverStatus.HIRED);
            driver.addHistory(driverHistory);
            //If driver was added successfully, return created (201).
            if ( driverService.addDriver(driver) != null ) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                //Otherwise there were database problems so return 500.
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @ApiOperation(value = "Dismiss a driver", notes="Method to dismiss a driver which will be noted in the database.")
    @RequestMapping(method = RequestMethod.POST, produces="text/plain", value="/dismiss")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully dismissed driver"), @ApiResponse(code=400,message="Input was not valid"), @ApiResponse(code=500,message="Database not available")})
    /**
     * Dismiss a driver based on the request supplied.
     * @param dismissDriverRequest a <code>DismissDriverRequest</code> object containing the driver to be dismissed.
     * @return a <code>ResponseEntity</code> which either indicates that the driver was dismissed, or bad request if
     * the validation was not successful or internal server error if problems with the database.
     */
    public ResponseEntity<Void> dismiss ( @RequestBody final DismissDriverRequest dismissDriverRequest ) {
        //Validate the request and return a 400 (bad request) if not successful.
        if ( !validateRetrieveDriverRequest(dismissDriverRequest) || dismissDriverRequest.getReasonForDismissal() == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Driver driver = driverService.findDriverByDateOfBirthAndNameAndCompany(convertStringToDate(dismissDriverRequest.getDateOfBirth()), dismissDriverRequest.getName(), dismissDriverRequest.getCompany());
            if ( driver != null ) {
                //If driver is dismissed successfully return 200.
                driverService.dismissDriver(driver, dismissDriverRequest.getReasonForDismissal());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                //If driver is not dismissed successfully then return 500 indicating database problems.
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @ApiOperation(value = "Track hours for drivers", notes="Method to track hours for drivers by incrementing hours for the current date.")
    @RequestMapping(method = RequestMethod.POST, produces="text/plain", value="/trackHours")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully tracked hours"), @ApiResponse(code=400,message="Input was not valid"), @ApiResponse(code=500,message="Database not available")})
    /**
     * Track the amount of hours a driver works by adding the hours for the current date.
     * @param driverHoursRequest a <code>DriverHoursRequest</code> containing the hours to add.
     * @return a <code>ResponseEntity</code> which is ok (200) if the hours were added successfully or bad request (400)
     * if validation was not successful or internal server error (500) if the database was not available.
     */
    public ResponseEntity<Void> trackHours ( @RequestBody final DriverHoursRequest driverHoursRequest ) {
        if ( !validateRetrieveDriverRequest(driverHoursRequest) ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Driver driver = driverService.findDriverByDateOfBirthAndNameAndCompany(convertStringToDate(driverHoursRequest.getDateOfBirth()), driverHoursRequest.getName(), driverHoursRequest.getCompany());
            if ( driver != null ) {
                driverService.incrementDriverHours(driver, driverHoursRequest.getHours());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @ApiOperation(value = "Check hours for drivers", notes="Method to check hours worked by a driver. Returns true if and only if the driver has worked less today than the maximum hours permitted")
    @RequestMapping(method = RequestMethod.POST, produces="application/json", value="/checkHours")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully checked hours"), @ApiResponse(code=400,message="Input was not valid"), @ApiResponse(code=500,message="Database not available")})
    /**
     * Check if the driver can legally work additional hours or not.
     * @param retrieveDriverRequest a <code>RetrieveDriverRequest</code> object containing the driver to check hours for.
     * @return a <code>ResponseEntity</code> of <code>CheckDriverHoursResponse</code> if the operation was
     * performed successfully or a bad request if the request was not valid or an internal server error if database
     * is not available.
     */
    public ResponseEntity<CheckDriverHoursResponse> checkHours ( @RequestBody final RetrieveDriverRequest retrieveDriverRequest ) {
        if ( !validateRetrieveDriverRequest(retrieveDriverRequest) ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Driver driver = driverService.findDriverByDateOfBirthAndNameAndCompany(convertStringToDate(retrieveDriverRequest.getDateOfBirth()), retrieveDriverRequest.getName(), retrieveDriverRequest.getCompany());
            if ( driver != null ) {
                if ( driver.getHoursWorkedForDate(LocalDate.now()) != null ) {
                    CheckDriverHoursResponse checkDriverHoursResponse = new CheckDriverHoursResponse();
                    checkDriverHoursResponse.setFurtherHoursAllowed(driver.getHoursWorkedForDate(LocalDate.now()) < maxDriverHours);
                    checkDriverHoursResponse.setRemainingHours(maxDriverHours - driver.getHoursWorkedForDate(LocalDate.now()));
                    return new ResponseEntity<>(checkDriverHoursResponse, HttpStatus.OK);
                }
                CheckDriverHoursResponse checkDriverHoursResponse = new CheckDriverHoursResponse();
                checkDriverHoursResponse.setFurtherHoursAllowed(true);
                checkDriverHoursResponse.setRemainingHours(maxDriverHours);
                return new ResponseEntity<>(checkDriverHoursResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @ApiOperation(value = "Pay drivers", notes="Method to pay drivers from a particular company for a particular period of time. Return amount paid out.")
    @RequestMapping(method = RequestMethod.POST, produces="application/json", value="/payDrivers")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully paid drivers"), @ApiResponse(code=400,message="Input was not valid"), @ApiResponse(code=500,message="Database not available")})
    /**
     * Pay Drivers according to the specified request and return a response containing the amount used to pay the drivers.
     * @param payDriversRequest a <code>PayDriversRequest</code> containing the drivers to pay.
     * @return a <code>ResponseEntity</code> of <code>PayDriversResponse</code> containing the amount paid or
     * a bad request if the validation was not successful.
     */
    public ResponseEntity<PayDriversResponse> payDrivers ( @RequestBody final PayDriversRequest payDriversRequest ) {
        if ( payDriversRequest.getCompany() == null || payDriversRequest.getFromDate() == null || payDriversRequest.getToDate() == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            BigDecimal totalPaid = driverService.payDrivers(payDriversRequest.getCompany(), convertStringToDate(payDriversRequest.getFromDate()), convertStringToDate(payDriversRequest.getToDate()));
            PayDriversResponse payDriversResponse = new PayDriversResponse();
            payDriversResponse.setTotalPayout(totalPaid.toString());
            return new ResponseEntity<>(payDriversResponse, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Assign route duty for driver", notes="Method to assign route duty for driver.")
    @RequestMapping(method = RequestMethod.POST, produces="text/plain", value="/assignRoute")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully assigned route"), @ApiResponse(code=400,message="Input was not valid"), @ApiResponse(code=500,message="Database not available")})
    /**
     * Assign a route duty to this driver.
     * @param assignDriverRequest a <code>AssignDriverRequest</code> object containing the driver and duty to assign.
     * @return a <code>ResponseEntity</code> which is ok (200) if the driver can be assigned successfully or bad
     * request (400) if the request could not be validated or internal server error (500) if the database was not
     * available.
     */
    public ResponseEntity<Void> assignRoute ( @RequestBody final AssignDriverRequest assignDriverRequest ) {
        if ( validateRetrieveDriverRequest(assignDriverRequest) && assignDriverRequest.getAssignedRouteSchedule() != null ) {
            Driver driver = driverService.findDriverByDateOfBirthAndNameAndCompany(convertStringToDate(assignDriverRequest.getDateOfBirth()), assignDriverRequest.getName(), assignDriverRequest.getCompany());
            if ( driver != null ) {
                driverService.assignRouteSchedule(driver, assignDriverRequest.getAssignedRouteSchedule());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Validation rules:
     * all fields are required.
     * @param driverRequest the object to validate.
     * @return true iff the input is correct.
     */
    public boolean validateInput ( final DriverRequest driverRequest ) {
        if ( driverRequest.getContractedHours() < minContractedHours || driverRequest.getContractedHours() > maxContractedHours ) {
            return false;
        } else if ( !validateDate(driverRequest.getDateOfBirth()) ) {
            return false;
        } else if ( driverRequest.getName() == null ) {
            return false;
        } else if ( !validateHourlyWage(driverRequest.getHourlyWage())) {
            return false;
        } else if ( driverRequest.getSkills() == null ) {
            return false;
        } else if ( !validateDate(driverRequest.getStartDate())) {
            return false;
        }
        return true;
    }

    /**
     * Convert dates from string format: dd-MM-yyyy to localdate.
     * @param dateStr a <code>String</code> containing the date to convert in the format dd-MM-yyyy.
     * @return a <code>LocalDate</code> object representing the converted date.
     */
    private LocalDate convertStringToDate ( final String dateStr ) {
        String[] dateArray = dateStr.split("-");
        return LocalDate.of(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[0]));
    }

    /**
     * Convert dates from localdate to string format: dd-MM-yyyy.
     * @param localDate a <code>LocalDate</code> object to convert to a String.
     * @return a <code>String</code> containing the converted date in format: dd-MM-yyyy.
     */
    private String convertDateToString ( final LocalDate localDate ) {
        return localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-" + localDate.getYear();
    }

    /**
     * Validate date to ensure it exists as a valid date!
     * @param date a <code>String</code> containing the date to validate in format dd-MM-yyyy.
     * @return a <code>boolean</code> which is true iff the date is valid.
     */
    private boolean validateDate ( final String date ) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Validate hourly wage to ensure it is between min and max parameters.
     * @param hourlyWage a <code>String</code> containing the hourly wage to validate.
     * @return a <code>boolean</code> which is true iff the hourly wage is valid.
     */
    private boolean validateHourlyWage ( final String hourlyWage ) {
        BigDecimal bigDecimalHourlyWage = new BigDecimal(hourlyWage);
        if ( bigDecimalHourlyWage.compareTo(BigDecimal.valueOf(minHourlyWage)) < 0 || bigDecimalHourlyWage.compareTo(BigDecimal.valueOf(maxHourlyWage)) > 0 ) {
            return false;
        }
        return true;
    }

    /**
     * Validate request to ensure name, date of birth and company are not null.
     * @param retrieveDriverRequest a <code>RetrieveDriverRequest</code> object to validate.
     * @return a <code>boolean</code> which is true iff the request is valid.
     */
    private boolean validateRetrieveDriverRequest ( final RetrieveDriverRequest retrieveDriverRequest ) {
        return retrieveDriverRequest.getName() != null && retrieveDriverRequest.getCompany() != null && validateDate(retrieveDriverRequest.getDateOfBirth());
    }


}
