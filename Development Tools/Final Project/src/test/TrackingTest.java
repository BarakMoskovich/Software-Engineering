package test;

import core.Flight;
import core.Tracking;
import org.junit.*;

import java.util.Calendar;

public class TrackingTest {

    private static Tracking TRACKING;
    private static Calendar DATE1, DATE2, DATE3;
    private static TestFileHandler testFileHandler;
    static final String filename = testFileHandler.FILENAME;
    static final String path = testFileHandler.PATH;

    private String telAviv = TestFileHandler.TEL_AVIV;
    private String newYork = TestFileHandler.NEW_YORK;
    private String london = TestFileHandler.LONDON;
    private String telAvivAirport = TestFileHandler.TEL_AVIV_AIRPORT;
    private String newYorkAirport = TestFileHandler.NEW_YORK_AIRPORT;
    private String londonAirport = TestFileHandler.LONDON_AIRPORT;
    private String firstFlightNumber = TestFileHandler.FIRST_FLIGHT_NUMBER;
    private String secondFlightNumber = TestFileHandler.SECOND_FLIGHT_NUMBER;
    private String firstAirline = TestFileHandler.FIRST_AIRLINE;
    private String secondAirline = TestFileHandler.SECOND_AIRLINE;
    private StringBuffer expectedResultBefore, expectedResultAfter;

    @BeforeClass
    public static void init() {
        TRACKING = new Tracking(filename, path);

        DATE1 = Calendar.getInstance();
        DATE1.set(2020, 4, 20, 00, 55);

        DATE2 = Calendar.getInstance();
        DATE2.set(2020, 4, 20, 8, 10);

        DATE3 = Calendar.getInstance();
        DATE3.set(2020, 4, 20, 16, 45);
    }

    @Before
    public void initStringBuffers() {
        TRACKING.clearEverything();
        expectedResultBefore = new StringBuffer();
        expectedResultAfter = new StringBuffer();
    }

    private void addTrack(String airline, String airport, String from, String to, String city, Calendar startDate, Calendar finishDate, String flightNumber, int terminalNumber){
        Flight f1 = new Flight(airline, airport, from, to, city, startDate, finishDate, flightNumber, terminalNumber);

        TRACKING.addFlight(f1);
    }

    @Test
    public void addFlight() {

        expectedResultBefore.append(0);
        Assert.assertEquals(Integer.parseInt(expectedResultBefore.toString()), TRACKING.getFlightList().size());

        addTrack(firstAirline, newYorkAirport, telAviv, newYork, newYork, DATE2, DATE1, firstFlightNumber, 3);
        expectedResultAfter.append(1);

        Assert.assertEquals(Integer.parseInt(expectedResultAfter.toString()), TRACKING.getFlightList().size());

        // check if duplicate flights are acceptable
        addTrack(firstAirline, newYorkAirport, telAviv, newYork, newYork, DATE2, DATE1, firstFlightNumber, 3);
        Assert.assertEquals(Integer.parseInt(expectedResultAfter.toString()), TRACKING.getFlightList().size());
    }

    @Test
    public void removeFlight() {

        addTrack(firstAirline, newYorkAirport, telAviv, newYork, newYork, DATE2, DATE1, firstFlightNumber, 3);
        expectedResultBefore.append(TRACKING.getFlightList().size());

        Assert.assertEquals(Integer.parseInt(expectedResultBefore.toString()), TRACKING.getFlightList().size());

        TRACKING.removeFlight(TRACKING.getFlightList().get(0));
        expectedResultAfter.append(TRACKING.getFlightList().size());

        Assert.assertEquals(Integer.parseInt(expectedResultAfter.toString()), TRACKING.getFlightList().size());
    }

