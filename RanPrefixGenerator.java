// Name: Wenyi Yang
// USC loginid: wenyiyan
// CS 455 PA4
// Spring 2015

import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * generate a random Prefix used to generate a word which follows the prefix.
 */
public class RanPrefixGenerator {
	/*
	 * Representation invariant:
	 * sourceText stores all words of sourceFile.
	 * prefixLength must less than the length of sourceFile and greater than 0.
	 */
	private ArrayList<String> sourceText;
	// sourceText stores all words of sourceFile.
	private int prefixLength;
	// prefixLength must less than the length of sourceFile and greater than 0.
	private Random generator;
	// a random generator used to generate random number.
	
	/*
	 * construct a RanPrefixGenerator which can used to generate a random prefix.
	 * @param sourceText sourceText stores all words of sourceFile.
	 * @param prefixlength prefixLength must less than the length of sourceFile and greater than 0.
	 * @param debugSwitch a string used to control debugging mode.
	 */
	public RanPrefixGenerator(ArrayList<String> sourceText, int prefixLength, String debugSwitch) {
		this.sourceText = new ArrayList<String>();
		this.prefixLength = prefixLength;
		if(debugSwitch.equals(GenText.DEBUGGING_FLAG)) {
			generator = new Random(1);
		}
		else {
			generator = new Random();
		}
		for(int i = 0; i < sourceText.size(); i++) {
			this.sourceText.add(sourceText.get(i));
		}
	}
	
	/*
	 * generate a random prefix.
	 * @return a random prefix used to generate a word.
	 */
	public Prefix generate() {
		int initialIndex = generator.nextInt(sourceText.size());
		if(prefixLength % 2 ==0) {
			while(!(initialIndex-prefixLength/2>=0 && initialIndex+prefixLength/2-1<=sourceText.size()-2)) {
				initialIndex = generator.nextInt(sourceText.size());
			}
		}
		else {
			while(!(initialIndex-prefixLength/2>=0 && initialIndex+prefixLength/2<=sourceText.size()-2)) {
				initialIndex = generator.nextInt(sourceText.size());
			}
		}
		LinkedList<String> prefix = new LinkedList<String>();
		prefix.add(sourceText.get(initialIndex));
		for(int i = 1; i <= prefixLength/2; i++ ) {
			prefix.addFirst(sourceText.get(initialIndex-i));
		}
		if(prefixLength % 2 != 0) {
			for(int j = 1; j <= prefixLength/2; j++ ) {
				prefix.addLast(sourceText.get(initialIndex+j));
			}
		}
		else {
			for(int j = 1; j < prefixLength/2; j++ ) {
				prefix.addLast(sourceText.get(initialIndex+j));
			}
		}
		Prefix ranPrefix = new Prefix(prefix);
		return ranPrefix;
	}
}
