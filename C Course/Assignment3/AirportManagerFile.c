#define _CRT_SECURE_NO_WARNINGS
#include "AirportManagerFile.h"
#include <stdlib.h>
#include <string.h>

void writeAirportToTextFile(FILE* fp, const Airport* pAirport)
{
	fprintf(fp, "%s\n%s\n%s\n", pAirport->name, pAirport->country, pAirport->code);
}

int addAirportFromTextFile(FILE* fp, AirportManager* pAirportManager)
{
	NODE* pNode = &(pAirportManager->list.head);

	Airport *airport = (Airport*)malloc(sizeof(Airport));
	if (!airport)
		return 0;

	airport->name = (char*) malloc (sizeof(char) * MAX_STR_LEN);
	airport->country = (char*) malloc (sizeof(char) * MAX_STR_LEN);
	char* code = (char*)malloc(sizeof(char) * (CODE_LENGTH + 1));

	char tav;
	fscanf(fp, "%c", &tav);

	if (fgets(airport->name, MAX_STR_LEN, fp) == NULL) return 0;
	if (fgets(airport->country, MAX_STR_LEN, fp) == NULL) return 0;
	if (fgets(code, CODE_LENGTH + 1, fp) == NULL) return 0;

	airport->name[strcspn(airport->name, "\n")] = '\0';
	airport->country[strcspn(airport->country, "\n")] = '\0';
	strcpy(airport->code, code);

	free(code);

	L_insert(pNode, airport);
	L_sort(&(pAirportManager->list), compareByAirportCode);

	return 1;
}

int writeAirportManagerToTextFile(const char* fileName, AirportManager* pAirportManager)
{
	FILE* fp;
	fp = fopen(fileName, "w");
	if (!fp)
		return 0;

	if (!addAirportManagerToTextFile(fp, pAirportManager)) {
		fclose(fp);
		return 0;
	}
	return 1;
}

int addAirportManagerToTextFile(FILE* fp, AirportManager* pAirportManager)
{
	fprintf(fp, "%d\n", pAirportManager->count);

	NODE *tmp;
	LIST *pList = &(pAirportManager->list);
 
	if (!pList)
		return 0;

	for (tmp = pList->head.next; tmp; tmp = tmp->next)
		writeAirportToTextFile(fp, (Airport*)tmp->key);

	free(tmp);

	return 1;
}

int initAirportManagerFromTextFile(const char* fileName, AirportManager* pAirportManager)
{
	FILE* fp = fopen(fileName, "r");
	if (!fp)
		return 0;

	if (!addAirportManagerFromTextFile(fp, pAirportManager)) {
		fclose(fp);
		return 0;
	}
	return 1;
}

int addAirportManagerFromTextFile(FILE* fp, AirportManager* pAirportManager)
{
	char* count = (char*)malloc(sizeof(int));

	if (fgets(count, sizeof(int), fp) == NULL)
		return 0;
	pAirportManager->count = atoi(count);

	free(count);

	L_init(&pAirportManager->list);

	if (pAirportManager->count == 0)
		return 0;

	for (int i = 0; i < pAirportManager->count; i++)
		if (!addAirportFromTextFile(fp, pAirportManager))
			return 0;
	


	return 1;
}