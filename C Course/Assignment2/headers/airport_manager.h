//
// Created by Barak
//

#ifndef HW02_AIRPORT_MANAGER_H
#define HW02_AIRPORT_MANAGER_H

#include "airport.h"
#include "def.h"

typedef struct {
    Airport* all_airports;
    int num_of_all_airports;
} AirportManager;

int      initAirportManager(AirportManager* pAirportManager);
void     printAirportManager(const AirportManager* pAirportManager);
void     freeAirportManager(AirportManager* pAirportManager);

int      addAirport(AirportManager* pAirportManager);
Airport* findAirportByIATA(const AirportManager* pAirportManager, const char code[IATA_CODE_SIZE + 1]);

#endif //HW02_AIRPORT_MANAGER_H