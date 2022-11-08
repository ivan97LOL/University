//  DNI 49623492A ÁLVAREZ GARCÍA, IVÁN
#include <fstream>
#include <sstream>

using namespace std;

#include "Betonski.h"
#include "Jankali.h"


void posBetonski(vector<Betonski *> betonskis,const Map &map,string name,int row,int column)
{
   int pos = -1;
   
   for (int i=0;i<(int)betonskis.size() && pos == -1;i++)
     if (betonskis[i]->getName() == name)
       pos = i;        
       
   if (pos != -1)
   {
     Coordinate position(row,column);
     
     if (map.isInside(position))
       betonskis[pos]->setPosition(position);
     else
       Util::error(ERR_OUTSIDE);
   }
   else     
     Util::error(ERR_NAME);
}

bool getJunkType(string type,JunkType &junkType)
{
  bool ok=true;
  
  if      (type == "WASTELAND") junkType = WASTELAND;
  else if (type == "GOLD") junkType = GOLD;
  else if (type == "METAL") junkType = METAL;
  else if (type == "FOOD") junkType = FOOD;
  else if (type == "STONE") junkType = STONE;
  else ok = false; 
  
  return ok;
}

void putJunk(Map &map,string type,int quantity,int row,int column)
{
  JunkType junkType;
  
  if (getJunkType(type,junkType))
  {
    try {
      Junk junk(junkType,quantity);
      Coordinate coord(row,column);
      
      map.putJunk(junk,coord);
    }
    catch (Exception e) {
      Util::error((Error)e);
    }
  }
  else
    Util::error(ERR_TYPE);
}

void setTrap(vector<Jankali *> jankalis,const Map &map,string name,int row,int column)
{
   int pos = -1;
   
   for (int i=0;i<(int)jankalis.size() && pos == -1;i++)
     if (jankalis[i]->getName() == name)
       pos = i;        
       
   if (pos != -1)
   {
     Coordinate position(row,column);
     
     if (map.isInside(position))
       jankalis[pos]->setTrap(position);
     else
       Util::error(ERR_OUTSIDE);
   }
   else     
     Util::error(ERR_NAME);
}


void replayGame(char *filename)
{
  ifstream f;
  
  f.open(filename,ios::in);
  if (f.is_open())
  {
    Map map(10,10);
    vector<Betonski *> betonskis;
    vector<Jankali *> jankalis;
    string line;
    
    while (getline(f,line))
    {
      stringstream streamLine;
      string instruction;
      
      // show Map, betonskis and jankalis
      cout << map;
      for (unsigned i=0;i<betonskis.size();i++) cout << *betonskis[i];
      for (unsigned i=0;i<jankalis.size();i++) cout << *jankalis[i];
      
      streamLine << line;  // meter la línea en stream para leer partes con '>>'
      streamLine >> instruction;
      cout << "Instruction: " << line << endl;
     
      if (instruction == "exit")
        break;
      else if (instruction == "newBetonski")
      {
        string name;
        
        streamLine >> name;
        try {
          Betonski *bet = new Betonski(name);
          
          betonskis.push_back(bet);
        }
        catch (Exception e) {
          Util::error((Error)e);
        }
      }
      else if (instruction == "newJankali")
      {
        string name;
        
        streamLine >> name;
        try {
          Jankali *jan = new Jankali(name);
          
          jankalis.push_back(jan);
        }
        catch (Exception e) {
          Util::error((Error)e);
        }
      }
      else if (instruction == "posBetonski")
      {
        string name;
        int row,column;
        
        streamLine >> name >> row >> column;
        posBetonski(betonskis,map,name,row,column);
      }
      else if (instruction == "putJunk")
      {
        string type;
        int quantity,row,column;
        
        streamLine >> type >> quantity >> row >> column;
        putJunk(map,type,quantity,row,column);
      }
      else if (instruction == "setTrap")
      {
        string name;
        int row,column;
        
        streamLine >> name >> row >> column;
        setTrap(jankalis,map,name,row,column);
      }
      else if (instruction == "play")
      {
        for (unsigned i=0;i<betonskis.size();i++)
          try {
            betonskis[i]->move(map);
          } catch (Exception e) {
            Util::error((Error)e);
          }
          
        for (unsigned i=0;i<betonskis.size();i++)
            betonskis[i]->extract(map);
          
        for (unsigned i=0;i<jankalis.size();i++)
            jankalis[i]->hunt(betonskis);
          
        JunkType junkType = Util::getRandomType();
        for (unsigned i=0;i<jankalis.size();i++)
            if (junkType == WASTELAND)
              jankalis[i]->spoil();
            else
              jankalis[i]->spoil(junkType);
      }
      else
        cout << "ERROR: wrong instruction \"" << instruction << "\"" << endl;
    }
    
    f.close();
    
    // free mem
    for (unsigned i=0;i<betonskis.size();i++)
       delete betonskis[i];
    betonskis.clear();
        
    for (unsigned i=0;i<jankalis.size();i++)
       delete jankalis[i];
    jankalis.clear();
  }
  else
    cout << "ERROR: can't open file \"" << filename << "\"" << endl;
}

int main(int argc,char *argv[])
{
  srand(1);
  if (argc == 2)
    replayGame(argv[1]);
  else
    cout << "ERROR: wrong arguments" << endl;
}
