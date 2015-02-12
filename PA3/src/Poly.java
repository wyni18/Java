// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA3
// Spring 2013
import java.util.LinkedList;
import java.util.ListIterator;

/**
   A polynomial. Polynomials can be added together, evaluated, and
   converted to a string form for printing.
*/
public class Poly {

    /**
       Creates the 0 polynomial
    */
    public Poly() {
    	polyn = new LinkedList<Term>();
    	assert isValidPoly();
    	
    }


    /**
       Creates polynomial with single term given
       @param term: a single term to create a new polynomial
     */
    public Poly(Term term) {
    	polyn = new LinkedList<Term>();
    	if(term.getCoeff() != ZERO) {
    		polyn.add(term);
    	}
    	assert isValidPoly();
    }
    
    /**
       Copy polynomial
       @param poly1: the single Poly used to create a new polynomial
     */    
    public Poly(Poly poly1) {
    	assert isValidPoly();
    	assert poly1.isValidPoly();
    	polyn = new LinkedList<Term>();
    	ListIterator<Term> iter = poly1.polyn.listIterator();
    	while(iter.hasNext()) {
    		polyn.add(iter.next());
    	}
    	assert isValidPoly();
    	assert poly1.isValidPoly();
    }    


    /**
       Returns the Poly that is the sum of this polynomial and poly1
       (neither poly is modified)
       @param poly1: a poly for add 
       @return the new poly after adding a poly to 
     */
    public Poly add(Poly poly1) {
    	Poly newPoly = new Poly(); // define the new polynomial that will be returned
    	assert isValidPoly();
    	assert poly1.isValidPoly();
    	LinkedList<Term> newpolyn = new LinkedList<Term>(polyn);
    	newPoly.polyn = newpolyn;
    	newPoly.addIn(poly1);
    	assert isValidPoly();
    	assert poly1.isValidPoly();
    	assert newPoly.isValidPoly();
    	
    	return newPoly;
    }
    	

    /**
       Returns the value of the poly at a given value of x.
       @param x: a given value for the polynomial
       @return the value after evaluation
     */
    public double eval(double x) {
    	double value = 0;
    	ListIterator<Term> iter = polyn.listIterator();
    	while(iter.hasNext()) {
    		Term next = iter.next();
    		value += next.getCoeff() * Math.pow(x, next.getExpon());
    	}
    	return value;       
    }

    /**
       Return a String version of the polynomial with the 
       following format, shown by exmaple:
       zero poly:   "0.0"
       1-term poly: "3.0x^2"
       4-term poly: "3.0x^5 + x^2 + 2.0x + 7.0"

       Poly is in a simplified form (only one term for any exponent),
       with no zero-coefficient terms, and terms are shown in
       decreasing order by exponent.
    */
    public String toFormattedString() {
    	ListIterator<Term> iter = polyn.listIterator();
    	String polynomial = "";	
    	if(polyn.size() == 0) { // print zero polynomial
    		polynomial = "0.0";
    	}
    	else {
    		while(iter.hasNext()) {
    			Term next = iter.next();
    			if(next.getCoeff() != ONE || next.getExpon() == 0) { // print polynomial with coefficient != 1.0
    				polynomial += next.getCoeff();
    			}
    			if(next.getExpon() == 1) { // for those whose exponent equals 1
    				polynomial += "x";
    			}
    			else if(next.getExpon() != 0) { // for those whose exponent is not zero
    				polynomial += "x^" + next.getExpon();
    			}
    			if(iter.hasNext()){ 
    				polynomial += " + ";
    			}
    		}
    	}
    	return polynomial;       
    }
    
    /**
    Adds a new poly polyNew to this poly.  (mutator)
    (polyNew is unchanged)
    @param polyNew: a poly for add
     */
    public void addIn(Poly polyNew) {
    	assert isValidPoly();
    	assert polyNew.isValidPoly();
    	ListIterator<Term> itera = polyn.listIterator();
    	ListIterator<Term> iterb = polyNew.polyn.listIterator();
    	while(itera.hasNext() && iterb.hasNext()) {
    		Term aTerm = itera.next();
    		Term bTerm = iterb.next();
    		if(polyNew.polyn.getFirst().getExpon() < polyn.getLast().getExpon()) {
    			polyn.add(bTerm);
    			while(iterb.hasNext()) {
    	   			polyn.add(iterb.next());
    	   			
    	   		}
    	   	}
    		else {
    			if(aTerm.getExpon() > bTerm.getExpon()) {
        			iterb.previous();
        		}
        		else if(aTerm.getExpon() < bTerm.getExpon()) {
        			itera.previous();
        			itera.add(bTerm);
        		}
        		else if(aTerm.getExpon() == bTerm.getExpon()) {
        			Term sum = new Term(aTerm.getCoeff() + bTerm.getCoeff(), aTerm.getExpon());
        			if(sum.getCoeff() == ZERO) {
        				itera.remove();	
        			}
        			else {
        				itera.set(sum);
        			}
        		}
    		}
    	}
        if(!itera.hasNext()) {
        	while(iterb.hasNext()) {
        		itera.add(iterb.next());
        	}
        }  	
        assert isValidPoly();
        assert polyNew.isValidPoly();
   	}
    	
    
    /**
       Multiplies poly2 to this poly.  (mutator)
       (b is unchanged)
       @param poly2: a Poly as a multiplier
       @return the changed poly after multiplying 
     */
    public Poly mult(Poly poly2) {
    	assert isValidPoly();
    	assert poly2.isValidPoly();
    	Poly multPoly = new Poly(); 
    	ListIterator<Term> iter1 = polyn.listIterator();
    	while(iter1.hasNext()) {
    		ListIterator<Term> iter2 = poly2.polyn.listIterator();
    		Term term1 = iter1.next();
    		int expon = term1.getExpon();
    		double coef = term1.getCoeff();
    		Poly tempPoly = new Poly(); 
    		while(iter2.hasNext()) {
    			Term term2 = iter2.next();
    			Term tempTerm = new Term(coef * term2.getCoeff(), expon + term2.getExpon());
    			tempPoly.polyn.add(tempTerm);
    		}
    		multPoly.addIn(tempPoly);
    	}
    	assert isValidPoly();
    	assert poly2.isValidPoly();
    	assert multPoly.isValidPoly();
    	return multPoly;
    }

    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
       Returns true iff the poly data is in a valid state.
    */

    private boolean isValidPoly() {
    	ListIterator<Term> iter = polyn.listIterator();
    	if(!iter.hasNext()) {
    		return true;
    	}
    	// Check whether there is zero coefficient
    	while(iter.hasNext()) {
    		if(iter.next().getCoeff() == ZERO) {
    			return false;
    		}
    	}
    	
    	// Check the decreasing order
    	iter = polyn.listIterator();
    	int present = iter.next().getExpon();
    	// Check whether the component is negative
    	if(present < 0) {
    		return false;
    	}
    	while(iter.hasNext()) {	
        	int next = iter.next().getExpon();
        	if(present <= next) {
        		return false;
        	}
        	present = next;
    	}	
    	return true;     // dummy code.  just to get stub to compile
    }


    // **************************************************************
    //  PRIVATE INSTANCE VARIABLE(S)
    private LinkedList<Term> polyn;  
    private static final double ONE = 1.0;
    private static final double ZERO = 0.0;
    
}
