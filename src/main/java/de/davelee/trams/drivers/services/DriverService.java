package de.davelee.trams.drivers.services;

import de.davelee.trams.drivers.data.Driver;
import de.davelee.trams.drivers.data.DriverHistory;
import de.davelee.trams.drivers.data.DriverStatus;
import de.davelee.trams.drivers.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * This class controls access to the driver database and calls appropriate operations.
 * @author Dave Lee
 */
@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Transactional
    /**
     * Add a new driver to the database.
     * @param driver a <code>Driver</code> object to add to the database.
     * @return a <code>Driver</code> object which was added to the database or null if the database is not available.
     */
    public Driver addDriver ( final Driver driver ) {
        return driverRepository.save(driver);
    }

    /**
     * Get the number of drivers in the database.
     * @return a <code>long</code> containing the number of drivers in the database.
     */
    public long getNumDrivers ( ) {
        return driverRepository.count();
    }

    /**
     * Get all drivers in the database.
     * @return a <code>List</code> of <code>Driver</code> objects representing all drivers in the database.
     */
    public List<Driver> getAllDrivers ( ) {
        return driverRepository.findAll();
    }

    /**
     * Get all drivers in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to find drivers for.
     * @return a <code>List</code> of <code>Driver</code> objects representing all drivers for a particular company in the database.
     */
    public List<Driver> getAllDriversForCompany ( final String company ) {
        return driverRepository.findByCompany(company);
    }

    @Transactional
    /**
     * Find a driver based on their date of birth, name and company.
     * @param dateOfBirth a <code>LocalDate</code> object containing the date of birth of the driver to retrieve.
     * @param name a <code>String</code> containing the name of the driver to retrieve.
     * @param company a <code>String</code> containing the company of the driver to retrieve.
     * @return a <code>Driver</code> object representing the driver matching the criteria or null if none can be found.
     */
    public Driver findDriverByDateOfBirthAndNameAndCompany ( final LocalDate dateOfBirth, final String name, final String company ) {
        return driverRepository.findByDateOfBirthAndNameAndCompany(dateOfBirth, name, company);
    }

    @Transactional
    /**
     * Increment the hours for a particular driver.
     * @param driver a <code>Driver</code> object representing the driver who's hours should increase.
     * @param hours a <code>int</code> containing the number of hours that should be added to this driver.
     */
    public void incrementDriverHours (final Driver driver, final int hours ) {
        driver.incrementDriverHours(LocalDate.now(), hours);
        driverRepository.saveAndFlush(driver);
    }

    @Transactional
    /**
     * Dismiss a driver for a particular reason.
     * @param driver a <code>Driver</code> object representing the driver who should be dismissed.
     * @param reason a <code>String</code> with the reason for dismissal.
     */
    public void dismissDriver (final Driver driver, final String reason) {
        driver.setStatus(DriverStatus.DISMISSED);
        DriverHistory driverHistory = new DriverHistory();
        driverHistory.setComment("Dismissed. Reason: " + reason);
        driverHistory.setDate(LocalDate.now());
        driverHistory.setStatus(DriverStatus.DISMISSED);
        driver.addHistory(driverHistory);
        driverRepository.saveAndFlush(driver);
    }

    @Transactional
    /**
     * Pay all drivers for a particular company for a particular date range.
     * @param company a <code>String</code> containing the company that should pay their drivers.
     * @param fromDate a <code>LocalDate</code> containing the start date that should be paid from.
     * @param toDate a <code>LocalDate</code> containing the end date that should be paid to.
     * @return a <code>BigDecimal</code> object representing the total amount paid to all drivers.
     */
    public BigDecimal payDrivers ( final String company, final LocalDate fromDate, final LocalDate toDate ) {
        //Find all drivers for the company who could be eligable to be paid.
        List<Driver> driversToBePaid = driverRepository.findByCompany(company);
        //Count money paid out.
        BigDecimal paidOut = new BigDecimal(0);
        //Go through the dates.
        LocalDate currentDate = fromDate;
        do {
            //Check to see if any drivers worked that day.
            for ( Driver driver : driversToBePaid ) {
                if ( driver.getHoursWorkedForDate(currentDate) != null && driver.getHoursWorkedForDate(currentDate) > 0 ) {
                    //Pay them if they did.
                    paidOut = paidOut.add(payDriver(driver, currentDate));
                }
            }
            currentDate = currentDate.plusDays(1);
        } while ( currentDate.isEqual(toDate) );
        //Return amount paid out.
        return paidOut;
    }

    @Transactional
    /**
     * Assign a driver to a particular route schedule.
     * @param driver a <code>Driver</code> object representing the driver who should be allocated.
     * @param routeSchedule a <code>String</code> containing the route schedule id to assign.
     */
    public void assignRouteSchedule ( final Driver driver, final String routeSchedule ) {
        driver.setAssignedRouteSchedule(routeSchedule);
        driverRepository.saveAndFlush(driver);
    }

    /**
     * Pay a driver for a particular date.
     * @param driver a <code>Driver</code> object containing the driver who should be paid.
     * @param currentDate a <code>LocalDate</code> object containing the date to pay the driver.
     * @return a <code>BigDecimal</code> object containing the amount paid to the driver.
     */
    private BigDecimal payDriver ( final Driver driver, final LocalDate currentDate ) {
        BigDecimal toBePaid = new BigDecimal(driver.getHoursWorkedForDate(currentDate)).multiply(driver.getHourlyWage());
        DriverHistory driverHistory = new DriverHistory();
        driverHistory.setComment("Paid " + toBePaid.toString() + " for working on " + currentDate.getDayOfMonth() + "-" + currentDate.getMonth() + "-" + currentDate.getYear());
        driverHistory.setDate(LocalDate.now());
        driverHistory.setStatus(DriverStatus.PAID);
        driver.addHistory(driverHistory);
        driverRepository.saveAndFlush(driver);
        return toBePaid;
    }

}
