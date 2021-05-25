//
// Created by Barak
//

#include "../headers/date.h"

int initDate(Date* pDate) {
    printf(ENTER_DATE);
    scanf("%d/%d/%d", &pDate->day, &pDate->month, &pDate->year);

    while (!validDate(pDate)) {
        printf(ENTER_DATE);
        scanf("%d/%d/%d", &pDate->day, &pDate->month, &pDate->year);
    }

    return 1;
}

void printDate(const Date* pDate) {
    printf(DATE_FORMAT, pDate->day, pDate->month, pDate->year);
}

int validDate(const Date* pDate) {
    if (pDate->year < 1900 || pDate->year > 9999)
        return 0;

    if (pDate->month >= 1 && pDate->month <= 12) {
        if (pDate->month == 2) {
            if (pDate->day > 28) return 0;
        } else if (pDate->month == 4 || pDate->month == 6 || pDate->month == 9 || pDate->month == 11) {
            if (pDate->day > 30) return 0;
        } else if (pDate->day > 31) return 0;
    }

    return 1;
}