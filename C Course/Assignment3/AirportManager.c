#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "AirportManager.h"

int	initManager(AirportManager* pManager)
{
	printf("-----------  Init airport Manager\n");
	pManager->count = 0;
	L_init(&pManager->list);

	int count = 0;
	do {
		printf("How many airport?\t");
		scanf("%d", &count);
	} while (count < 0);
	//clean buffer
	char tav;
	scanf("%c", &tav);
	if (count == 0)
		return 1;

	for (int i = 0; i < count; i++)
		addAirport(pManager);


	return 1;
}


int	addAirport(AirportManager* pManager)
{
	NODE* pNode = &(pManager->list.head);

	Airport *airport = (Airport*)malloc(sizeof(Airport));
	if (!airport)
		return 0;

	setAirport(airport, pManager);
	L_insert(pNode, airport);
	pManager->count = pManager->count++;

	if (pManager->count > 1)
		L_sort(&(pManager->list), compareByAirportCode);

	return 1;
}

void setAirport(Airport* pPort, AirportManager* pManager)
{
	while (1)
	{
		getAirportCode(pPort->code);
		if (checkUniqeCode(pPort->code, pManager))
		break;

		printf("This code already in use - enter a different code\n");
	}

	initAirportNoCode(pPort);
}


Airport* findAirportByCode(AirportManager* pManager, char* code)
{
	NODE* pNode = findAirport(pManager->list.head.next, code, compareAirportCode);
	if (pNode)
		return pNode->key;
	else
		return NULL;
}


int checkUniqeCode(char* code, AirportManager* pManager)
{
	Airport* port = findAirportByCode(pManager, code);

	if (port != NULL)
		return 0;

	return 1;
}


void printAirports(AirportManager* pManager)
{
	printf("there are %d airports\n", pManager->count);
	L_print(&(pManager->list), showAirports);
}


void freeManager(AirportManager* pManager)
{
	L_free(&(pManager->list), NULL);
}


int	compareAirportCode(const Airport* airport, const char* code)
{
	return strcmp(airport->code, code);
}


int	showAirports(const void* airport)
{
	const Airport* pPort = (const Airport*)airport;
	printAirport(pPort);
	return 1;
}

NODE* findAirport(NODE* pNode, DATA value, int(*compare)(const Airport*, const char*))
{
	NODE* temp = NULL;
	if (!pNode) return NULL;
	while (pNode != NULL)
	{
		if (compare(pNode->key, value) == 0)
		{
			temp = pNode;
			break;
		}
		pNode = pNode->next;
	}

	return temp;
}