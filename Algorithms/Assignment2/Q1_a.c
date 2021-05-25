#include "stdio.h"
#include "string.h"

#define LEFT_ARROW "\u2190"
#define UP_ARROW "\u2191"
#define UP_LEFT_ARROW "\u2196"

void LCS_LENGTH(const char* x, int sizeX, const char* y, int sizeY) {
    int c[sizeX + 1][sizeY + 1];
    char* b[sizeX+1][sizeY+1];

    for(int i = 0; i <= sizeX; i++) {
        c[i][0] = 0;
    }

    for(int j = 0; j <= sizeY; j++) {
        c[0][j] = 0;
    }

    for(int i = 1; i <= sizeX; i++) {
        for(int j = 1; j <= sizeY; j++) {
            if (x[i-1] == y[j-1]) {
                c[i][j] = c[i-1][j-1] + 1;
                b[i][j] = strdup(UP_LEFT_ARROW);
            } else if (c[i-1][j] >= c[i][j-1]) {
                c[i][j] = c[i-1][j];
                b[i][j] = strdup(UP_ARROW);
            } else {
                c[i][j] = c[i][j-1];
                b[i][j] = strdup(LEFT_ARROW);
            }
        }
    }

    for(int i = 0; i <= sizeY; i++) {
        b[0][i] = "0";
    }
    for(int j = 0; j <= sizeX; j++) {
        b[j][0] = "0";
    }
    printf("The table:\n");
    for(int i = 0; i <= sizeX; i++) {
        for(int j = 0; j <= sizeY; j++) {
            printf("%d ", c[i][j]);
        }
        printf("\n");
    }

    printf("The Arrows\n");
    for(int i = 0; i <= sizeX; i++) {
        for(int j = 0; j <= sizeY; j++) {
            printf("%s ", b[i][j]);
        }
        printf("\n");
    }
}

int main() {
    char* X = "1100101";
    int sizeX = strlen(X);
    char* Y = "101101110";
    int sizeY = strlen(Y);
    LCS_LENGTH(X, sizeX, Y, sizeY);
    return 1;
}
