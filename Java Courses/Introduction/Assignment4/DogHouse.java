package pensionNSudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DogHouse {
	public static enum dogTypes { Wolf, Poodle, Pitbull, Malimois };
	private final int MAX_CAGES = 20;
	private final int MAX_IN_CAGE = 4;
    private String name;

    private Cage[] cages;
    private Dog[] dogs;
    private int dogsCount;
    private int cagesCount;
    private int outDogs;
    private int lastCageOut;
    
    public DogHouse(String name) {
        this.name = name;
        this.cages = new Cage[MAX_CAGES];

        this.dogsCount = 0;
        this.cagesCount = 0;
        this.outDogs = 0;
        lastCageOut = -1;
        
        dogs = new Dog[MAX_CAGES * MAX_IN_CAGE];
    }
    
    // New constructor
    public DogHouse(Scanner scan) throws FileNotFoundException {
    	this.cages = new Cage[MAX_CAGES];
    	
        this.dogsCount = 0;
        this.cagesCount = 0;
        this.outDogs = 0;
        lastCageOut = -1;
        
        dogs = new Dog[MAX_CAGES * MAX_IN_CAGE];
        
    	this.name = scan.nextLine(); 
    	Dog.GLOBAL_ID = Integer.parseInt(scan.nextLine()); 
    	this.cagesCount = Integer.parseInt(scan.nextLine()); 
    	if (cagesCount > MAX_CAGES)
    		this.cagesCount = MAX_CAGES;
    	
    	int cageNum;
    	for (int i = 0; i < cagesCount; i++) {
    		if (scan.hasNextLine()) {
	    		cageNum = Integer.parseInt(scan.nextLine()); 
	    		cages[i] = new Cage(scan);
	    		cages[i].setNumber(cageNum);
	    		addDogToArray(cages[i]);
    		}
    	}
    }
    
    // New function
    public void save(String fileName) throws FileNotFoundException {
    	File f = new File(fileName);
		PrintWriter writer = new PrintWriter(f);
		writer.println(name);
		writer.println(Dog.GLOBAL_ID);
		writer.println(cagesCount);
    	for (int i = 0; i < MAX_CAGES; i++) {
    		if (cages[i] != null)
    			cages[i].save(writer);
    	}
    	System.out.println("Save to file");
    	writer.close();
    }
    
    // New function
    public void addDogToArray(Cage cage) {
    	int dogsExistInCage = cage.getExistDogs();
    	Dog[] d = new Dog[dogsExistInCage];
    	d = cage.getDogs();
    	
    	for (int i = 0; i < dogsExistInCage; i++) {
    		dogs[dogsCount] = d[i];
    		dogs[dogsCount].setCageNumber(cage.getCageNumber());
    		dogsCount++;
    	}
    }
    
    // New fucntion
    private boolean vaildDogType(String type) {
    	for (dogTypes types : dogTypes.values()) 
    		if (StringUtil.equalString(types.name(), type) ) 
    			return true;
    	return false;
    }
    
    public String getName() {
    	return name;
    }
    
    public Cage[] getCages() {
    	return cages;
    }
    
    public int getDogsCount() {
    	return dogsCount;
    }
    
    public int getCagesCount() {
    	return cagesCount;
    }

    public Cage getCageByNumber(int number) {
    	for (int i = 0; i < MAX_CAGES; i++)
    		if (cages[i] != null)
    			if (cages[i].getCageNumber() == number)
    				return cages[i];
    	
    	return null;
    }
    
    public void deleteDog(Dog dog) {
    	for (int i = 0; i < dogs.length; i++)
    		if (dogs[i] != null)
    			if (dogs[i].equals(dog))
    				dogs[i] = null;
    	

    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    private void closeCage(int index) {
    	cages[index] = null;
    	cagesCount--;
    	for (int i = 0; i < MAX_CAGES; i++) {
    		if (cages[i] == null || cages[i].isEmpty()) {
    			if ((i + 1) < MAX_CAGES) {
    				if (cages[i + 1] != null) {
    					cages[i] = cages[i + 1];
    					cages[i + 1] = null;
    				} else return;
    			} 
    		}
    	}
    }
    
    public boolean addDog(Dog dog) {
    	if (!vaildDogType(dog.getType())) // Added
    		return false;
    	
    	for (int i = 0; i < MAX_CAGES; i++) {
    		if (cages[i] != null) {
    			if (!cages[i].isFull()) {
    				if (cages[i].addDogToCage(dog)) {
	    				dogsCount++;
	    				dogs[dogsCount + outDogs] = cages[i].getDogById(dog.getId());
	    				return true;
	    			}
    			}
    		} else {
    			cages[i] = new Cage();
    			cagesCount++;
        		if (addDog(dog))
        			return true;
    		}
    	}
    	
    	return false;
    }
    
    public int outDog(int id, MyDate date) {
    	int days = -1;
    	
    	for (int i = 0; i < MAX_CAGES; i++) {
    		if (cages[i] != null) {
	    		if (cages[i].getDogById(id) != null) {
	    			Dog dog = cages[i].getDogById(id);
	    			days = dog.getDate().daysCount(date);
	    			if (cages[i].removeDog(dog)) {
	    				lastCageOut = dog.getCageNumber();
	    				deleteDog(dog);
						dogsCount--;
						outDogs++;
						if (cages[i].isEmpty())
							closeCage(i);
						break;
					}
	    		}
    		}
    	}
    	
    	return days;
    }
    
    public void makePriceStatment(Dog dog, int days) { 
        double perday = dog.getWeight() * 0.8;
        

        if (perday < 30)
            perday = 30;

        double sum = perday * days;
        
        System.out.print(dog.toString() + " is in cage number " + lastCageOut + " Need to pay : " + sum + " ILS\n");
    }
    
    public String toString() {
    	StringBuffer st = new StringBuffer("In house " + name + " there are " + dogsCount + " dogs\n");

		for (int i = 0; i < dogs.length; i++) 
			if (dogs[i] != null)
				st.append(dogs[i].toString() + " is in cage number " + dogs[i].getCageNumber() + "\n");
			
		st.append("\nThere are " + cagesCount + " cages\n");
		
		for (int i = 0; i <= cagesCount; i++) 
			if (cages[i] != null) 
				st.append(cages[i].toString() + "\n"); 
		return st.toString();
    }
}
