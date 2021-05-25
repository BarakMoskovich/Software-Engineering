//
// Created by Barak
//

#include "../headers/flight.h"


int initFlight(const AirportManager* pAirportManager, Flight* pFlight) {
    if (!getValidCode(pAirportManager, pFlight->origin_code, ENTER_ORIGIN_CODE)) {
        printf(AIRPORT_NOT_EXIST);
        return 0;
    }

    if (!getValidCode(pAirportManager, pFlight->dest_code, ENTER_DEST_CODE)) {
        printf(AIRPORT_NOT_EXIST);
        return 0;
    }

    if (!strcmp(pFlight->origin_code, pFlight->dest_code)) {
        printf(IDENTICAL_AIRPORTS);
        return 0;
    }

    do {
        printf(ENTER_TIME);
        scanf("%d", &pFlight->time);
    } while (!(pFlight->time <= 23 && pFlight->time >= 0));

    if (!(initDate(&pFlight->date)))
        return 0;

    return 1;
}

int getValidCode(const AirportManager* pAirportManager, char code[IATA_CODE_SIZE + 1], const char* msg) {
    getCode(code, msg);
    if (findAirportByIATA(pAirportManager, code) == NULL)
        return 0;
    return 1;
}

void printFlight(const AirportManager* pAirportManager, const Flight* pFlight) {
    char* origin_airport_name = findAirportByIATA(pAirportManager, pFlight->origin_code)->name;
    char* dest_airport_name = findAirportByIATA(pAirportManager, pFlight->dest_code)->name;

    printf(PRINT_FLIGHT, origin_airport_name, dest_airport_name,
           pFlight->date.day, pFlight->date.month, pFlight->date.year, pFlight->time);
}

int checkFlightDest(const Flight* pFlight, const char origin_code[IATA_CODE_SIZE + 1], const char dest_code[IATA_CODE_SIZE + 1]) {
    if (!strcmp(pFlight->dest_code,dest_code) && !strcmp(pFlight->origin_code, origin_code))
        return 1;
    return 0;
}

int countSimilarFlights(const Flight** pFlights, const int num_of_flights,
		const char origin_code[IATA_CODE_SIZE + 1], const char dest_code[IATA_CODE_SIZE + 1]) {
    int count = 0;

    for (int i = 0; i < num_of_flights; i++)
        if (checkFlightDest(pFlights[i], origin_code, dest_code))
            count++;

    return count;
}
