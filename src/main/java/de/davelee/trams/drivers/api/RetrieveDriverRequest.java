package de.davelee.trams.drivers.api;

/**
 * This class represents a request to retrieve drivers.
 * @author Dave Lee
 */
public class RetrieveDriverRequest {

    private String name;
    private String dateOfBirth;
    private String company;

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
