 /**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

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
    
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		boolean running = true;
		while (running) {
			System.out.println("Enter your choice of exe\n"
					+ "1  - Sudoku\n"
					+ "2  - Dog Pension\n"
					+ "-1 - to exit");
	        switch(s.nextInt()) {
	            case 1:
	            	sudoku(s);
	                break;
	            case 2:
	            	dogPension();
	                break;
	            case -1:
	            	running = false;
	            	break;
	        }
		}
	}
}
