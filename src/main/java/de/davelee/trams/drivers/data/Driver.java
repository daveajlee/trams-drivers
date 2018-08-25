package de.davelee.trams.drivers.data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a driver.
 * @author Dave Lee
 */
@Entity
@Table(name="DRIVER")
public class Driver {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private int contractedHours;

    @Column
    private BigDecimal hourlyWage;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String skills = "";

    @Column
    private String company;

    @Column
    private String assignedRouteSchedule;

    @Column
    private DriverStatus status;

    @OneToMany(targetEntity=DriverHistory.class, fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<DriverHistory> driverHistoryList = new ArrayList<>();

    @ElementCollection
    private Map<LocalDate, Integer> driverHoursMap = new HashMap<>();

    /**
     * Create a new driver with the default constructor - creating a blank driver.
     */
    public Driver (){
    }

    /**
     * Create a new driver by supplying the information to initialise the driver.
     * @param name a <code>String</code> with the name of the driver.
     * @param company a <code>String</code> containing the company.
     * @param dateOfBirth a <code>LocalDate</code> containing the date of birth for this driver.
     * @param hourlyWage a <code>String</code> containing the hourly wage.
     * @param startDate a <code>LocalDate</code> containing the start date for this driver.
     */
    public Driver (final String name, final String company, final LocalDate dateOfBirth, final String hourlyWage,
                   final LocalDate startDate) {
        this.name = name;
        this.company = company;
        this.dateOfBirth = dateOfBirth;
        this.hourlyWage = new BigDecimal(hourlyWage);
        this.startDate = startDate;
        this.status = DriverStatus.HIRED;
    }

    /**
     * Return the identifier for this driver.
     * @return a <code>Long</code> object containing the identifier for this driver.
     */
    public Long getId() {
        return id;
    }

    /**
     * Return the name of the driver as a String.
     * @return a <code>String</code> with the name of the driver.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the driver as a String.
     * @param name a <code>String</code> with the name of the driver.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Return the amount of contracted hours.
     * @return a <code>int</code> containing the amount of contracted hours.
     */
    public int getContractedHours() {
        return contractedHours;
    }

    /**
     * Set the amount of contracted hours.
     * @param contractedHours a <code>int</code> containing the amount of contracted hours.
     */
    public void setContractedHours(final int contractedHours) {
        this.contractedHours = contractedHours;
    }

    /**
     * Return the hourly wage.
     * @return a <code>BigDecimal</code> containing the hourly wage.
     */
    public BigDecimal getHourlyWage() {
        return hourlyWage;
    }

    /**
     * Set the hourly wage as a BigDecimal.
     * @param hourlyWage a <code>BigDecimal</code> containing the hourly wage.
     */
    public void setHourlyWage(final BigDecimal hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    /**
     * Return the start date as a LocalDate.
     * @return a <code>LocalDate</code> containing the start date for this driver.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Set the start date as a LocalDate.
     * @param startDate a <code>LocalDate</code> containing the start date for this driver.
     */
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Return the date of birth as a LocalDate.
     * @return a <code>LocalDate</code> containing the date of birth for this driver.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set the date of birth as a LocalDate.
     * @param dateOfBirth a <code>LocalDate</code> containing the date of birth for this driver.
     */
    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Return the skills for this driver as a String.
     * @return a <code>String</code> containing the skills for this driver.
     */
    public String getSkills() {
        return skills;
    }

    /**
     * Set the skills for this driver as a String.
     * @param skills a <code>String</code> containing the skills for this driver.
     */
    public void setSkills(final String skills) {
        this.skills = skills;
    }

    /**
     * Return the company as a String.
     * @return a <code>String</code> containing the company.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Set the company as a String.
     * @param company a <code>String</code> containing the company.
     */
    public void setCompany(final String company) {
        this.company = company;
    }

    /**
     * Return the assigned route schedule as a String.
     * @return a <code>String</code> containing the assigned route schedule.
     */
    public String getAssignedRouteSchedule() {
        return assignedRouteSchedule;
    }

    /**
     * Set the assigned route schedule as a String.
     * @param assignedRouteSchedule a <code>String</code> containing the assigned route schedule.
     */
    public void setAssignedRouteSchedule(final String assignedRouteSchedule) {
        this.assignedRouteSchedule = assignedRouteSchedule;
    }

    /**
     * Return the status of this driver.
     * @return a <code>DriverStatus</code> containing the current status of this driver.
     */
    public DriverStatus getStatus() {
        return status;
    }

    /**
     * Set the status of this driver.
     * @param status a <code>DriverStatus</code> object containing the current status of this driver.
     */
    public void setStatus(final DriverStatus status) {
        this.status = status;
    }

    /**
     * Add an entry to the driver history list.
     * @param driverHistory a <code>DriverHistory</code> object to add to the driver history list.
     */
    public void addHistory(final DriverHistory driverHistory) {
        this.driverHistoryList.add(driverHistory);
    }

    /**
     * Return the driver history as a list.
     * @return a <code>List</code> of <code>DriverHistory</code> containing the driver history.
     */
    public List<DriverHistory> getDriverHistoryList() {
        return this.driverHistoryList;
    }

    /**
     * This method increments the driver hours for the specified date by the specified hours.
     * @param date a <code>LocalDate</code> with the date that the hours were worked.
     * @param hours a <code>int</code> with the number of hours to increase by.
     */
    public void incrementDriverHours ( final LocalDate date, final int hours ) {
        Integer currentHours = driverHoursMap.get(date);
        if ( currentHours == null ) {
            driverHoursMap.put(date, hours);
        } else {
            driverHoursMap.put(date, currentHours.intValue() + hours);
        }
    }

    /**
     * Retrieve the number of hours worked for this driver for the specified date.
     * @param date a <code>LocalDate</code> object with the date to retrieve the hours worked for.
     * @return a <code>Integer</code> with the number of hours worked.
     */
    public Integer getHoursWorkedForDate ( final LocalDate date ) {
        return driverHoursMap.get(date);
    }

}
