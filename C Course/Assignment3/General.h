#ifndef __GENERAL__
#define __GENERAL__

#define MAX_STR_LEN 255
#define CODE_LENGTH 3
char*	getStrExactName(const char* msg);
char*	myGets(char* buffer, int size);
char*	getDynStr(char* str);
char**	splitCharsToWords(char* str,int* pCount,int* pTotalLength);
void	generalArrayFunction(const void* arr, int numberOfElements, int typeSize, void(*f)(void* element));

#endif

