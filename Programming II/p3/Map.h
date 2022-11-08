//Iván Álvarez García DNI: 49623492A
#include "Util.h"
#include <vector>
#include "Junk.h"
#include "Coordinate.h"
#ifndef _Map_H
#define _Map_H

using namespace std;

class Map{

    friend ostream &operator<<(ostream &os, const Map &map);

    protected:
        int rows, columns;
        vector<vector<Junk>> junks;
    
    public:
        Map(int rows, int columns);
        bool isInside(const Coordinate &coord) const;
        void putJunk(const Junk &junk, const Coordinate &coord);
        Junk getJunk(const Coordinate &coord) const;
        Junk collectJunk(const Coordinate &coord);
};
#endif