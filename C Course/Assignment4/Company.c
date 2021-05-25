#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Company.h"
#include "Airport.h"
#include "General.h"
#include "fileHelper.h"

static const char* sortOptStr[eNofSortOpt] = {
	"None","Hour", "Date", "Airport takeoff code", "Airport landing code" };


int	initCompanyFromFile(Company* pComp, AirportManager* pManaer, const char* fileName)
{
	L_init(&pComp->flighDateList);
	if (loadCompanyFromFile(pComp, pManaer, fileName))
	{
		initDateList(pComp);
		return 1;
	}
	return 0;
}

void	initCompany(Company* pComp,AirportManager* pManaer)
{
	printf("-----------  Init Airline Company\n");
	L_init(&pComp->flighDateList);
	
	pComp->name = getStrExactName("Enter company name");
	pComp->flightArr = NULL;
	pComp->flightCount = 0;
}

void	initDateList(Company* pComp)
{	
	for (int i = 0; i < pComp->flightCount; i++)
	{
		if(isUniqueDate(pComp,i))
		{
			char* sDate = createDateString(&pComp->flightArr[i]->date);
			L_insert(&(pComp->flighDateList.head), sDate);
		}
	}
}

int		isUniqueDate(const Company* pComp, int index)
{
	Date* pCheck = &pComp->flightArr[index]->date;
	for (int i = 0; i < pComp->flightCount; i++)
	{
		if (i == index)
			continue;
		if (equalDate(&pComp->flightArr[i]->date,pCheck))
			return 0;
	}
	return 1;
}

int		addFlight(Company* pComp, const AirportManager* pManager)
{

	if (pManager->count < 2)
	{
		printf("There are not enoght airport to set a flight\n");
		return 0;
	}
	pComp->flightArr = (Flight**)realloc(pComp->flightArr, (pComp->flightCount + 1) * sizeof(Flight*));
	//if (!pComp->flightArr)
	//	return 0;

	CHECK_RETURN_0(pComp->flightArr)

	pComp->flightArr[pComp->flightCount] = (Flight*)calloc(1, sizeof(Flight));
	//if (!pComp->flightArr[pComp->flightCount])
	//	return 0;

	CHECK_RETURN_0(pComp->flightArr[pComp->flightCount])

	initFlight(pComp->flightArr[pComp->flightCount], pManager);
	if (isUniqueDate(pComp, pComp->flightCount))
	{
		char* sDate = createDateString(&pComp->flightArr[pComp->flightCount]->date);
		L_insert(&(pComp->flighDateList.head), sDate);
	}
	pComp->flightCount++;
	return 1;
}

void	printCompany(const Company* pComp)
{

	printf("Company %s:\n", pComp->name);
	printf("Has %d flights\n", pComp->flightCount);
#ifdef DETAIL_PRINT
	generalArrayFunction((void*)pComp->flightArr, pComp->flightCount, sizeof(Flight**), printFlightV);
	printf("\nFlight Date List:");
	L_print(&pComp->flighDateList, printStr);
#endif // DETAIL_PRINT


}

void	printFlightsCount(const Company* pComp)
{
	char codeOrigin[CODE_LENGTH + 1];
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

	printf("from %s to %s\n", codeOrigin, codeDestination);
}

