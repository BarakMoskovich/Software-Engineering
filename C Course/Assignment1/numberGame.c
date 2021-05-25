#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include "numberGame.h"

#define SHUFFEL_COUNT 10

void initializeGameBoard(u_int *game, u_int rows, u_int cols)
{
    u_int count = 1;
    int cell;

    for (u_int i = 0; i < rows; i++)
    {
        for (u_int j = 0; j < cols; j++)
        {
            if ((i + 1) * (j + 1) == rows * cols) // last cell
                count = 0;
            
            cell = (rows * i) + j + (i * ( (int)(cols) - (int)(rows)));
            *(game + cell) = count++;
        }
    }
}

void printBoard(u_int *game, u_int rows, u_int cols)
{
    int cell;
    for (u_int i = 0; i < rows; i++)
    {
        for (u_int j = 0; j < cols; j++)
        {
            cell = (rows * i) + j + (i * ( (int)(cols) - (int)(rows)));
            printf("\t%d", *(game + cell));
        }
        printf("\n");
    }
}

int checkBoard(u_int *game, u_int rows, u_int cols)
{
    u_int count = 1;
    u_int toCompare;
    int cell;

    for (u_int i = 0; i < rows; i++)
    {
        for (u_int j = 0; j < cols; j++)
        {
            if (count == (rows * cols))
                toCompare = 0;
            else 
                toCompare = count++;

            cell = (rows * i) + j + (i * ( (int)(cols) - (int)(rows)));
            if ( *(game + cell) != toCompare) return 0;
        }
    }
    return 1;
}

int checkLegalityMove(u_int rows, u_int cols, int position1, int position2)
{
    int diff = position1 - position2;

    if (position1 == -1 || position2 == -1)
        return 0; 

    if (diff != -1 && diff != 1 && diff != -(cols) && diff != cols)
        return 0; 
    return 1;
}

int getPosition(u_int *game, u_int rows, u_int cols, int number)
{
    int cell;

	for (u_int i = 0; i < rows; i++)
    {
		for (u_int j = 0; j < cols; j++)
        {
            cell = (rows * i) + j + (i * ( (int)(cols) - (int)(rows)));
			if ( *(game + cell) == number) return cell;
        }
    }

	return -1;
}

int getNumberByPosition(u_int *game, u_int rows, u_int cols, u_int position)
{
    int maxPosition = (rows * cols); 
    int reqPosition = (position);
    if (reqPosition > maxPosition || reqPosition < 0)
        return -1;
    return *(game + position);
}

void moveAction(u_int *game, u_int rows, u_int cols, u_int position1, u_int position2) 
{
    u_int temp = *(game + position1);
    *(game + position1) = *(game + position2);
    *(game + position2) = temp;     
}

int move(u_int *game, u_int rows, u_int cols, int numberToMove)
{
    int emptyCell = getPosition(game, rows, cols, 0);
    int toMoveCell = getPosition(game, rows, cols, numberToMove);

    if (checkLegalityMove(rows, cols, emptyCell, toMoveCell) == 0) return 0; 
    moveAction(game, rows, cols, emptyCell, toMoveCell);
    return 1;
}

void shuffelBoard(u_int *game, u_int rows, u_int cols)
{
    srand(time(NULL));
    u_int position;
    u_short dir;
    int num;

    // 0 - top, 1 - left, 2 - right, 3 - bottom

    for (u_int shuffles = 0; shuffles < SHUFFEL_COUNT; shuffles++)
    {   
        dir = (rand() % 4); // 0-3 

        position = getPosition(game, rows, cols, 0); // get pos of 0
        if (dir == 0) position = position - cols;
        else if (dir == 1) position = position - 1;
        else if (dir == 2) position = position + 1;
        else if (dir == 3) position = position + cols;

        num = getNumberByPosition(game, rows, cols, position);

        if (num != -1)
            if (move(game, rows, cols, num) == 0)
                shuffles--;
    }
}

void play(u_int *game, u_int rows, u_int cols)
{
    int step; 

    initializeGameBoard(game, rows, cols);
    do 
        shuffelBoard(game, rows, cols);
    while (checkBoard(game, rows, cols) == 1);

    printBoard(game, rows, cols);


    while (checkBoard(game, rows, cols) != 1) 
    {
        printf("\nYour step: ");
        scanf("%d", &step);
        if (move(game, rows, cols, step) == 0)
            printf("Invalid step!\n");
        else
            printBoard(game, rows, cols);
        
    }
    printf("\nYou win! The game is over!\n");
}