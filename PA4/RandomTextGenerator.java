// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA4
// Spring 2013
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

/**
 * RandomTextGenerator Class
 * generate a random text based on source file 
 * @author wyn
 * (Invariant: text.size() > prefixLength
 * 			   length < text.size()
 * 			   last word in prefix selected randomly is not 
 * 						the last word the file)
 *
 */
public class RandomTextGenerator {
	private Map<String, ArrayList<String>> map;
	private static int prefixLength;
	private static int count;
	private boolean debug;
	private static Random selectPrefix;
	private static Random selectNext;
	private ArrayList<String> text;
	private String newText;
	
	private static final int EACHLINE = 80;
	private static final int ONE_MATCH = 1;
	private static final int END = 0;
	
	/**
	 * create an empty map
	 */
	public RandomTextGenerator() {
		map = new HashMap<String, ArrayList<String>>();
		
	}
	
	/**
	 * initialize the map with arguments from GenText class, the map stores
	 *     all pairs of prefixes and successors
	 * @param text: the input file content
	 * (Precondition: text.size() > Length)
	 * @param Length: the prefix length
	 * (Precondition: Length >= 1)
	 * @param debug: true if debug is needed, otherwise false
	 */
	public RandomTextGenerator(ArrayList<String> text, int Length, boolean debug) {
		newText = "";
		count = 0;
		this.debug = debug;
		map = new HashMap<String, ArrayList<String>>();
		this.text = (ArrayList<String>) text.clone();
		prefixLength = Length;
		for(int i = 0; i < text.size() - prefixLength + 1; i++) {
			ArrayList<String> temp = new ArrayList<String>();
			String mapPrefix = "";
			for(int j = i; j < i + prefixLength; j++) {
				mapPrefix += text.get(j);
			}
			
			if(i < text.size() - prefixLength) {
				if(!map.containsKey(mapPrefix)) {
					temp.add(text.get(i + prefixLength));
					map.put(mapPrefix, temp);
				}
				else {
					temp = (ArrayList<String>) map.get(mapPrefix);
					temp.add(text.get(i + prefixLength));
					map.put(mapPrefix, temp);
				}
			}
			else {
				map.put(mapPrefix, temp);
			}	
		}
	}
	
	/**
	 * generate a random passage 
	 * @param numWord: number of words in the passage
	 * (Precondition: numWord > 0)
	 */
	public void generator(int numWord) {
		LinkedList<String> prefixArr = randomPrefix();
		Prefix prefix = new Prefix(prefixArr);
		ListIterator<String> iter = prefixArr.listIterator();
		while(iter.hasNext()) {
			Print(iter.next());
		}
		int Pos = prefixLength;
		String current = prefix.toString();

		while(Pos < numWord) {
			ArrayList<String> temp1 = map.get(current);
			int size = temp1.size();
			if(size == END) {
				if(debug) {
					System.out.println("DEBUG: successors: <END OF FILE>");
				}
				prefixArr = randomPrefix();
				prefix = new Prefix(prefixArr);
				iter = prefixArr.listIterator();
				while(iter.hasNext() && Pos < numWord) {
					Print(iter.next());
					Pos++;
				}
				current = prefix.toString();
			}
			else {
				String next1 = findNext(temp1);
				Print(next1);
				current = prefix.update(next1);
				Pos++;
			}
		}
	}
	
	/**
	 * print the next word
	 * @param next: the string to be printed next
	 */
	public void Print(String next) {
		if(count + next.length() + 1 > EACHLINE) {
			newText += "\n" + next;
			count = next.length();
		}
		
		else if(count != 0 || newText.equals("")){
			newText += " " + next;
			count += next.length() + 1;
		}
		
	}
	
	/**
	 * generate a random prefix whose last word is not the last word
	 * 		in the file
	 * @return a linkedlist containing the prefix words
	 */
	public LinkedList<String> randomPrefix() {
		if(debug) {
			System.out.print("DEBUG: chose a new initial prefix: ");
		}
		
		LinkedList<String> prefixArr = new LinkedList<String>();
		selectPrefix = new Random();
		int start = selectPrefix.nextInt(text.size() - prefixLength);
		
		for(int i = start; i < start + prefixLength; i++) {
			String prefix = text.get(i);
			if(debug) {
				System.out.print(prefix + " ");
			}
			prefixArr.add(prefix);
			
		}
		if(debug) {
			System.out.println("");
		}
		

		return prefixArr;
	}
	
	/**
	 * a method to find the successor of the prefix randomly
	 * @param temp1: the array containing all the successors
	 * @return the successor string
	 */
	public String findNext(ArrayList<String> temp1) {
		selectNext = new Random();
		String next;
		if(debug) {
			System.out.print("DEBUG: successors: ");
			for(int i = 0; i < temp1.size(); i++) {
				System.out.print(temp1.get(i) + " ");
			}
			System.out.println("");
		}
		int nextPos = selectNext.nextInt(temp1.size());
		next = temp1.get(nextPos);
		if(debug) {
			System.out.println("DEBUG: word generated: " + next);
		}
		
		return next;
	}
	
	/**
	 * get the string of the new text file
	 * @return the string generated
	 */
	public String toString() {
		
		return newText;
	}
	
	
	
}
