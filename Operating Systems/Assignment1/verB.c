//
// Created by barak
//
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include "mystrlib.h"

#define LINELEN  10
#define MAX_RES  100
#define READ     0
#define WRITE    1

int main () {
    int fd1[2], fd2[2];
    pid_t pid;

    if (pipe(fd1) == -1)
        sys_error("An error occurred with opening pipe");

    if (pipe(fd2) == -1)
        sys_error("An error occurred with opening pipe");

    if ((pid = fork()) < 0) {
        sys_error("An error occurred with fork");
    } else if (pid == 0) {
        close(fd1[WRITE]);
        close(fd2[READ]);

        dup2(fd1[READ], fileno(stdin));
        dup2(fd2[WRITE], fileno(stdout));

        char* const path = strdup("./xorstr");
        char* const parmList[] = { path, NULL };
//        if (execl("./xorstr", "./xorstr", (int *) NULL) < 0)
        if (execve(path, parmList, NULL) < 0)
            sys_error("An error occurred with execute");

        // close(fd1[READ]);
        // close(fd2[WRITE]);
    } else {
        close(fd1[READ]);
        close(fd2[WRITE]);

        char res[MAX_RES];
        char str[2][LINELEN + 1];
        int len;

        for (int i = 0; i < 2; i++) {
            fgets(str[i], LINELEN, stdin);
            len = strlen(str[i]);
            str[i][len] = '\n';

            if (write(fd1[WRITE], &str[i], sizeof(char) * len) < 0)
                sys_error("An error occurred with writing to pipe");
        }

        if (read(fd2[READ], res, sizeof(char) * MAX_RES) > 0)
            printf("%s", res);
        else
            sys_error("An error occurred with reading from pipe");

        close(fd1[WRITE]);
        close(fd2[READ]);
        exit(EXIT_SUCCESS);
    }
}
