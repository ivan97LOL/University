//Iván Álvarez García DNI: 49623492A
#include "Coordinate.h"
#include <iostream>
#include <fstream>

using namespace std;

Coordinate::Coordinate(){
    row = -1;
    column = -1;
}
Coordinate::Coordinate(int row, int column){
    this->row = row;
    this->column = column;
}
int Coordinate::getRow() const{
    return row;
}

int Coordinate::getColumn() const{
    return column;
}
       
void Coordinate::setRow(int row){
    this->row = row;
}
        
void Coordinate::setColumn(int column){
    this->column = column;
}

bool Coordinate::compare(const Coordinate& coord)const{
    bool ok;
    if(row == coord.row && column == coord.column){
        ok = true;
    }else{
        ok = false;
    }
    return ok;
}
ostream& operator<<(ostream &os, const Coordinate &coord){
    os<< "["<<coord.row<<","<<coord.column<<"]";
    return os;
}