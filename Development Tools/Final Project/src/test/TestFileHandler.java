package test;

import core.Flight;
import org.junit.Assert;

import java.io.*;
import java.util.ArrayList;

public class TestFileHandler implements Runnable{

    /*
        available data:
            r - read from TestData file
            d - delete from TestData file
            any other String will be written directly to TestData
     */
    public static final String TEL_AVIV = "Tel Aviv";
    public static final String NEW_YORK = "New York";
    public static final String LONDON = "London";
    public static final String TEL_AVIV_AIRPORT = "Ben-Gurion";
    public static final String NEW_YORK_AIRPORT = "York";
    public static final String LONDON_AIRPORT = "London-city";
    public static final String FIRST_FLIGHT_NUMBER = "EL123";
    public static final String SECOND_FLIGHT_NUMBER = "EL1234";
    public static final String FIRST_AIRLINE = "EL-AL";
    public static final String SECOND_AIRLINE = "Turkish Airlines";
    public static final String FILENAME = "TestData",
            PATH = "./src/data/";

    private final String data;
    private ArrayList<String> readResult;

    public TestFileHandler(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public void run() {
        switch (data) {
            case "R":
            case "r":
                readFromFile();
                break;
            default:
                writeToFile(data);
        }
    }

    public ArrayList<String> getReadResult() {
        if (readResult == null) {
            readFromFile();
        }
        return readResult;
    }

    private void writeToFile(String data) {
        try {
            FileWriter fileWriter = new FileWriter(PATH + FILENAME, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data);

            bufferedWriter.close();
        } catch (IOException ex) {
            Assert.fail("Can't write to this file!");
        }
    }

    public void readFromFile() {
        // use only to force read again

        String line = null;
        ArrayList<String> result = null;
        try {
            FileReader fileReader = new FileReader(PATH + FILENAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            result = new ArrayList<>();

            while ( (line = bufferedReader.readLine()) != null ) {
                result.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + FILENAME + "'");
        } catch(IOException ex) {
            System.out.println("Error : couldn't reading file '" + FILENAME + "'");
        }

        readResult = result;
    }

    public void deleteTestFileContent() {
        writeToFile("");
        if (readResult != null) {
            readResult.clear();
        }
    }

}