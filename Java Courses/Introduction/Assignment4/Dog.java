/**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

import java.io.PrintWriter;
import java.util.Scanner;

public class Dog {
	public static int GLOBAL_ID = 100;
	
    private final double MIN_WEIGHT = 5;
    private final String MALE = "male";
    private final String WOMEN = "female";
    
    private double weight;
    private boolean isMale;
    private MyDate date;
    private String name;
    private String type;
    private int dogId;
    private int cageNumber;
    
    public Dog(double weight, boolean isMale, MyDate date, String name, String type) {
        if (weight < MIN_WEIGHT)
            this.weight = MIN_WEIGHT;
        else
            this.weight = weight;

        this.isMale = isMale;
        this.date = date;
        this.name = legalName(name); // Changed
        this.type = type;
        this.cageNumber = -1;
        this.dogId = GLOBAL_ID++;
    }
    
    public Dog(Dog dog) {
    	this.weight = dog.getWeight();
        this.isMale = dog.isMale;
        this.date = dog.getDate();
        this.name = dog.getName(); // No need
        this.type = dog.getType();
        this.cageNumber = dog.getCageNumber();
        this.dogId = dog.getId();
        
    }
    
    // New constructor
    public Dog(Scanner scan) {
    	if (scan.hasNextLine()) {
		    String line = scan.nextLine();
		    String[] lineData = line.split(", ");
		    this.name = legalName(lineData[0]);
		    this.weight = Double.parseDouble(lineData[1]);
		    this.isMale = Boolean.parseBoolean(lineData[2]);
		    this.type = lineData[3];
		    this.dogId = Integer.parseInt(lineData[4]);
	    	this.date = new MyDate(scan);
    	}
    }
    	
    // New function
    public void save(PrintWriter writer) {
    	writer.println(name + ", " + (int)(weight)  + ", " + isMale + ", " + type + ", " + dogId);
    	date.save(writer);
    }
    
    // New function
    private String legalName(String name) {
    	if (name.length() > 8 || !StringUtil.isOnlyLetters(name) ||
    			name.charAt(0) != name.toUpperCase().charAt(0))
    		return "TheD";
    	return name;
    }
    
    public int getCageNumber() {
    	return cageNumber;
    }
    
    public double getWeight() {
    	return weight;
    }
    
    public boolean isMale() {
    	return isMale;
    }
    
    public String getGender() {
        if (isMale)
            return MALE;
        return WOMEN;
    }
    
    public MyDate getDate() {
    	return date;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getType() {
    	return type;
    }
    
    public int getId() {
    	return dogId;
    }
    
    public void setCageNumber(int cageNumber) {
    	this.cageNumber = cageNumber;
    }
    
    public void setWeight(int weight) {
    	this.weight = weight;
    }
    
    public void setDate(MyDate date) {
    	this.date = date;
    }
    
    public void setName(String name) {
    	this.name  = legalName(name); // Changed
    }

    public double getMinWeight() { 
        return MIN_WEIGHT;
    }
    
    @Override
    public String toString() {
        return "Dog id " + dogId + " " + name + " of type " + type + " weights " + weight + " " + getGender() + " enter to dog house on " + date;
    }
}
