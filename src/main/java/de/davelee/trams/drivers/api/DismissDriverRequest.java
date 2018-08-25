package de.davelee.trams.drivers.api;

/**
 * This class represents a request to dismass a driver for a particular reason.
 * @author Dave Lee
 */
public class DismissDriverRequest extends RetrieveDriverRequest {

    private String reasonForDismissal;

    /**
     * Return the reason for dismissal as a String.
     * @return a <code>String</code> containing the reason for dismissal as a String.
     */
    public String getReasonForDismissal() {
        return reasonForDismissal;
    }

    /**
     * Set the reason for dismissal as a String.
     * @param reasonForDismissal a <code>String</code> containing the reason for dismissal as a String.
     */
    public void setReasonForDismissal(final String reasonForDismissal) {
        this.reasonForDismissal = reasonForDismissal;
    }

}
