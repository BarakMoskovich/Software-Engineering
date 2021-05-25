//
// Created by Barak on 02/12/2020.
//

#include "../headers/airport_manager.h"

int initAirportManager(AirportManager* pAirportManager) {
    pAirportManager->num_of_all_airports = 0;
    pAirportManager->all_airports = malloc(0);
    if (!pAirportManager->all_airports) {
        printf(ALLOCATE_ERR);
        return 0;
    }

    return 1;
}

void printAirportManager(const AirportManager* pAirportManager) {
    printf(PRINT_AIRPORT_MANAGER, pAirportManager->num_of_all_airports);
    for (int i = 0; i < pAirportManager->num_of_all_airports; i++)
        printAirport(&pAirportManager->all_airports[i]);
    printf(NEWLINE);
}

void freeAirportManager(AirportManager* pAirportManager) {
    for (int i = 0; i < pAirportManager->num_of_all_airports; i++)
        freeAirport(&pAirportManager->all_airports[i]);

    free(pAirportManager->all_airports);
}

int addAirport(AirportManager* pAirportManager) {
    Airport newAirport;

    if (!initAirport(&newAirport))
        return 0;

    if (findAirportByIATA(pAirportManager, newAirport.code) != NULL) {
        printf(AIRPORT_EXIST);
        return 0;
    }

    pAirportManager->all_airports = (Airport*) realloc(pAirportManager->all_airports,sizeof(Airport) * (++pAirportManager->num_of_all_airports));
    pAirportManager->all_airports[pAirportManager->num_of_all_airports - 1] = newAirport;

    return 1;
}

Airport* findAirportByIATA(const AirportManager* pAirportManager, const char code[IATA_CODE_SIZE + 1]) {
    for (int i = 0; i < pAirportManager->num_of_all_airports; i++) {
        if (checkIATA(&pAirportManager->all_airports[i], code))
            return &pAirportManager->all_airports[i];
    }
    return NULL;
}
