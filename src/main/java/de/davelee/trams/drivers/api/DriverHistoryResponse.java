package de.davelee.trams.drivers.api;

/**
 * This class represents a driver history response to be returned by the Rest API.
 * @author Dave Lee
 */
public class DriverHistoryResponse {

    private String date;
    private String status;
    private String comment;

    /**
     * Return the date of this event in the driver's history.
     * @return a <code>String</code> containing the date of this event in the driver's history.
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of this event in the driver's history.
     * @param date a <code>String</code> containing the date of this event in the driver's history.
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * Get the status of this event in the driver's history.
     * @return a <code>String</code> containing the status of this event in the driver's history.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of this event in the driver's history.
     * @param status a <code>String</code> containing the status of this event in the driver's history.
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Get the comment of this entry in the driver's history.
     * @return a <code>String</code> containing the comment of this entry in the driver's history.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment of this entry in the driver's history.
     * @param comment a <code>String</code> containing the comment of this entry in the driver's history.
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }
}
