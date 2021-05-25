//
// Created by Barak
//

#ifndef HW02_DEF_H
#define HW02_DEF_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

/** Define size **/
#define MAX_STRING 255
#define IATA_CODE_SIZE 3

/** Define messages **/
// General
#define ALLOCATE_ERR "Error : Unable to allocate memory error\n"
#define ENTER_ORIGIN_CODE "Enter origin airport IATA code:"
#define ENTER_DEST_CODE "Enter destination airport IATA code:"
#define NEWLINE "\n"
// main.c
#define ENTER_NUMBER_OF_AIRPORTS "Enter a number for the number of airports you would like to enter:\n"
#define EXIT "Good bye"

// aiport.c
#define PRINT_AIRPORT "\n - Airport %s is located in %s, IATA : %s"
#define ENTER_IATA_CODE "Enter IATA code:"
#define ENTER_COUNTRY "Enter country:"
#define ENTER_AIRPORT_NAME "Enter airport name:"

// airport_manager.c
#define PRINT_AIRPORT_MANAGER "Existing %d Airports:"
#define AIRPORT_EXIST "Airport exist\n"

// flight.c
#define IDENTICAL_AIRPORTS "Identical airports\n"
#define ENTER_TIME "Enter the departure time (0-23):"
#define AIRPORT_NOT_EXIST "Airport code not exist\n"
#define PRINT_FLIGHT " - Flight from airport %s to airport %s, in %02d/%02d/%04d at %d\n"

// airline.c
#define ENTER_AIRLINE_NAME "Enter airline name:"
#define PRINT_AIRLINE "Has %d flights to %s:\n\n"
#define FLIGHT_NOT_EXIST "Flights not exist\n"
#define ALL_FLIGHTS_BY_CODES "has %d flights from %s to %s\n"

// date.c
#define ENTER_DATE "Enter date (DD/MM/YYYY format):"
#define DATE_FORMAT "%02d/%02d/%04d"

#endif //HW02_DEF_H
