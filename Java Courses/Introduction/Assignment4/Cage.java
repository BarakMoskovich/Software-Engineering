/**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

import java.io.PrintWriter;
import java.util.Scanner;

public class Cage {
	public static int CAGE_NUMBER = 1;
	
    private final double MAX_WEIGHT = 100;
    private final int MAX_IN_CAGE = 4;
    private final int MAX_MALE = 1;

    private Dog[] dogs;
    private boolean isFull;
    private int cageNumber;
    private int totalWeight;
    private int maleCount;

    public Cage() {
        this.dogs = new Dog[MAX_IN_CAGE];
        this.isFull = false;
        this.cageNumber = CAGE_NUMBER++;
        this.totalWeight = 0;
        this.maleCount = 0;
    }
    
    // New constructor
    public Cage(Scanner scan) {
        this.dogs = new Dog[MAX_IN_CAGE];
        this.isFull = false;
        this.totalWeight = 0;
        this.maleCount = 0;
        this.cageNumber = -1;
        
        int inCage = Integer.parseInt(scan.nextLine()); 
        int overArr = 0;
        
        if (inCage > MAX_IN_CAGE - getExistDogs()) {
        	overArr = Math.abs(MAX_IN_CAGE - getExistDogs() - inCage);
        	inCage = MAX_IN_CAGE - getExistDogs();
        }
        
        for (int j = 0; j < inCage; j++) {
        	if (scan.hasNextLine()) {
        		dogs[j] = new Dog(scan);
        		dogs[j].setCageNumber(cageNumber);
        		if (dogs[j].isMale())
        			maleCount++;
        		checkAvailability();
        		totalWeight += dogs[j].getWeight();
        	}
        }
        
        if (overArr > 0) {
        	for (int i = 0; i < overArr; i++) {
        		if (scan.hasNextLine()) {
        			scan.nextLine();
        			scan.nextLine();
        		}
        	}
        }
        	
    }
    
    // New function
    public void save(PrintWriter writer) {
    	writer.println(cageNumber);
    	writer.println(getExistDogs());
    	for (int i = 0; i < MAX_IN_CAGE; i++) {
    		if (dogs[i] != null)
    			dogs[i].save(writer);
    	}
    }

    public void setNumber(int cageNumber) {
    	this.cageNumber = cageNumber;
    }
    
    public Dog[] getDogs() {
    	return dogs;
    }
    
    public boolean isFull() {
    	return isFull;
    }
    
    public int getCageNumber() {
    	return cageNumber;
    }

    public double getMaxWeight() {
    	return MAX_WEIGHT;
    }
    
    public int getMaxInCage () {
    	return MAX_IN_CAGE;
    }
    
    public int getMaxMale() {
    	return MAX_MALE;
    }
    
    public Dog getDogById(int dogId) {
        for (int i = 0; i < MAX_IN_CAGE; i++)
            if (dogs[i] != null)
                if (dogs[i].getId() == dogId)
                    return dogs[i];
       
        return null;
    }
    
    public int getExistDogs() {
    	int count = 0;
    	for (int i = 0; i < MAX_IN_CAGE; i++)
    		if (dogs[i] != null)
    			count++;
    	return count;
    }

    public boolean isEmpty() {
    	for (int i = 0; i < MAX_IN_CAGE; i++)
    		if (dogs[i] != null)
    			return false;
    	
    	return true;
    }
    
    public void checkAvailability() {
    	isFull = true;
    	
    	for (int i = 0; i < MAX_IN_CAGE; i++) {
    		if (dogs[i] == null) {
    			isFull = false;
    			break;
    		}
    	}
    }
    
    private boolean Exists(Dog dog) {
        for (int i = 0; i < MAX_IN_CAGE; i++)
            if (dogs[i] != null)
                if (dogs[i].equals(dog))
                    return true;
       
        return false;
    }
    
    public boolean addDogToCage(Dog dog) {
    	if (isFull || ( totalWeight + dog.getWeight() ) > MAX_WEIGHT || Exists(dog))
    		return false;
    	
    	if (dog.isMale())
    		if (maleCount >= MAX_MALE)
    			return false;

        for (int i = 0; i < MAX_IN_CAGE; i++) {
            if (dogs[i] == null) {
            	dogs[i] = new Dog(dog);
            	totalWeight += dogs[i].getWeight();
            	if (dog.isMale())
					maleCount++;
            	dogs[i].setCageNumber(cageNumber);
	            checkAvailability();
	            return true;
            }
        }
    	
    	return false;
    }
    
    public boolean removeDog(Dog dog) {
        for (int i = 0; i < MAX_IN_CAGE; i++) {
        	if (dogs[i] != null) {
	            if (dogs[i].equals(dog)) {
	            	if (dogs[i].isMale())
	            		maleCount--;
	            	totalWeight -= dogs[i].getWeight();
	                dogs[i] = null;
	                checkAvailability();
	                return true;
	            }
        	}
        }
        return false;
    }
    
    public boolean removeDogById(int dogId) {
        for (int i = 0; i < MAX_IN_CAGE; i++) {
        	if (dogs[i] != null) {
	            if (dogs[i].getId() == dogId) {
	            	if (dogs[i].isMale())
	            		maleCount--;
	            	totalWeight -= dogs[i].getWeight();
	                dogs[i] = null;
	                checkAvailability();
	                return true;
	            }
            }
        }
    	return false;
    }
   
    @Override
    public String toString() {
    	StringBuffer s = new StringBuffer("In cage " + cageNumber + " there are " + getExistDogs() +" dogs \n");
    	for (int i = 0; i < MAX_IN_CAGE; i++)
	    if (dogs[i] != null)
	    	s.append(dogs[i].toString() + " is in cage number " + cageNumber + "\n");
    	return s.toString();
    }
}