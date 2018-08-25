package de.davelee.trams.drivers.data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * This class represents a driver history.
 * @author Dave Lee
 */
@Entity
@Table(name="DRIVER_HISTORY")
public class DriverHistory {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column
    private LocalDate date;

    @Column
    private DriverStatus status;

    @Column
    private String comment;

    /**
     * Return the date of this event in the driver's history.
     * @return a <code>LocalDate</code> containing the date of this event in the driver's history.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the date of this event in the driver's history.
     * @param date a <code>LocalDate</code> containing the date of this event in the driver's history.
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * Get the status of this event in the driver's history.
     * @return a <code>DriverStatus</code> object containing the status of this event in the driver's history.
     */
    public DriverStatus getStatus() {
        return status;
    }

    /**
     * Set the status of this event in the driver's history.
     * @param status a <code>DriverStatus</code> containing the status of this event in the driver's history.
     */
    public void setStatus(final DriverStatus status) {
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
