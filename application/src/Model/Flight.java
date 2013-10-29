/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author user
 */
public class Flight {

    public static final String FLIGHTDATAFORMAT = "dd-MM-yyyy";
    private Date date;
    private Airport destination;
    private Airport From;
    private int number;
    private int id;
    private Set<Staff> otherPersonal;
    private Set<Airport> stops;
    private Staff pilot;
    private Staff copilot;
    private Plane plane;
    private Set<Airmarshall> marshalls;

    public Flight() {
        this.date = new Date();
        this.otherPersonal = new HashSet<Staff>();
        this.stops = new HashSet<Airport>();
        this.marshalls = new HashSet<Airmarshall>();
    }

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public Date getDate() {
        return date;
    }

    public String flyDateString(boolean returnDate) {
        DateFormat formatter = new SimpleDateFormat(FLIGHTDATAFORMAT);
        String frmDate = formatter.format(date);

        if (returnDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            frmDate = formatter.format(cal.getTime());
        }
        return frmDate;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the value of plane
     *
     * @return the value of plane
     */
    public Plane getPlane() {
        return plane;
    }

    /**
     * Set the value of plane
     *
     * @param plane new value of plane
     */
    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    /**
     * Get the value of From
     *
     * @return the value of From
     */
    public Airport getFrom() {
        return From;
    }

    /**
     * Set the value of From
     *
     * @param From new value of From
     */
    public void setFrom(Airport From) {
        this.From = From;
    }

    /**
     * Get the value of destination
     *
     * @return the value of destination
     */
    public Airport getDestination() {
        return destination;
    }

    /**
     * Set the value of destination
     *
     * @param destination new value of destination
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    /**
     * Get the value of otherPersonal
     *
     * @return the value of otherPersonal
     */
    public Set<Staff> getOtherPersonal() {
        return otherPersonal;
    }

    /**
     * Set the value of otherPersonal
     *
     * @param otherPersonal new value of otherPersonal
     */
    public void setOtherPersonal(Set<Staff> otherPersonal) {
        this.otherPersonal = otherPersonal;
    }

    /**
     * Find staff by staffnumber in otherPersonal Return true if staff is found
     * within this flight
     *
     * @param staffnumber
     */
    public boolean findOtherPersonal(Staff staf) {
        for (Staff stf : otherPersonal) {
            if (stf.getId() == staf.getId()) {
                return true;
            }
        }

        return false;
    }
    
    public boolean findAirmarshalls(Staff ams) {
       for (Airmarshall am : marshalls) {
            if (am.getId() == ams.getId()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the value of stops
     *
     * @return the value of stops
     */
    public Set<Airport> getStops() {
        return stops;
    }

    /**
     * Set the value of stops
     *
     * @param otherPersonal new value of stops
     */
    public void setStops(Set<Airport> stops) {
        this.stops = stops;
    }

    public Staff getCopilot() {
        return copilot;
    }

    public void setCopilot(Staff copilot) {
        this.copilot = copilot;
    }

    public Staff getPilot() {
        return pilot;
    }

    public void setPilot(Staff pilot) {
        this.pilot = pilot;
    }

    /**
     * Get the value of number
     *
     * @return the value of number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the value of number
     *
     * @param number new value of number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Set<Airmarshall> getMarshalls() {
        return marshalls;
    }

    public void setMarshalls(Set<Airmarshall> marshalls) {
        this.marshalls = marshalls;
    }
}
