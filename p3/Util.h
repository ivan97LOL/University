//Iván Álvarez García DNI: 49623492A
#ifndef _UTIL_H_
#define _UTIL_H_

#include <iostream>

using namespace std;


enum Error{
  ERR_QUANTITY,
  ERR_SIZE,
  ERR_OUTSIDE,
  ERR_NAME,
  ERR_TYPE
};

enum Exception {
  EXCEPTION_QUANTITY   = ERR_QUANTITY,
  EXCEPTION_SIZE       = ERR_SIZE,
  EXCEPTION_OUTSIDE    = ERR_OUTSIDE,
  EXCEPTION_NAME       = ERR_NAME,
  EXCEPTION_REBELION
};

enum InternalException {
  BETONSKI_NOT_CAPTURED
};


enum JunkType {
  WASTELAND,
  GOLD,
  METAL,
  FOOD,
  STONE
};

class Util{
  public:
    // prints an error message
    static void error(Error e);
    
    // get a random number entre 0 y max-1
    static int getRandomNumber(int max);
    
    static JunkType getRandomType();
};

#endif
