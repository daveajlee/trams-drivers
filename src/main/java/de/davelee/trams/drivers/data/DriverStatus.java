package de.davelee.trams.drivers.data;

/**
 * This enum contains the various allowed statuses for the status of a driver.
 * @author Dave Lee
 */
public enum DriverStatus {

    /**
     * Driver is hired.
     */
    HIRED {
        /**
         * Return the text of hired.
         * @return a <code>String</code> object representing the text for hired.
         */
        public String getText() {
            return "Hired";
        }
    },
    /**
     * Driver is working.
     */
    WORKING {
        /**
         * Return the text of working.
         * @return a <code>String</code> object representing the text for working.
         */
        public String getText() {
            return "In Employment";
        }
    },
    /**
     * Driver has been dismissed.
     */
    DISMISSED {
        /**
         * Return the text for dismissed.
         * @return a <code>String</code> object representing the text for dismissed.
         */
        public String getText() {
            return "Dismissed";
        }
    },
    /**
     * Driver has been paid.
     */
    PAID {
        /**
         * Return the text for paid.
         * @return a <code>String</code> object representing the text for paid.
         */
        public String getText() { return "Paid"; }
    };

    /**
     * Abstract method to return the text for a particular status.
     * @return a <code>String</code> object representing the text for a particular status.
     */
    public abstract String getText();

}