int		saveCompanyToFile(const Company* pComp, const char* fileName)
{
	FILE* fp;
	fp = fopen(fileName, "wb");
	//if (!fp) {
	//	printf("Error open copmpany file to write\n");
	//	return 0;
	//}

	CHECK_MSG_RETURN_0(fp, "Error open copmpany file to write\n")

#ifndef COMPRESSED_BFILE
	
	if (!writeStringToFile(pComp->name, fp, "Error write comapny name\n"))
		return 0;

	if (!writeIntToFile(pComp->flightCount, fp, "Error write flight count\n"))
		return 0;

	if (!writeIntToFile((int)pComp->sortOpt, fp, "Error write sort option\n"))
		return 0;

	for (int i = 0; i < pComp->flightCount; i++)
	{
		if (!saveFlightToFile(pComp->flightArr[i], fp))
			return 0;
	}
#else

	int byteAmount = BYTE_SIZE_OF__COUNT_OPTION_NAME_LENGTH;
	unsigned char* pBuffer = compressCountOptionAndNameLength(pComp, byteAmount);

	CHECK_NULL__MSG_COLSE_FILE(fp, "Unable to allocated memory!\n", pBuffer)

	// write compressed Flights count, Sort option and Airline name length
	if (fwrite(pBuffer, sizeof(char), byteAmount, fp) != byteAmount) {
		MSG_CLOSE_RETURN_0(fp, "Error in compressing Flights count, Sort option and Airline name length\n")
		//fclose(fp);
		//return 0;
	}

	// write airline name
	if (!writeCharsToFile(pComp->name, (int)strlen(pComp->name) + 1, fp, "Error write comapny name\n"))
		return 0;

	// write flights
	for (int i = 0; i < pComp->flightCount; i++)
	{
		if (!saveFlightToFile(pComp->flightArr[i], fp))
			return 0;
	}

#endif

	fclose(fp);
	return 1;
}

unsigned char* compressCountOptionAndNameLength(const Company* pComp, int size) {
	unsigned char* pBuffer = (unsigned char*)malloc(size * sizeof(unsigned char));

	CHECK_RETURN_NULL(pBuffer);
	int len = (int)strlen(pComp->name);

	pBuffer[0] = pComp->flightCount >> 1;
	pBuffer[1] = (pComp->flightCount << 8) | (pComp->sortOpt << 4) | (unsigned char)(len & createMask(0, 3));



	//pBuffer[0] = strlen(pComp->name) | (sortOption << 4) | ((pComp->flightCount) << 8);
	//pBuffer[1] = ((pComp->flightCount) >> 1);

	return pBuffer;

}

void deCompress(Company* pComp, unsigned char* pBuffer, int* nameSize) {
	pComp->flightCount = pBuffer[0] | (pBuffer[1] & createMask(8, 8));
	pComp->sortOpt = (eSortOption)((pBuffer[1] & createMask(4, 6)) >> 4);
	*nameSize = pBuffer[1] & createMask(0, 3);
}


int loadCompanyFromFile(Company* pComp, const AirportManager* pManager, const char* fileName)
{
	FILE* fp;
	fp = fopen(fileName, "rb");
	//if (!fp)
	//{
	//	printf("Error open company file\n");
	//	return 0;
	//}

	CHECK_MSG_RETURN_0(fp, "Error open company file\n");

#ifndef COMPRESSED_BFILE
	pComp->flightArr = NULL;


	pComp->name = readStringFromFile(fp, "Error reading company name\n");
	//if (!pComp->name)
	//	return 0;
	CHECK_RETURN_0(pComp->name)

	if (!readIntFromFile(&pComp->flightCount, fp, "Error reading flight count name\n"))
		return 0;

	int opt;
	if (!readIntFromFile(&opt, fp,"Error reading sort option\n"))
		return 0;

	pComp->sortOpt = (eSortOption)opt;

	if (pComp->flightCount > 0)
	{
		pComp->flightArr = (Flight**)malloc(pComp->flightCount * sizeof(Flight*));
		//if (!pComp->flightArr)
		//{
		//	printf("Alocation error\n");
		//	fclose(fp);
		//	return 0;
		//}

		CHECK_NULL__MSG_COLSE_FILE(fp, "Alocation error\n", pComp->flightArr)

	}
	else
		pComp->flightArr = NULL;

	for (int i = 0; i < pComp->flightCount; i++)
	{
		pComp->flightArr[i] = (Flight*)calloc(1, sizeof(Flight));
		//if (!pComp->flightArr[i])
		//{
		//	printf("Alocation error\n");
		//	fclose(fp);
		//	return 0;
		//}
		CHECK_NULL__MSG_COLSE_FILE(fp, "Alocation error\n", pComp->flightArr[i])

		if (!loadFlightFromFile(pComp->flightArr[i], pManager, fp))
			return 0;
	}
#else
	unsigned char* pBuffer = malloc(2*sizeof(unsigned char));

	CHECK_NULL__MSG_COLSE_FILE(fp, "Error in memory alloc\n", pBuffer);

	if (fread(pBuffer, sizeof(unsigned char), 2, fp) != 2) {
		fclose(fp);
		return 0;
	}
	int nameSize;
	deCompress(pComp, pBuffer, &nameSize);
	nameSize++;

	pComp->name = malloc(nameSize * sizeof(char));

	CHECK_NULL__MSG_COLSE_FILE(fp, "Error in memory allocation\n", pComp->name);

	if (!readCharsFromFile(pComp->name, nameSize - 1, fp, "Error in read name from file\n"))
		return 0;
	pComp->name[nameSize - 1] = '\0';

	if (pComp->flightCount > 0)
	{
		L_init(&pComp->flighDateList);
		pComp->flightArr = (Flight**)malloc(pComp->flightCount * sizeof(Flight*));
		CHECK_NULL__MSG_COLSE_FILE(fp, "Alocation error\n", pComp->flightArr);

		for (int i = 0; i < pComp->flightCount; i++)
		{
			pComp->flightArr[i] = (Flight*)calloc(1, sizeof(Flight));
			CHECK_NULL__MSG_COLSE_FILE(fp, "Alocation error\n", pComp->flightArr[i]);

			if (!loadFlightFromFile(pComp->flightArr[i], pManager, fp))
				return 0;
			

		}
		initDateList(pComp);
	}
	else
		pComp->flightArr = NULL;

#endif

	fclose(fp);
	return 1;
}

