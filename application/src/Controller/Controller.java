/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import Model.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.impl.SQLQueryImpl;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author user
 */
public class Controller extends Observable {

    private Session session;
    private static Controller controller;
    private User loggedIn;

    @Override
    public void notifyObservers(Object o) {
        this.setChanged();
        super.notifyObservers(o);
    }

    public static Controller Instance() {
        if (controller == null) {
            controller = new Controller();
        }

        return controller;
    }

    // <editor-fold defaultstate="collapsed" desc="LOGOUT">
    /**
     * *
     * Logout current user
     */
    public void Logout() {
        this.loggedIn = null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="LOGIN">
    public User getLoggedIn() {
        return loggedIn;
    }

    /**
     * *
     * Login as Admin
     *
     * @param username
     * @param password
     * @return
     */
    public boolean Login(String username, String password) {
        boolean result = false;

        initSession();
        List results = session.createSQLQuery("SELECT id,username,PASSWORD,rank FROM `user` WHERE username='" +username + "' and password=md5('" + password + "')").list();

        if (!results.isEmpty()) {
            User usr = new User();
            Object[] o = (Object[]) results.get(0);
            usr.setId(Integer.parseInt(o[0].toString()));
            usr.setUsername(o[1].toString());
            usr.setPassword(o[2].toString());
            if (o[3].toString().equals("STAFF")) {
                usr.setRank(Rank.STAFF);
            } else if (o[3].toString().equals("ADMIN")) {
                usr.setRank(Rank.ADMIN);
            } else {
                usr.setRank(Rank.USER);
            }
            this.loggedIn = usr;
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    private void initSession() {
        if (session == null || HibernateUtil.getSessionFactory().isClosed()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="GENERAL">   
    /**
     * *
     * @example List<Staff> staff = (List<Staff>) data.list(Staff.class)
     * @param <T> list to be retrieved based on given type
     * @param type
     * @return
     */
    public <T> List list(Class<T> type) {
        try {
            initSession();
            Transaction tx = session.beginTransaction();
            String entName = type.toString().substring(type.toString().indexOf(".") + 1, type.toString().length());
            List<T> list = session.createQuery(String.format("from %s", entName)).list();

            //take hibernate's proxy in account for loazy loading
            List<T> returnList = new ArrayList();
            for (T t : list) {
                if (!(t instanceof HibernateProxy)) {
                    returnList.add(t);
                } else {
                    returnList.add((T) (((HibernateProxy) t).getHibernateLazyInitializer().getImplementation()));
                }
            }

            return returnList;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * *
     * @example Airport schiphol = <Airport>loadById(Airport.class, 2);
     * @param type class type
     * @param id object id
     * @return the object from specified class
     */
    public <T> T loadById(Class<T> type, int id) {
        try {
            initSession();
            Transaction tx = session.beginTransaction();
            T rObj = (T) session.load(type, id);
            tx.commit();
            return rObj;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public boolean save(Object obj) {
        boolean result = false;
        try {
            initSession();
            Transaction tx = session.beginTransaction();

            int id = (Integer) session.save(obj);
            tx.commit();

            result = true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }

    public boolean update(Object obj) {
        try {
            initSession();
            Transaction tx = session.beginTransaction();
            session.update(obj);
            tx.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public boolean delete(Object obj) {
        try {
            initSession();
            Transaction tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="AIRPORT">
    public ArrayList<Airport> getAirports() {
        return new ArrayList<Airport>(list(Airport.class));
    }

    public ArrayList<Airport> searchAirport(String searchString) {
        ArrayList<Airport> result = new ArrayList<Airport>();

        for (Airport a : getAirports()) {
            if (a.getCity().contains(searchString)) {
                result.add(a);
            } else if (a.getCountry().contains(searchString)) {
                result.add(a);
            } else if (a.getName().contains(searchString)) {
                result.add(a);
            } else if (a.getCode().contains(searchString)) {
                result.add(a);
            } else if (String.valueOf(a.getId()).contains(searchString)) {
                result.add(a);
            }
        }

        return result;
    }

    public Airport getAirportByName(String name) {
        Airport foundAirport = null;

        for (Airport a : getAirports()) {

            if (a.getName().equals(name)) {
                foundAirport = a;
                break;
            }
        }
        return foundAirport;
    }

    public Airport getAirportByCode(String code) {
        Airport foundAirport = null;

        for (Airport a : getAirports()) {

            if (a.getCode().equals(code)) {
                foundAirport = a;
                break;
            }
        }

        return foundAirport;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="FLIGHT">
    public ArrayList<Flight> getFlights() {
        return new ArrayList<Flight>(list(Flight.class));
    }

    public ArrayList<Flight> searchFlight(Date date) {
        ArrayList<Flight> result = new ArrayList<Flight>();
        DateFormat df = new SimpleDateFormat(Flight.FLIGHTDATAFORMAT);
        for (Flight f : getFlights()) {
            if (df.format(f.getDate()).equals(df.format(date))) {
                result.add(f);
            }
        }
        return result;
    }

    public boolean addFlight(Flight f) {
        initSession();
        boolean result = false;
        try {
            if (save(f)) {
                result = true;
            }

            if (result) {
                this.notifyObservers(f);
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return result;
    }

    public boolean updateFlight(Flight newFlight, Flight oldFlight) {
        initSession();
        boolean result = false;

        if (newFlight.getNumber() != oldFlight.getNumber()) {
            String query = String.format("update Flight set flightnumber = %s where flightnumber = %s", newFlight.getNumber(), oldFlight.getNumber());
            try {
                Transaction tx = session.beginTransaction();
                session.evict(oldFlight);
                Query hQuery = session.createQuery(query);
                hQuery.executeUpdate();
                tx.commit();
                result = true;
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            if (update(newFlight)) {
                result = true;
            }
        }
        if (result) {
            notifyObservers(oldFlight);
        }
        return result;
    }

    public boolean removeFlight(Flight f) {
        boolean result = false;

        if (delete(f)) {
            result = true;
            notifyObservers(f);
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="STAFF">
    public ArrayList<Staff> getStaff() {
        return new ArrayList<Staff>(list(Staff.class));
    }

    public ArrayList<User> getStaff(Rank rank) {
        ArrayList<User> formatedList = new ArrayList<User>();

        for (User usr : new ArrayList<User>(list(User.class))) {
            if (usr.getRank() == rank) {
                formatedList.add(usr);
            }
        }
        return formatedList;
    }

    public ArrayList<Staff> getStaff(PersonalType type) {
        ArrayList<Staff> typePersonal = new ArrayList<Staff>();
        for (Staff staff : getStaff()) {
            if (staff.getType() == type) {
                typePersonal.add(staff);
            }
        }
        return typePersonal;
    }

    public ArrayList<Staff> searchStaff(String name) {
        ArrayList<Staff> result = new ArrayList<Staff>();

        for (Staff s : getStaff()) {
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    public ArrayList<Staff> searchStaff(PersonalType st) {
        ArrayList<Staff> result = new ArrayList<Staff>();

        for (Staff s : getStaff()) {
            if (s.getType().equals(st)) {
                result.add(s);
            }
        }
        return result;
    }

    public ArrayList<Staff> searchStaffPilots(String name) {
        ArrayList<Staff> result = new ArrayList<Staff>();

        for (Staff s : getStaff()) {
            if (s.getName().toLowerCase().contains(name.toLowerCase()) && s.getType().equals(PersonalType.Pilot)) {
                result.add(s);
            }
        }
        return result;
    }

    public ArrayList<Staff> searchStaff(int staffId) {
        ArrayList<Staff> result = new ArrayList<Staff>();

        for (Staff s : getStaff()) {
            if (s.getId() == staffId) {
                result.add(s);
            }
        }
        return result;
    }

    public ArrayList<Staff> searchStaffAvailable(Date d) {

        ArrayList<Staff> foundStaff = new ArrayList<Staff>();
        boolean dontAdd;
        Date nextDay, previousDay;
        for (Staff s : getStaff()) {
            dontAdd = false;
            for (Flight f : getFlights()) {
                if (f.getOtherPersonal().contains(s) || f.getPilot().equals(s) || f.getCopilot().equals(s)) {
                    nextDay = (Date) d.clone();
                    nextDay.setDate(d.getDate() + 1);
                    previousDay = (Date) d.clone();
                    previousDay.setDate(d.getDate() - 1);


                    if ((f.getDate().getDate() == d.getDate()
                            && f.getDate().getMonth() == d.getMonth()
                            && f.getDate().getYear() == d.getYear()) || // staff is already on a plane today
                            (f.getDate().getDate() == nextDay.getDate()
                            && f.getDate().getMonth() == nextDay.getMonth()
                            && f.getDate().getYear() == nextDay.getYear()) || // staff is already on a plane tomorrow
                            (f.getDate().getDate() == previousDay.getDate()
                            && f.getDate().getMonth() == previousDay.getMonth()
                            && f.getDate().getYear() == previousDay.getYear()) // staff was already on a plane yesterday
                            ) {
                        dontAdd = true;
                    }
                }
            }
            if (!dontAdd) {
                foundStaff.add(s);
            }
        }
        return foundStaff;
    }

    public ArrayList<Staff> searchStaffPilotsAvailable(Date d) {

        ArrayList<Staff> foundStaff = new ArrayList<Staff>();
        boolean dontAdd;
        Date nextDay, previousDay;
        for (Staff s : getStaff()) {
            if (s.getType() != PersonalType.Pilot) {
                continue; // only looking for pilots, so if this isn't a pilot, continue to the next staff member
            }
            dontAdd = false;
            for (Flight f : getFlights()) {
                if (f.getPilot().equals(s) || f.getCopilot().equals(s)) {
                    nextDay = (Date) d.clone();
                    nextDay.setDate(d.getDate() + 1);
                    previousDay = (Date) d.clone();
                    previousDay.setDate(d.getDate() - 1);


                    if ((f.getDate().getDate() == d.getDate()
                            && f.getDate().getMonth() == d.getMonth()
                            && f.getDate().getYear() == d.getYear()) || // staff is already on a plane today
                            (f.getDate().getDate() == nextDay.getDate()
                            && f.getDate().getMonth() == nextDay.getMonth()
                            && f.getDate().getYear() == nextDay.getYear()) || // staff is already on a plane tomorrow
                            (f.getDate().getDate() == previousDay.getDate()
                            && f.getDate().getMonth() == previousDay.getMonth()
                            && f.getDate().getYear() == previousDay.getYear()) // staff was already on a plane yesterday
                            ) {
                        dontAdd = true;
                    }
                }
            }
            if (!dontAdd) {
                foundStaff.add(s);
            }
        }
        return foundStaff;
    }

    public Staff getStaffById(int staffId) {
        return loadById(Staff.class, staffId);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PLANES">
    public ArrayList<Plane> getPlanes() {
        return new ArrayList<Plane>(list(Plane.class));
    }

    public ArrayList<Plane> searchPlanes(int number) {
        ArrayList<Plane> foundPlanes = new ArrayList<Plane>();
        for (Plane p : getPlanes()) {
            if (p.getNumber() == number) {
                foundPlanes.add(p);
            }
        }
        return foundPlanes;
    }

    public ArrayList<Plane> searchPlanes(String type) {
        ArrayList<Plane> foundPlanes = new ArrayList<Plane>();
        for (Plane p : getPlanes()) {
            if (p.getType().contains(type)) {

                foundPlanes.add(p);
            }
        }
        return foundPlanes;
    }

    public ArrayList<Plane> searchPlanesAvailable(Date d) {
        ArrayList<Plane> foundPlanes = new ArrayList<Plane>();
        boolean dontAdd;
        Date dateAfter;
        for (Plane p : getPlanes()) {
            dontAdd = false;
            for (Flight f : getFlights()) {
                dateAfter = addDays(d, -1);
                //dateAfter = d.

                if (d.getYear() == f.getDate().getYear() && d.getMonth() == f.getDate().getMonth() && d.getDay() == f.getDate().getDay() && f.getPlane() == p) {
                    dontAdd = true;
                }
                if (dateAfter.getYear() == f.getDate().getYear() && dateAfter.getMonth() == f.getDate().getMonth() && dateAfter.getDay() == f.getDate().getDay() && f.getPlane() == p) {
                    dontAdd = true;
                }
            }

            if (!dontAdd) {
                foundPlanes.add(p);
            }
        }
        return foundPlanes;
    }

    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public Plane getPlaneById(int planeId) {
        return loadById(Plane.class, planeId);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="USERS">
    public ArrayList<User> getUsers(boolean formatted) {
        if (formatted) {
            ArrayList<User> formattedList = new ArrayList<User>();

            for (User usr : new ArrayList<User>(list(User.class))) {
                if ((usr.getRank() == Rank.ADMIN) || (usr.getRank() == Rank.USER)) {
                    formattedList.add(usr);
                }
            }
            return formattedList;
        } else {
            return new ArrayList<User>(list(User.class));
        }
    }
    // </editor-fold>
}
