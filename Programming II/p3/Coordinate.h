//Iván Álvarez García DNI: 49623492A
#include <iostream>
#include <fstream>
#ifndef _Coordinate_H
#define _Coordinate_H

using namespace std;

class Coordinate{
    
    friend ostream& operator<<(ostream &os, const Coordinate &coord);
    protected:
        int row, column;
    public:
        Coordinate();
        Coordinate(int row, int column);
        int getRow() const;
        int getColumn() const;
        void setRow(int row);
        void setColumn(int column);
        bool compare(const Coordinate& coord) const;
};
#endif