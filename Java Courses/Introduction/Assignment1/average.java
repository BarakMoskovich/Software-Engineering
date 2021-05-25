package firstHomework;

import java.util.Scanner; 

/**
 * Created by Barak Moskovich
 */

public class average {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int examScore = -1, avgHomeworkScore = -1, amountHomework = -1, finalScore, tempFinalScore;
		
		// Must enter number between 0-100
		while (examScore < 0 || examScore > 100) {
			System.out.print("Please enter a score on the exam (0-100) : ");
			examScore = scanner.nextInt();
		}
		
		// Must enter number between 0-100
		while (avgHomeworkScore < 0 || avgHomeworkScore > 100) {
			System.out.print("Please enter average home work score (0-100) : ");
			avgHomeworkScore = scanner.nextInt();
		}
	
		// Must enter number between 0-8
		while (amountHomework < 0 || amountHomework > 8) {
			System.out.print("Please enter the number of homework you submitted (0-8) : ");
			amountHomework = scanner.nextInt();
		}
		
		if (amountHomework <= 4) {
			// if amount homework <= 4 then final score 0
			finalScore = 0;
		} else if (amountHomework == 5 || amountHomework == 6) {
			// if amount homework 5/6
			if (examScore > 54) {
				// and if exam score > 54 then calculate (examScore * 0.8) + (avgHomeworkScore * 0.2) even if it lower the final score
				finalScore = (int)((examScore * 0.8) + avgHomeworkScore * 0.2);
			} else {
				// or if exam < 54 then final score is exam score
				finalScore = examScore;
			}
		} else {
			// if amount homework 7/8
			if (examScore < 54) {
				// and if exam score < 54 
				if (avgHomeworkScore > 80) {
					// and if avg homework score > 80 then calculate (examScore * 0.75) + (avgHomeworkScore * 0.25) only if it doesn't lower the final score 
					tempFinalScore = (int)((examScore * 0.75) + avgHomeworkScore * 0.25);
					if (tempFinalScore > examScore) {
						finalScore = tempFinalScore;
					} else {
						finalScore = examScore;
					}
				} else {
					// and if avg homework score < 80 then calculate (examScore * 0.75) + (avgHomeworkScore * 0.25) only if it doesn't lower the final score 
					tempFinalScore = (int)((examScore * 0.8) + avgHomeworkScore * 0.2);
					if (tempFinalScore > examScore) {
						finalScore = tempFinalScore;
					} else {
						finalScore = examScore;
					}
				}
			} else {
				// if exam score > 54 then  calculate (examScore * 0.7) + (avgHomeworkScore * 0.3) only if it doesn't lower the final score 
				tempFinalScore = (int)((examScore * 0.70) + avgHomeworkScore * 0.3);
				if (tempFinalScore > examScore) {
					finalScore = tempFinalScore;
				} else {
					finalScore = examScore;
				}
			}
		}

		System.out.println("Final score : " + finalScore);		
		scanner.close();
	}

}
