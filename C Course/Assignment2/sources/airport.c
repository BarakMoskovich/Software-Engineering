//
// Created by Barak
//

#include "../headers/airport.h"

int initAirport(Airport* pAirport) {
    pAirport->name = getAirportName(ENTER_AIRPORT_NAME);
    if (!pAirport->name) {
        printf(ALLOCATE_ERR);
        return 0;
    }

    pAirport->country = createDynamicStr(ENTER_COUNTRY);
    if (!pAirport->country) {
        printf(ALLOCATE_ERR);
        return 0;
    }

    getCode(pAirport->code, ENTER_IATA_CODE);

    return 1;
}

void printAirport(const Airport* pAirport) {
    printf(PRINT_AIRPORT, pAirport->name,
           pAirport->country, pAirport->code);
}

void freeAirport(Airport* pAirport) {
    free(pAirport->name);
    free(pAirport->country);
}

int compare(const Airport* pAirport1, const Airport* pAirport2) {
    if (!strcmp(pAirport1->code ,pAirport2->code))
        return 1;
    return 0;
}

int checkIATA(const Airport* pAirport, const char code[IATA_CODE_SIZE + 1]) {
    if (!strcmp(pAirport->code ,code))
        return 1;
    return 0;
}

char* getAirportName(const char* msg) {
    char* name = createDynamicStr(msg);
    fixSpaces(name);
    name = capitalString(name);
    crazyString(name);
    name = doubleSpace(name);
    return name;
}
