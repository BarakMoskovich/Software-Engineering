#include <stdio.h>
#include <ctype.h>
#include "exe.h"

#define PICTURE_MANIPULATION 'P'
#define NUMBER_GAME 'N'
#define QUIT 'E'


int main()
{
	char choose;

	printf("Please choose one of the following options\n");
	printf("P/p - picture Manipulation\n");
	printf("N/n - Mumber Game\n");
	printf("E/e - Quit\n");
	scanf("%c", &choose);


	switch (toupper(choose)) {
		case PICTURE_MANIPULATION:
			pictureManipulation();
			break;
		case NUMBER_GAME:
			numberGame();
			break;
		default:
		case QUIT:
			break;
	}

	return 0;
}
