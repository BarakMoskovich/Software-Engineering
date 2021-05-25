//
// Created by Barak
//

#ifndef HW02_AIRPORT_H
#define HW02_AIRPORT_H

#include "functions.h"
#include "def.h"

typedef struct {
    char* name;
    char* country;
    char  code[IATA_CODE_SIZE + 1];
} Airport;

int     initAirport(Airport* pAirport);
void    printAirport(const Airport* pAirport);
void    freeAirport(Airport* pAirport);

int     compare(const Airport* pAirport1, const Airport* pAirport2);
int     checkIATA(const Airport* pAirport, const char code[IATA_CODE_SIZE + 1]);
char*   getAirportName(const char* msg);

#endif //HW02_AIRPORT_H
