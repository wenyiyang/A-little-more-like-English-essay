// Name: Wenyi Yang
// USC loginid: wenyiyan
// CS 455 PA4
// Spring 2015

import java.util.LinkedList;
import java.util.ListIterator;

/*
 * Prefix Class
 * construct Prefix which store a prefix of sourceFile, update a prefix, and overwrite HashCode and equals.
 */
public class Prefix {
	/*
	 * Representation invariant:
	 * every string stored in it is a word of sourceFile.
	 * the length of the LinkedList must be less that the length of sourceFile and greater than 0.
	 */
	private LinkedList<String> prefix;
	// a linkedList of string used to store a prefix.
	
	/*
	 * construct a prefix.
	 * @param a linkedList stores a prefix.
	 */
	public Prefix(LinkedList<String> prefix) {
		this.prefix = new LinkedList<String>();
		ListIterator<String> iter = prefix.listIterator();
		while(iter.hasNext()) {
			this.prefix.add(iter.next());
		}
	}
	
	/*
	 * update a prefix by remove the first string and add a string to the end.
	 * @param ranWord a string used to update the prefix.
	 * @return return a new prefix.
	 */
	public Prefix update(String ranWord) {
		ListIterator<String> iter = prefix.listIterator();
		LinkedList<String> newPrefix = new LinkedList<String>();
		while(iter.hasNext()) {
			newPrefix.add(iter.next());
		}
		newPrefix.add(ranWord);
		newPrefix.removeFirst();
		return new Prefix(newPrefix);
	}
	
	/*
	 * get the linkedList wrapped in a prefix.
	 * @return a linkedList wrapped in a prefix.
	 */
	public LinkedList<String> getPrefix( ) {
		LinkedList<String> prefix = new LinkedList<String>();
		ListIterator<String> iter = this.prefix.listIterator();
		while(iter.hasNext()) {
			prefix.add(iter.next());
		}
		return prefix;
	}
	
	/*
	 * print a prefix.
	 */
	public void printPrefix() {
		ListIterator<String> iter = prefix.listIterator();
		while(iter.hasNext()) {
			System.out.print(iter.next());
			if(iter.hasNext()) {
				System.out.print(" ");
			}
		}
		System.out.println();
	}
	
	/*
	 * overwrite hashCode of object.
	 * @return the hashCode of a prefix. 
	 */
	public int hashCode() {
		final int HASH_MULTIPLIER = 31;
		ListIterator<String> iter = prefix.listIterator();
		String strPrefix = "";
		int h = 0;
		while(iter.hasNext()) {
			strPrefix = strPrefix + iter.next();
		}
		for (int i = 0; i < strPrefix.length(); i++) {
			h = HASH_MULTIPLIER * h + strPrefix.charAt(i); 
		}
		return h;
	}
	/*
	 * compare two Prefix. If they are same, return true otherwise false.
	 * @return If they are same, return true otherwise false.
	 */
	public boolean equals(Object obj) {
		Prefix other = (Prefix) obj;
		ListIterator<String> iter = prefix.listIterator();
		ListIterator<String> mark = other.getPrefix().listIterator();
		String strPrefix = "";
		String strObj = "";
		while(iter.hasNext()) {
			strPrefix = strPrefix + iter.next();
		}
		while(mark.hasNext()) {
			strObj = strObj + mark.next();
		}
		if(strPrefix.equals(strObj)) {
			return true;
		}
		else {
			return false;
		}
	}
}
