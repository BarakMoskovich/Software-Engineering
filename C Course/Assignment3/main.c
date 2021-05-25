#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include "CompanyBinary.h"
#include "Company.h"
#include "AirportManagerFile.h"
#include "General.h"

#define TEXT_FILE "airport_authority.txt"
#define BIN_FILE "company.bin"

typedef enum 
{ 
	eAddFlight, eAddAirport, ePrintCompany, ePrintAirports,
	ePrintFlightOrigDest, eSearchFlight, eSortFlights, eNofOptions
} eMenuOptions;

const char* str[eNofOptions] = { "Add Flight", "Add Airport",
								"Print Company", "Print all Airports",
								"Print flights between origin-destination",
								"Search flight in company", "Sort flights in company"};


const char* sortMenu[eNotSorted] = { "Sort by hour", "Sort by date",
								"Sort by dest code", "Sort by origin code" };


#define EXIT			-1
int menu();
int getSorted();

int main()
{
	AirportManager	manager;
	Company			company;

	if (!initAirportManagerFromTextFile(TEXT_FILE, &manager))
		initManager(&manager);

	if (!initCompanyFromBFile(BIN_FILE, &company))
		initCompany(&company);


	int option;
	int stop = 0;
	
	do
	{
		option = menu();
		switch (option)
		{
			case eAddFlight:
				if (!addFlight(&company, &manager))
					printf("Error adding flight\n");
				break;


			case eAddAirport:
				if (!addAirport(&manager))
					printf("Error adding airport\n");
				break;

			case ePrintCompany:
				printCompany(&company);
				break;

			case ePrintAirports:
				printAirports(&manager);
				break;

			case ePrintFlightOrigDest:
				printFlightsCount(&company);
				break;

			case eSortFlights:
				doSort(&company, getSorted());
				break;

			case eSearchFlight:
				printSearchFlight(&company);
				break;

			case EXIT:
				printf("Bye bye\n");
				stop = 1;
				break;

			default:
				printf("Wrong option\n");
				break;
		}
	} while (!stop);

	writeAirportManagerToTextFile(TEXT_FILE, &manager);
	writeCompayToBFile(BIN_FILE, &company);

	freeManager(&manager);
	freeCompany(&company);
	return 1;
}

int getSorted() { 
	int choseSort = -1;
	for (int i = 0; i < eNotSorted; i++)
		printf("%d - %s\n", i, sortMenu[i]);
	while (choseSort < eSortByHour || choseSort > eNotSorted) {
		printf("Please choose one of the sorts: ");
		scanf("%d", &choseSort);
	}
	return choseSort;
}

int menu()
{
	int option;
	printf("\n\n");
	printf("Please choose one of the following options\n");
	for(int i = 0 ; i < eNofOptions ; i++)
		printf("%d - %s\n",i,str[i]);
	printf("%d - Quit\n", EXIT);
	scanf("%d", &option);
	//clean buffer
	char tav;
	scanf("%c", &tav);
	return option;
}