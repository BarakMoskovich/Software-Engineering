#ifndef __LINKED__LIST__
#define __LINKED__LIST__

#include "General.h"

typedef void* DATA;					
typedef enum { False, True } BOOL;	

// Node
typedef struct node
{
	DATA			key;
	struct node*	next;
} NODE;

// List
typedef struct
{
	NODE head;
} LIST;


BOOL	L_init(LIST* pList);  
NODE*	L_insert(NODE* pNode, const DATA Value);
BOOL	L_delete(NODE* pNode, void(*freeFunc)(void*));  
//NODE*	L_find(const NODE* pNode, const DATA Value, int(*compare)(const void*, const void*));
BOOL	L_free(LIST* pList, void(*freeFunc)(void*));  
int		L_print(const LIST* pList, void(*print)(const void*));  
void	L_sort(LIST* pList, int(*compare)(const void*, const void*));
void	swap(NODE *a, NODE *b);
int		existValue(const LIST* pList, const DATA value, int(*compare)(const void*, const void*));

#endif