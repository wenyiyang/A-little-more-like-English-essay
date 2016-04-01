// Name: Wenyi Yang
// USC loginid: wenyiyan
// CS 455 PA4
// Spring 2015

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * SourceTextMap Class
 * generate a hashMap which stores all possible prefixes and their successors.
 */
public class SourceTextMap {
	/*
	 * generate a hashMap which stores all possible prefixes and their successors.
	 * @param sourceText a ArrayList of string stores all words of sourceFile.
	 * @param prefixLength an integer indicates the length of a prefix.
	 * @return a hashMap which stores all possible prefixes and their successors.
	 */
	public static HashMap<Prefix, ArrayList<String>> GenSourceTextMap (ArrayList<String> sourceText, int prefixLength) {
		HashMap<Prefix, ArrayList<String>> sourceTextMap = new HashMap<Prefix, ArrayList<String>>();
		int k = 0;
		LinkedList<String> initialListPrefix = new LinkedList<String>();
		ArrayList<String> initialSuccessor = new ArrayList<String>();
		for(int j = 0; j <=prefixLength-1; j++) {
			initialListPrefix.add(sourceText.get(j));
			k++;
		}
		Prefix initialPrefix = new Prefix(initialListPrefix);
		initialSuccessor.add(sourceText.get(k));
		sourceTextMap.put(initialPrefix, initialSuccessor);
		for(int i = 1; i < sourceText.size()-prefixLength; i++) {
			int l = i;
			LinkedList<String> listPrefix = new LinkedList<String>();
			ArrayList<String> successor = new ArrayList<String>();
			for(int j = i; j <=prefixLength-1+i; j++) {
				listPrefix.add(sourceText.get(j));
				l++;
			}
			Prefix prefix = new Prefix(listPrefix);
			successor.add(sourceText.get(l));
			if(sourceTextMap.containsKey(prefix)) {
				sourceTextMap.get(prefix).add(sourceText.get(l));
			}
			else {
				sourceTextMap.put(prefix, successor);
			}
		}
		return sourceTextMap;
	}
}
