// DNI 49623492 Álvarez García, Iván 

#include <iostream>
#include <vector>
#include <cctype>
#include <cstring>
#include <fstream>

using namespace std;

const int KMAXSTRING = 50;

enum Error {
  ERR_OPTION,
  ERR_BOOK_TITLE,
  ERR_BOOK_AUTHORS,
  ERR_BOOK_DATE,
  ERR_BOOK_PRICE,
  ERR_ID,
  ERR_FILE,
  ERR_ARGS
};

enum Message{
  M_BOOK_TITLE,
  M_BOOK_AUTHORS,
  M_YEAR,
  M_PRICE,
  M_ID,
  M_FILENAME,
  M_DATAERASED
};

struct Book {
  unsigned int id;
  string title;
  string authors;
  int year;
  string slug;
  float price;
};

struct BinBook {
  unsigned int id;
  char title[KMAXSTRING];
  char authors[KMAXSTRING];
  int year;
  char slug[KMAXSTRING];
  float price;
};

struct BookStore {
  string name;
  vector<Book> books;
  unsigned int nextId;
};

struct BinBookStore {
  char name[KMAXSTRING];
  unsigned int nextId;
};

void error(Error e) {
  switch (e) {
    case ERR_OPTION:
      cout << "ERROR: wrong menu option" << endl;
      break;
    case ERR_BOOK_TITLE:
      cout << "ERROR: wrong book title" << endl;
      break;
    case ERR_BOOK_AUTHORS:
      cout << "ERROR: wrong author(s)" << endl;
      break;
    case ERR_BOOK_DATE:
      cout << "ERROR: wrong date" << endl;
      break;
    case ERR_BOOK_PRICE:
      cout << "ERROR: wrong price" << endl;
      break;
    case ERR_ID:
      cout << "ERROR: wrong book id" << endl;
      break;
    case ERR_FILE:
      cout << "ERROR: cannot open file" << endl;
      break;
    case ERR_ARGS:
      cout << "ERROR: wrong arguments" << endl;
      break;
  }
}

void message(Message m){
  switch (m) {
    case M_BOOK_TITLE:
      cout<< "Enter book title: ";
      break;
    case M_BOOK_AUTHORS:
      cout<< "Enter author (s): ";
      break;
    case M_YEAR:
      cout<<"Enter publication year: ";
      break;
    case M_PRICE:
      cout<<"Enter price: ";
      break;
    case M_ID:
      cout<<"Enter book id: ";
      break;
    case M_FILENAME:
      cout<<"Enter filename: ";
      break;
    case M_DATAERASED:
      cout<<"All data will be erased, do you want to continue (Y/N)?: ";
      break;
  }
}

void showMainMenu() {
  cout << "[Options]" << endl
       << "1- Show catalog" << endl
       << "2- Show extended catalog" << endl
       << "3- Add book" << endl
       << "4- Delete book" << endl
       << "5- Import/export" << endl
       << "q- Quit" << endl
       << "Option: ";
}

void showImportExportMenu() {
  cout << "[Import/export options]" << endl
       << "1- Import from CSV" << endl
       << "2- Export to CSV" << endl
       << "3- Load data" << endl
       << "4- Save data" << endl
       << "b- Back to main menu" << endl
       << "Option: ";
}

void showCatalog(const BookStore &bookStore){
  
  for (unsigned int i=0;i<bookStore.books.size();i++){ //recorre toda la array de books
    if(bookStore.books[i].id != 0){
      cout<<bookStore.books[i].id<<". "<<bookStore.books[i].title<<" ("<<bookStore.books[i].year<<"), "<<bookStore.books[i].price<<endl;
    }
  }
}

void showExtendedCatalog(const BookStore &bookStore){

  for (unsigned int i=0;i<bookStore.books.size();i++){ //recorre toda la array de books
    cout<<'\u0022'<<bookStore.books[i].title<<'\u0022'<<',';
    cout<<'\u0022'<<bookStore.books[i].authors<<'\u0022'<<',';
    cout<<bookStore.books[i].year<<',';
    cout<<'\u0022'<<bookStore.books[i].slug<<'\u0022'<<',';
    cout<<bookStore.books[i].price<<endl;
  }
}

