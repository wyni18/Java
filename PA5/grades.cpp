// grades.cpp
// CSCI 455 PA5
// Name: Yanni Wang
// Loginid: 7540834851
//
/*
 * A program to test the Table class.
 * How to run it:
 *      grades [hashSize]
 *
 * the optional argument hashSize is the size of hash table to use.
 * if it's not given, the program uses default size (Table::HASH_SIZE)
 *
 */

#include "Table.h"

// cstdlib needed for call to atoi
#include <cstdlib>

// insert a pair of key and value
void doInsert(string key, int value, Table &grades) {
	cout << "Please enter the key and value: ";
	cin >> key;
	cin >> value;
    if(!grades.insert(key, value)) {
    	cout << "The name has already existed." << endl;
	}
}

// change the value of an existed key
void doChange(string key, int value, Table &grades) {
	cout << "Please enter the key and value: ";
	cin >> key;
	cin >> value;
	if(!grades.newscore(key, value)) {
		cout << "The name does not existed." << endl;
	}
}

// look up whether a key has existed
void doLookup(string key, Table &grades) {
	cout << "Please enter the key: ";
	cin >> key;
	if(grades.lookup(key) == NULL) {
		cout << "The name does not existed." << endl;
	}
	else {
		cout << *(grades.lookup(key)) << endl;
	}
}

// remove an existed key
void doRemove(string key, Table &grades) {
	cout << "Please enter the key: ";
	cin >> key;
	if(!grades.remove(key)) {
		cout << "The name does not existed." << endl;
	}
}

// print help line
void doHelp() {
	 cout<< "Valid commands are i(insert), c(change), l(look up), " << endl;
	 cout << "p(print all) , n(print number of entries), " << endl;
	 cout << "h(help), q(quit)." << endl;
}

// handle the main command line
void doCmd(char c, int keepgoing, string key, int value, Table &grades) {
	do {
		cout << "cmd> ";
		cin >> c;

		switch(c) {
		case'i':
			doInsert(key, value, grades);
			break;
		case'c':
			doChange(key, value, grades);
			break;
		case'l':
			doLookup(key, grades);
			break;
		case'r':
			doRemove(key, grades);
			break;
		case'p':
		    grades.printAll();
			break;
		case'n':
			cout << grades.numEntries() << endl;
			break;
		case's':
			grades.hashStats(cout);
			break;
		case'q':
		  	keepgoing = 0;
		  	break;
		default:
			doHelp();
			break;
		  }
	  } while (keepgoing);
}

int main(int argc, char * argv[]) {

  // gets the hash table size from the command line

  int hashSize = Table::HASH_SIZE;

  if (argc > 1) {
    hashSize = atoi(argv[1]);  // atoi converts c-string to int

    if (hashSize < 1) {
      cout << "Command line argument (hashSize) must be a positive number"
	   << endl;
      exit(1);
    }
  }


  Table grades(hashSize);

  grades.hashStats(cout);

  char c;
  int keepgoing = 1;
  string key;
  int value;

  doCmd(c, keepgoing, key, value, grades);

  // add more code here
  return 0;
}