void	sortFlight(Company* pComp)
{
	pComp->sortOpt = showSortMenu();
	int(*compare)(const void* air1, const void* air2) = NULL;

	switch (pComp->sortOpt)
	{
	case eHour:
		compare = compareByHour;
		break;
	case eDate:
		compare = compareByDate;
		break;
	case eSorceCode:
		compare = compareByCodeOrig;
		break;
	case eDestCode:
		compare = compareByCodeDest;
		break;
	
	}

	if (compare != NULL)
		qsort(pComp->flightArr, pComp->flightCount, sizeof(Flight*), compare);

}

void	findFlight(const Company* pComp)
{
	int(*compare)(const void* air1, const void* air2) = NULL;
	Flight f = { 0 };
	Flight* pFlight = &f;


	switch (pComp->sortOpt)
	{
	case eHour:
		f.hour = getFlightHour();
		compare = compareByHour;
		break;
	case eDate:
		getchar();
		getCorrectDate(&f.date);
		compare = compareByDate;
		break;
	case eSorceCode:
		getchar();
		getAirportCode(f.originCode);
		compare = compareByCodeOrig;
		break;
	case eDestCode:
		getchar();
		getAirportCode(f.destCode);
		compare = compareByCodeDest;
		break;
	}

	if (compare != NULL)
	{
		Flight** pF = bsearch(&pFlight, pComp->flightArr, pComp->flightCount, sizeof(Flight*), compare);
		if (pF == NULL)
			printf("Flight was not found\n");
		else {
			printf("Flight found, ");
			printFlight(*pF);
		}
	}
	else {
		printf("The search cannot be performed, array not sorted\n");
	}

}

eSortOption showSortMenu()
{
	int opt;
	printf("Base on what field do you want to sort?\n");
	do {
		for (int i = 1; i < eNofSortOpt; i++)
			printf("Enter %d for %s\n", i, sortOptStr[i]);
		scanf("%d", &opt);
	} while (opt < 0 || opt >eNofSortOpt);

	return (eSortOption)opt;
}

void	freeCompany(Company* pComp)
{
	generalArrayFunction((void*)pComp->flightArr, pComp->flightCount, sizeof(Flight**), freeFlight);
	free(pComp->flightArr);
	free(pComp->name);
	L_free(&pComp->flighDateList, freePtr);
}








