//Iván Álvarez García DNI: 49623492A
#include <iostream>
#include <vector>
#include <fstream>
#include "Map.h"

using namespace std;


Map::Map(int rows, int columns){
    this->rows = rows;
    this->columns = columns;
    
    if(rows<5 || columns<5){
        throw EXCEPTION_SIZE;
    }else{
        for(int i = 0; i < rows; i++){
            Junk map_junk;
            vector<Junk> map_junks;

            for(int j = 0; j < columns; j++){
               map_junks.push_back(map_junk); 
            }
            junks.push_back(map_junks);
        }
    }
}

bool Map::isInside(const Coordinate &coord)const{
    bool ok;
    if((coord.getRow() >= 0 && coord.getRow()<=rows) && (coord.getColumn() >= 0 && coord.getColumn() <= columns)){
        ok = true;
    }else{
        ok = false;
    }
    return ok;
}

void Map::putJunk(const Junk &junk, const Coordinate &coord){
    if(isInside(coord) == true){
        junks[coord.getRow()][coord.getColumn()] = junk;
    }else{
        throw EXCEPTION_OUTSIDE;
    }
}

Junk Map::getJunk(const Coordinate &coord)const{
    if(isInside(coord) == true){
        return junks[coord.getRow()][coord.getColumn()];
    }else{
        throw EXCEPTION_OUTSIDE;
    }
}

Junk Map::collectJunk(const Coordinate &coord){
    Junk wasteland, collected_resource;
    if(isInside(coord) == true){
        collected_resource = junks[coord.getRow()][coord.getColumn()];
        junks[coord.getRow()][coord.getColumn()] = wasteland;
    }else{
        throw EXCEPTION_OUTSIDE;
    }
    return collected_resource;
}

ostream& operator<<(ostream &os, const Map &map){
    for(int i = 0; i <= map.rows; i++){
        for(int j = 0; j <= map.columns; j++){
            if(i == 0){
                if(j<=10){
                    if(j == 0){
                        os<<"   ";
                    }else{
                        os<<0<<j-1<<" ";
                    }
                }else{
                    os<<j-1<<" ";
                }
            }else if(j == 0){
                if(i<=10){
                    os<<0<<i-1<<" ";
                }else{
                    os<<i-1<<"   ";
                }
            }else{
                if(map.junks[i-1][j-1].getTypeChar() == 'W'){
                    os<<"   ";
                }else{
                    os<<map.junks[i-1][j-1].getTypeChar()<<"  ";
                }
            }
        }
        os<<endl;
    }
    return os;
}