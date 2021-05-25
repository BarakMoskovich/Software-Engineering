//
// Created by Barak
//

#ifndef HW02_FUNCTIONS_H
#define HW02_FUNCTIONS_H

#include "def.h"

char*   createDynamicStr(const char* msg);
char*   capitalString(char* str);
int     countWords(char *str);
int     isUppercase(char* str);
void    getCode(char str[IATA_CODE_SIZE + 1], const char* msg);
char*   doubleSpace(char* str);
void    fixSpaces(char* str);
void    crazyString(char* str);
void    substr(char* str, char* sub , int start, int len);
void    flush();

#endif //HW02_FUNCTIONS_H