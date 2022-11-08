//Iván Álvarez García DNI: 49623492A
#include "Jankali.h"

using namespace std;

Jankali::Jankali(string name){
    power = 300;
    if(name.empty()){
        throw EXCEPTION_NAME;
    }else{
        this->name = name;
    }
}

string Jankali::getName()const{
    return name;
}
 
int Jankali::getPower()const{
    return power;
}

void Jankali::hunt(vector<Betonski *> betonskis){
    for(unsigned int i=0; i<betonskis.size(); i++){
        for(unsigned int j=0; j<traps.size(); j++){
            Betonski *betonski = betonskis[i];

            if(betonski->getPosition().getRow() == traps[j].getRow() &&
            betonski->getPosition().getColumn() == traps[j].getColumn() &&
            betonski->isCaptured() == false){
                
                subdued.push_back(betonski);
                betonski->capture();
            }
        }
    }
}

bool Jankali::setTrap(const Coordinate &coord){
    int trap_cost = (coord.getRow()+2)*(coord.getColumn()+2);

    for(unsigned int i=0; i<traps.size(); i++){
        if(coord.compare(traps[i])){
            return false;
        }
    }
    if(power >= trap_cost){
        traps.push_back(coord);
        power = power - trap_cost;
        return true;
    }
    return false;
}

void Jankali::spoil(){
    for(unsigned int i=0; i<subdued.size(); i++){
        try{
            power += subdued[i]->spoliation();
        }
        catch(Exception e){
            subdued.erase(subdued.begin()+i);
        }
    }
}

void Jankali::spoil(JunkType type){
    for(unsigned int i=0; i<subdued.size(); i++){
        try{
            power += subdued[i]->spoliation(type);
        }
        catch(Exception e){
            subdued.erase(subdued.begin()+i);
        }
    }
}

void Jankali::spoil(int pos){
    if((int)subdued.size()>=pos){
        try{
            power += subdued[pos]->spoliation();
        }
        catch(Exception e){
            subdued.erase(subdued.begin()+pos);
        }
    }
}

ostream& operator<<(ostream& os,const Jankali &jankali){
    os<<"Jankali "<<'\u0022'<<jankali.getName()<<'\u0022'<<" "<<jankali.getPower()<<endl;
    for(unsigned int i=0; i<jankali.subdued.size(); i++){
        os<<*jankali.subdued[i];
    }
    os<<"Traps"<<endl;
    for(unsigned int i=0; i<jankali.traps.size(); i++){
        os<<jankali.traps[i]<<endl;
    }
    return os;
}