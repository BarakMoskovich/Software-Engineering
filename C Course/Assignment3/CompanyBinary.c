#define _CRT_SECURE_NO_WARNINGS
#include "CompanyBinary.h"
#include "AirportManager.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int	writeFlightToBFile(FILE* fp, const Flight* flight)
{
	if (fwrite(&flight->originCode, sizeof(char), (CODE_LENGTH + 1), fp) != (CODE_LENGTH + 1))
		return 0;
	if (fwrite(&flight->destCode, sizeof(char), (CODE_LENGTH + 1), fp) != (CODE_LENGTH + 1))
		return 0;
	if (fwrite(&flight->hour, sizeof(int), 1, fp) != 1)
		return 0;
	if (fwrite(&flight->date.year, sizeof(int), 1, fp) != 1)
		return 0;
	if (fwrite(&flight->date.month, sizeof(int), 1, fp) != 1)
		return 0;
	if (fwrite(&flight->date.day, sizeof(int), 1, fp) != 1)
		return 0;
	return 1;
}

int	addFlightFromBFile(FILE* fp, Company* pComp)
{
	pComp->flightArr = (Flight**) realloc(pComp->flightArr, (pComp->flightCount + 1) * sizeof(Flight*));
	if (!pComp->flightArr)
		return 0;

	pComp->flightArr[pComp->flightCount] = (Flight*)calloc(1, sizeof(Flight));
	if (!pComp->flightArr[pComp->flightCount])
		return 0;

	if (fread(pComp->flightArr[pComp->flightCount]->originCode, sizeof(char), (CODE_LENGTH + 1), fp) != (CODE_LENGTH + 1))
		return 0;
	if (fread(pComp->flightArr[pComp->flightCount]->destCode, sizeof(char), (CODE_LENGTH + 1), fp) != (CODE_LENGTH + 1))
		return 0;
	if (fread(&pComp->flightArr[pComp->flightCount]->hour, sizeof(int), 1, fp) != 1)
		return 0;
	if (fread(&pComp->flightArr[pComp->flightCount]->date.year, sizeof(int), 1, fp) != 1)
		return 0;
	if (fread(&pComp->flightArr[pComp->flightCount]->date.month, sizeof(int), 1, fp) != 1)
		return 0;
	if (fread(&pComp->flightArr[pComp->flightCount]->date.day, sizeof(int), 1, fp) != 1)
		return 0;

	LIST* pList = &(pComp->dates);
	NODE* pNode = &(pComp->dates.head);
	Date* pDate = &(pComp->flightArr[pComp->flightCount])->date;
	if (!existValue(pList, pDate, comapreByDate))
		L_insert(pNode, &(pComp->flightArr[pComp->flightCount]->date));
	pComp->flightCount++;

	return 1;
}

int	writeCompayToBFile(const char* fileName, const Company* pComp)
{
	FILE* fp;
	fp = fopen(fileName, "wb");
	if (!fp)
		return 0;

	if (!addCompayToBFile(fp, pComp)) {
		fclose(fp);
		return 0;
	}
	return 1;
}


int	addCompayToBFile(FILE* fp, const Company* pComp)
{
	size_t len = strlen(pComp->name);
	if (fwrite(&len, sizeof(int), 1, fp) != 1)
		return 0;

	if (fwrite(pComp->name, sizeof(char), (len + 1), fp) != (len + 1))
		return 0;

	if (fwrite(&pComp->flightCount, sizeof(int), 1, fp) != 1)
		return 0;

	if (fwrite(&pComp->sorted, sizeof(int), 1, fp) != 1)
		return 0;

	for (int i = 0; i < pComp->flightCount; i++)
		if (!writeFlightToBFile(fp, pComp->flightArr[i]))
			return 0;

	return 1;
}

int initCompanyFromBFile(const char* fileName, Company* pComp) 
{
	FILE* fp = fopen(fileName, "rb");
	if (!fp)
		return 0;

	pComp->flightCount = 0;
	pComp->flightArr = NULL;
	L_init(&pComp->dates);
	pComp->sorted = eNotSorted; // Default

	if (!addCompanyFromBFile(fp, pComp)) {
		fclose(fp);
		return 0;
	}
	return 1;
}

int	addCompanyFromBFile(FILE* fp, Company* pComp)
{
	int len, flights;
	char temp[MAX_STR_LEN];

	if (fread(&len, sizeof(int), 1, fp) != 1)
		return 0;
	if (fread(&temp, sizeof(char), (len + 1), fp) != (len + 1))
		return 0;
	pComp->name = getDynStr(temp);

	if (fread(&flights, sizeof(int), 1, fp) != 1)
		return 0;

	if (fread(&pComp->sorted, sizeof(int), 1, fp) != 1)
		return 0;

	for (int i = 0; i < flights; i++)
		if (!addFlightFromBFile(fp, pComp))
			return 0;

	return 1;
}