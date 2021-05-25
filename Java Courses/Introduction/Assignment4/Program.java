 /**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program {

    public static void sudoku(Scanner s) {
        Sudoku game = new Sudoku(s);
        boolean ok = game.checkSduoku();
        if (!ok)
            System.out.println("Not a valid Sudoku\n");
        else
            System.out.println("A valid Sudoku\n");
    }

    public static void dogPension() {
    	DogHouseTest.testDogPension();
    }
    
    // New function
    public static void dogPensionFileEditon() throws FileNotFoundException {
		File f = new File("src/pensionNSudoku/house.txt");
		Scanner s2 = new Scanner(f);
		
		DogHouse dh = new DogHouse(s2);
		System.out.print(dh.toString());
		
		dh.save("src/pensionNSudoku/house.txt");
//		dh.save("src/pensionNSudoku/test.txt");
    }
    
	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(System.in);
		
		// HW3
//		boolean running = true;
//		while (running) {
//			System.out.println("Enter your choice of exe\n"
//					+ "1  - Sudoku\n"
//					+ "2  - Dog Pension\n"
//					+ "-1 - to exit");
//	        switch(s.nextInt()) {
//	            case 1:
//	            	sudoku(s);
//	                break;
//	            case 2:
////	            	dogPension();
//	            	dogPensionFileEditon();
//	                break;
//	            case -1:
//	            	running = false;
//	            	break;
//	        }
//		}
	
		// ----------- Dog House \w File -----------
//		dogPensionFileEditon();

		// ----------- RecClass -----------
		// Rec 1
//		String a1 = "aaa"; // ture
//		String a2 = "aaAa"; // false
//		String a3 = "aaba"; // false
//		String three = "3333"; // ture
//		System.out.println(RecClass.isTheSame(a1)); 
//		System.out.println(RecClass.isTheSame(a2));
//		System.out.println(RecClass.isTheSame(a3));
//		System.out.println(RecClass.isTheSame(three));
		
		// Rec 2
//		int[] arr = {36, 102, 91, 72, 34, 24};
//		System.out.println(RecClass.numOfMod3(arr, arr.length));
		
		// Rec 2
//		RecClass.powerSet(4)
		
		// ----------- StringUtil -----------
		
		// String 1
//		String str1 = "Hellow World";
//		String str2 = "Good morning Sunshine Moshe";
//		String str3 = "num of student";
//		System.out.print(StringUtil.getFirstLetters(str1));
//		System.out.print(StringUtil.getFirstLetters(str2));
//		System.out.print(StringUtil.getFirstLetters(str3));
		
		// String 2
//		String example = "abc aS  dasd dda sada";  // return true
//		String example2 = "abc aS  das5 dda sada"; // return false
//		System.out.print(StringUtil.isOnlyLetters(example));
//		System.out.print(StringUtil.isOnlyLetters(example2));
	
		s.close();
	}
}