string askTitle(BookStore &bookStore, string &title){ //función que pregunta el título

  bool ok = true;
  do{

    if(!ok){
      ok = true;
    }

    message(M_BOOK_TITLE);
    getline(cin,bookStore.books[bookStore.books.size()-1].title);

    title = bookStore.books[bookStore.books.size()-1].title;

    if(title.empty() == true){
        ok = false;
    }

    if (ok == false){
      error(ERR_BOOK_TITLE);
    }

  }while(ok == false);

  return title;
}

string validationTitle(BookStore &bookStore, string title, bool isFromFile){ //función verifica el título

  bool ok = true;
  do{

    for(unsigned int i = 0; i<title.length();i++){ //verifica que los caracteres son correctos

      if (isspace(title[i]) || isalnum(title[i]) || title[i] == ',' || 
          title[i] == ':' || title[i] == '-'){
        ok = true;
      }
      else{
        ok = false;
        error(ERR_BOOK_TITLE);
        if(isFromFile == false){
          askTitle(bookStore, title);
        }      
      }
    }
  }while(ok == false);

  return title;
}

string askAuthor(BookStore &bookStore, string &author){ //función que pregunta el autor

  bool ok = true;
  do{

    if(!ok){
      ok = true;
    }

    message(M_BOOK_AUTHORS);
    getline(cin, bookStore.books[bookStore.books.size()-1].authors);
    author = bookStore.books[bookStore.books.size()-1].authors;
    if(author.empty() == true){
        ok = false;
    }
    
    if (ok == false){
      error(ERR_BOOK_AUTHORS);
    }
  }while(ok == false);

  return author;
}

string validationAuthor(BookStore &bookStore, string author, bool isFromFile){ //función que verifica el autor

   bool ok = true;
  do{
    for(unsigned int i = 0; i<author.length();i++){ //verifica que los caracteres son correctos

      if (isspace(author[i]) || isalnum(author[i]) || author[i] == ',' || author[i] == ':' || 
          author[i] == '-'){
        ok = true;
      }
      else{
        ok= false;
        error(ERR_BOOK_AUTHORS);

        if(isFromFile == false){
          askAuthor(bookStore,author);
        }  
        
      }
    }
  }while(ok == false);

  return author;
}

string askYear(BookStore &bookStore, string &year){ //función que pregunta el año
  bool ok = true;
  do{

    if(!ok){
      ok = true;
    }

    message(M_YEAR);
    getline(cin,year,'\n');

    if(year.empty() == true){
      ok = false;
    }
    
    if(ok == false){
      error(ERR_BOOK_DATE);
    }

  }while(ok == false);

  return year;
}

int validationYear(BookStore &bookStore, string year, bool isFromFile){ //función que valida el año
  bool ok = false;
  do{  
      bookStore.books[bookStore.books.size()-1].year = stoi(year);
      ok = true;
      if(bookStore.books[bookStore.books.size()-1].year<1440 || bookStore.books[bookStore.books.size()-1].year>2022){
        ok = false;
        error(ERR_BOOK_DATE);
        if(isFromFile == false){
          askYear(bookStore,year);
        }   
      }
  }while(ok == false);

  return bookStore.books[bookStore.books.size()-1].year;
}

string askPrice(BookStore &bookStore, string &price){ //función que pregunta el precio
  bool ok = true;
  do{

    if(!ok){
      ok = true;
    }
    message(M_PRICE);
    getline(cin,price,'\n');

    if(price.empty() == true){
      ok = false;
    }
    
    if(ok == false){
      error(ERR_BOOK_PRICE);
    }
  }while(ok == false);

  return price;
}

float validationPrice(BookStore &bookStore, string price, bool isFromFile){ //función que valida el precio

  bool ok = false;
  do{
    bookStore.books[bookStore.books.size()-1].price = stof(price);
    ok = true;
    if(bookStore.books[bookStore.books.size()-1].price<=0){
      error(ERR_BOOK_PRICE);
      if(isFromFile == false){
        ok = false;
        askPrice(bookStore,price);
      } 
    }
  }while(ok == false);

  return bookStore.books[bookStore.books.size()-1].price;
}

