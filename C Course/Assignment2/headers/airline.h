//
// Created by Barak
//

#ifndef HW02_AIRLINE_H
#define HW02_AIRLINE_H

#include "flight.h"
#include "airport.h"

typedef struct {
    char* name;
    Flight** all_flights;
    int num_of_flights;
} Airline;

int     initAirline(Airline* pAirline);
void    printAirline(const AirportManager* pAirportManager, const Airline* pAirline);
void    freeAirline(Airline* pAirline);

int     addFlight(const AirportManager* pAirportManager, Airline* pAirline);
void    printAllFlightsInLine(const AirportManager* pAirportManager, const Airline* pAirline);

#endif //HW02_AIRLINE_H