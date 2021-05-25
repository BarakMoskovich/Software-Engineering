package WeatherSiteTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
    private File file;

    public FileUtils(String fileName, String path) {
        file = new File(path + '/' + fileName);
        checkFile();
    }

    public void checkFile() {
        try {
            if (!file.exists() || file.isDirectory()) {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File exists.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public ArrayList<String> read() {
        ArrayList<String> list = new ArrayList<>();

        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext())
                list.add(reader.nextLine());
        } catch (FileNotFoundException e) {
            checkFile();
            System.out.println("Please try again.");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return list;
    }
}
