package de.davelee.trams.drivers.api;

/**
 * This class represents a check driver hours response to be returned by the Rest API.
 * @author Dave Lee
 */
public class CheckDriverHoursResponse {

    private boolean furtherHoursAllowed;

    private int remainingHours;

    /**
     * Check if the driver is allowed to driver further hours or has already reached their maximum.
     * @return a <code>boolean</code> which is true iff the driver has further hours which they can drive.
     */
    public boolean isFurtherHoursAllowed() {
        return furtherHoursAllowed;
    }

    /**
     * Set whether a driver is able to drive further hours (true) or not (false).
     * @param furtherHoursAllowed a <code>boolean</code> which is true iff the driver has further hours which they can drive.
     */
    public void setFurtherHoursAllowed(final boolean furtherHoursAllowed) {
        this.furtherHoursAllowed = furtherHoursAllowed;
    }

    /**
     * Get the remaining hours that the driver may drive.
     * @return a <code>int</code> containing the remaining hours that the driver may drive.
     */
    public int getRemainingHours() {
        return remainingHours;
    }

    /**
     * Set the remaining hours that the driver may drive.
     * @param remainingHours a <code>int</code> containing the remaining hours that the driver may drive.
     */
    public void setRemainingHours(final int remainingHours) {
        this.remainingHours = remainingHours;
    }
}
