package firstHomework;

import java.util.Scanner; 

/**
 * Created by Barak Moskovich
 */

public class hourglass {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Please enter number : ");
		int base = scanner.nextInt();

		for (int line = 0; line < base; line++) {
			for (int space = 0; space < line; space++)
				System.out.print(" ");
			
			for (int star = line; star < base; star++) {
				System.out.print("* ");
			}
			System.out.println(" ");
		}

		for (int line = base; line > 0; line--) {
			for (int space = line - 1; space > 0; space--)
				System.out.print(" ");
			
			for (int star = line; star <= base; star++) {
				System.out.print("* ");
			}

			System.out.println();
		}
		
		
		scanner.close();
	}

}
