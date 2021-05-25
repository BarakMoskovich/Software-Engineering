#ifndef __COMPANY__BINARY__
#define __COMPANY__BINARY__

#include <stdio.h>
#include "Company.h"

int	writeFlightToBFile(FILE* fp, const Flight* flight);
int	writeCompayToBFile(const char* fileName, const Company* pComp);
int	addCompayToBFile(FILE* fp, const Company* pComp);
int	addFlightFromBFile(FILE* fp, Company* pComp);
int	addCompanyFromBFile(FILE* fp, Company* pComp);
int initCompanyFromBFile(const char* fileName, Company* pComp);



#endif