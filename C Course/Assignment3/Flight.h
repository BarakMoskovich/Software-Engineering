#ifndef __FLIGHT__
#define __FLIGHT__

#include "AirportManager.h"
#include "Date.h"
#include "General.h"

typedef struct
{
	char		originCode[CODE_LENGTH + 1];
	char		destCode[CODE_LENGTH + 1];
	int			hour;
	Date		date;
}Flight;

void	initFlight(Flight* pFlight, AirportManager* pManager);
int		isFlightInRoute(const Flight* pFlight, const char* codeSource, const char* codeDest);
int		countFlightsInRoute(Flight** arr, int size,const char* codeSource, const char* codeDest);
void	genericPrintFlight(const void* f);
void	printFlight(const Flight* pFlight);
int		getFlightHour();
Airport*	setAiportToFlight(AirportManager* pManager, const char* msg);
void	genericfreeFlight(const void* f);
void	freeFlight(Flight* pFlight);
int		compareByHour(const void* a1, const void* a2);
int		compareByDate(const void* a1, const void* a2);
int		compareByOriginCode(const void* a1, const void* a2);
int		compareByDestCode(const void* a1, const void* a2);

#endif
