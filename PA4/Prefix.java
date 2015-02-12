// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA4
// Spring 2013
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Prefix class
 * create and update prefix with certain length
 * @author wyni
 *
 */
public class Prefix {
	private LinkedList<String> prefix;
	private static Random selectPrefix;
	
	public Prefix(){
		prefix = new LinkedList<String>();
	}
	
	public Prefix(LinkedList<String> prefixArr){
		
		prefix = (LinkedList<String>) prefixArr.clone();
		
	}
	
	
	/**
	 * update the prefix with successor
	 * @param next: successor to be added to prefix
	 * @return: new prefix string
	 */
	public String update(String next) {
		prefix.removeFirst();
		prefix.add(next);
		
		String newStr = toString();
		return newStr;
	}
	
	/**
	 * get the prefix string from the LinkedList
	 * @return the prefix string
	 */
	public String toString() {
		
		ListIterator<String> iter = prefix.listIterator();
		String prefixStr = iter.next();
		while (iter.hasNext()) {
			prefixStr += iter.next();
		}
		
		return prefixStr;
	}
}
