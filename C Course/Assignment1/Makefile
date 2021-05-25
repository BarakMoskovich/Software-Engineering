CG = gcc
OBJS = main.o exe.o pictureManipulation.o numberGame.o
EXEC = main
DIR = out
DEBUG_FLAG =*assign -g for debug
COMP_FLAG = -std=c99 -Wall -Werror

$(EXEC) : $(OBJS)
	$(CC) $(OBJS) -o $@

# gcc -MM *.c
exe.o: exe.c pictureManipulation.h numberGame.h exe.h
main.o: main.c exe.h
numberGame.o: numberGame.c numberGame.h
pictureManipulation.o: pictureManipulation.c pictureManipulation.h

clean:
	rm -f $(OBJS) $(EXEC)