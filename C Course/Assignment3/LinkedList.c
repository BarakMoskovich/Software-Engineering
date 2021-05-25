#include <stdio.h>
#include <stdlib.h>
#include "LinkedList.h"

BOOL L_init(LIST* pList)
{
	if (pList == NULL) return False;

	pList->head.next = NULL;
	return True;
}

NODE* L_insert(NODE* pNode, const DATA Value)
{
	NODE* tmp;

	if (!pNode) return NULL;

	tmp = (NODE*)malloc(sizeof(NODE));

	if (tmp != NULL) {
		tmp->key = Value;
		tmp->next = pNode->next;
		pNode->next = tmp;
	}
	return tmp;
}

BOOL L_delete(NODE* pNode, void(*freeFunc)(void*))
{
	NODE* tmp;

	if (!pNode || !(tmp = pNode->next)) return False;

	pNode->next = tmp->next;
	if (freeFunc != NULL)
		freeFunc(tmp->key);
	free(tmp);
	return True;
}

BOOL L_free(LIST* pList, void(*freeFunc)(void*))
{
	NODE *tmp;

	if (!pList) return False;
	tmp = &(pList->head);
	BOOL res = True;
	while (res)
	{
		res = L_delete(tmp, freeFunc);
	}

	return True;
}

int L_print(const LIST* pList, void(*print)(const void*))
{
	NODE	*tmp;
	int		c = 0;

	if (!pList) return 0;

	printf("\n");
	for (tmp = pList->head.next; tmp; tmp = tmp->next, c++)
		print(tmp->key);
	printf("\n");
	return c;
}

void L_sort(LIST* pList, int(*compare)(const void*, const void*))
{
	int swapped;
	NODE *ptr1 = pList->head.next, *lptr = NULL;

	if (ptr1 == NULL)
		return;

	do
	{
		swapped = 0;

		while (ptr1->next != NULL && ptr1->next != lptr)
		{
			if (compare(ptr1->key, ptr1->next->key) > 0)
			{
				swap(ptr1, ptr1->next);
				swapped = 1;
			}
			ptr1 = ptr1->next;
		}
		lptr = ptr1;
	} while (swapped);
}

void swap(NODE *a, NODE *b)
{
	DATA* temp = a->key;
	a->key = b->key;
	b->key = temp;
}

int existValue(const LIST* pList, const DATA value, int(*compare)(const void*, const void*))
{
	NODE* pNode = pList->head.next;

	for (pNode; pNode != NULL; pNode = pNode->next)
		if (compare(pNode->key, value) == 0)
			return 1;
	return 0;
}