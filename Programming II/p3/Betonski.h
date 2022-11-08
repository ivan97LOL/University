//Iván Álvarez García DNI: 49623492A
#include <iostream>
#include <fstream>
#include "Map.h"
#include "Util.h"
#ifndef _Betonski_H
#define _Betonski_H

using namespace std;

class Betonski{

    friend ostream& operator<<(ostream& os, const Betonski &betonski);

    protected:
        string name;
        int anger;
        bool captured;
        vector<Junk> bag;
        Coordinate position;
    
    public:
        Betonski(string name);
        string getName() const;
        int getAnger() const;
        bool isCaptured() const;
        Coordinate getPosition() const;
        void capture() {captured = true;};
        void setPosition(const Coordinate& coord) {position.setRow(coord.getRow()); position.setColumn(coord.getColumn());};
        int calculateValue() const;
        int calculateValue(JunkType type) const;
        int spoliation();
        int spoliation(JunkType type);
        int extract(Map& map);
        bool move(const Map& map);
};
#endif