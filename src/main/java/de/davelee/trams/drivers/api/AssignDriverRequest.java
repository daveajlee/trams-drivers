package de.davelee.trams.drivers.api;

/**
 * This class represents a request to assign a driver to a particular route schedule.
 * @author Dave Lee
 */
public class AssignDriverRequest extends RetrieveDriverRequest {

    private String assignedRouteSchedule;

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

}
