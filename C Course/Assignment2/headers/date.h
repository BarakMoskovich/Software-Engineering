//
// Created by Barak
//

#ifndef HW02_DATE_H
#define HW02_DATE_H

#include "def.h"
#include "functions.h"

typedef struct {
    int day;
    int month;  // 1-12
    int year;
} Date;

int     initDate(Date* pDate);
void    printDate(const Date* pDate);
int     validDate(const Date* pDate);

#endif //HW02_DATE_H