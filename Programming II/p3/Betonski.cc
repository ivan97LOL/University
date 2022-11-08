#include "Betonski.h"
#include <iostream>
#include <fstream>
#include <string>
#include <cstring>

using namespace std;

Betonski::Betonski(string name){
    captured = false;
    anger = 0;
    position.setRow(-1);
    position.setColumn(-1);
    if(name.empty()){
        throw EXCEPTION_NAME;
    }else{
        this->name = name;
    }
}

string Betonski::getName() const{
    return name;
}
        
int Betonski::getAnger() const{
    return anger;
}
        
bool Betonski::isCaptured() const{
    return captured;
}
 
Coordinate Betonski::getPosition() const{
    return position;
}

int Betonski::calculateValue()const{
    int value = 0;

    for(unsigned int i = 0; i<bag.size(); i++){
        value += bag[i].getValue();
    }
    return value;
}

int Betonski::calculateValue(JunkType type)const{
    int value = 0;

    for(unsigned int i = 0; i<bag.size(); i++){
        if(bag[i].getType() == type){
            value += bag[i].getValue();
        }
    }
    return value;
}

int Betonski::spoliation(){
    int value_expoliated;
    if(captured == false){
        throw BETONSKI_NOT_CAPTURED;
    }else{
        value_expoliated = calculateValue();
        anger += value_expoliated;
        if(anger > 5000){
            anger = 0;
            captured = false;
            throw EXCEPTION_REBELION;
        }else{
            bag.clear();
        }
    }
    return value_expoliated;
}

int Betonski::spoliation(JunkType type){
    int value_expoliated;
    vector<Junk> new_bag;
    if(captured == false){
        throw BETONSKI_NOT_CAPTURED;
    }else{
        value_expoliated = calculateValue(type);
        anger += value_expoliated;
        if(anger > 5000){
            anger = 0;
            captured = false;
            throw EXCEPTION_REBELION;
        }else{
            for(unsigned int i = 0; i<bag.size(); i++){
                if(bag[i].getType() != type){
                    new_bag.push_back(bag[i]);
                }
            }
            bag.clear();
            bag = new_bag;
        }
    }
    return value_expoliated;
}

int Betonski::extract(Map& map){
    int value;
    Junk junk;
    char c;
    if(map.isInside(position) == true){
        junk = map.collectJunk(position);
        c = junk.getTypeChar();
        if(c != 'W'){
            bag.push_back(junk);
            value = junk.getValue();
        }
    }
    return value;
}

bool Betonski::move(const Map &map){
    bool ok;
    if(map.isInside(position) == true){
       int num = Util::getRandomNumber(8);
        Coordinate new_position;
        switch (num)
        {
        case 0:
            new_position.setRow(position.getRow()-1);
            new_position.setColumn(position.getColumn());
            break;
        case 1:
            new_position.setRow(position.getRow()-1);
            new_position.setColumn(position.getColumn()+1);
            break;
        case 2:
            new_position.setRow(position.getRow());
            new_position.setColumn(position.getColumn()+1);
            break;
        case 3:
            new_position.setRow(position.getRow()+1);
            new_position.setColumn(position.getColumn()+1);
            break;
        case 4:
            new_position.setRow(position.getRow()+1);
            new_position.setColumn(position.getColumn());
            break;
        case 5:
            new_position.setRow(position.getRow()+1);
            new_position.setColumn(position.getColumn()-1);
            break;
        case 6:
            new_position.setRow(position.getRow());
            new_position.setColumn(position.getColumn()-1);
            break;
        case 7:
            new_position.setRow(position.getRow()-1);
            new_position.setColumn(position.getColumn()-1);
            break;
        default:
            break;
        }
        if(map.isInside(new_position) == true){
                 position = new_position;
                 ok = true;
             }else{
                 ok = false;
             }
    }else{
        throw EXCEPTION_OUTSIDE;
    }
    return ok;
}

ostream& operator<<(ostream& os, const Betonski &betonski){
    string status;
    if(betonski.isCaptured() == true){
        status = "Captured";
    }else{
        status = "Free";
    }
    os<<"Betonski "<<'\u0022'<<betonski.getName()<<'\u0022'<<" "<<status<<
    " "<<betonski.getAnger()<<" "<< betonski.position<<endl;
    os<<endl;
    
    for(unsigned int i = 0; i<betonski.bag.size(); i++){
        string resource;
        switch(betonski.bag[i].getType()){
            case GOLD: resource = "GOLD";
                break;
            case METAL: resource = "METAL";
                break;
            case FOOD: resource = "FOOD";
                break;
            case STONE: resource = "STONE";
                break;
            default:   resource = " ";
                break;
        }
        if(resource == " "){
            os<<endl;
        }
        else if(i == betonski.bag.size()-1){
            os<<"["<<resource<<":"<<betonski.bag[i].getQuantity()<<"]"<<endl;
        }
        else{
            os<<"["<<resource<<":"<<betonski.bag[i].getQuantity()<<"]";
        }
    }
    return os;
}