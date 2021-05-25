#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <time.h>
#include "pictureManipulation.h"

void generatePicture(int *picture, u_int size)
{
    srand(time(NULL));

    for (u_int i = 0; i < size; i++)
        for (u_int j = 0; j < size; j++)
            *(picture + (i * size) + j) = (rand() % 100); 
}

void printPicture(int *picture, u_int size)
{
    for (u_int i = 0; i < size; i++)
    {
        for (u_int j = 0; j < size; j++)
            printf("\t%d", *(picture + (i * size) + j));
        printf("\n");
    }
}

int generateMenu()
{
    int choose;

    printf("1 - 90 degree clockwise\n");
    printf("2 - 90 degree counter clockwise\n");
    printf("3 - Flip Horizontal\n");
    printf("4 - Flip Vertical\n");
    printf("-1 - Quit\n");
    scanf("%d", &choose);

    return choose;
}

void swap(int *num1, int *num2)
{
    int temp = *num1;
    *num1 = *num2;
    *num2 = temp; 
}

void clockwiseRotation(int *picture, u_int size) 
{
    for (u_int level = 0; level < size / 2; level++)
    {
        int max = size - 1 - level;
        for (u_int i = level; i < max; i++)
        {
            swap(picture + (level * size) + i, picture + (i * size + max));
            swap(picture + (level * size) + i, picture + (max * size + (max - i + level)));
            swap(picture + (level * size) + i, picture + ((max - i + level) * (size) + level));
        }
    }
}

void counterClockwiseRotation(int *picture, u_int size)
{
    for (u_int level = 0; level < size / 2; level++)
    {
        int max = size - 1 - level;
        for (u_int i = level; i < max; i++)
        {
            swap(picture + ((max - i + level) * (size) + level), picture + (max * size + (max - i + level)));
            swap(picture + ((max - i + level) * (size) + level), picture + (i * size + max));
            swap(picture + ((max - i + level) * (size) + level), picture + (level * size) + i); 
        }
    }
}

void flipHorizontal(int *picture, u_int size)
{
    for (u_int row = 0; row < size; row++)
        for (u_int index = 0; index < size / 2; index++)
            swap(picture + (size * row) + index, picture + (size *row) + (size - 1 - index));
}

void flipVerical(int *picture, u_int size)
{
    for (u_int column = 0; column < size; column++)
        for (u_int row = 0; row < size/2; row++)
            swap(picture + (size * row) + column, picture + size * (size - 1 - row) +column);
}