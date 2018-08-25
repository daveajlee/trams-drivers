package de.davelee.trams.drivers.api;

/**
 * This class represents a response to pay drivers with the total payout.
 * @author Dave Lee
 */
public class PayDriversResponse {

    private String totalPayout;

    /**
     * Return the total payout as a String.
     * @return a <code>String</code> containing the total payout.
     */
    public String getTotalPayout() {
        return totalPayout;
    }

    /**
     * Set the total payout as a String.
     * @param totalPayout a <code>String</code> containing the total payout.
     */
    public void setTotalPayout(final String totalPayout) {
        this.totalPayout = totalPayout;
    }
}
