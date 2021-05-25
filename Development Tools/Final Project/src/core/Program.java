package core;

import java.util.*;

import static core.Utilities.*;

public class Program {
    public static Tracking departures = new Tracking("Departures"); // Departures Tracking
    public static Tracking arrivals = new Tracking("Arrivals"); // Arrivals Tracking

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Utilities.importAll();
        boolean on = true;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("homepage")) {
                Utilities.header();
                System.out.println("Search Flight");
                Utilities.footer();
            } else {
                boolean sunday, monday, tuesday, wednesday, thursday, friday, saturday;
                String outformat, status, airline, country, city, airport;
                int day1, month1, year1, day2, month2, year2;

                try {
                    outformat = args[0];
                    status = args[1]; // Departures OR Arrivals
                    airline = args[2];
                    country = args[3];
                    city = args[4];
                    airport = args[5];
                    day1 = Integer.parseInt(args[6]);
                    month1 = Integer.parseInt(args[7]);
                    year1 = Integer.parseInt(args[8]);
                    day2 = Integer.parseInt(args[9]);
                    month2 = Integer.parseInt(args[10]);
                    year2 = Integer.parseInt(args[11]);
                    sunday = Boolean.parseBoolean(args[12]);
                    monday = Boolean.parseBoolean(args[13]);
                    tuesday = Boolean.parseBoolean(args[14]);
                    wednesday = Boolean.parseBoolean(args[15]);
                    thursday = Boolean.parseBoolean(args[16]);
                    friday = Boolean.parseBoolean(args[17]);
                    saturday = Boolean.parseBoolean(args[18]);

                    ArrayList<Flight> flights = null;

                    if (status.equalsIgnoreCase("departures"))
                        flights = departures.search(true, airline, country, city, airport, day1, month1, year1, day2, month2, year2, sunday, monday, tuesday, wednesday, thursday, friday, saturday);
                    else if (status.equalsIgnoreCase("arrivals"))
                        flights = arrivals.search(false, airline, country, city, airport, day1, month1, year1, day2, month2, year2, sunday, monday, tuesday, wednesday, thursday, friday, saturday);

                    if (outformat.equalsIgnoreCase("html")) { // web
                        Utilities.header();
                        Utilities.printSearchResults(true, flights);
                        Utilities.footer();
                    } else if (outformat.equalsIgnoreCase("text")) { // terminal
                        Utilities.printSearchResults(false, flights);
                    }
                } catch (Exception e) {
                    System.out.println("Wrong URI");
                }
            }
        } else {
            // Test
            Calendar startCal = new GregorianCalendar();
            startCal.set(Calendar.YEAR, 2010);
            startCal.set(Calendar.MONTH, 10);
            startCal.set(Calendar.DAY_OF_MONTH, 9);

            Calendar finishCal = new GregorianCalendar();
            finishCal.set(Calendar.YEAR, 2010);
            finishCal.set(Calendar.MONTH, 10);
            finishCal.set(Calendar.DAY_OF_MONTH, 11);

            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, 10);
            cal.set(Calendar.DAY_OF_MONTH, 12);

            if (startCal.compareTo(cal) * cal.compareTo(finishCal) > 0)
                System.out.println("SFsdfsd");

            Utilities.showMenu(s);
            while (on) {
                try {
                    switch (s.nextInt()) {
                        case -1:
                            on = false;
                            break;
                        case 1:
                            departures.addFlight(Utilities.newFlight(s, true));
                            showMenu(s);
                            break;
                        case 2:
                            arrivals.addFlight(Utilities.newFlight(s, false));
                            Utilities.showMenu(s);
                            break;
                        case 3:
                            showDepartures();
                            showMenu(s);
                            break;
                        case 4:
                            showArrivals();
                            showMenu(s);
                            break;
                        case 5:
                            exportAll();
                            showMenu(s);
                            break;
                        case 6:
                            showAll();
                            showMenu(s);
                            break;
                        default:
                            exportAll();
                            break;
                    }
                } catch (InputMismatchException exception) {
                    System.out.println("Integers only");
                    s.nextLine();
                }
            }
        }
    }
}