string createSlug(BookStore &bookStore){

  string slug = bookStore.books[bookStore.books.size()-1].title;
  char lower;          //variable auxiliar para pasar a minúsculas
  for(unsigned int i=0; i<slug.length();i++){
    lower = tolower(slug[i]);
    slug[i] = lower;
  }
  
  for(unsigned int i=0; i<slug.length();i++){     //sustituir por '-'
    if(isspace(slug[i]) ||  slug[i] == ',' || slug[i] == ':'){
      
     slug = slug.replace(i, 1, "-");

    }
  }

  for(unsigned int i=0; i<=slug.length();i++){    //borrar '-' que sobran
  
    if(slug[i] == '-' && slug[i+1] == '-'){
      slug = slug.erase(i, 2);
    }
    else if(slug[i] == '-' && i == slug.length()){
      slug = slug.erase(i, 2);
    }
  }

  bookStore.books[bookStore.books.size()-1].slug = slug;

  return bookStore.books[bookStore.books.size()-1].slug;
}
void addBook(BookStore &bookStore, bool &isFromFile){
  string title, author, slug, year, price;
  isFromFile = false;

  bookStore.books.push_back(Book());

  askTitle(bookStore,title);
  validationTitle(bookStore,title,isFromFile);
  askAuthor(bookStore,author);
  validationAuthor(bookStore,author,isFromFile);
  askYear(bookStore,year);
  validationYear(bookStore,year,isFromFile);
  askPrice(bookStore,price);
  validationPrice(bookStore,price,isFromFile);
  createSlug(bookStore);

  bookStore.books[bookStore.books.size()-1].id = bookStore.nextId;
  bookStore.nextId++;
}

void validationId(BookStore &bookStore){   //valida el id y borra el libro

  bool ok = false;
  do{  
    string id;
    message(M_ID);
    getline(cin,id, '\n');

    if(id.empty() == true){
        ok = false;
    }else{
      int num_id = stoi(id);
      for(unsigned int i = 0; i< bookStore.books.size(); i++){
        if(bookStore.books[i].id == (unsigned int)num_id){
          ok = true;
          bookStore.books.erase(bookStore.books.begin()+i);  
        }
      }
    }
    if(ok == false){
      error(ERR_ID);
    }

  }while(ok == false);
}

void deleteBook(BookStore &bookStore) {
  validationId(bookStore);
}

string dobleComma(string &cad, string line, unsigned int &i){ //almacena la cadena entre comillas
  i++;
  while(i<line.length() && line[i] != '"'){
    cad += line[i];
    i++;
  }
  i++;
  return cad;
}

void comma(string &line, unsigned int &i){ //salta hasta la siguiente coma
  string cad;
  while(i<line.length() && line[i] != ','){
    i++;
  }
  i++;
}

string commaNums(string line, unsigned int &i){ //almacena el número entre comas
  string cad;
  while(i<line.length() && line[i] != ','){
    cad += line[i];
    i++;
  }
  i++;
  return cad;
}

void stringToEmpty(string &s){ //vacía la string
  for(unsigned int i = 0;i<s.length();i++){
    s.erase(i);
  }
}

void readFile(BookStore &bookStore, string fileName, string &title, string &author, string &year, string &price, string &slug, bool &isFromFile){ //lee el archivo de texto
  ifstream fl(fileName);

  if(fl.is_open()){
    string line;
    while(getline(fl,line)){

      unsigned int i = 0;
      if(i == 0){
        bookStore.books.push_back(Book());
      }

      title = dobleComma(title,line,i);
      validationTitle(bookStore,title,isFromFile);
      bookStore.books[bookStore.books.size()-1].title = title;
      comma(line,i);
      author = dobleComma(author,line,i);
      validationAuthor(bookStore,author,isFromFile);
      bookStore.books[bookStore.books.size()-1].authors = author;
      comma(line,i);
      year = commaNums(line,i);
      validationYear(bookStore,year,isFromFile);
      slug = dobleComma(slug,line,i);
      bookStore.books[bookStore.books.size()-1].slug = slug;
      comma(line,i);
      price = commaNums(line,i);
      validationPrice(bookStore,price,isFromFile);

      stringToEmpty(title);
      stringToEmpty(author);
      stringToEmpty(year);
      stringToEmpty(slug);
      stringToEmpty(price);
      
      bookStore.books[bookStore.books.size()-1].id = bookStore.nextId;
      bookStore.nextId++;
    }
    fl.close();
  }
  else{
    error(ERR_FILE);
  }
}

