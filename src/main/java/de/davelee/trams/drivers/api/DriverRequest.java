package de.davelee.trams.drivers.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class represents a request to add a new driver.
 * @author Dave Lee
 */
@ApiModel
public class DriverRequest {

    private String name;
    private int contractedHours;
    private String hourlyWage;
    private String startDate;
    private String dateOfBirth;
    private String skills;
    private String company;

    @ApiModelProperty(position=1, required=true, value="name of the driver")
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

    @ApiModelProperty(position=2, required=true, value="contracted hours for this driver")
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

    @ApiModelProperty(position=3, required=true, value="hourly wage for this driver in format 000000.00")
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

    @ApiModelProperty(position=4, required=true, value="start date for this driver in format dd-MM-yyyy")
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

    @ApiModelProperty(position=5, required=true, value="date of birth for this driver in format dd-MM-yyyy")
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

    @ApiModelProperty(position=6, required=true, value="skills for this driver")
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

    @ApiModelProperty(position=7, required=true, value="company that the driver works for")
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
}
