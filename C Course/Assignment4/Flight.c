#define _CRT_SECURE_NO_WARNINGS
#define _USE_MATH_DEFINES
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Flight.h"
#include "General.h"
#include "fileHelper.h"


int		isFlightInRoute(const Flight* pFlight,const char* codeSource, const char* codeDest)
{
	if ((strcmp(pFlight->originCode, codeSource) == 0) &&
		(strcmp(pFlight->destCode, codeDest) == 0))
		return 1;

	return 0;
}

int		countFlightsInRoute(Flight** arr, int size,const char* codeSource, 
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

void	printFlight(const Flight* pFlight)
{
	printf("Flight From %s To %s\t",pFlight->originCode, pFlight->destCode);
	printDate(&pFlight->date);
	printf("Hour: %d\n",pFlight->hour);
}

void	printFlightV(const void* val)
{
	const Flight* pFlight = *(const Flight**)val;
	printFlight(pFlight);
}

void	initFlight(Flight* pFlight, const AirportManager* pManager)
{
	Airport* pPortOr = setAiportToFlight(pManager, "Enter code of origin airport:");
	strcpy(pFlight->originCode, pPortOr->code);
	int same;
	Airport* pPortDes;
	do {
		pPortDes = setAiportToFlight(pManager, "Enter code of destination airport:");
		same = isSameAirport(pPortOr, pPortDes);
		if (same)
			printf("Same origin and destination airport\n");
	} while (same);
	strcpy(pFlight->destCode, pPortDes->code);
	getCorrectDate(&pFlight->date);
	pFlight->hour = getFlightHour();
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

Airport* setAiportToFlight(const AirportManager* pManager, const char* msg)
{
	char code[MAX_STR_LEN];
	Airport* port;
	do
	{
		printf("%s\t", msg);
		myGets(code, MAX_STR_LEN, stdin);
		port = findAirportByCode(pManager, code);
		if (port == NULL)
			printf("No airport in this country - try again\n");
	} while(port == NULL);

	return port;
}

int saveFlightToFile(const Flight* pF, FILE* fp)
{
#ifndef COMPRESSED_BFILE
	if (fwrite(pF, sizeof(Flight), 1, fp) != 1)
	{
		printf("Error write flight\n");
		return 0;
	}
#else
	// write Source IATA
	if (!writeCharsToFile((char*)pF->originCode, CODE_LENGTH, fp, "Error in writing Source IATA\n"))
		return 0;

	// write Dest IATA
	if (!writeCharsToFile((char*)pF->destCode, CODE_LENGTH, fp, "Error in writing Destination IATA\n"))
		return 0;

	//############# write compressed Year, Month, Day and Flight Hour ##############

	// get compressed Date 
	int byteAmount = BYTE_SIZE_OF__COMPRESSED_DATE_HOUR;
	unsigned char* pBuffer = compressFlight(pF, byteAmount);

	CHECK_NULL__MSG_COLSE_FILE(fp, "Unable to allocated memory!\n", pBuffer)

	if (fwrite(pBuffer, sizeof(char), byteAmount, fp) != byteAmount)
	{
		MSG_CLOSE_RETURN_0(fp, "Year, Month, Day and Flight Hour\n")
		//fclose(fp);
		//return 0;
	}
#endif // !COMPRESSED_BFILE
	return 1;
}

unsigned char* compressFlight(const Flight* pF, int size) {
	unsigned char* pBuffer = (unsigned char*)malloc(size * sizeof(unsigned char));
	CHECK_RETURN_NULL(pBuffer);

	int year = pF->date.year;
	int month = pF->date.month;
	int day = pF->date.day;

	pBuffer[0] = (year >> 10);
	pBuffer[1] = (unsigned char)(((year) & (createMask(2, 9))) >> 2);
	pBuffer[2] = (unsigned char)(year & createMask(0, 1)) | (month << 2) | (day >> 3);
	pBuffer[3] = (day << 2) | pF->hour;

	return pBuffer;
}

void deCompressFlight(Flight* pF, unsigned char* pBuffer) {
	//pF->date.year = (pBuffer[0] << 10) | (pBuffer[1]>>8) | ((pBuffer[2] & createMask(6, 8)) >> 6);

	pF->date.year = (pBuffer[0] << 10) | (pBuffer[1] << 2) | (unsigned char)(pBuffer[2] & createMask(7,8));
	pF->date.month = (unsigned char)(pBuffer[2] & createMask(5, 8));
	pF->date.day = (pBuffer[2] << 6) | (unsigned char)(pBuffer[3] & createMask(0, 1));
	pF->hour = (unsigned char)(pBuffer[3] & createMask(0, 4));
}

// create mask with 1's between left-right (including)
unsigned int createMask(int left, int right)
{
	int temp;
	if (left < right)
	{
		temp = left;
		left = right;
		right = temp;
	}
	return (1 << (left + 1)) - (1 << right);
}

int loadFlightFromFile(Flight* pF, const AirportManager* pManager, FILE* fp)
{
#ifndef COMPRESSED_BFILE
	if (fread(pF, sizeof(Flight), 1, fp) != 1)
	{
		printf("Error read flight\n");
		return 0;
	}
#else
	if (!readCharsFromFile(pF->originCode, CODE_LENGTH, fp, "Can't read Source IATA from File\n"))
		return 0;

	pF->originCode[CODE_LENGTH] = '\0';

	if (!readCharsFromFile(pF->destCode, CODE_LENGTH, fp, "Can't read Destination IATA from File\n"))
		return 0;
	pF->destCode[CODE_LENGTH] = '\0';

	unsigned char* pBuffer = malloc(4 * sizeof(unsigned char));

	if (!pBuffer) {
		printf("Error in allocating memory\n");
		return 0;
	}

	if (fread(pBuffer, sizeof(unsigned char), 4, fp) != 4) {
		printf("Error read Compressed Flight\n");
		return 0;
	}

	deCompressFlight(pF, pBuffer);
#endif
	return 1;
}

int	compareByCodeOrig(const void* flight1, const void* flight2)
{
	const Flight* pFlight1 = *(const Flight**)flight1;
	const Flight* pFlight2 = *(const Flight**)flight2;
	return strcmp(pFlight1->originCode, pFlight2->originCode);
}

int	compareByCodeDest(const void* flight1, const void* flight2)
{
	const Flight* pFlight1 = *(const Flight**)flight1;
	const Flight* pFlight2 = *(const Flight**)flight2;
	return strcmp(pFlight1->destCode, pFlight2->destCode);
}

int	compareByHour(const void* flight1, const void* flight2)
{
	const Flight* pFlight1 = *(const Flight**)flight1;
	const Flight* pFlight2 = *(const Flight**)flight2;
	return (pFlight1->hour - pFlight2->hour);
}

int		compareByDate(const void* flight1, const void* flight2)
{
	const Flight* pFlight1 = *(const Flight**)flight1;
	const Flight* pFlight2 = *(const Flight**)flight2;

	Date d1 = pFlight1->date;
	Date d2 = pFlight2->date;

	if (d1.year > d2.year)
		return 1;
	if (d1.year < d2.year)
		return -1;

	if (d1.month > d2.month)
		return 1;
	if (d1.month < d2.month)
		return -1;

	if (d1.day > d2.day)
		return 1;
	if (d1.day < d2.day)
		return -1;

	return 0;
}



void	freeFlight(void* val)
{
	Flight* pFlight = *(Flight**)val;
	free(pFlight);
}
