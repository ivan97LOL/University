//Iván Álvarez García DNI: 49623492A
#include "Util.h"

void Util::error(Error e){
  switch(e){
    case ERR_QUANTITY:
      cout << "ERROR: quantity must be greater than 0" << endl;
      break;
    case ERR_SIZE:
      cout << "ERROR: map size must be at least 5x5" << endl;
      break;
    case ERR_OUTSIDE:
      cout << "ERROR: position is outside the map" << endl;
      break;
    case ERR_NAME:
      cout << "ERROR: wrong name" << endl;
      break;
    case ERR_TYPE:
      cout << "ERROR: wrong junk type" << endl;
      break;
  }
}

int Util::getRandomNumber(int max)
{
  return rand()%max;
}

JunkType Util::getRandomType()
{
  int randType = getRandomNumber(5);
  
  switch (randType) {
    case 0: return WASTELAND;
    case 1: return GOLD;
    case 2: return METAL;
    case 3: return FOOD;
    case 4: return STONE;
    default: return WASTELAND;  // just in case ...
  }
}
