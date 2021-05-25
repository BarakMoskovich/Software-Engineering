#include "stdio.h"
#include "stdlib.h"

#define MAX(a, b) (((a)>(b)) ? (a) : (b))

typedef struct Tables {
    int **T;
    int **S;
    int *X;
} Q3_Tables;

void freeTables(Q3_Tables* tables, int n) {
    for (int i = 0; i < n + 1; i++) {
        free(tables->T[i]);
    }
    free(tables->T);
    for (int i = 0; i < n + 1; i++) {
        free(tables->S[i]);
    }
    free(tables->S);
}

void initTables(Q3_Tables *tables, int n, int W) {
    tables->T = calloc(n + 1, sizeof(int *));
    if (!tables->T) {
        printf("Error in alloc memory\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < n + 1; i++) {
        tables->T[i] = calloc(W + 1, sizeof(int));
        if (!tables->T[i]) {
            printf("Error in alloc memory\n");
            exit(EXIT_FAILURE);
        }
    }

    tables->S = calloc(n + 1, sizeof(int *));
    if (!tables->S) {
        printf("Error in alloc memory\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < n + 1; i++) {
        tables->S[i] = calloc(W + 1, sizeof(int));
        if (!tables->S[i]) {
            printf("Error in alloc memory\n");
            exit(EXIT_FAILURE);
        }
    }
}

void print2D_Table(int **T, int n, int w, char* tableName) {
    printf("%s table:\n", tableName);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < w; j++) {
            printf(" %5d ", T[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void printX_Table(int *T, int n) {
    printf("X table:\nItem:\t");
    for (int i = 0; i < n; i++)
        printf(" %5d ", i + 1);
    printf("\nCount:\t");
    for (int i = 0; i < n; i++)
        printf(" %5d ", T[i]);;
    printf("\n");
}

void Q3E(int items, int *values, int *weights, int W) {
    Q3_Tables tables;
    initTables(&tables, items, W);

    tables.X = calloc(items, sizeof(int *));
    if (!tables.X) {
        printf("Error in alloc memory\n");
        exit(EXIT_FAILURE);
    }

    for (int i = 1; i <= items; i++) {
        for (int j = 1; j <= W; j++) {
            if (weights[i - 1] <= j) {
                if (j - weights[i - 1] >= 0) {
                    tables.T[i][j] = MAX(tables.T[i - 1][j],tables.T[i][j - weights[i - 1]] + values[i - 1]);
                } else {
                    tables.T[i][j] = MAX(tables.T[i - 1][j],values[i - 1]);
                }


            }
            else tables.T[i][j] = tables.T[i - 1][j];


            if (tables.T[i][j] !=  tables.T[i - 1][j]) {
                tables.X[i - 1]++;
                tables.S[i][j] = 1 + tables.S[i][j - weights[i - 1]];
            }

        }
    }

    print2D_Table(tables.T, items + 1, W + 1, "T");
    print2D_Table(tables.S, items + 1, W + 1, "S");
    printX_Table(tables.X, items);

    printf("\nMax Result: %d\n", tables.T[items][W]);

    while (items != 0) {
        if (tables.T[items][W] != tables.T[items - 1][W]) {
            printf("\tPackage %d with W = %d and value = %d\n", items, weights[items - 1], values[items - 1]);
            W -= weights[items - 1];
        }
        items--;
    }

    freeTables(&tables, items);
}

int main() {
    // replace values and weights with dynamic arrays and then free them
    int values[] = {16, 32, 56, 28, 30};
    int weights[] = {1, 4, 5, 2, 4};
    int W = 11;
    int items = sizeof(values) / sizeof(values[0]);
    Q3E(items, values, weights, W);
    return 1;
}