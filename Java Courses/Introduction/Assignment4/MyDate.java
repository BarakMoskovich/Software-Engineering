/**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class MyDate {
	private final int[] DAYS_OF_MONTHS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	private int year;
	private int month;
	private int day;
	
	public MyDate(int day, int month, int year) {
        this.year = checkYear(year);
        this.month = checkMonth(month);
        this.day = checkDay(day, this.month);
	}

	// New constructor
	public MyDate(Scanner scan) {
		if (scan.hasNextLine()) {
	    	String line = scan.nextLine();
	    	String[] lineData = line.split(", ");
	    	this.day = Integer.parseInt(lineData[0]);
	    	this.month = Integer.parseInt(lineData[1]);
	    	this.year = Integer.parseInt(lineData[2]);
	  
	    	// Check?
	        this.year = checkYear(year);
	        this.month = checkMonth(month);
	        this.day = checkDay(day, this.month);
		}
	}
	
	// New function
	public void save(PrintWriter writer) {
		writer.println(day + ", " + month + ", " + year);
	}

    private int checkYear(int year) {
        if (year >= 2000 && year <= 2020) // Year 2000-2020
            return year;
        else
            return 2020;
    }
    
    private int checkMonth(int month) {
        if (month >= 1 && month <= 12) // Month 1-12
            return month;
        else
            return  1;
    }
    
    private int checkDay(int day, int month) {
    	if (day < 1 || day > DAYS_OF_MONTHS[month - 1])
    		return 1;
    	return day;
    }
    
    public int getYear() {
    	return year;
    }
    
    public int getMonth() {
    	return month;
    }
    
    public int getDay() {
    	return day;
    }

    public void setYear(int year) {
    	this.year = checkYear(year);
    }
    
    public void setMonth(int month) {
    	this.month = checkMonth(month);
    }
    
    public void setDay(int day) {
    	this.day = checkDay(day, month);
    }

	public int daysCount(MyDate d) {
		LocalDate enter = LocalDate.of(year, month, day);
		LocalDate out = LocalDate.of(d.year, d.month, d.day);
		Period period = Period.between(enter, out);
		int diff = Math.abs(period.getDays() + period.getMonths() * DAYS_OF_MONTHS[month - 1] + period.getYears() * 365);
		return diff;
	}

    @Override
    public String toString() {
        return String.format("%d/%d/%d", day, month, year);
    }
}
