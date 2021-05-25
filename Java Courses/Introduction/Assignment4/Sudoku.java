/**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

import java.util.Scanner;

public class Sudoku {
    private int[][] board;
    private int boardSize;
    private Scanner scanner;

    public Sudoku(Scanner scanner) {
    	this.scanner = scanner;
    	this.boardSize = 0; 
    }

    public int[][] getBoard() {
        return board;
    }
    
    private int getBoardSize() {
        int size;
        do {
            System.out.print("Please enter board size (N*N) : ");
            size = scanner.nextInt();
        } while ((double)(Math.sqrt(size)) != (int)(Math.sqrt(size)) || size <= 1); 
        return size;
    }
    
    private int[][] createBoard() {
        int[][] board = new int[boardSize][boardSize];

        for (int indexRow = 0; indexRow < boardSize; indexRow++) {
            for (int indexCol = 0; indexCol < boardSize; indexCol++) {
            	do {
                System.out.printf("Enter number to (%d, %d) : ", (indexRow + 1), (indexCol + 1));
                board[indexRow][indexCol] = scanner.nextInt();
            	} while (board[indexRow][indexCol] < 1 || board[indexRow][indexCol] > boardSize);
            }
        }

        return board;
    }
    
    private boolean isValidSodukoRow(int indexRow) {
    	
    	boolean[] checker = new boolean[9];
    	for (int col = 0; col < boardSize; col++) {
    		if (board[indexRow][col] < 0 || board[indexRow][col] > boardSize)
    			return false;
    		
    		if (checker[(board[indexRow][col] - 1)]) 
    			return false;
    		
    		checker[(board[indexRow][col] - 1)] = true;
    	}
    	
        return true;
    }

    private boolean isValidSodukoCol(int indexCol) {
    	boolean[] checker = new boolean[9];
    	for (int row = 0; row < boardSize; row++) {
    		if (board[row][indexCol] < 0 || board[row][indexCol] > boardSize)
    			return false;
    		
    		if (checker[(board[row][indexCol] - 1)]) 
    			return false;
    		
    		checker[(board[row][indexCol] - 1)] = true;
    	}
        return true;
    }

    private boolean isValidSodukoQuadrant(int Qr, int Qc) {
    	boolean[] checker = new boolean[9];
        int sqrtSize = (int)(Math.sqrt(boardSize));
        
        if (Qr > sqrtSize || Qr < 0 || Qc > sqrtSize || Qc < 0)
        	return false;

    	for (int row = (Qr * sqrtSize); row < ((Qr + 1) * sqrtSize); row++) {
    		for (int col = (Qc * sqrtSize); col < ((Qc + 1) * sqrtSize); col++) {
        		if (board[row][col] < 0 || board[row][col] > boardSize)
        			return false;
        		
        		if (checker[(board[row][col] - 1)]) 
        			return false;
        		
        		checker[(board[row][col] - 1)] = true;
    		}
    	}
        return true;
    }

    public boolean checkSduoku() {
    	boardSize = getBoardSize();
    	board = createBoard();
    	
    	int sqrtSize = (int)(Math.sqrt(boardSize));
    	
    	for (int i = 0; i < boardSize; i++) 
    		if (!isValidSodukoCol(i) || !isValidSodukoRow(i))
    			return false;
    	
    	for (int i = 0; i < sqrtSize; i++) 
    		for (int j = 0; j < sqrtSize; j++) 
    				if (!isValidSodukoQuadrant(i, j))
    						return false;
    	
    	System.out.print(toString());
    	
        return true;
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
            	if (j == 0)
            		str += board[i][j];
            	else
            		str += " " + board[i][j];
            }
            str += "\n";
        }
        return str;
    }
}