package test;

import core.Database;
import core.Flight;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseTest {
    private static Calendar date1, date2;
    private static Database database;
    private static TestFileHandler testFileHandler;

    private final String firstAirline = TestFileHandler.FIRST_AIRLINE;
    private final String secondAirline = TestFileHandler.SECOND_AIRLINE;
    private final String newYorkAirport = TestFileHandler.NEW_YORK_AIRPORT;
    private final String telAviv = TestFileHandler.TEL_AVIV;
    private final String newYork = TestFileHandler.NEW_YORK;
    private final String firstFlightNumber = TestFileHandler.FIRST_FLIGHT_NUMBER;

    @BeforeClass
    public static void initDatabase() {
        database = new Database(TestFileHandler.FILENAME, TestFileHandler.PATH);


        testFileHandler = new TestFileHandler("EL-AL, York, Tel Aviv, New York, New York, 20-04-2020, 00:00, 20-45-2020, 08:10, EL123, 3\n" +
                                                   "EL-AL, York, Tel Aviv, New York, New York, 20-04-2020, 00:00, 20-45-2020, 08:10, EL1234, 3");
        date1 = Calendar.getInstance();
        date1.set(2020, 4, 20, 00, 55);

        date2 = Calendar.getInstance();
        date2.set(2020, 4, 20, 8, 10);

    }

    @After
    public void clear() {
        database.clear();
        testFileHandler.deleteTestFileContent();
    }

    @Test
    public void isFileEmpty() {
        try {
            Method isFileEmptyMethod = Database.class.getDeclaredMethod("isFileEmpty");
            isFileEmptyMethod.setAccessible(true);

            boolean isFileEmpty = (Boolean)isFileEmptyMethod.invoke(database, null);

            Assert.assertTrue("Should be empty!", isFileEmpty);
            testFileHandler.run();

            isFileEmpty = (Boolean)isFileEmptyMethod.invoke(database, null);

            Assert.assertFalse("Shouldn't be empty!", isFileEmpty);
        }catch (NoSuchMethodException nsme) {
            Assert.fail("There is not method called isFileEmpty!");
        } catch (IllegalAccessException e) {
            Assert.fail("Can't access the method!");
        } catch (InvocationTargetException e) {
            Assert.fail("Can't read the method!");
        }
    }

    @Test
    public void write() {
        // writeLine was tested here as well
        try {
            Method writeMethod = Database.class.getDeclaredMethod("write");
            writeMethod.setAccessible(true);

            // Before write
            Assert.assertTrue("Should be empty", testFileHandler.getReadResult().isEmpty());

            // write
            Flight flight1 = new Flight(firstAirline, newYorkAirport, telAviv, newYork, newYork, date1, date2, firstFlightNumber, 3);
            Flight flight2 = new Flight(secondAirline, newYorkAirport, telAviv, newYork, newYork, date1, date2, firstFlightNumber, 3);

            database.addFlight(flight1);
            database.addFlight(flight2);
            boolean isWritten = (Boolean)writeMethod.invoke(database, null);

            // After write
            ArrayList<Flight> result = database.getFlightsList();

            Assert.assertTrue("Should be written", isWritten);
            Assert.assertNotNull("Should contain array", result);
            Assert.assertTrue("Should be the same string", flight1.toString().equals(result.get(0).toString()));
            Assert.assertTrue("Should be the same string", flight2.toString().equals(result.get(1).toString()));

            ArrayList<String> fileResult = testFileHandler.getReadResult();
            Assert.assertTrue("All written data to file is correct", database.getFlights().values().containsAll(fileResult));

        } catch (NoSuchMethodException e) {
            Assert.fail("There is not method called write!");
        } catch (IllegalAccessException e) {
            Assert.fail("Can't access the method!");
        } catch (InvocationTargetException e) {
            Assert.fail("Can't read the method!");
        }
    }

    @Test
    public void read() {
        try {
            // Before read
            database.read();
            Assert.assertTrue("Should be empty", database.getFlights().isEmpty() || database.getFlights() == null);

            testFileHandler.run();
            database.read();

            // After read
            Assert.assertTrue("Should contain the data for testFileHandler", database.getFlights().values().containsAll(testFileHandler.getReadResult()));

        } catch (Exception e) {
            Assert.fail("Error in filename or path");
        }
    }

    @Test
    public void addFlight() {
        Flight flight1 = new Flight(firstAirline, newYorkAirport, telAviv, newYork, newYork, date2, date1, firstFlightNumber, 3);

        // Before addFlight
        Assert.assertTrue("Should be empty", database.getFlightsList().isEmpty());

        database.addFlight(flight1);

        // After addFlight
        Assert.assertFalse("Shouldn't be empty", database.getFlightsList().isEmpty());
        Assert.assertTrue("Should contain the exact flight", database.getFlightsList().contains(flight1));

    }

    @Test
    public void generateFlightToString() {
        Flight flight1 = new Flight(firstAirline, newYorkAirport, telAviv, newYork, newYork, date1, date2, firstFlightNumber, 3);

        // Before generateFlightToString
        Assert.assertFalse("Shouldn't exist", database.getFlightsList().contains(flight1));

        try {
            Method generateFlightToStringMethod = Database.class.getDeclaredMethod("generateFlightToString", Flight.class);
            generateFlightToStringMethod.setAccessible(true);

            String invokeResult = (String)generateFlightToStringMethod.invoke(database, flight1);


            // After generateFlightToString
            Assert.assertNull("Method void -> null", invokeResult);
            Assert.assertTrue("Should contain the exact String", database.getFlightsList().get(0).toString().equals(flight1.toString()));

        } catch (NoSuchMethodException e) {
            Assert.fail("There is no method called generateFlightToString!");
        } catch (IllegalAccessException e) {
            Assert.fail("Can't access the method!");
        } catch (InvocationTargetException e) {
            Assert.fail("Can't read the method!");
        }
    }

    @Test
    public void generateToFlight() {
        String flightToBe = "EL-AL, York, Tel Aviv, New York, New York, 20/04/2020, 00:00, 20/05/2020, 08:10, EL123, 3";

        try {
            Method generateToFlightMethod = Database.class.getDeclaredMethod("generateToFlight", String.class, int.class);
            generateToFlightMethod.setAccessible(true);

            Flight flight = (Flight)generateToFlightMethod.invoke(database, flightToBe, 1);

            Assert.assertNotNull("Shouldn't be null", flight);
        } catch (NoSuchMethodException e) {
            Assert.fail("There is no method called generateToFlight!");
        } catch (IllegalAccessException e) {
            Assert.fail("Can't access the method!");
        } catch (InvocationTargetException e) {
            Assert.fail("Can't read the method!");
        }
    }
}
