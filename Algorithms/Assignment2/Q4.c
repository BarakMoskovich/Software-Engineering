
#include <stdbool.h>
#include "stdio.h"
#include "stdlib.h"

typedef struct Tables {
    int **T;
    int **X;
} Q4_Tables;

void freeTablesQ4(Q4_Tables *tables, int n) {
    // frees all dynamically allocated tables
    for (int i = 0; i < n + 1; i++) {
        free(tables->T[i]);
    }
    free(tables->T);
    for (int i = 0; i < n + 1; i++) {
        free(tables->X[i]);
    }
    free(tables->X);
}

void initTablesQ4(Q4_Tables *tables, int n, int W) {
    // allocating all tables
    tables->T = calloc(n + 1, sizeof(int *));
    if (!tables->T) {
        printf("Error in alloc memory\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < n + 1; i++) {
        tables->T[i] = calloc(W, sizeof(int));
        if (!tables->T[i]) {
            printf("Error in alloc memory\n");
            exit(EXIT_FAILURE);
        }
    }

    tables->X = calloc(n + 1, sizeof(int *));
    if (!tables->X) {
        printf("Error in alloc memory\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < n + 1; i++) {
        tables->X[i] = calloc(W, sizeof(int));
        if (!tables->X[i]) {
            printf("Error in alloc memory\n");
            exit(EXIT_FAILURE);
        }
    }
}

void printX_TableQ4(int **X, int n, int w) {
    // printing S table
    printf("X table:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < w; j++) {
            printf(" %5d ", X[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void printT_TableQ4(int **T, int n, int w) {
    // printing T table
    printf("T table:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < w; j++) {
            printf(" %5d ", T[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void Q4(int values[4][6], int K, int N, char typesNames[4][10]) {
    Q4_Tables tables;
    initTablesQ4(&tables, K, N);
    int x, y, z;
    bool zeroBuildingTypes, jBuildingTypes, singleBuildingType;

    for (int i = 1; i < K + 1; i++) {
        for (int j = 1; j < N; j++) {
            y = 0;
            z = 0;
            x = values[i - 1][j];
            if (i > 1) {
                if (j > 1) {
                    y = values[i - 1][tables.X[i][j - 1]] + tables.T[i - 1][j - tables.X[i][j - 1]];
                }
                z = tables.T[i - 1][j];
            }
            zeroBuildingTypes = z > x && z >= y;
            jBuildingTypes = y > x && y > z;
            singleBuildingType = x >= y && x >= z;
            if (zeroBuildingTypes) {
                tables.T[i][j] = z;
                tables.X[i][j] = 0;
            }
            if (jBuildingTypes) {
                tables.T[i][j] = y;
                tables.X[i][j] = tables.X[i][j - 1];
            }
            if (singleBuildingType) {
                tables.T[i][j] = x;
                tables.X[i][j] = j;
            }
        }
    }
    printT_TableQ4(tables.T, K + 1, N);
    printX_TableQ4(tables.X, K + 1, N);

    int i = K;
    int m = i - 1;
    int j = N-1;
    int amount;
    while(i > 0 && j > 0) {
        amount = tables.X[i][j];
        printf("%d %s\n", amount, typesNames[m]);
        i -= amount;
        j -= amount;
        m = m - amount + 1;
    }

    printf("\nMax Result: %d\n", tables.T[K][N - 1]);
    freeTablesQ4(&tables, K);
}

int main() {
// Q4
    int values[4][6] = {
            {0, 2, 4,  6,  8,  10},
            {0, 6, 9,  9,  10, 11},
            {0, 1, 1,  2,  3,  15},
            {0, 8, 12, 20, 16, 12}
    };
    char typesNames[4][10] = {
            "Megurim",
            "Mishar",
            "Misradim",
            "Malon"
    };
    int K = 4; // max building types
    int N = 6; // maximum buildings
    Q4(values, K, N, typesNames);
    return 0;
}
