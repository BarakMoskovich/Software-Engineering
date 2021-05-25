
# 'make', 'make all'

CC = gcc
CFLAGS  = -Wall

all:  verA verB verC
# all:  argsxorstr xorstr verA verB verC

# ###### Header #######
mystrlib.o:  mystrlib.c mystrlib.h
	$(CC) $(CFLAGS) -c mystrlib.c

# ###### verA #######
verA:  verA.o mystrlib.o
	$(CC) $(CFLAGS) -o verA verA.o mystrlib.o

verA.o:  verA.c mystrlib.h
	$(CC) $(CFLAGS) -c verA.c

# ###### verB #######
verB:  verB.o mystrlib.o
	$(CC) $(CFLAGS) -o verB verB.o mystrlib.o

verB.o:  verB.c mystrlib.h
	$(CC) $(CFLAGS) -c verB.c

# ###### verC #######
verC:  verC.o mystrlib.o
	$(CC) $(CFLAGS) -o verC verC.o mystrlib.o

verC.o:  verC.c mystrlib.h
	$(CC) $(CFLAGS) -c verC.c

# all: argsxorstr xorstr verC verB verA

# ###### argsxorstr #######
# argsxorstr:  argsxorstr.o mystrlib.o
# 	 $(CC) $(CFLAGS) -o argsxorstr argsxorstr.o mystrlib.o

# argsxorstr.o:  argsxorstr.c mystrlib.h
#	 $(CC) $(CFLAGS) -c argsxorstr.c

# ###### xorstr #######
# xorstr:  xorstr.o mystrlib.o
#	 $(CC) $(CFLAGS) -o xorstr xorstr.o mystrlib.o

# xorstr.o:  xorstr.c mystrlib.h
#	 $(CC) $(CFLAGS) -c xorstr.c


clean:
	rm -f str verC verB verA *.o