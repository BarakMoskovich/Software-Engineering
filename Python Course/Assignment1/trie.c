#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SIGMA 26 // N - Size of the alphabet

typedef struct node {
    struct node *children[SIGMA + 1]; // +1 for \0
} TrieNode;

// Get index by subtracting the ASCII value of ch at the value of 'A'
int getIndex(char ch) {
    return (ch == '\0') ? 0 : ((int)ch - (int)'A' + 1);
}

// Check if node is leaf
int isLeaf(TrieNode* pNode) {
    if (pNode->children[0] == NULL)
        return 0;
    for (int i = 1; i < SIGMA + 1; i++)
        if (pNode->children[i])
            return 0;
    return 1;
}

// init new Trie node
TrieNode* init(void) {
    TrieNode *pNode = NULL;

    pNode = (TrieNode*) malloc(sizeof (TrieNode));

    if (pNode) {
        for (int i = 0; i < SIGMA + 1; i++)
            pNode->children[i] = NULL;
    }

    return pNode;
}

// Insert to Trie
void insert(TrieNode* pNode, const char* str) {
    int index;
    int len = strlen(str);

    TrieNode* pCurrNode = pNode;

    for (int i = 0; i < len; i++) {
        index = getIndex(str[i]);
        if (!pCurrNode->children[index])
            pCurrNode->children[index] = init();

        pCurrNode = pCurrNode->children[index];
    }

    if (!pCurrNode->children[0])
        pCurrNode->children[0] = init();
}

// Recursive function to remove a string from a Trie
int removeString(TrieNode *pNode, char* str) {
    int index;
    TrieNode* pNextNode;

    if (pNode == NULL || !pNode)
        return 0;

    if (*str) {
        index = getIndex(*str);
        pNextNode = pNode->children[index];
        if (!pNextNode)
            return 0;

        removeString(pNextNode, str + 1);

        if (isLeaf(pNextNode)) {
            free(pNextNode);
            pNode->children[index] = NULL;
            return 1;
        }
    }

    return 1;
}

// Search in Trie
int search(TrieNode* pNode, const char* str) {
    int index;
    int len = strlen(str);
    TrieNode* pCurrNode = pNode;

    for (int i = 0; i < len; i++) {
        index = getIndex(str[i]);

        if (!pCurrNode->children[index])
            return 0;

        pCurrNode = pCurrNode->children[index];
    }

    return (pCurrNode != NULL && pCurrNode->children[0]) ? 1 : 0;
}

int main() {
    // #define CHAR_MAX 127 // Default
    char strings[][CHAR_MAX] = { "ROMANE", "ROMANUS", "ROMULUS", "RUBENS", "RUBER", "RUB", "RUBICON", "RUBICUNDUS", "RUBY", "ROAD" }; // s - String
    char found_msg[][CHAR_MAX] = { "Not found", "Found" };

    TrieNode* trieRoot = init(); // T - Rooted tree

    int arr_size = sizeof(strings) / sizeof(strings[0]);
//    int arr_size = 10;

    for (int i = 0; i < arr_size; i++)
        insert(trieRoot, strings[i]);

    char* s = "RUBER"; // String: s

    printf("--- Before Remove ---\n");
    printf("Search %s in Trie:\t%s\n", s, found_msg[search(trieRoot, s)] );
    printf("Search %s in Trie:\t%s\n", "RUBY", found_msg[search(trieRoot, "RUBY")] );
    printf("Search %s in Trie:\t%s\n", "RUBI", found_msg[search(trieRoot, "RUBI")] );

    printf("\nDeletes %s...", s);
    if (removeString(trieRoot, s))
        printf("\n%s Successfully deleted!\n", s);


    printf("\n--- After Remove ---\n");
    printf("Search %s in Trie:\t%s\n", s, found_msg[search(trieRoot, s)] );
    printf("Search %s in Trie:\t%s\n", "RUBY", found_msg[search(trieRoot, "RUBY")] );
    printf("Search %s in Trie:\t%s\n", "RUBI", found_msg[search(trieRoot, "RUBI")] );
    return 0;
}
