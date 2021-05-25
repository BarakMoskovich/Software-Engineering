#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include "pictureManipulation.h"
#include "numberGame.h"
#include "exe.h"

#define SIZE 3
#define ROWS 3
#define COLS 4

void pictureManipulation()
{
    int picture[SIZE][SIZE];
    // int picture[SIZE][SIZE] = { {77, 1, 59} , {24, 91, 19} , {55, 83, 32} };

    generatePicture(*picture, SIZE);
    printPicture(*picture, SIZE);

    int choose;   

    while (choose != -1) {
        choose = generateMenu();

        switch (choose) {
            case 1:
                clockwiseRotation(*picture, SIZE);
                break;
            case 2:
                counterClockwiseRotation(*picture, SIZE);
                break;
            case 3:
                flipHorizontal(*picture, SIZE);
                break;
            case 4:
                flipVerical(*picture, SIZE);
                break;
            default:
                break;
        }

        printf("------- Picture after manipulation -------\n");
        printPicture(*picture, SIZE);
    }
}

void numberGame()
{
    // int game[ROWS][COLS] = { {1, 2, 3, 4} , {5, 6, 7, 8} , {9, 10, 11, 12} , {13, 14, 15, 0} }; // 4x4
    // int game[ROWS][COLS] = { {1, 2, 3, 4} , {5, 6, 7, 8} , {9, 10, 0, 11} }; // 4x3
    // int game[ROWS][COLS] = { {1, 2, 3} , {4, 5, 6} , {7, 8, 9} , {10, 0, 11} }; // 3x4
    
    int rows = ROWS, cols = COLS;

    if (ROWS == 1 && COLS == 1)
    {
        rows = 3;
        cols = 3;
    }

    u_int game[rows][cols];
    play(*game, rows, cols);
}
