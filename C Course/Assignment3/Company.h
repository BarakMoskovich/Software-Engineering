#ifndef __COMP__
#define __COMP__

#include "Flight.h"
#include "AirportManager.h"

typedef enum { eSortByHour, eSortByDate, eSortByDestCode, eSortByOriginCode, eNotSorted } eSorted;	

typedef struct
{
	char*		name;
	int			flightCount;
	LIST		dates;
	Flight**	flightArr;
	eSorted		sorted;
}Company;

void	initCompany(Company* pComp);
int		addFlight(Company* pComp, AirportManager* pManager);
void	printCompany(const Company* pComp);
void	printFlightsCount(const Company* pComp);
void	freeCompany(Company* pComp);
void	sortFlights(Company* pComp, eSorted sortedBy, int(*compare)(const void*, const void*));
int		showDates(const void* date);
void	printSearchResults(const Flight** results);
Flight** searchFlightByHour(const Company* pComp);
Flight** searchFlightByDate(const Company* pComp);
Flight** searchFlightByOriginCode(const Company* pComp);
Flight** searchFlightByDestCode(const Company* pComp);
void	doSort(Company* pComp, eSorted sortedBy);
void	printSearchFlight(Company* pComp);

#endif

