#define _CRT_SECURE_NO_WARNINGS
#define _USE_MATH_DEFINES
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Flight.h"


void	initFlight(Flight* pFlight, AirportManager* pManager)
{
	Airport* pPortOr = setAiportToFlight(pManager,"Enter code of origin airport:");
	strcpy(pFlight->originCode, pPortOr->code);
	int same;
	Airport* pPortDes;
	do {
		pPortDes = setAiportToFlight(pManager,"Enter code of destination airport:");
		same = isSameAirport(pPortOr, pPortDes);
		if (same)
			printf("Same origin and destination airport\n");
	} while (same);
	strcpy(pFlight->destCode, pPortDes->code);
	getCorrectDate(&pFlight->date);
	pFlight->hour = getFlightHour();
}


int	isFlightInRoute(const Flight* pFlight,const char* codeSource, const char* codeDest)
{
	if ((strcmp(pFlight->originCode, codeSource) == 0) &&
		(strcmp(pFlight->destCode, codeDest) == 0))
		return 1;

	return 0;
}


int	countFlightsInRoute(Flight** arr, int size,const char* codeSource, 
		const char* codeDest)
{
	int count = 0;
	for (int i = 0; i < size; i++)
	{
		if (isFlightInRoute(arr[i], codeSource, codeDest))
			count++;
	}
	return count;
}


void genericPrintFlight(const void* f)
{
	const Flight* pFlight = *(Flight**)f;
	printFlight(pFlight);
}

void printFlight(const Flight* pFlight)
{
	printf("Flight From %s To %s\t",pFlight->originCode, pFlight->destCode);
	printDate(&pFlight->date);
	printf("Hour: %d\n",pFlight->hour);
}


int getFlightHour()
{
	int h;
	do {
		printf("Enter flight hour [0-23]:\t");
		scanf("%d", &h);
	} while (h < 0 || h>23);
	return h;
}


Airport* setAiportToFlight(AirportManager* pManager, const char* msg)
{
	char code[MAX_STR_LEN];
	Airport* port;
	do
	{
		printf("%s\t", msg);
		myGets(code, MAX_STR_LEN);
		port = findAirportByCode(pManager, code);
		if (port == NULL)
			printf("No airport in this country - try again\n");
	} while(port == NULL);

	return port;

}

void genericfreeFlight(const void* f)
{
	Flight* pFlight = *(Flight**)f;
	freeFlight(pFlight);
}

void freeFlight(Flight* pFlight)
{
	free(pFlight);
}

int compareByHour(const void* a1, const void* a2) {
	const Flight* f1 = *(Flight**)a1;
	const Flight* f2 = *(Flight**)a2;

	return f1->hour - f2->hour;
}

int compareByDate(const void* a1, const void* a2) {
	const Flight* f1 = *(Flight**)a1;
	const Flight* f2 = *(Flight**)a2;

	return comapreByDate(&(f1->date), &(f2->date));
}

int compareByOriginCode(const void* a1, const void* a2) {
	const Flight* f1 = *(Flight**)a1;
	const Flight* f2 = *(Flight**)a2;

	return compareByCode(f1->originCode, f2->originCode);
}

int compareByDestCode(const void* a1, const void* a2) {
	const Flight* f1 = *(Flight**)a1;
	const Flight* f2 = *(Flight**)a2;

	return compareByCode(f1->destCode, f2->destCode);
}