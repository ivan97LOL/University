OPTS=-Wall -g -std=c++11
OBJS=prac3.o Jankali.o Betonski.o Map.o Junk.o Coordinate.o Util.o
CC=g++

prac3: $(OBJS)
	$(CC) $(OPTS) $(OBJS) -o prac3

prac3.o: prac3.cc Jankali.h Betonski.h Map.h Junk.h Coordinate.h Util.h
	$(CC) $(OPTS) -c prac3.cc

Map.o: Map.cc Map.h Junk.h Coordinate.h Util.h
	$(CC) $(OPTS) -c Map.cc

Jankali.o: Jankali.cc Jankali.h Betonski.h Map.h Junk.h Coordinate.h Util.h
	$(CC) $(OPTS) -c Jankali.cc

Betonski.o: Betonski.cc Betonski.h Map.h Junk.h Coordinate.h Util.h
	$(CC) $(OPTS) -c Betonski.cc

Junk.o: Junk.cc Junk.h Util.h
	$(CC) $(OPTS) -c Junk.cc

Util.o: Util.cc Util.h
	$(CC) $(OPTS) -c Util.cc

Coordinate.o: Coordinate.cc Coordinate.h
	$(CC) $(OPTS) -c Coordinate.cc

clean:
	rm $(OBJS)
