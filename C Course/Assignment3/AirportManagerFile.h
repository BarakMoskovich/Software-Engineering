#ifndef __AIRPORT__MANAGER__FILE__
#define __AIRPORT__MANAGER__FILE__

#include <stdio.h>
#include <stdlib.h>
#include "AirportManager.h"

void		writeAirportToTextFile(FILE* fp, const Airport* pAirport);
int		addAirportFromTextFile(FILE* fp, AirportManager* pAirportManager);
int		writeAirportManagerToTextFile(const char* fileName, AirportManager* pAirportManager);
int		initAirportManagerFromTextFile(const char* fileName, AirportManager* pAirportManager);
int		addAirportManagerToTextFile(FILE* fp, AirportManager* pAirportManager);
int		addAirportManagerFromTextFile(FILE* fp, AirportManager* pAirportManager);

#endif