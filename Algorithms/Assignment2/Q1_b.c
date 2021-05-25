#include <stdbool.h>
#include <string.h>
#include <stdio.h>

bool checkCS(char* x, char* y, char* z) {
    int prevI;
    int lenX = (int)strlen(x);
    int lenZ = (int)strlen(z);
    int lenY = (int)strlen(y);
    int progressX = 0;
    int progressY = 0;

    for(int i = 0; i < lenZ; i++) {
        prevI = i;
        for(int j = progressX; j < lenX; j++) {
            if (x[j] == z[i]) {
                i++;
                progressX++;
            }
        }
        i = prevI;
        for(int j = progressY; j < lenY; j++) {
            if (y[j] == z[i]) {
                i++;
                progressY++;
            }
        }
    }
    if(progressY == progressX && progressX == lenZ){
        return true;
    }
    return false;
}

int main() {
    char* X = "abaaa";
    char* Y = "baabaca";
//    char* Z = "aaaa";
    char* Z = "abaa";
//    char* Z = "baaca";
    if(checkCS(X,Y,Z)){
        printf("found");
    }else
        printf("not found");
    return 0;
}
