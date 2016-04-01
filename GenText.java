// Name: Wenyi Yang
// USC loginid: wenyiyan
// CS 455 PA4
// Spring 2015

/*
 * GenText Class
 * store all words of sourceFile, do error checking, and output outFile.
 */

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class GenText {
	public final static String DEBUGGING_FLAG = "-d";
	// a string used to control debugging mode.
	public final static int LENGTH_PER_LINE = 80;
	// an integer used to control the length of each line of outFile.
	public final static int THE_LEAST_VALID_ARGUMENTS = 4;
	// an integer used to represent the shortest valid length of command-line arguments.
	
	public static void main(String[] args) {
		String debugSwitch = "";
		int prefixLength = 0;
		int numWords = 0;
		String outFile = "";
		ArrayList<String> sourceText = errorChecking(args);
		if(args.length == THE_LEAST_VALID_ARGUMENTS) {
			prefixLength = Integer.parseInt(args[0]);
			numWords = Integer.parseInt(args[1]);
			outFile = args[3];
		}
		else {
			debugSwitch = args[0];
			prefixLength = Integer.parseInt(args[1]);
			numWords = Integer.parseInt(args[2]);
			outFile = args[4];
		}
		HashMap<Prefix, ArrayList<String>> sourceTextMap = SourceTextMap.GenSourceTextMap(sourceText, prefixLength);
		try {
			writeFile(sourceTextMap, sourceText, outFile, debugSwitch, prefixLength, numWords); 	
		}
		catch (FileNotFoundException exc) {
			System.out.println("ERROR: can't write to output file!");
			System.exit(0);
		}
		catch (IOException exc) {
		    exc.printStackTrace();
		}		
	}
	
	/*
	 * do error checking. If an error is reported the program will exit without generating any text.
	 * @param args An array of string which store command-line arguments.
	 * @return An array of string which store all words of sourceFile.
	 */
	private static ArrayList<String> errorChecking(String[] args) {
		ArrayList<String> sourceText = new ArrayList<String>();
		try {
			if(args.length < THE_LEAST_VALID_ARGUMENTS) {
				System.out.println("ERROR: missing command-line arguments! The right format: java GenText [-d] prefixLength numWords sourceFile outFile.");
				System.exit(0);
			}
			else if(args.length == THE_LEAST_VALID_ARGUMENTS) {
				errorCheckingHelp(args);
				sourceText = readFile(args[2]);
				if(Integer.parseInt(args[0]) >= sourceText.size()) {
					System.out.println("ERROR: prefixLength is greater than the length of sourceFile!");
					System.exit(0);
				}
			}
			else {
				errorCheckingHelp(args);
				sourceText = readFile(args[3]);
				if(Integer.parseInt(args[1]) >= sourceText.size()) {
					System.out.println("ERROR: prefixLength is greater than the length of sourceFile!");
					System.exit(0);
				}
		    }
		}
		catch (FileNotFoundException exc) {
	        if(args.length == THE_LEAST_VALID_ARGUMENTS) {
		        System.out.println("ERROR: File not found: " + args[2] + "!");
		        System.exit(0);
	        }
	        else {
	        	System.out.println("ERROR: File not found: " + args[3] + "!");
	        	System.exit(0);
	        } 
		}
		catch (IOException exc) {
		    exc.printStackTrace();
		}	
		return sourceText;
	}
	
	/*
	 * read sourceFile and store all words of sourceFile.
	 * @param A string indicates the mane of sourceFile.
	 * @return An array of string which store all words of sourceFile.
	 * @throws throws FileNotFound if sourceFile do not exist.
	 */
	private static ArrayList<String> readFile(String sourceFile) throws IOException {
		File inputFile = new File(sourceFile);
		Scanner in = new Scanner(inputFile);
		ArrayList<String> sourceText = new ArrayList<String>();
		while(in.hasNext()) {
		    sourceText.add(in.next());
		}
		in.close();
	    return sourceText;
	}
	
	/*
	 * generate the outFile and throw permission denied error if don't have write permission.
	 * @param sourceTextMap A hash map which store all possible prefixes and their successors.
	 * @param sourceText An ArrayList which store all words of sourceFile.
	 * @param outFile a String which indicates the name of outFile.
	 * @param debugSwitch a string used to control debugging mode.
	 * @param prefixLength an integer used to indicates the length of a prefix.
	 * @param numWords an integer used to indicates the number of words will generate.
	 * @throws Throw permission denied error if don't have write permission.  
	 */
	private static void writeFile(HashMap<Prefix, ArrayList<String>> sourceTextMap, ArrayList<String> sourceText, String outFile, String debugSwitch, int prefixLength, int numWords ) throws IOException {
		PrintWriter out = new PrintWriter(outFile);
		RanPrefixGenerator generator = new RanPrefixGenerator(sourceText, prefixLength, debugSwitch);
		RandomTextGenerator seed = new RandomTextGenerator(sourceTextMap, debugSwitch);
		Prefix prefix = generator.generate();
		Prefix flag = prefix;
		ArrayList<String> successor = sourceTextMap.get(prefix);
		String word = seed.genText(prefix);
		String mark = word;
		out.print(word);
		int numWordsOut = 1;
		printDebug(sourceTextMap, successor, prefix, flag, word, debugSwitch, numWordsOut);
		for(int i = 0; i < numWords-1; i++) {
			prefix = prefix.update(word);
			flag = prefix;
			if(!sourceTextMap.containsKey(prefix)) {
				prefix = generator.generate();
			}
			successor = sourceTextMap.get(prefix);
			word = seed.genText(prefix);
			mark = mark + " " + word;
			if(mark.length() <= LENGTH_PER_LINE) {
				out.print(" " + word);
				numWordsOut++;
			}
			else {
				mark = word;
				out.println();
				out.print(word);
				numWordsOut++;
			}
			printDebug(sourceTextMap, successor, prefix, flag, word, debugSwitch, numWordsOut);
		}
		out.close();
	}
	
	/* 
	 * print debug result in console.
	 * @param sourceTextMap A hash map which store all possible prefixes and their successors.
	 * @param successor An ArrayList of String which stores all successors of a specific prefix.
	 * @param prefix A Prefix used to store a prefix.
	 * @param flag A prefix used to indicates whether the file is in the end.
	 * @param word A string needed to write to outFile.
	 * @param debugSwitch a string used to control debugging mode.
	 * @param numWordsOut An integer used to represent the number of words been wrote in outFile.
	 */
	private static void printDebug(HashMap<Prefix, ArrayList<String>> sourceTextMap, ArrayList<String> successor, Prefix prefix, Prefix flag, String word, String debugSwitch, int numWordsOut) {
		if(debugSwitch.equals(DEBUGGING_FLAG)) {
			String strSuccessor = successor.toString();
			strSuccessor = strSuccessor.replace("[", "");
			strSuccessor = strSuccessor.replace(",", "");
			strSuccessor = strSuccessor.replace("]", "");
			if(!sourceTextMap.containsKey(flag)) {
		        System.out.print("DEBUG: prefix: ");
				flag.printPrefix();
				System.out.println("DEBUG: successors: <END OF FILE>");
				System.out.print("DEBUG: chose a new initial prefix: ");
				prefix.printPrefix();
			}
			if(numWordsOut == 1) {
				System.out.print("DEBUG: chose a new initial prefix: ");
				prefix.printPrefix();
			}
			System.out.print("DEBUG: prefix: ");
			prefix.printPrefix();
			System.out.println("DEBUG: successors: " + strSuccessor);
			System.out.println("DEBUG: word generated: " + word);
		   }	
	 }
	
	/*
	 * help errorChecking to check errors.
	 * @param args An array of string which store command-line arguments.
	 */
	private static void errorCheckingHelp(String[] args) {
		try {
		    if(args.length == THE_LEAST_VALID_ARGUMENTS ) {
		        if(Integer.parseInt(args[0]) < 1) {
			        System.out.println("ERROR: prefixLength is less than 1! The right format: java GenText [-d] prefixLength numWords sourceFile outFile.");
			        System.exit(0);
		        }
		        if(Integer.parseInt(args[1]) < 0) {
			        System.out.println("ERROR: numWords is less than 0! The right format: java GenText [-d] prefixLength numWords sourceFile outFile.");
			        System.exit(0);
		        }
		    }
		    else {
			    if(Integer.parseInt(args[1]) < 1) {
				    System.out.println("ERROR: prefixLength is less than 1! The right format: java GenText [-d] prefixLength numWords sourceFile outFile.");
				    System.exit(0);
			    }
			    if(Integer.parseInt(args[2]) < 0) {
				    System.out.println("ERROR: numWords is less than 0! The right format: java GenText [-d] prefixLength numWords sourceFile outFile.");
				    System.exit(0);
			    }
		    }
		}
		catch (NumberFormatException e) {
		    System.out.println("ERROR: prefixLength or numWords arguments are not integers! The right format: java GenText [-d] prefixLength numWords sourceFile outFile.");
		    System.exit(0);
		} 	
	}
}
