package de.davelee.trams.drivers.repository;

import de.davelee.trams.drivers.data.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents the database operations automatically generated using Spring Data JPA.
 * @author Dave Lee
 */
public interface DriverRepository extends JpaRepository<Driver, Long> {

    /**
     * Find a driver by their date of birth, name and company they are working for.
     * @param dateOfBirth a <code>LocalDate</code> containing the date of birth for this driver in format dd-MM-yyyy.
     * @param name a <code>String</code> with the name of the driver.
     * @param company a <code>String</code> containing the company.
     * @return a <code>Driver</code> object representing the driver matching the criteria or null if none found.
     */
    Driver findByDateOfBirthAndNameAndCompany(LocalDate dateOfBirth, String name, String company);

    /**
     * List all the drivers working for a particular company.
     * @param company a <code>String</code> containing the company.
     * @return a <code>List</code> of <code>Driver</code> objects matching the company criteria or null if none found.
     */
    List<Driver> findByCompany(String company);

}
