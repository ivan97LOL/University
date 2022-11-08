//Iván Álvarez García DNI: 49623492A
#include <iostream>
#include <fstream>
#include "Util.h"
#ifndef _Junk_H
#define _Junk_H


using namespace std;

class Junk{
    friend ostream& operator<<(ostream &os, const Junk &junk);
    protected:
        JunkType type;
        int quantity;

    public:
        Junk();
        Junk(JunkType type, int quantity);
        JunkType getType() const;
        int getQuantity() const;
        char getTypeChar() const;
        int getValue() const;
    
};
#endif