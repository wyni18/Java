// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA3
// Spring 2013
public class PolyTester {
	public static void main(String[] args) {
		/**
		 * test create zero polynomial
		 */
		Poly poly = new Poly();
		String string = poly.toFormattedString();
		System.out.println("Expected result: 0.0");
		System.out.println("Real result: " + string);
		
		/**
		 * test Creates polynomial with single term given
		 */
		Term term =  new Term(1,1);
		Poly poly1 = new Poly(term);
		String string1 = poly1.toFormattedString();
		System.out.println("Expected result: x");
		System.out.println("Real result: " + string1);
		
		Term term1 = new Term(2,3);
		Poly poly2 = new Poly(term1);
		poly1.addIn(poly2);
		String string2 = poly1.toFormattedString();
		System.out.println("Expected result: 2.0x^3 + x");
		System.out.println("Real result: " + string2);
		
		Term term2 = new Term(3,4);
		Poly poly3 = new Poly(term2);
		poly1.addIn(poly3);
		String string3 = poly1.toFormattedString();
		System.out.println("Expected result: 3.0x^4 + 2.0x^3 + x");
		System.out.println("Real result: " + string3);
		
		Term term3 = new Term(-2,3);
		Poly poly4 = new Poly(term3);
		poly1.addIn(poly4);
		String string4 = poly1.toFormattedString();
		System.out.println("Expected result: 3.0x^4 + x");
		System.out.println("Real result: " + string4);
		
		Term term4 = new Term(5,0);
		Poly poly5 = new Poly(term4);
		poly1.addIn(poly5);
		String string5 = poly1.toFormattedString();
		System.out.println("Expected result: 3.0x^4 + x + 5.0");
		System.out.println("Real result: " + string5);
		
		Term term5 = new Term(1,6);
		Poly poly6 = new Poly(term5);
		poly1.add(poly6);
		String string6 = poly6.toFormattedString();
		System.out.println("Expected result: 3.0x^4 + x +5.0");
		System.out.println("Real result: " + string6);
		
		
	}
}
