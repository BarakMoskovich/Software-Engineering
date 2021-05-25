#ifndef NUMBER_GAME
#define NUMBER_GAME

void initializeGameBoard(u_int *game, u_int rows, u_int cols);
void printBoard(u_int *game, u_int rows, u_int cols);
int checkBoard(u_int *game, u_int rows, u_int cols);
int checkLegalityMove(u_int rows, u_int cols, int position1, int position2);
int get_position(u_int *game, u_int rows, u_int cols, int number);
int getNumberByPosition(u_int *game, u_int rows, u_int cols, u_int position);
void moveAction(u_int *game, u_int rows, u_int cols, u_int position1, u_int position2);
int move(u_int *game, u_int rows, u_int cols, int numberToMove);
void shuffelBoard(u_int *game, u_int rows, u_int cols);
void play(u_int *game, u_int rows, u_int cols);

#endif