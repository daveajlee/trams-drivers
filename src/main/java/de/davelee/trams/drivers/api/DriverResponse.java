package de.davelee.trams.drivers.api;

import java.util.List;

/**
 * This class represents a driver response to be returned by the Rest API.
 * @author Dave Lee
 */
public class DriverResponse {

    private String name;
    private String dateOfBirth;
    private int contractedHours;
    private String hourlyWage;
    private String startDate;
    private String skills;
    private String company;
    private String assignedRouteSchedule;
    private String status;
    private List<DriverHistoryResponse> driverHistoryResponseList;

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
     * Return the date of birth as a String.
     * @return a <code>String</code> containing the date of birth for this driver in format dd-MM-yyyy.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set the date of birth as a String.
     * @param dateOfBirth a <code>String</code> containing the date of birth for this driver in format dd-MM-yyyy.
     */
    public void setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
     * Return the hourly wage as a String.
     * @return a <code>String</code> containing the hourly wage.
     */
    public String getHourlyWage() {
        return hourlyWage;
    }

    /**
     * Set the hourly wage as a String.
     * @param hourlyWage a <code>String</code> containing the hourly wage.
     */
    public void setHourlyWage(final String hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    /**
     * Return the start date as a String.
     * @return a <code>String</code> containing the start date for this driver in format dd-MM-yyyy.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Set the start date as a String.
     * @param startDate a <code>String</code> containing the start date for this driver in format dd-MM-yyyy.
     */
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
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
     * @return a <code>String</code> containing the current status of this driver.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of this driver.
     * @param status a <code>String</code> containing the current status of this driver.
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Return the driver history as a list.
     * @return a <code>List</code> of <code>DriverHistoryResponse</code> containing the driver history.
     */
    public List<DriverHistoryResponse> getDriverHistoryResponseList() {
        return driverHistoryResponseList;
    }

    /**
     * Set the driver history as a list.
     * @param driverHistoryResponseList a <code>List</code> of <code>DriverHistoryResponse</code> containing the driver history.
     */
    public void setDriverHistoryResponseList(final List<DriverHistoryResponse> driverHistoryResponseList) {
        this.driverHistoryResponseList = driverHistoryResponseList;
    }
}
