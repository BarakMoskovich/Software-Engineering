package core;

import java.util.*;

import static core.Program.arrivals;
import static core.Program.departures;

public class Utilities {
    public static void showMenu(Scanner s) {
        System.out.print("Welcome to Tel-Aviv Airport\n" +
                "Choose one of the inputs to update our flights\n" +
                "1. Enter new departure flight\n" +
                "2. Enter new arrival flight\n" +
                "3. Show all departures\n" +
                "4. Show all arrivals\n" +
                "5. Export to Existing files\n" +
                "6. Show Everything\n" +
                "Enter your choice (exit -1) : ");
    }

    // True - Departure
    // False - Arrival
    public static Flight newFlight(Scanner s, boolean type) {
        String airline, from, to, flightNumber, date, time, airport, city;
        Calendar startCal = new GregorianCalendar();
        Calendar finishCal = new GregorianCalendar();
        int terminal;
        String[] dateSplit;
        boolean cont = false;

        s.nextLine();

        airline = getStringFromClient("What is your airline company? ", s);

        if (type) {
            to = getStringFromClient("What is your destination? ", s);
            from = "Tel Aviv";
        } else {
            to = "Tel Aviv";
            from = getStringFromClient("From where is your flight? ", s);
        }

        city = getStringFromClient("Enter city name: ", s);
        airport = getStringFromClient("Enter airport name: ", s);


        date = getStringFromClient("Enter start flight date: (dd/mm/yyyy) ", s);
        while (!cont) {
            try {
                dateSplit = date.split("/");
                startCal.set(Calendar.YEAR, Integer.parseInt(dateSplit[2]));
                startCal.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]));
                startCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[0]));
                cont = true;
            }
            catch (Exception e) {
                cont = false;
                date = getStringFromClient("Enter start flight date: (dd/mm/yyyy) ", s);
            }
        }

        time = getStringFromClient("Enter start flight time: (hh:mm) ", s);
        cont = false;
        while (!cont) {
            try {
                dateSplit = time.split(":");
                startCal.set(Calendar.MINUTE, Integer.parseInt(dateSplit[1]));
                startCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateSplit[0]));
                cont = true;
            }
            catch (Exception e) {
                cont = false;
                time = getStringFromClient("Enter start flight time: (hh:mm) ", s);
            }
        }

        date = getStringFromClient("Enter finish flight date: (dd/mm/yyyy) ", s);
        cont = false;
        while (!cont) {
            try {
                dateSplit = date.split("/");
                finishCal.set(Calendar.YEAR, Integer.parseInt(dateSplit[2]));
                finishCal.set(Calendar.MONTH, Integer.parseInt(dateSplit[1]));
                finishCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[0]));
                cont = true;
            }
            catch (Exception e) {
                cont = false;
                date = getStringFromClient("Enter finish flight date: (dd/mm/yyyy) ", s);
            }
        }

        time = getStringFromClient("Enter finish flight time: (hh:mm) ", s);
        cont = false;
        while (!cont) {
            try {
                dateSplit = time.split(":");
                finishCal.set(Calendar.MINUTE, Integer.parseInt(dateSplit[1]));
                finishCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateSplit[0]));
                cont = true;
            }
            catch (Exception e) {
                cont = false;
                time = getStringFromClient("Enter finish flight time: (hh:mm) ", s);
            }
        }

        flightNumber = getStringFromClient("Enter your flight number: ", s);

        terminal = getIntFromClient("Enter Terminal number: ", s);

        Flight flight = new Flight(airline, airport, from, to, city, startCal, finishCal, flightNumber, terminal);
        return flight;
    }

    public static void exportAll() {
        departures.exportFlights();
        arrivals.exportFlights();
    }

    public static void importAll() {
        departures.importFlights();
        arrivals.importFlights();
    }

    public static void showDepartures() {
        System.out.println(departures.toString());
    }

    public static void showArrivals() {
        System.out.println(arrivals.toString());
    }

    public static void showAll() {
        showDepartures();
        showArrivals();
    }

    private static String getStringFromClient(String msg, Scanner s) {
        String str = "";

        try {
            System.out.print(msg);
            str = s.nextLine();
        }
        catch (Exception e) {
            System.out.println("Try again");
        }

        return str;
    }

    private static int getIntFromClient(String msg, Scanner s) {
        int number = 0;

        try {
            System.out.print(msg);
            number = s.nextInt();
        } catch (Exception e) {
            System.out.println("Try again");
        }

        return number;
    }

    public static void header() {
        System.out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Flight Tracking</title>\n" +
                "    <style>\n" +
                "        * {\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        body {\n" +
                "            background: #fafafa;\n" +
                "            border-top: 5px solid #add1e8;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        span, a {\n" +
                "            font-family: Times, serif;\n" +
                "        }\n" +
                "\n" +
                "        .logo {\n" +
                "            background: url(\"http://up419.siz.co.il/up2/0yggzkym5tet.png\") no-repeat;\n" +
                "            background-size:contain;\n" +
                "            width: 500px;\n" +
                "            height: 125px;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .main {\n" +
                "            margin-top:20px;\n" +
                "        }\n" +
                "\n" +
                "        .title {\n" +
                "            font-family: Verdana, Arial, Tahoma, Serif;\n" +
                "            color: #add1e8;\n" +
                "            font-size: 22px;\n" +
                "            padding-right: 5px;\n" +
                "            padding-left: 5px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        a.title, a.title:link, a.title:visited a.title:active {\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        .title:hover {\n" +
                "            color: #77b3d8;\n" +
                "        }\n" +
                "\n" +
                "        .heading {\n" +
                "            font-size: 20px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        .subheading {\n" +
                "            font-size: 18px;\n" +
                "        }\n" +
                "\n" +
                "        .body {\n" +
                "            color: #999;\n" +
                "            font-size: 14px;\n" +
                "        }\n" +
                "\n" +
                "        .footer {\n" +
                "            color: #999;\n" +
                "            font-size: 12px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        div.body {\n" +
                "            margin-top: 5px;\n" +
                "            text-align: left;\n" +
                "            background: #fafafa;\n" +
                "            border: #CCCCCC 1px solid;\n" +
                "            padding: 20px;\n" +
                "            width: 800px;\n" +
                "            min-height: 250px;\n" +
                "        }\n" +
                "\n" +
                "        div.search {\n" +
                "            margin-top: 10px;\n" +
                "            text-align: left;\n" +
                "            background: #fafafa;\n" +
                "            border: #CCCCCC 1px solid;\n" +
                "            padding: 20px;\n" +
                "            width: 800px;\n" +
                "            height: 70px;\n" +
                "        }\n" +
                "\n" +
                "        div.footer {\n" +
                "            margin-top: 5px;\n" +
                "            width: 840px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        a.footer, a.footer:link, a.footer:visited a.footer:active {\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        a.footer:hover {\n" +
                "            color: #77b3d8;\n" +
                "        }\n" +
                "\n" +
                "        input[type=text], input[type=date], textarea, button, select {\n" +
                "            all: unset;\n" +
                "            background: #FFF;\n" +
                "            width: 150px;\n" +
                "            height: 20px;\n" +
                "            border: 1px solid #CCC;\n" +
                "            padding: 2px 2px 2px 2px;\n" +
                "            font-size: 16px;\n" +
                "            color: #999;\n" +
                "            margin-left: 1px;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        input:focus, textarea:focus, button:focus {\n" +
                "            border: 1px solid #77b3d8;\n" +
                "            color: #000;\n" +
                "        }\n" +
                "\n" +
                "        input[type=submit] {\n" +
                "            all: unset;\n" +
                "            width: 75px;\n" +
                "            height: 75px;\n" +
                "            box-shadow:inset 0px 1px 0px 0px #ffffff;\n" +
                "            background:linear-gradient(to bottom, #ededed 5%, #dfdfdf 100%);\n" +
                "            background-color:#ededed;\n" +
                "            border-radius:6px;\n" +
                "            border:1px solid #dcdcdc;\n" +
                "            display:inline-block;\n" +
                "            cursor:pointer;\n" +
                "            color:#777777;\n" +
                "            font-family:Arial;\n" +
                "            font-size:15px;\n" +
                "            font-weight:bold;\n" +
                "            text-decoration:none;\n" +
                "            text-shadow:0px 1px 0px #ffffff;\n" +
                "            padding:0 0 0 17px;\n" +
                "            margin-left: 8px;\n" +
                "        }\n" +
                "\n" +
                "        input[type=submit]:hover {\n" +
                "            background:linear-gradient(to bottom, #dfdfdf 5%, #ededed 100%);\n" +
                "            background-color:#dfdfdf;\n" +
                "        }\n" +
                "\n" +
                "        input[type=submit]:active {\n" +
                "            position:relative;\n" +
                "            top:1px;\n" +
                "        }\n" +
                "\n" +
                "        .right {\n" +
                "            float: right;\n" +
                "        }\n" +
                "\n" +
                "        .left {\n" +
                "            float: left;\n" +
                "        }\n" +
                "\n" +
                "        label {\n" +
                "            color: #999;\n" +
                "            padding-right: 2px;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <script>\n" +
                "        function actionFunc() {\n" +
                "            if (document.getElementById(\"type\").value === \"Departures\")\n" +
                "                document.search_form.action = \"departures\";\n" +
                "            else\n" +
                "                document.search_form.action = \"arrivals\";\n" +
                "        }\n" +
                "\n" +
                "        function sendCheckboxes() {\n" +
                "            if (document.getElementById(\"sunday\").checked)\n" +
                "                document.getElementById('sundayHidden').disabled = true;\n" +
                "\n" +
                "            if (document.getElementById(\"monday\").checked)\n" +
                "                document.getElementById('mondayHidden').disabled = true;\n" +
                "\n" +
                "            if (document.getElementById(\"tuesday\").checked)\n" +
                "                document.getElementById('tuesdayHidden').disabled = true;\n" +
                "\n" +
                "            if (document.getElementById(\"wednesday\").checked)\n" +
                "                document.getElementById('wednesdayHidden').disabled = true;\n" +
                "\n" +
                "            if (document.getElementById(\"thursday\").checked)\n" +
                "                document.getElementById('thursdayHidden').disabled = true;\n" +
                "\n" +
                "            if (document.getElementById(\"friday\").checked)\n" +
                "                document.getElementById('fridayHidden').disabled = true;\n" +
                "\n" +
                "            if (document.getElementById(\"saturday\").checked)\n" +
                "                document.getElementById('saturdayHidden').disabled = true;\n" +
                "        }\n" +
                "\n" +
                "        function startDateChange() {\n" +
                "            let res = document.getElementById(\"startDate\").value.split(\"-\");\n" +
                "            document.getElementById(\"year1\").value = res[0];\n" +
                "            document.getElementById(\"month1\").value = res[1];\n" +
                "            document.getElementById(\"day1\").value = res[2];\n" +
                "        }\n" +
                "\n" +
                "        function finishDateChange() {\n" +
                "            let res = document.getElementById(\"finishDate\").value.split(\"-\");\n" +
                "            document.getElementById(\"year2\").value = res[0];\n" +
                "            document.getElementById(\"month2\").value = res[1];\n" +
                "            document.getElementById(\"day2\").value = res[2];\n" +
                "        }\n" +
                "        function finishDateBiggerThanStart() {\n" +
                "            let startDate = document.getElementById(\"startDate\").value;\n" +
                "            let finishDate = document.getElementById(\"finishDate\").value;\n" +
                "            if (startDate > finishDate) {\n" +
                "                alert(\"Please enter a date after the start date\");\n" +
                "                document.getElementById(\"finishDate\").value = '';\n" +
                "            }\n" +
                "        }\n" +
                "    </script>\n" +
                "</head>\n" +
                "\n" +
                "<body onload=\"actionFunc()\">\n" +
                "<div class=\"main\">\n" +
                "    <a href=\"http://localhost:8000/\"><div class=\"logo\"></div></a>\n" +
                "    <div class=\"search\">\n" +
                "        <form method=\"get\" id=\"search_form\" name=\"search_form\" action=\"/\">\n" +
                "            <input type=\"hidden\" name=\"outformat\" id=\"outformat\" value=\"html\" />\n" +
                "            <table>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <select id=\"type\" onchange=\"actionFunc()\">\n" +
                "                            <option value=\"\" disabled selected>Select your option</option>\n" +
                "                            <option value=\"Departures\">Departures</option>\n" +
                "                            <option value=\"Arrivals\">Arrivals</option>\n" +
                "                        </select>\n" +
                "                    </td>\n" +
                "                    <td><input type=\"text\" id=\"airline\" name=\"airline\" placeholder=\"Airline\" /></td>\n" +
                "                    <td><input type=\"text\" id=\"country\" name=\"country\" placeholder=\"Country\" style=\"width: 170px\" /></td>\n" +
                "                    <td><input type=\"text\" id=\"city\" name=\"city\" placeholder=\"City\" style=\"width: 170px\"/></td>\n" +
                "                    <td rowspan=\"2\"><input type=\"submit\" value=\"Search\" onclick=\"sendCheckboxes()\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><input type=\"text\" id=\"airport\" name=\"airport\" placeholder=\"Airport\" /></td>\n" +
                "                    <td>\n" +
                "                        <!-- Start Day-->\n" +
                "                        <input type=\"date\" id=\"startDate\" onchange=\"startDateChange()\">\n" +
                "                        <input type='hidden' id='day1' value='0' name='day1'>\n" +
                "                        <input type='hidden' id='month1' value='0' name='month1'>\n" +
                "                        <input type='hidden' id='year1' value='0' name='year1'>\n" +
                "                    </td>\n" +
                "                    <td colspan=\"2\">\n" +
                "                        <!-- Finish Day-->\n" +
                "                        <input type=\"date\" id=\"finishDate\" onchange=\"finishDateChange()\" onfocusout=\"finishDateBiggerThanStart()\">\n" +
                "                        <input type='hidden' id='day2' value='0' name='day2'>\n" +
                "                        <input type='hidden' id='month2' value='0' name='month2'>\n" +
                "                        <input type='hidden' id='year2' value='0' name='year2'>\n" +
                "                        <!-- Days -->\n" +
                "                        <label for=\"sunday\">S</label><input type=\"checkbox\" id=\"sunday\" name=\"sunday\" value=\"true\" checked >\n" +
                "                        <input type='hidden' id='sundayHidden' value='false' name='sunday'>\n" +
                "\n" +
                "                        <label for=\"monday\">M</label><input type=\"checkbox\" id=\"monday\" name=\"monday\" value=\"true\" checked >\n" +
                "                        <input type='hidden' id='mondayHidden' value='false' name='monday'>\n" +
                "\n" +
                "                        <label for=\"tuesday\">T</label><input type=\"checkbox\" id=\"tuesday\" name=\"tuesday\" value=\"true\" checked >\n" +
                "                        <input type='hidden' id='tuesdayHidden' value='false' name='tuesday'>\n" +
                "\n" +
                "                        <label for=\"wednesday\">W</label><input type=\"checkbox\" id=\"wednesday\" name=\"wednesday\" value=\"true\" checked > \n" +
                "                        <input type='hidden' id='wednesdayHidden' value='false' name='wednesday'>\n" +
                "\n" +
                "                        <label for=\"thursday\">T</label><input type=\"checkbox\" id=\"thursday\" name=\"thursday\" value=\"true\" checked >\n" +
                "                        <input type='hidden' id='thursdayHidden' value='false' name='thursday'>\n" +
                "\n" +
                "                        <label for=\"friday\">F</label><input type=\"checkbox\" id=\"friday\" name=\"friday\" value=\"true\" checked >\n" +
                "                        <input type='hidden' id='fridayHidden' value='false' name='friday'>\n" +
                "\n" +
                "                        <label for=\"saturday\">S</label><input type=\"checkbox\" id=\"saturday\" name=\"saturday\" value=\"true\" checked >\n" +
                "                        <input type='hidden' id='saturdayHidden' value='false' name='saturday'>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "    <div class=\"body\">\n");
    }

    public static void footer() {
        System.out.println("    </div>\n" +
                "    <div class=\"footer\">\n" +
                "        <div class=\"left\"><span class=\"footer\">Flight Tracking</span></div>\n" +
                "        <div class=\"right\">\n" +
                "            <span class=\"footer\">Barak Moskovich & Denis Karabitski</span>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public static void printSearchResults(boolean html, ArrayList<Flight> flights) {
        if (!flights.isEmpty()) {
            for (Flight f : flights) {
                System.out.println(f.toString() + "\n");
                if (html)
                    System.out.println("<br /><br />");
            }
        } else
            System.out.println("Not Found!");
    }
}