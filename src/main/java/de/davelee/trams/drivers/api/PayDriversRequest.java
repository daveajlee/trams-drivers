package de.davelee.trams.drivers.api;

/**
 * This class represents a request to pay drivers.
 * @author Dave Lee
 */
public class PayDriversRequest {

    private String company;
    private String fromDate;
    private String toDate;

    /**
     * Return the company.
     * @return a <code>String</code> containing the company.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Set the company.
     * @param company a <code>String</code> containing the company.
     */
    public void setCompany(final String company) {
        this.company = company;
    }

    /**
     * Return the date to pay drivers from as a String.
     * @return a <code>String</code> containing the date to pay drivers from.
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Set the date to pay drivers from as a String.
     * @param fromDate a <code>String</code> containing the date to pay drivers from.
     */
    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Return the date to pay drivers to as a String.
     * @return a <code>String</code> containing the date to pay drivers to.
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Set the date to pay drivers to as a String.
     * @param toDate a <code>String</code> containing the date to pay drivers to.
     */
    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

}
