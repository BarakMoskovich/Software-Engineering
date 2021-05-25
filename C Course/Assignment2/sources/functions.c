//
// Created by Barak
//

#include "../headers/functions.h"

char* createDynamicStr(const char* msg) {
    char* str;
    char temp[MAX_STRING];
    printf("%s",msg);
    fgets(temp, MAX_STRING, stdin);

    str = (char*) malloc((strlen(temp) + 1) * sizeof(char));
    if (str != NULL) {
        strcpy(str, temp);
        str[strlen(str) - 1] = '\0';
    }

    return str;
}

int countWords(char *str) {
    int count = 0;
    char *token = strdup(str);

    while ( (token = strtok(token, " ")) != NULL) {
        count++;
        token = NULL;
    }

    return ++count;
}

char* capitalString(char* str) {
    char *res = (char *) malloc(strlen(str) + 1);
    int capital = 1, i, words = countWords(str), wordsCounter = 1;

    if (!res)
        return str;

    for (i = 0; str[i] != '\0'; i++) {
        if (capital) {
            res[i] = toupper(str[i]);
            capital = 0;
        } else if (isspace(str[i])) {
            if (words > (wordsCounter + 1))
                capital = 1;
            wordsCounter++;
            res[i] = str[i];
        } else
            res[i] = str[i];
    }
    res[i] = '\0';

    free(str);
    return res;
}

char* doubleSpace(char* str) {
    int words = countWords(str), counter = 1, len = strlen(str) + (words * 2) + 1;
    if (words == 1)
        return str;

    char *token = strdup(str);
    char* res = (char *) calloc(len, sizeof(char));

    if (!res)
        return str;

    while ( (token = strtok(token, " ")) != NULL) {
        strncat(res, token, strlen(token));
        if (counter < (words - 1))
            strncat(res, "  ", strlen(token) + 2);
        counter++;
        token = NULL;
    }

    free(str);
    return res;
}

int isUppercase(char* str) {
    for (int i = 0; i < strlen(str); i++)
        if (!isupper(str[i]))
            return 0;
    return 1;
}

void getCode(char str[IATA_CODE_SIZE + 1], const char* msg) {
    char temp[MAX_STRING] = "";

    while (strlen(temp) != IATA_CODE_SIZE || isUppercase(temp) != 1) {
        printf("%s",msg);
        fgets(temp, MAX_STRING, stdin);

        temp[strcspn(temp, "\n")] = '\0';
    }

    strcpy(str, temp);
}

void fixSpaces(char* str) {
    int prefix = 0, suffix = 0, i = 0;

    while (str[i] == ' ') {
        prefix++;
        i++;
    }

    i = strlen(str);

    while (str[i] == ' ') {
        suffix++;
        i--;
    }

    substr(str, str, prefix, strlen(str) - prefix - suffix);
}

void crazyString(char* str) {
    if (strlen(str) % 2 == 0) {
        for (int i = 0; i < strlen(str); i++){
            if (i % 2 == 0)
                str[i]=toupper(str[i]);
            else
                str[i]=tolower(str[i]);
        }
    }
}

void substr(char* str, char* sub , int start, int len) {
    memcpy(sub, &str[start], len);
    sub[len] = '\0';
}

void flush() {
    int c;
    while ( (c = getchar()) != '\n' && c != EOF );
}
