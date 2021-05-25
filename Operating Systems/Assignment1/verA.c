//
// Created by barak
//
#include <stdio.h>
#include <unistd.h>
#include <stddef.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include "mystrlib.h"

int main(int argc, char *argv[]) {
    char* str1 = argv[1];
    char* str2 = argv[2];
    int wstatus;

    pid_t pid;
    int res;

    if ((pid = fork()) < 0) {
        sys_error("An error occurred with fork");
    } else if (pid == 0) { // Childs
        char* const path = strdup("./argsxorstr");
        char* const parmList[] = { path, str1, str2, NULL };
        res = execve(path, parmList, NULL);
    } else { // Parent
        wait(&wstatus);
        res = WEXITSTATUS(wstatus);
        // printf("Result: %d\n", res);
        return res;
    }
}