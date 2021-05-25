#ifndef __AIR_MANAGER__
#define __AIR_MANAGER__

#include "Airport.h"
#include "LinkedList.h"

typedef struct
{
	LIST	list;
	int		count;
} AirportManager;

int		initManager(AirportManager* pManager);
int		addAirport(AirportManager* pManager);
void	setAirport(Airport* pPort, AirportManager* pManager);
Airport* findAirportByCode(AirportManager* pManager, char* code);
int		checkUniqeCode(char* code, AirportManager* pManager);
void	printAirports(AirportManager* pManager);
void	freeManager(AirportManager* pManager);
int		compareAirportCode(const Airport* airport, const char* code);
int		showAirports(const void* airport);
NODE*	findAirport(NODE* pNode, const DATA value, int(*compare)(const Airport*, const char*));

#endif