package core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Flight {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private int id;
    private static int numGen;
    private String airline;
    private String airport; // new
    private String from;
    private String to;
    private String city; // new
    private Calendar startDate; // new
    private Calendar finishDate; // new
    private String flightNumber;
    private int terminalNumber;

    public Flight(String airline, String airport, String from, String to, String city, Calendar startDate, Calendar finishDate, String flightNumber, int terminalNumber, int id) {
        setAirline(airline);
        setAirport(airport);
        setFrom(from);
        setTo(to);
        setCity(city);
        setStartDate(startDate);
        setFinishDate(finishDate);
        setFlightNumber(flightNumber);
        setTerminalNumber(terminalNumber);
        setId(id);
    }

    public Flight(String airline, String airport, String from, String to, String city, Calendar startDate, Calendar finishDate, String flightNumber, int terminalNumber) {
        this(airline, airport, from, to, city, startDate, finishDate, flightNumber, terminalNumber, -5);
    }

    /**************** Get functions ****************/
    public String getAirline() {
        return airline;
    }

    public String getAirport() {
        return airport;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public String getFormatStartDate() {
        return dateFormat.format(startDate.getTime());
    }

    public String getFormatStartTime() {
        return timeFormat.format(startDate.getTime());
    }

    public String getFormatFinishTime() {
        return timeFormat.format(finishDate.getTime());
    }

    public String getFormatFinishDate() {
        return dateFormat.format(finishDate.getTime());
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getCity() {
        return city;
    }

    public int getTerminalNumber() {
        return terminalNumber;
    }

    public int getId() {
        return id;
    }

    /**************** Set functions ****************/
    private void setAirline(String airline) {
        this.airline = airline;
    }

    private void setAirport(String airport) {
        this.airport = airport;
    }

    private void setFrom(String from) {
        this.from = from;
    }

    private void setTo(String to) {
        this.to = to;
    }

    private void setCity(String city) {
        this.city = city;
    }

    private void setStartDate(Calendar startDate) {
        this.startDate = (Calendar)startDate.clone();
        this.startDate.add(Calendar.MONTH, -1);

    }

    private void setFinishDate(Calendar finishDate) {
        this.finishDate = (Calendar)finishDate.clone();
        this.finishDate.add(Calendar.MONTH, -1);
    }

    private void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    private void setTerminalNumber(int terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    private void setId(int id) {
        if (id <= -1) {
            this.id = numGen;
            numGen++;
        } else {
            this.id = id;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightNumber.equals(flight.flightNumber);
    }

    /****************** functions ******************/
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append("Airline: " + airline + ". ");
        if(!from.toUpperCase().equals("Tel-Aviv".toUpperCase()) && !from.toUpperCase().equals("Tel Aviv".toUpperCase()))
            sb.append("From: " + from + ". ");

        if(!to.toUpperCase().equals("Tel-Aviv".toUpperCase()) && !to.toUpperCase().equals("Tel Aviv".toUpperCase()))
            sb.append("To: " + to + ". ");



        sb.append("Start Date: " + dateFormat.format(startDate.getTime()) + ", " +
                timeFormat.format(startDate.getTime())  + ". " +
                "Finish Date: " + dateFormat.format(finishDate.getTime()) + ", " +
                timeFormat.format(finishDate.getTime())  + ". " +
                "Flight number: " + flightNumber + ". " +
                "Airport: " + airport + ". " +
                "Terminal: " + terminalNumber);

        return sb.toString();
    }
}