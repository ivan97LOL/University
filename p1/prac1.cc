// DNI 49623492A ÁLVAREZ GARCÍA, IVÁN
#include <iostream>
#include <cstdlib> // Para rand(), srand() y atoi()
#include <cctype> //para isalnum()
#include <string.h>

using namespace std;

const int KNAME=32;
const int KENEMIES=5;
const int KPOINTS=200;
const int KDICE=20; // Número de caras del dado

struct Core{
  int attack;
  int defense;
  int hp;
};

enum Breed{
  AXOLOTL,
  TROLL,
  ORC,
  HELLHOUND,
  DRAGON
};

struct Enemy{
  Breed name;
  Core features;
};

struct Hero{
  char name[KNAME];
  Core features;
  bool special;
  int runaways;
  int exp;
  int kills[KENEMIES];
};

string ask(Hero&);
void distribution(Hero&, int);
Hero createHero(Hero&);
void printEnemy(Enemy&);
Enemy createEnemy(Enemy&);
void report(const Hero&);
void fight(Hero &,Enemy &, bool&, bool&);
void runaway(Hero&, bool&, Enemy&, bool&);
void special(Hero &, Enemy &, bool&);
void options(char, Hero &, Enemy &);
void showMenu(Hero &, Enemy &, bool&, bool&);

int rollDice(){
  return rand()%KDICE+1;
}

string ask(Hero& hero){ //función que pregunta y verifica el nombre

   bool ok = true;
  do{

    if(!ok){
      ok = true;
    }

    cout<< "Enter hero name: ";
    cin.getline(hero.name, KNAME, '\n');

    for(int i= 0; i<(int)strlen(hero.name);i++){ //verifica que los caracteres son correctos

      if (!isspace(hero.name[i]) && !isalnum(hero.name[i])){
        ok = false;
      }
      else if(isdigit(hero.name[0]) != 0 || !isalpha(hero.name[0])){
        ok= false;
      }  
    }

    if (ok == false){
      cout<< "ERROR: wrong name"<<endl;
    }
    
  }while(ok == false);

  return hero.name;
}

void distribution(Hero& hero, int points){ //distribución de ptos de combate hecha por el usuario

  int n1 = 0, n2 = 0;
  char c;
  do{
    cout<<"Enter attack/defense: ";
    cin>> n1 >> c >> n2;

    if(n1+n2 != 100 || c != '/' || n1<0 || n2<0){
      cout<< "ERROR: wrong distribution"<<endl;
    }
  
  }while(n1+n2 != 100 || c != '/' || n1<0 || n2<0);

  hero.features.attack = n1*points/100;
  hero.features.defense = n2*points/100;
  hero.features.hp= hero.features.defense*2;
}
Hero createHero( Hero& hero){

  ask(hero);
  distribution(hero, KPOINTS);

  hero.special= true;
  hero.exp= 0;
  hero.runaways= 3;
  hero.kills[0] = 0;
  hero.kills[1] = 0;
  hero.kills[2] = 0;
  hero.kills[3] = 0;
  hero.kills[4] = 0;

  return hero;
}

void printEnemy(Enemy& enemy){

  string name;
  switch(enemy.name){

    case 0: name= "Axolotl";
            break;
    case 1: name= "Troll";
            break;
    case 2: name= "Orc";
            break;
    case 3: name= "Hellhound";
            break;
    case 4: name= "Dragon";
            break;
    default: cout <<"ERROR"<<endl;
  }
  cout<<"[Enemy]"<<endl;
  cout<<"Breed: "<<name<<endl;
  cout<<"Attack: "<<enemy.features.attack<<endl;
  cout<<"Defense: "<<enemy.features.defense<<endl;
  cout<<"Health points: "<<enemy.features.hp<<endl;
}

