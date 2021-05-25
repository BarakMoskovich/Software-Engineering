package firstHomework;

import java.util.Scanner; 

/**
 * Created by Barak Moskovich
 */

public class identityCard {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int id = 0, sum = 0, digit, calu, checkDigit;
		
		// while (id < 10000000 || id > 1000000000) {  
		// max integer 2147483647 so to avoid error we can define 'id' variable as a type 'long' or 'string' and casting/convert to 'int' to work with digits
//		while (String.valueOf(id).length() < 8 || String.valueOf(id).length() > 9) { 
//			System.out.print("Please enter ID number include check digit (8-9 digits) : ");
//			id = scanner.nextInt();
//		}
		
		int counter = 0, temp = 0;
		
		while (counter < 8 || counter > 9) {
			counter = 0;
			
			System.out.print("Please enter ID number include check digit (8-9 digits) : ");
			id = scanner.nextInt();
			temp = id;
			
			while (temp > 0) {
				counter++;
				temp = temp / 10;
			}
		}
		
		checkDigit =  id % 10; // save check digit
		id = id / 10; // remove right digit
		
		
//		for (int i = String.valueOf(id).length(); i > 0 ; i--) {
		for (int i = (counter - 1); i > 0 ; i--) {
			digit = id % 10; // right digit
			calu = (( (i + 1) % 2) + 1) * digit;
			
			if (calu > 9) {
				sum += ((calu % 10) + (calu / 10));
			} else {
				sum += calu; // add to sum
			}
			
			id = id / 10; // remove right digit
		}
		
		if (10 - (sum % 10) == checkDigit) 
			System.out.println("Vaild ID");
		else
			System.out.println("Invalid id");
		
		scanner.close();
	}

}