void writeFile(BookStore bookStore, string fileName){ //escribe en el archivo de texto
  ofstream f(fileName);

  if(f.is_open()){
    for(unsigned int i = 0; i<bookStore.books.size(); i++){
      f<<'\u0022'<<bookStore.books[i].title<<'\u0022'<<',';
      f<<'\u0022'<<bookStore.books[i].authors<<'\u0022'<<',';
      f<<bookStore.books[i].year<<',';
      f<<'\u0022'<<bookStore.books[i].slug<<'\u0022'<<',';
      f<<bookStore.books[i].price<<endl;
    }
    f.close();
  }
  else{
    error(ERR_FILE);
  }
}

void importFromCsv(BookStore &bookStore, bool &isFromFile, bool isFromArgs, string fileArgs){
  isFromFile = true;
  string title, author, year, price, slug, fileName;
  if(isFromArgs == false){
    message(M_FILENAME);
    getline(cin,fileName,'\n');
  }else{
    fileName = fileArgs;
  }
  readFile(bookStore,fileName,title,author,year,price,slug,isFromFile);
  isFromFile = false;
}

void exportToCsv(const BookStore bookStore){
  string fileName;

  message(M_FILENAME);
  getline(cin,fileName,'\n');

  writeFile(bookStore,fileName);
}

void readBinay(BookStore &bookStore, BinBookStore  &binBookStore, BinBook &binBook, string filename){ //lee el fichero binario
  ifstream fl(filename, ios::binary);
    if(fl.is_open()){
      for(unsigned int i = 0; i< bookStore.books.size(); i++){
        bookStore.books.erase(bookStore.books.begin()+i);  
      }

      fl.read((char*)&binBookStore, sizeof(BinBookStore));
      bookStore.name = binBookStore.name;
      bookStore.nextId = binBookStore.nextId;
      unsigned int i=0;

      while(fl.read((char*)&binBook, sizeof(BinBook))){
        bookStore.books.push_back(Book());
        bookStore.books[i].id = binBook.id;
        bookStore.books[i].title = binBook.title;
        bookStore.books[i].authors = binBook.authors;
        bookStore.books[i].year = binBook.year;
        bookStore.books[i].slug = binBook.slug;
        bookStore.books[i].price = binBook.price;
        i++;
      }
      fl.close();
    }
    else{
      error(ERR_FILE);
    }
}

void loadData(BookStore &bookStore, BinBookStore &binBookStore, BinBook &binBook, string fileArgs, bool isFromArgs){
  char option;
  if(isFromArgs == false){
    do{
      message(M_DATAERASED);
      cin>>option;
      cin.get();

      if(option == 'y' || option == 'Y'){
        string filename;
        message(M_FILENAME);
        getline(cin,filename,'\n');
        readBinay(bookStore,binBookStore,binBook,filename);
        
      }
    }while(option != 'y' && option != 'Y' && option != 'n' && option != 'N');
  }else{
    readBinay(bookStore,binBookStore,binBook,fileArgs);
  }
}

void saveData(const BookStore &bookStore, BinBookStore &binBookStore, BinBook &binBook){

  string filename;
  message(M_FILENAME);
  cin>>filename;

  ofstream fl(filename, ios::binary);
  if(fl.is_open()){

    strncpy(binBookStore.name,bookStore.name.c_str(),KMAXSTRING-1);
    binBookStore.name[KMAXSTRING-1] = '\0';
    binBookStore.nextId = bookStore.nextId;
    fl.write((const char*)&binBookStore, sizeof(BinBookStore));

    for(unsigned int i = 0; i< bookStore.books.size(); i++){
      binBook.id = bookStore.books[i].id;
      strncpy(binBook.title,bookStore.books[i].title.c_str(),KMAXSTRING-1);
      binBook.title[KMAXSTRING-1] = '\0';
      strncpy(binBook.authors,bookStore.books[i].authors.c_str(),KMAXSTRING-1);
      binBook.authors[KMAXSTRING-1] = '\0';
      binBook.year = bookStore.books[i].year;
      strncpy(binBook.slug,bookStore.books[i].slug.c_str(),KMAXSTRING-1);
      binBook.slug[KMAXSTRING-1] = '\0';
      binBook.price = bookStore.books[i].price;
      fl.write((char*)&binBook, sizeof(BinBook));
    }
    fl.close();
  }
  else{
    error(ERR_FILE);
  }
}

