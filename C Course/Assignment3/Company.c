#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Company.h"
#include "Airport.h"
#include "General.h"


const char* const getSortedValue[] =
{
	[eSortByHour] = "Sorted By Hour",
	[eSortByDate] = "Sorted By Date",
	[eSortByOriginCode] = "Sorted By Origin Code",
	[eSortByDestCode] = "Sorted By Dest Code",
	[eNotSorted] = "Not Sorted"
};


void initCompany(Company* pComp)
{
	printf("-----------  Init Airline Company\n");
	pComp->name = getStrExactName("Enter company name");
	pComp->flightArr = NULL;
	pComp->flightCount = 0;
	pComp->sorted = eNotSorted;
	L_init(&pComp->dates);
}


int	addFlight(Company* pComp, AirportManager* pManager)
{
	if (pManager->count < 2)
	{
		printf("There are not enoght airport to set a flight\n");
		return 0;
	}
	pComp->flightArr = (Flight**)realloc(pComp->flightArr, (pComp->flightCount+1) * sizeof(Flight*));
	if (!pComp->flightArr)
		return 0;

	pComp->flightArr[pComp->flightCount] = (Flight*)calloc(1,sizeof(Flight));
	if (!pComp->flightArr[pComp->flightCount])
		return 0;

	initFlight(pComp->flightArr[pComp->flightCount],pManager);

	LIST* pList = &(pComp->dates);
	NODE* pNode = &(pComp->dates.head);
	Date* pDate = &(pComp->flightArr[pComp->flightCount])->date;
	if (!existValue(pList, pDate, comapreByDate))
		L_insert(pNode, &(pComp->flightArr[pComp->flightCount]->date));

	pComp->flightCount++;
	return 1;
}

void printCompany(const Company* pComp)
{
	printf("Company %s:\n", pComp->name);
	printf("Has %d flights\n", pComp->flightCount);
	printf("%s\n", getSortedValue[pComp->sorted]);
	generalArrayFunction(pComp->flightArr, pComp->flightCount, sizeof(Flight*), genericPrintFlight);
	printf("\nAll dates with flights:");
	L_print(&(pComp->dates), showDates);
}

void printFlightsCount(const Company* pComp)
{
	char codeOrigin[CODE_LENGTH+1];
	char codeDestination[CODE_LENGTH + 1];
	
	if (pComp->flightCount == 0)
	{
		printf("No flight to search\n");
		return;
	}

	printf("Origin Airport\n");
	getAirportCode(codeOrigin);
	printf("Destination Airport\n");
	getAirportCode(codeDestination);

	int count = countFlightsInRoute(pComp->flightArr, pComp->flightCount, codeOrigin, codeDestination);
	if (count != 0)
		printf("There are %d flights ", count);
	else
		printf("There are No flights ");

	printf("from %s to %s\n",codeOrigin, codeDestination);
}

int	showDates(const void* date)
{
	const Date* pDate = (const Date*)date;
	printDate(pDate);
	return 1;
}

void freeCompany(Company* pComp)
{
	generalArrayFunction(pComp->flightArr, pComp->flightCount, sizeof(Flight*), genericfreeFlight);
	free(pComp->flightArr);
	free(pComp->name);
}

void sortFlights(Company* pComp, eSorted sortedBy, int(*compare)(const void*, const void*))
{
	qsort(pComp->flightArr, pComp->flightCount, sizeof(Flight*), compare);
	pComp->sorted = sortedBy;
}

void doSort(Company* pComp, eSorted sortedBy)
{
	switch (sortedBy)
	{
	case eSortByDate:
		sortFlights(pComp, sortedBy, compareByDate);
		break;
	case eSortByHour:
		sortFlights(pComp, sortedBy, compareByHour);
		break;
	case eSortByOriginCode:
		sortFlights(pComp, sortedBy, compareByOriginCode);
		break;
	case eSortByDestCode:
		sortFlights(pComp, sortedBy, compareByDestCode);
		break;
	case eNotSorted:
	default:
		return;
	}
}

void printSearchResults(const Flight** results) {
	if (results != NULL)
		printFlight(results[0]);
	else
		printf("Not found!");
}

Flight** searchFlightByHour(const Company* pComp)
{
	Flight* tmp = (Flight*)malloc(sizeof(Flight));
	tmp->hour = getFlightHour();

	Flight** value = (Flight**)bsearch(&tmp, pComp->flightArr, pComp->flightCount, sizeof(Flight*), compareByHour);

	free(tmp);

	return value;
}

Flight** searchFlightByDate(const Company* pComp)
{
	Flight* tmp = (Flight*)malloc(sizeof(Flight));
	getCorrectDate(&tmp->date);

	Flight** value = (Flight**)bsearch(&tmp, pComp->flightArr, pComp->flightCount, sizeof(Flight*), compareByDate);

	free(tmp);

	return value;
}

Flight** searchFlightByOriginCode(const Company* pComp)
{
	Flight* tmp = (Flight*)malloc(sizeof(Flight));
	char* tmpCode = malloc(sizeof(char) * (CODE_LENGTH + 1));
	if (!tmpCode)
		return NULL;

	getAirportCode(tmpCode);
	strcpy(tmp->originCode, tmpCode);

	Flight** value = (Flight**)bsearch(&tmp, pComp->flightArr, pComp->flightCount, sizeof(Flight*), compareByOriginCode);

	free(tmp);
	free(tmpCode);

	return value;
}

Flight** searchFlightByDestCode(const Company* pComp)
{	
	Flight* tmp = (Flight*)malloc(sizeof(Flight));
	char* tmpCode = malloc(sizeof(char) * (CODE_LENGTH + 1));
	if (!tmpCode)
		return NULL;

	getAirportCode(tmpCode);
	strcpy(tmp->destCode, tmpCode);

	Flight** value = (Flight**)bsearch(&tmp, pComp->flightArr, pComp->flightCount, sizeof(Flight*), compareByDestCode);

	free(tmp);
	free(tmpCode);

	return value;
}

void printSearchFlight(Company* pComp)
{
	switch (pComp->sorted) {
	case eNotSorted:
		printf("Please sort before searching");
		return;
	case eSortByDate:
		sortFlights(pComp, eSortByDate, compareByDate);
		printSearchResults(searchFlightByDate(pComp));
		break;
	case eSortByHour:
		sortFlights(pComp, eSortByHour, compareByHour);
		printSearchResults(searchFlightByHour(pComp));
		break;
	case eSortByDestCode:
		sortFlights(pComp, eSortByDestCode, compareByDestCode);
		printSearchResults(searchFlightByDestCode(pComp));
		break;
	case eSortByOriginCode:
		sortFlights(pComp, eSortByOriginCode, compareByOriginCode);
		printSearchResults(searchFlightByOriginCode(pComp));
		break;
	default:
		return;
	}
}