Enemy createEnemy(Enemy& enemy){

  int num= rollDice();
  //asignamos los valores correspondientes a las stats de enemy
  if(num<=6){

    enemy.name= AXOLOTL;
    enemy.features.attack= 40;
    enemy.features.defense= 40;
    enemy.features.hp= enemy.features.defense*2;
  }
  else if(num>6 && num<=11){

    enemy.name= TROLL;
    enemy.features.attack= 60;
    enemy.features.defense= 80;
    enemy.features.hp= enemy.features.defense*2;
    
  }
  else if(num>11 && num <=15){

    enemy.name= ORC;
    enemy.features.attack= 80;
    enemy.features.defense= 120;
    enemy.features.hp= enemy.features.defense*2;
  }
  else if(num>15 && num <=18){

    enemy.name= HELLHOUND;
    enemy.features.attack= 120;
    enemy.features.defense= 100;
    enemy.features.hp= enemy.features.defense*2;
  }
  else{

    enemy.name= DRAGON;
    enemy.features.attack= 160;
    enemy.features.defense= 140;
    enemy.features.hp= enemy.features.defense*2;

  }
  printEnemy(enemy);
  return enemy;
}

void report(const Hero &hero){

  cout<<"[Report]"<<endl;
  cout<<"Name: "<<hero.name<<endl;
  cout<< "Attack: "<<hero.features.attack<<endl;
  cout<< "Defense: "<<hero.features.defense<<endl;
  cout<< "Health points: "<<hero.features.hp<<endl;
  cout<< "Special: ";
  if(hero.special == true){
    cout<< "yes"<<endl;
  }
  else{
    cout<< "no"<<endl;
  }
  cout<< "Runaways: "<<hero.runaways<<endl;
  cout<<"Exp: "<<hero.exp<<endl;
  cout<< "Enemies killed:"<<endl;
  cout<< "- Axolotl: "<< hero.kills[AXOLOTL]<<endl;
  cout<< "- Troll: "<< hero.kills[TROLL]<<endl;
  cout<< "- Orc: "<< hero.kills[ORC]<<endl;
  cout<< "- Hellhound: "<< hero.kills[HELLHOUND]<<endl;
  cout<< "- Dragon: "<< hero.kills[DRAGON]<<endl;
  cout<< "- Total: "<< hero.kills[AXOLOTL]+ hero.kills[TROLL]+hero.kills[ORC]+hero.kills[HELLHOUND]+hero.kills[DRAGON]<<endl; //suma de kills
}

void fight(Hero &hero,Enemy &enemy, bool &two_scapes, bool &special_attack){
  //ataca el héroe y se defiende el enemigo
  int mult_attack, mult_defense, attack, defense, hit_points, xp;

  mult_attack= rollDice();
  mult_defense= rollDice();

  attack= mult_attack*5+hero.features.attack;
  defense= mult_defense*5+enemy.features.defense;

  if(special_attack== true){
    attack=mult_attack*5*3+hero.features.attack;
  }

  hit_points= attack-defense;
  if(hit_points>0){
    enemy.features.hp= enemy.features.hp-hit_points;
  }
  else{
    hit_points=0;
  }

  if(enemy.features.hp<0){
    enemy.features.hp=0;  //vida del enemigo solo positiva
  }
  cout<<"[Hero -> Enemy]"<<endl;

  if(special_attack== true){
    cout<<"Attack: "<< hero.features.attack<< " + " <<  mult_attack*5*3<<endl;
    special_attack=false; //volvemos a poner la variable en su estado inicial
  }else{
    cout<<"Attack: "<< hero.features.attack<< " + " <<  mult_attack*5<<endl;
  }

  cout<<"Defense: "<< enemy.features.defense<< " + " <<  mult_defense*5<<endl;
  cout<<"Hit points: "<<hit_points<<endl;
  cout<<"Enemy health points: "<<enemy.features.hp<<endl;

  // Enemigo muere y repartición de experiencia
  if(enemy.features.hp<= 0){
    cout<<"Enemy killed"<<endl; 
      two_scapes= false;
    switch(enemy.name){
      case 0: xp= 100;
              hero.kills[AXOLOTL]++;
              break;
      case 1: xp= 150;
              hero.kills[TROLL]++;
              break;
      case 2: xp= 200;
              hero.kills[ORC]++;
              break;
      case 3: xp= 300;
              hero.kills[HELLHOUND]++;
              break;
      case 4: xp= 400;
              hero.kills[DRAGON]++;
              break;
    }
    hero.exp= hero.exp+xp;
    createEnemy(enemy);
    showMenu(hero,enemy,two_scapes,special_attack);
  }
  else{
    //ataca el enemigo y se defiende el héroe
    mult_attack= rollDice();
    mult_defense= rollDice();

    attack= mult_attack*5+enemy.features.attack;
    defense= mult_defense*5+hero.features.defense;
    hit_points= attack-defense;
    if(hit_points>0){
      hero.features.hp= hero.features.hp-hit_points;
    }
    else{
      hit_points=0;
    }

    if(hero.features.hp<0){
      hero.features.hp=0; //vida del héroe solo positiva
    }
    cout<<"[Enemy -> Hero]"<<endl;
    cout<<"Attack: "<< enemy.features.attack<< " + " <<  mult_attack*5<<endl;
    cout<<"Defense: "<< hero.features.defense<< " + " <<  mult_defense*5<<endl;
    cout<<"Hit points: "<<hit_points<<endl;
    cout<<"Hero health points: "<<hero.features.hp<<endl;
    
    if(hero.features.hp>0){
      showMenu(hero,enemy,two_scapes,special_attack);
    }
    else{
      cout<<"You are dead"<<endl; //héroe muere
      report(hero);
    }
  }
}

