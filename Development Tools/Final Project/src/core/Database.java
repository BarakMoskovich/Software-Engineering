package core;

import java.io.*;
import java.util.*;

import static core.Flight.dateFormat;
import static core.Flight.timeFormat;

public class Database {
    private boolean isFileEmpty;
    private String fileName;
    private String path;
    private HashMap<Integer, String> flights; // contains all flights directly from file

    public Database(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
        flights = new HashMap<>();
        isFileEmpty = isFileEmpty();
    }

    public Database(String fileName) {
        this(fileName, "./src/data/");
    }

//    public Database(String fileName) { this(fileName, ""); }

    /**************** Get functions ****************/
    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public int getFileLength() {
        return flights.size();
    }

    public HashMap<Integer, String> getFlights() {
        return flights;
    }

    public ArrayList<Flight> getFlightsList() {
        ArrayList<Flight> temp = new ArrayList<>();
        if (!flights.isEmpty()) {
            for (int id: flights.keySet())
                temp.add(generateToFlight(flights.get(id), id));

        }
        return temp;
    }

    /**************** Set functions ****************/
    private boolean isFileEmpty() {
        File file = new File(path + fileName);
        if (file.length() <= 1) {
            isFileEmpty = true;
            return true;
        }
        isFileEmpty = false;
        return false;
    }

    /****************** functions ******************/
    protected boolean write() {
        // writes all data to path+filename
        try {
            // false -> overwrite the file
            FileWriter fileWriter = new FileWriter(path + fileName, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String flight: flights.values()) {
                writeLine(flight);
            }
            bufferedWriter.close();
            return true;
        }
        catch (IOException ex) {
            System.out.println("Error : couldn't writing to file '" + fileName + "'");
            return false;
        }

    }

    private void writeLine(String data) {
        try{
            // true -> add line to existing file
            FileWriter fileWriter = new FileWriter(path + fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error : couldn't writing to file '" + fileName + "'");
        }
    }

    public HashMap<Integer, String> read() {
        // reads all data and puts it to flights

        String line = null;
        int linesCount = 1;

        try {
            FileReader fileReader = new FileReader(path + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ( (line = bufferedReader.readLine()) != null ) {
                flights.put(linesCount, line);
                linesCount++;
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error : couldn't reading file '" + fileName + "'");
        }
        return flights;
    }

    public boolean addFlight(Flight flight) {
        if (flight != null && !isExists(flight.getId())) {
            generateFlightToString(flight);
            return true;
        }
        return false;
    }

    private boolean isExists(int id) {
        return flights.containsKey(id);
    }

    private void generateFlightToString(Flight flight) {

        String temp = flight.getAirline() + ", " + flight.getAirport() + ", " + flight.getFrom() +
                ", " + flight.getTo() + ", " + flight.getCity() + ", " +
                dateFormat.format(flight.getStartDate().getTime()) + ", " +
                timeFormat.format(flight.getStartDate().getTime()) + ", " +
                dateFormat.format(flight.getFinishDate().getTime()) + ", " +
                timeFormat.format(flight.getFinishDate().getTime()) + ", " +
                flight.getFlightNumber() + ", " + flight.getTerminalNumber();

        flights.put(flight.getId(), temp);
    }

    private Flight generateToFlight(String flightData, int id) {
        try {
            String[] temp = flightData.split(", ");
            Calendar startCal = new GregorianCalendar();
            Calendar finishCal = new GregorianCalendar();

            String airline = temp[0];                                              // set Airline
            String airport = temp[1];                                              // set Airport
            String from = temp[2];                                                 // set From
            String to = temp[3];                                                   // set To
            String city = temp[4];                                                 // set City
            String[] dateSplit = temp[5].split("/");
            startCal.set(Calendar.YEAR, Integer.parseInt(dateSplit[2]));           // set StartYear
            startCal.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]));          // set StartMonth
            startCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[0]));   // set StartDay

            dateSplit = temp[6].split(":");
            startCal.set(Calendar.MINUTE, Integer.parseInt(dateSplit[1]));         // set StartMinute
            startCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateSplit[0]));    // set StartHour

            dateSplit = temp[7].split("/");
            finishCal.set(Calendar.YEAR, Integer.parseInt(dateSplit[2]));         // set FinishYear
            finishCal.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]));        // set FinishMonth
            finishCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[0])); // set FinishDay

            dateSplit = temp[8].split(":");
            finishCal.set(Calendar.MINUTE, Integer.parseInt(dateSplit[1]));       // set FinishMinute
            finishCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateSplit[0]));  // set FinishHour

            String flightNumber = temp[9];                                        // set FlightNumber
            int terminalNumber = Integer.parseInt(temp[10]);                      // set terminalNumber

            return new Flight(airline, airport, from, to, city, startCal, finishCal, flightNumber, terminalNumber, id);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error in file format!");
        }
        return null;
    }

    public void clear() {
        flights.clear();
    }

    public void removeFlight(int flightNumber) {
        flights.remove(flightNumber);
    }

    public boolean isEmpty() {
        return flights.isEmpty() && isFileEmpty;
    }
}
