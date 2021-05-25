//
// Created by Barak
//

#ifndef HW02_FLIGHT_H
#define HW02_FLIGHT_H

#include "date.h"
#include "airport.h"
#include "airport_manager.h"
#include "functions.h"

typedef struct {
    char origin_code[IATA_CODE_SIZE + 1];
    char dest_code[IATA_CODE_SIZE + 1];
    int  time; // Hour : 0-23
    Date date;
} Flight;

int     initFlight(const AirportManager* pAirportManager, Flight* pFlight);
void    printFlight(const AirportManager* pAirportManager, const Flight* pFlight);

int     checkFlightDest(const Flight* pFlight, const char origin_code[IATA_CODE_SIZE + 1], const char dest_code[IATA_CODE_SIZE + 1]);
int     countSimilarFlights(const Flight** pFlights, const int num_of_flights, const char origin_code[IATA_CODE_SIZE + 1], const char dest_code[IATA_CODE_SIZE + 1]);
int     getValidCode(const AirportManager* pAirportManager, char code[IATA_CODE_SIZE + 1], const char* msg);

#endif //HW02_FLIGHT_H