void runaway(Hero &hero, bool &two_scapes, Enemy& enemy, bool &special_attack){
  
  if(hero.runaways != 0 && two_scapes != true){ 
    cout<< "You run away"<<endl;
    hero.runaways--;
    two_scapes= true;
    createEnemy(enemy);
  }
  else{
    cout<<"ERROR: cannot run away"<<endl;
  }
  showMenu(hero,enemy,two_scapes,special_attack);
}

void special(Hero &hero, Enemy &enemy, bool &two_scapes, bool &special_attack){

  if(hero.special== true){
    special_attack= true;
    hero.special=false;
    fight(hero,enemy,two_scapes,special_attack);
  }
  else{
    cout<<"ERROR: special not available"<<endl;
    showMenu(hero,enemy,two_scapes,special_attack);
  }
  
}

void options(char c, Hero &hero, Enemy &enemy, bool &two_scapes, bool &special_attack){
  switch(c){
    case '1': fight(hero,enemy,two_scapes,special_attack);
            break;
    case '2': runaway(hero,two_scapes,enemy,special_attack);
            break;
    case '3': special(hero,enemy,two_scapes,special_attack);
            break;
    case '4': 
      report(hero);
      if(hero.features.hp>0){
        showMenu(hero,enemy,two_scapes,special_attack);
      }
      break;
  }
}

void showMenu(Hero &hero, Enemy &enemy, bool &two_scapes, bool &special_attack){

  char input;

  do{
    cout << "[Options]" << endl
        << "1- Fight" << endl
        << "2- Run away" << endl
        << "3- Special" << endl 
        << "4- Report" << endl
        << "q- Quit" << endl
        << "Option: ";
    cin>> input;

    if(input != '1' && input!= '2' && input!= '3' && input!= '4' && input!= 'q'){
      cout<<"ERROR: wrong option"<<endl;
      cout<<endl;
    }
  }while(input != '1' && input!= '2' && input!= '3' && input!= '4' && input!= 'q');

  options(input, hero, enemy, two_scapes,special_attack);
}

int main(int argc,char *argv[]){
  if(argc!=2){ // Si los parámetros no son correctos, el programa termina inmediatamente
    cout << "Usage: " << argv[0] << " <seed>" << endl;
  }
  else{
    srand(atoi(argv[1])); // Introducimos la semilla para generar números aleatorios
    
    Hero hero;
    Enemy enemy;
    bool two_scapes = false; //variable que controla que no huya 2 veces seguidas el héroe
    bool special_attack= false; //ataque especial para el nuevo daño
    createHero(hero);

    createEnemy(enemy);
    showMenu(hero,enemy, two_scapes,special_attack);

  }
  return 0;
}