void importExportMenu(BookStore &bookStore, bool &isFromFile, BinBookStore &binBookStore, BinBook &binBook, bool isFromArgs, string fileArgs) {
  char option;
  do {
    showImportExportMenu();
    cin >> option;
    cin.get();

    switch (option) {
      case '1':
        importFromCsv(bookStore,isFromFile,isFromArgs,fileArgs);
        break;
      case '2':
        exportToCsv(bookStore);
        break;
      case '3':
        loadData(bookStore,binBookStore,binBook,fileArgs,isFromArgs);
        break;
      case '4':
        saveData(bookStore,binBookStore, binBook);
        break;
      case 'b':
        break;
      default:
        error(ERR_OPTION);
    }
  } while (option != 'b');
}

void Arg_l(BookStore &bookStore, BinBookStore &binBookStore, BinBook &binBook, int argc, char *argv[], bool &isFromArgs, string &fileArgs){
  string arg = argv[2];
  string l = "-l";
  string arg2 = argv[4];

  if(arg == l){
    fileArgs = argv[3];
    loadData(bookStore,binBookStore,binBook,fileArgs,isFromArgs);
  }else if(arg2 == l && arg != l){
    fileArgs = argv[5];
    loadData(bookStore,binBookStore,binBook,fileArgs,isFromArgs);
  }
}

void Arg_i(BookStore &bookStore, int argc, char *argv[], bool &isFromArgs, bool &isFromFile, string &fileArgs){
  string arg = argv[2];
  string i = "-i";
  string arg2 = argv[4];

  if(argv[2] == i){
    fileArgs = argv[3];
    importFromCsv(bookStore,isFromFile,isFromArgs,fileArgs);
  }else if(arg2 == i && arg != i){
    fileArgs = argv[5];
    importFromCsv(bookStore,isFromFile,isFromArgs,fileArgs);
  }
}

void Args(BookStore &bookStore, BinBookStore &binBookStore, BinBook &binBook, int argc, char *argv[], bool &ArgsOk, bool &isFromArgs, bool &isFromFile, string &fileArgs){
  
  if(argc == 1){
    ArgsOk = true;
  }else if(argc == 3){
    ArgsOk = true;
    Arg_l(bookStore,binBookStore,binBook,argc,argv,isFromArgs,fileArgs);
    Arg_i(bookStore,argc,argv,isFromArgs,isFromFile,fileArgs);

  }else if(argc == 5){
    ArgsOk = true;
    Arg_l(bookStore,binBookStore,binBook,argc,argv,isFromArgs,fileArgs);
    Arg_i(bookStore,argc,argv,isFromArgs,isFromFile,fileArgs);
  }
  isFromArgs = false;
}

int main(int argc, char *argv[]) {
  BookStore bookStore;
  BinBookStore binBookStore;
  BinBook binBook;
  bool isFromFile = true;
  bool ArgsOk = false;
  bool isFromArgs = true;
  bookStore.name = "My Book Store";
  bookStore.nextId = 1;
  string fileArgs;
  Args(bookStore,binBookStore,binBook,argc,argv,ArgsOk,isFromArgs,isFromFile,fileArgs);
  if(ArgsOk == true){
    char option;
    do {
      showMainMenu();
      cin >> option;
      cin.get();

      switch (option) {
        case '1':
          showCatalog(bookStore);
          break;
        case '2':
          showExtendedCatalog(bookStore);
          break;
        case '3':
          addBook(bookStore,isFromFile);
          break;
        case '4':
          deleteBook(bookStore);
          break;
        case '5':
          importExportMenu(bookStore,isFromFile,binBookStore,binBook,isFromArgs,fileArgs);
          break;
        case 'q':
          break;
        default:
          error(ERR_OPTION);
      }
    } while (option != 'q');
  }else{
    error(ERR_ARGS);
  }
  return 0;
}
