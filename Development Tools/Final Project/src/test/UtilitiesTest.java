package test;

import core.Flight;
import core.Utilities;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class UtilitiesTest {

    public static Utilities menuUtils;
    public Scanner userInput;
    public String input;
    public Method method;

    @BeforeClass
    public static void beforeClass() {
        menuUtils = new Utilities();
    }

    @Test
    public void getStringFromClient() {
        input = "string";
        userInput = new Scanner(input + "\n");

        try {
            method = Utilities.class.getDeclaredMethod("getStringFromClient", String.class, Scanner.class);
            method.setAccessible(true);

            String result = (String)method.invoke(menuUtils, "", userInput);

            Assert.assertEquals("Should be the same", result, input);


        } catch (NoSuchMethodException e) {
            Assert.fail("There is not method called getStringFromClient!");
        } catch (IllegalAccessException e) {
            Assert.fail("Can't access the method!");
        } catch (InvocationTargetException e) {
            Assert.fail("Can't read the method!");
        }
    }

    @Test
    public void getIntFromClient() {
        input = "5";
        userInput = new Scanner(input + "\n");

        try {
            method = Utilities.class.getDeclaredMethod("getIntFromClient", String.class, Scanner.class);
            method.setAccessible(true);

            int result = (int)method.invoke(menuUtils, "", userInput);

            Assert.assertEquals("Should be the same", result, Integer.parseInt(input));


        } catch (NoSuchMethodException e) {
            Assert.fail("There is not method called getStringFromClient!");
        } catch (IllegalAccessException e) {
            Assert.fail("Can't access the method!");
        } catch (InvocationTargetException e) {
            Assert.fail("Can't read the method!");
        }
    }

    @Test
    public void newFlight() {

        String airline = "ELAL";
        String to = "London";
        String city = "London-city";
        String airport = "London";
        String startDate = "10/05/2020";
        String startTime = "02:55";
        String finishDate = "12/05/2020";
        String finishTime = "02:55";
        String flightNumber = "LN-533";
        String terminal = "3";

        input = "\n" + airline + "\n" + to + "\n" + city + "\n" + airport + "\n" + startDate + "\n" + startTime + "\n" + finishDate + "\n" +finishTime + "\n" +
                flightNumber + "\n" + terminal;

        // Departure
        Flight flight = getFlight(input, true);
        Assert.assertNotNull("Shouldn't be null", flight);
        Assert.assertEquals("Should be the same", flight.getAirline(), airline);
        Assert.assertEquals("Should be the same", flight.getTo(), to);
        Assert.assertEquals("Should be the same", flight.getFrom(), "Tel Aviv");
        Assert.assertEquals("Should be the same", flight.getCity(), city);
        Assert.assertEquals("Should be the same", flight.getAirport(), airport);
        Assert.assertEquals("Should be the same", flight.getFormatStartDate(), startDate);
        Assert.assertEquals("Should be the same", flight.getFormatStartTime(), startTime);
        Assert.assertEquals("Should be the same", flight.getFormatFinishDate(), finishDate);
        Assert.assertEquals("Should be the same", flight.getFormatFinishTime(), finishTime);
        Assert.assertEquals("Should be the same", flight.getFlightNumber(), flightNumber);
        Assert.assertEquals("Should be the same", flight.getTerminalNumber(), Integer.parseInt(terminal));

        // Arrival
        flight = getFlight(input, false);
        Assert.assertNotNull("Shouldn't be null", flight);
        Assert.assertEquals("Should be the same", flight.getAirline(), airline);
        Assert.assertEquals("Should be the same", flight.getTo(), "Tel Aviv");
        Assert.assertEquals("Should be the same", flight.getFrom(), to);
        Assert.assertEquals("Should be the same", flight.getCity(), city);
        Assert.assertEquals("Should be the same", flight.getAirport(), airport);
        Assert.assertEquals("Should be the same", flight.getFormatStartDate(), startDate);
        Assert.assertEquals("Should be the same", flight.getFormatStartTime(), startTime);
        Assert.assertEquals("Should be the same", flight.getFormatFinishDate(), finishDate);
        Assert.assertEquals("Should be the same", flight.getFormatFinishTime(), finishTime);
        Assert.assertEquals("Should be the same", flight.getFlightNumber(), flightNumber);
        Assert.assertEquals("Should be the same", flight.getTerminalNumber(), Integer.parseInt(terminal));
    }

    private Flight getFlight(String input, Boolean type) {
        userInput = new Scanner(input + "\n");
        return Utilities.newFlight(userInput, type);
    }
}
