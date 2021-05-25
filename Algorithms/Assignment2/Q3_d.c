#include "stdio.h"
#include "stdlib.h"

#define MAX(a, b) (((a)>(b)) ? (a) : (b))

typedef struct Tables {
    int **T;
    char **S;
    int **X;
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

    tables->S = calloc(n + 1, sizeof(char *));
    if (!tables->S) {
        printf("Error in alloc memory\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < n + 1; i++) {
        tables->S[i] = calloc(W + 1, sizeof(char));
        if (!tables->S[i]) {
            printf("Error in alloc memory\n");
            exit(EXIT_FAILURE);
        }
    }
}

void printS_Table(char **S, int n, int w) {
    printf("S table:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < w; j++) {
            if (S[i][j] == 0)
                printf(" %5d ", S[i][j]);
            else
                printf(" %5c ", S[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void printT_Table(int **T, int n, int w) {
    printf("T table:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < w; j++) {
            printf(" %5d ", T[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void Q3D(int items, int *values, int *weights, int W) {
    Q3_Tables* tables = malloc(sizeof(Q3_Tables));
    initTables(tables, items, W);

    for (int i = 1; i < items + 1; i++) {
        for (int j = 1; j < W + 1; j++) {
            if (weights[i - 1] > j) {
                tables->T[i][j] = tables->T[i - 1][j];
                tables->S[i][j] = 'n';
            } else {
                tables->T[i][j] = MAX(tables->T[i - 1][j - weights[i - 1]] + values[i - 1], tables->T[i - 1][j]);
                if (tables->T[i][j] == tables->T[i - 1][j - weights[i - 1]] + values[i - 1])
                    tables->S[i][j] = 'y';
                else
                    tables->S[i][j] = 'n';
            }
        }
    }
    printT_Table(tables->T, items + 1, W + 1);
    printS_Table(tables->S, items + 1, W + 1);

    printf("Max Result: %d\n", tables->T[items][W]);

    freeTables(tables, items);
    free(tables);
}

int main() {
    // replace values and weights with dynamic arrays and then free them
    int values[] = {16, 32, 56, 28, 30};
    int weights[] = {1, 4, 5, 2, 4};
    int W = 11;
    int items = sizeof(values) / sizeof(values[0]);
    Q3D(items, values, weights, W);
    return 1;
}
