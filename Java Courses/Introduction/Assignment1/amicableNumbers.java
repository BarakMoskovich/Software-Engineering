package firstHomework;

/**
 * Created by Barak Moskovich
 */

public class amicableNumbers {
	public static void main(String[] args) {
		int n = 1, sumOne = 0, sumTwo = 0, couples = 1, lastOne = 0;

		while (couples <= 10) {
			// run to the biggest divider - (n / 2), if divided without residue add to sumOne += i
			for (int i = 1; i < ((n / 2) + 1); i++)
				if ((n % i) == 0)
					sumOne += i;
			
			for (int j = 1; j < ((sumOne / 2) + 1); j++)
				if ((sumOne % j) == 0)
					sumTwo += j;
			
			if (sumTwo == n && sumOne != sumTwo && lastOne != sumTwo) {
				System.out.println(couples + ") " + sumTwo + " and " + sumOne + " are mates");
				lastOne = sumOne;
				couples++;
			}

			sumOne = 0;
			sumTwo = 0;
			n++;
		}
	}
}
