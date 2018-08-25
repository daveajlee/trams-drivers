package de.davelee.trams.drivers.api;

/**
 * This class represents a request for the driver to drive a certain amount of hours.
 * @author Dave Lee
 */
public class DriverHoursRequest extends RetrieveDriverRequest {

    private int hours;

    /**
     * Return the number of hours that the driver should work.
     * @return a <code>int</code> containing the number of hours that the driver should work.
     */
    public int getHours() {
        return hours;
    }

    /**
     * Set the number of hours that the driver should work.
     * @param hours a <code>int</code> containing the number of hours that the driver should work.
     */
    public void setHours(final int hours) {
        this.hours = hours;
    }
}
