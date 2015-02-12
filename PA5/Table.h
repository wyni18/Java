// Table.h
// CSCI 455 PA5
// Name: Yanni Wang
// Loginid: 7540834851

#ifndef TABLE_H
#define TABLE_H

// Table class
//    stores a collection of (string, int) entries
//    such that each key (the string) is unique
//    with efficient (O(1)) lookup, insert, and removal
//

#include <iostream>
#include <string>

// the following include is for string hash function
#include <tr1/functional>

using namespace std;

// the following line of code is a forward declaration of Node struct.
// allows us to use Node* in private section of Table below
// (Complete Node definition is in Table.cpp)
class Node;

class Table {
public:

   static const int HASH_SIZE = 997;

   // create an empty table, i.e., one where numEntries() is 0
   // (Underlying hash table is HASH_SIZE.)
   Table();

   // create an empty table, i.e., one where numEntries() is 0
   // such that the underlying hash table is hSize
   Table(unsigned int hSize);

   // insert a new pair into the table
   // return false iff it was already there (and no change made to table)
   bool insert(const string &key, int value);

   // returns the address of the value or null if key is not present
   int *lookup(const string &key);

   // remove an element
   // false iff element wasn't present
   bool remove(const string &key);

   // change an element
   // false iff element wasn't present
   bool newscore(const string &key, int value);

  // print out all the entries in the table, one per line.
   void printAll() const;

   int numEntries() const;      // number of entries in the table

   // hashStats: for diagnostic purposes only
   // prints out info to let us know if we're getting a good distribution
   // of values in the hash table.
   // Sample output from this function
   //   number of buckets: 997
   //   number of entries: 10
   //   number of non-empty buckets: 9
   //   longest chain: 2
   void hashStats(ostream &out) const;

private:

   //***********do not change the following two lines*************
   // making these private disallows copying of tables
   // (do not implement these two functions)
   Table &operator =(const Table &);
   Table(const Table &);

   // hash function for a string
   // (we defined it for you)
   // returns a value in the range [0, hashSize)
  unsigned int hashCode(const string &word)  {
    // tr1 is a new (not standard yet) C++ library
    // calls a library-defined hash function for string
    return tr1::hash<string>()(word) % hashSize;
  }

// add private data and methods here

  Node **list;  // declare for array of pointers to Node
  unsigned int numEntry; // number of entries in the table
  unsigned int nonBucket; // number of non-empty bucket in the table
  unsigned int longestChain; // the length of the longest chain

  unsigned int hashSize;      // size of the hash table
                              // (used in hashCode method above)

  // find the longest chain
  void findLongest();


};

#endif
