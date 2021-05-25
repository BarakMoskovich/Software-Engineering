package core;

import java.util.*;


public class Tracking {
    private List<Flight> flightList;
    private String filename, path;
    private Database db;
    private boolean flightListUpdated;

    public Tracking(String filename) {
        flightList = new ArrayList<Flight>();
        db = new Database(filename);
        this.filename = filename;
        path = db.getPath();
    }

    public Tracking(String filename, String path) {
        flightList = new ArrayList<Flight>();
        db = new Database(filename, path);
        this.filename = filename;
        this.path = path;
    }

    public Tracking(Tracking tracking) {
        flightList = tracking.getFlightList();
        db = tracking.getDB();
        flightListUpdated = tracking.flightListUpdated;
        filename = tracking.getFileName();
        path = tracking.getPath();
    }

    public Tracking() {
        flightList = new ArrayList<Flight>();
        db = null;
    }

    /**************** Get functions ****************/
    public List<Flight> getFlightList() {
        return flightList;
    }

    public Database getDB() {
        return db;
    }

    public String getFileName() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    /****************** Comparator *****************/
    private Comparator<Flight> compareByAirline = Comparator.comparing(Flight::getAirline);
    private Comparator<Flight> compareByAirport = Comparator.comparing(Flight::getAirport);
    private Comparator<Flight> compareByFrom = Comparator.comparing(Flight::getFrom);
    private Comparator<Flight> compareByTo = Comparator.comparing(Flight::getTo);
    private Comparator<Flight> compareByCity = Comparator.comparing(Flight::getCity);
    private Comparator<Flight> compareByStartDate = Comparator.comparing(Flight::getStartDate);
    private Comparator<Flight> compareByFinishDate = Comparator.comparing(Flight::getFinishDate);
    private Comparator<Flight> compareByFlightNumber = Comparator.comparing(Flight::getFlightNumber);

    /****************** functions ******************/
    public void importFlights() {
        if(!db.isEmpty()) {
//        if(db.isEmpty()) {
            db.read();
            updateFlightList();
        }
    }

    public void exportFlights() {
        for (Flight flight: flightList) {
            db.addFlight(flight);
        }
        db.write();
    }

    public void addFlight(Flight flight) {
        if (flight != null && !flightList.contains(flight)) {
            flightList.add(flight);
            db.addFlight(flight);
        }
    }

    public void removeFlight(Flight flight) {
        flightList.remove(flight);
        db.removeFlight(flight.getId());
    }

    private void updateFlightList() {
        // updates flightList from file

        if (!flightListUpdated) {
            flightList = db.getFlightsList();
            flightListUpdated = true;
        }
    }

    public void sortByAirline() {
        flightList.sort(compareByAirline);
    }

    public void sortByAirport() {
        flightList.sort(compareByAirport);
    }

    public void sortByFrom() {
        flightList.sort(compareByFrom);
    }

    public void sortByTo() {
        flightList.sort(compareByTo);
    }

    public void sortByCity() {
        flightList.sort(compareByCity);
    }

    public void sortByStartDate() {
        flightList.sort(compareByStartDate);
    }

    public void sortByFinishDate() {
        flightList.sort(compareByFinishDate);
    }

    public void sortByFlightNumber() {
        flightList.sort(compareByFlightNumber);
    }

    public void clearEverything() {
        flightList.clear();
        db.clear();
    }

    public ArrayList<Flight> search(boolean departure, String airline, String country, String city, String airport,
                                    int day1, int month1, int year1, int day2, int month2, int year2, boolean sunday,
                                    boolean monday, boolean tuesday, boolean wednesday, boolean thursday,
                                    boolean friday, boolean saturday) {

        ArrayList<Flight> searchFlightList = new ArrayList<>();

        Calendar startCal = generateCalendar(year1, month1, day1);
        Calendar finishCal = generateCalendar(year2, month2, day2);

        String from, to;

        if (departure) {
            from = "Tel Aviv";
            to = country;
        } else {
            from = country;
            to = "Tel Aviv";
        }

        Calendar nullCal = generateCalendar(0,0,0);
        Calendar currentCal = Calendar.getInstance();

        if (!startCal.equals(nullCal) && finishCal.equals(nullCal))
            finishCal = generateCalendar(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH) + 1, currentCal.get(Calendar.DAY_OF_MONTH));

        for (Flight flight : flightList) {
            Calendar flightCal = flight.getStartDate();
            Calendar cal = generateCalendar(flightCal.get(Calendar.YEAR), flightCal.get(Calendar.MONTH) + 1, flightCal.get(Calendar.DAY_OF_MONTH));

            // Check dates
            if ( ( cal.equals(startCal) || cal.equals(finishCal) ) ||
                    ( cal.after(startCal) && cal.before(finishCal) ) ||
                    nullCal.equals(startCal) && nullCal.equals(finishCal) ) {

                // Check days
                if ( ( sunday && getCorrectDayString(cal).equals("sunday") ) ||
                     ( monday && getCorrectDayString(cal).equals("monday") ) ||
                     ( tuesday && getCorrectDayString(cal).equals("tuesday") ) ||
                     ( wednesday && getCorrectDayString(cal).equals("wednesday") ) ||
                     ( thursday && getCorrectDayString(cal).equals("thursday") ) ||
                     ( friday && getCorrectDayString(cal).equals("friday") ) ||
                     ( saturday && getCorrectDayString(cal).equals("saturday") )) {

                    // Check strings
                    if (airline != null && airline.length() != 0)
                        if (!flight.getAirline().equals(airline))
                            break;
                    if (airport != null && airport.length() != 0)
                        if (!flight.getAirport().equals(airport))
                            break;
                    if (from != null && from.length() != 0)
                        if (!flight.getFrom().equals(from))
                            break;
                    if (to != null && to.length() != 0)
                        if (!flight.getTo().equals(to))
                            break;
                    if (city != null && city.length() != 0)
                        if (!flight.getCity().equals(city))
                            break;

                    searchFlightList.add(flight);
                }
            }
        }
        return searchFlightList;
    }


    private Calendar generateCalendar(int year, int month, int day) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    private int getCorrectDay(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek - 3 == 0)
            return 7;
        else if (dayOfWeek - 3 == -1)
            return 6;
        else if (dayOfWeek - 3 == -2)
            return 5;
        else
            return (dayOfWeek - 3);
    }

    private String getCorrectDayString(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek - 3) {
            case 4:
                return "sunday";
            case 3:
                return "monday";
            case 2:
                return "tuesday";
            case 1:
                return "wednesday";
            case 0:
                return "saturday";
            case -1:
                return "friday";
            default:
            case -2:
                return "thursday";
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < flightList.size(); i++) {
            if (i == 0) {
                sb.append(flightList.get(i).toString());
            } else {
                sb.append("\n" + flightList.get(i).toString());
            }
        }

        return sb.toString();
    }
}
