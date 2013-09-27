/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Bryan
 */
public class ScheduleInfo {

    private Flight flight;
    private String date, type, from, destination, plane, flightnumber, personal;

    public ScheduleInfo(Flight scheduledFlight, String personalRol, boolean returnFlight) {
        this.flight = scheduledFlight;
        type = returnFlight ? "Terug" : "Heenvlucht";
        date = flight.flyDateString(returnFlight);
        from = flight.getFrom().getName();
        destination = flight.getDestination().getName();
        plane = flight.getPlane().getType();
        flightnumber = flight.getNumber() + "";
        personal = personalRol;
    }

    public String getDate() {
        return date;
    }

    public String getDestination() {
        return destination;
    }

    public String getFlightnumber() {
        return flightnumber;
    }

    public String getFrom() {
        return from;
    }

    public String getPlane() {
        return plane;
    }

    public String getType() {
        return type;
    }

    public String getPersonal() {
        return personal;
    }
}
