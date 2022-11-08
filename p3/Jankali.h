//Iván Álvarez García DNI: 49623492A
#include <iostream>
#include <fstream>
#include "Betonski.h"
#ifndef _Jankali_H
#define _Jankali_H


using namespace std;

class Jankali{
    friend ostream& operator<<(ostream& os,const Jankali &jankali);
    protected:
        string name;
        int power;
        vector<Betonski *> subdued;
        vector<Coordinate> traps;
    
    public:
        Jankali(string name);
        string getName()const;
        int getPower()const;
        void hunt(vector<Betonski *> betonskis);
        bool setTrap(const Coordinate& coord);
        void spoil();
        void spoil(JunkType type);
        void spoil(int pos);
};
#endif