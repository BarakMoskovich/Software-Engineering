#ifndef PICTURE_MANIPULATION
#define PICTURE_MANIPULATION

void generatePicture(int *picture, u_int size);
void printPicture(int *picture, u_int size);
int generateMenu();
void swap(int *num1, int *num2);
void clockwiseRotation(int *picture, u_int size);
void counterClockwiseRotation(int *picture, u_int size);
void flipHorizontal(int *picture, u_int size);
void flipVerical(int *picture, u_int size);

#endif