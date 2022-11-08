//Iván Álvarez García DNI: 49623492A
#include "Junk.h"
#include <cstring>

using namespace std;

Junk::Junk(){
    type = WASTELAND;
    quantity = 0;
}

Junk::Junk(JunkType type, int quantity){
    this->type = type;
    this->quantity = quantity;
    
    if(quantity < 0){
        throw EXCEPTION_QUANTITY;
    }
}

JunkType Junk::getType() const{
    return type;
}

int Junk::getQuantity() const{
    return quantity;
}

char Junk::getTypeChar()const{
    char letter;
    switch(type){
        case WASTELAND: letter = 'W';
            break;
        case GOLD: letter = 'G';
            break;
        case METAL: letter = 'M';
            break;
        case FOOD: letter = 'F';
            break;
        case STONE: letter = 'S';
            break;
    }
    return letter;
}

int Junk::getValue()const{
    int value;
    switch(type){
        case WASTELAND: value = 0;
            break;
        case GOLD: value = 500*quantity;
            break;
        case METAL: value = 100*quantity;
            break;
        case FOOD: value = 50*quantity;
            break;
        case STONE: value = 20*quantity;
            break;
    }
    return value;
}

ostream& operator<<(ostream &os, const Junk &junk){
    string resource;
    switch(junk.getType()){
        case WASTELAND: resource = "WASTELAND";
            break;
        case GOLD: resource = "GOLD";
            break;
        case METAL: resource = "METAL";
            break;
        case FOOD: resource = "FOOD";
            break;
        case STONE: resource = "STONE";
            break;
    }
    os<<"["<<resource<<":"<<junk.getQuantity()<<"]";
    return os;
}