    @Test
    public void flightDepartures_SortedByTo() {

        addTrack(firstAirline, newYorkAirport, telAviv, newYork, telAviv, DATE1, DATE2, firstFlightNumber, 3);
        addTrack(secondAirline, londonAirport, telAviv, london, telAviv, DATE1, DATE2, secondFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: EL-AL. To: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: York. Terminal: 3\n");
        expectedResultBefore.append("Airline: Turkish Airlines. To: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: London-city. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        // After sort
        TRACKING.sortByTo();
        expectedResultAfter.append("Airline: Turkish Airlines. To: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: London-city. Terminal: 3\n");
        expectedResultAfter.append("Airline: EL-AL. To: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: York. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());

    }

    @Test
    public void flightArrivals_SortedByFrom() {

        addTrack(firstAirline, telAvivAirport, newYork, telAviv, newYork, DATE1, DATE2, firstFlightNumber, 3);
        addTrack(secondAirline, telAvivAirport, london, telAviv, london, DATE1, DATE2, secondFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultBefore.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        // After sort
        TRACKING.sortByFrom();
        expectedResultAfter.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultAfter.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());
    }

    @Test
    public void SortByAirline() {

        addTrack(secondAirline, telAvivAirport, london, telAviv, london, DATE1, DATE2, secondFlightNumber, 3);
        addTrack(firstAirline, telAvivAirport, newYork, telAviv, newYork, DATE1, DATE2, firstFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultBefore.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        // After Sort
        TRACKING.sortByAirline();
        expectedResultAfter.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultAfter.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());
    }

    @Test
    public void SortByAirport() {
        addTrack(firstAirline, newYorkAirport, telAviv, newYork, telAviv, DATE1, DATE2, firstFlightNumber, 3);
        addTrack(secondAirline, londonAirport, telAviv, london, telAviv, DATE1, DATE2, secondFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: EL-AL. To: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: York. Terminal: 3\n");
        expectedResultBefore.append("Airline: Turkish Airlines. To: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: London-city. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        TRACKING.sortByAirport();

        expectedResultAfter.append("Airline: Turkish Airlines. To: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: London-city. Terminal: 3\n");
        expectedResultAfter.append("Airline: EL-AL. To: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: York. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());
    }

    @Test
    public void SortByCity() {
        addTrack(firstAirline, telAvivAirport, newYork, telAviv, newYork, DATE1, DATE2, firstFlightNumber, 3);
        addTrack(secondAirline, telAvivAirport, london, telAviv, london, DATE1, DATE2, secondFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultBefore.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        TRACKING.sortByCity();

        // After sort
        expectedResultAfter.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultAfter.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());
    }

    @Test
    public void SortByFlightNumber() {
        addTrack(secondAirline, telAvivAirport, london, telAviv, london, DATE1, DATE2, secondFlightNumber, 3);
        addTrack(firstAirline, telAvivAirport, newYork, telAviv, newYork, DATE1, DATE2, firstFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultBefore.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        TRACKING.sortByFlightNumber();

        // After sort
        expectedResultAfter.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultAfter.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());
    }

    @Test
    public void SortByStartDate() {
        addTrack(secondAirline, telAvivAirport, london, telAviv, london, DATE2, DATE3, secondFlightNumber, 3);
        addTrack(firstAirline, telAvivAirport, newYork, telAviv, newYork, DATE1, DATE3, firstFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 08:10. Finish Date: 20/04/2020, 16:45. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultBefore.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 16:45. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        TRACKING.sortByStartDate();

        // After sort
        expectedResultAfter.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 16:45. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultAfter.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 08:10. Finish Date: 20/04/2020, 16:45. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());
    }

    @Test
    public void SortByFinishDate() {
        addTrack(firstAirline, telAvivAirport, newYork, telAviv, newYork, DATE1, DATE3, firstFlightNumber, 3);
        addTrack(secondAirline, telAvivAirport, london, telAviv, london, DATE1, DATE2, secondFlightNumber, 3);

        // Before sort
        expectedResultBefore.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 16:45. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultBefore.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultBefore.toString(), TRACKING.toString());

        TRACKING.sortByFinishDate();

        // After sort
        expectedResultAfter.append("Airline: Turkish Airlines. From: London. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 08:10. Flight number: EL1234. Airport: Ben-Gurion. Terminal: 3\n");
        expectedResultAfter.append("Airline: EL-AL. From: New York. Start Date: 20/04/2020, 00:55. Finish Date: 20/04/2020, 16:45. Flight number: EL123. Airport: Ben-Gurion. Terminal: 3");
        Assert.assertEquals(expectedResultAfter.toString(), TRACKING.toString());
    }

    @Test
    public void importFlights() {

        String firstFlight = "Fasr, New Yorker, USA, Tel Aviv, New York, 12/05/2020, 11:00, 12/05/2020, 18:00, FDSr4, 3";
        String secondFlight = "El-Al, London-city, London, Tel Aviv, London, 12/02/2021, 12:22, 12/02/2021, 15:00, FDS54, 3";

        try {
            // Write to file
            String toFile = firstFlight + "\n" + secondFlight;
            testFileHandler = new TestFileHandler(toFile);
//            writeToFile(toFile);
            testFileHandler.run();

            boolean isEmpty = testFileHandler.getReadResult().isEmpty();

            // initialize tracking
            TRACKING = new Tracking(filename, path);

            // Before import
            Assert.assertFalse(isEmpty); // TestData should not be empty
            Assert.assertTrue(TRACKING.getDB().getFlightsList().isEmpty()); // db list should be empty

            TRACKING.importFlights();

            // After import
            Assert.assertFalse(TRACKING.getDB().getFlightsList().isEmpty());

        } finally {
            // in case there was an error in The test
            testFileHandler.deleteTestFileContent();
        }
    }

    @Test
    public void exportFlights() {
        // Before export
        addTrack(firstAirline, newYorkAirport, telAviv, newYork, telAviv, DATE1, DATE2, firstFlightNumber, 3);
        addTrack(secondAirline, londonAirport, telAviv, london, telAviv, DATE1, DATE2, secondFlightNumber, 3);

        testFileHandler = new TestFileHandler("r");

        Assert.assertTrue(testFileHandler.getReadResult().isEmpty()); // should return true

        try {
            TRACKING.exportFlights();
            testFileHandler.readFromFile();

            // After export
            Assert.assertFalse(testFileHandler.getReadResult().isEmpty());
        } finally {
            testFileHandler.deleteTestFileContent();
        }
    }
}