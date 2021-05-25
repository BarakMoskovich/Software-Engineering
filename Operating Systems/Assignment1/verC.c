//
// Created by barak
//
#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/mman.h>
#include <string.h>
#include "mystrlib.h"

#define MAX_BUFF 80
#define ARR_SIZE 2

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s [str1] [str2]\n", argv[0]);
        exit(-1);
    }

    pid_t pid;
    char *shared_strings[MAX_BUFF];
    for (int i = 0; i < ARR_SIZE; i++) {
        shared_strings[i] = mmap(NULL, sizeof(char) * MAX_BUFF, PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, 0,0);
        if (shared_strings[i] == MAP_FAILED) {
            sys_error("Mapping Failed (Shared strings)");
        }
    }

    int *result = mmap(NULL, sizeof(int), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, 0, 0);
    if (result == MAP_FAILED) {
        sys_error("Mapping Failed (Shared result)");
    }

    if ((pid = fork()) == 0) { // Child
        *(result) = mystrxor(shared_strings[0], shared_strings[1]);
//        printf("Child: %s XOR %s, Result = %d\n", shared_strings[0], shared_strings[1], *result);
        exit(EXIT_SUCCESS);
    } else if (pid < 0) {
        sys_error("Error in forking\n");
    }

    strcpy(shared_strings[0], argv[1]);
    strcpy(shared_strings[1], argv[2]);

    wait(NULL);
    printf("Parent: \"%s\" XOR \"%s\", Result = %d\n", shared_strings[0], shared_strings[1], *result);

    // Unmap
    int err = munmap(result, sizeof(int));
    if (err != 0) {
        sys_error("Unmapping Failed");
    }

    for (int i = 0; i < ARR_SIZE; i++) {
        err = munmap(shared_strings[i], sizeof(char) * MAX_BUFF);
        if (err != 0) {
            sys_error("Unmapping Failed");
        }
    }
}


