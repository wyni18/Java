// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA4
// Spring 2013

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * GenText class
 * Generate a random text based on a source file and length of prefix
 * @author wyn
 * (Invariants: textArray.size() > prefixLength
 * 				prefixLength >= 1
 * 				numWords > 0
 * 			    prefixLength < textArray.size()
 *              args.length() >=4 )
 *
 */
public class GenText {
	private static int start;
	private static final int DEBUG = 5;
	private static final int PREFIXLENGTH = 1;
	private static final int NUMWORD = 0;
	private static final int NUMWORD_LOC = 1;
	private static final int SOURCE_LOC = 2;
	private static final int OUTPUT_LOC = 3;
	private static final int NODEBUG = 4;
	private static final String HINT = "Hint: a run of your program, java GenText [-d] prefixLength " +
			"numWords sourceFile outFile generates numWords words of prefixLength-order text " +
			"using sourceFile as the source document. " +
			"The generated words are written to outFile (not the console).";
	
	/**
	 * the main method, test command line
	 * @param args: the command line
	 * (Precondition: args.length() >= 4)
	 */
	public static void main(String[] args) {	
		
		try {
			CmdTest(args);	
		}
		catch(BadArgumentDataException exception) {
			System.out.println(exception.getMessage());
		}
		catch(NumberFormatException exception) {
			System.out.println("ERROR: prefixLength or numWords arguments are not integers\n" + HINT);
		}
		catch(FileNotFoundException exception) {
			System.out.println("ERROR: input file not found");
		}
		
	}
	
	/**
	 * test arguments in command line
	 * @param args: contains command string
	 * @throws BadArgumentDataException: arguments does not meet the requirement 
	 * @throws NumberFormatException: the prefixLength and numWords are not integers
	 * @throws FileNotFoundException: cannot open or write 
	 */
	private static void CmdTest(String[] args) 
			throws BadArgumentDataException, NumberFormatException, FileNotFoundException {
		boolean debug = false;
		start = 0;
		
		if(args.length < NODEBUG || (args[0].equals("-d") && args.length < DEBUG)) {
			throw new BadArgumentDataException("ERROR: missing command-line arguments\n" + HINT);
		}
		
		if(args.length == DEBUG && args[0].equals("-d")) {
			debug = true;
			start = 1;
		}
		
		int prefixLength = Integer.parseInt(args[start]);
		
		int  numWord= Integer.parseInt(args[start + NUMWORD_LOC]);
		String source = args[start + SOURCE_LOC];
		String outFile = args[start + OUTPUT_LOC];
		argTest(prefixLength, numWord);
		
		doWrite(prefixLength, numWord, source, outFile, debug);
	}
	
	/**
	 * test the range of prefixLength and numWord
	 * @param prefixLength: the required length of prefix
	 * @param numWord: the length of text to be generated
	 * @throws BadArgumentDataException: wrong range of arguments
	 * (Precondition: prefixLength >= 1, numWord > 0; prefixLength < textArray.size())
	 */
	private static void argTest(int prefixLength, int numWord) 
			throws BadArgumentDataException {
		if(numWord < NUMWORD) {
			throw new BadArgumentDataException("ERROR: numWords < 0\n" + HINT);
		}
		
		if(prefixLength < PREFIXLENGTH) {
			throw new BadArgumentDataException("ERROR: prefixLength < 1\n" + HINT);
		}
		
	}
	
	/**
	 * generate the content
	 * @param prefixLength: the required length of prefix
	 * @param numWord: the length of text to be generated
	 * @param source: the name of source file
	 * @param output: the name of output file
	 * @param debug: true if debug is needed, otherwise false
	 * @throws FileNotFoundException: cannot open or write 
	 * @throws BadArgumentDataException: arguments not meet the requirement
	 * (Precondition:  prefixLength >= 1, numWord > 0, prefixLength < textArray.size(), source file exist)
	 */
	private static void doWrite(int prefixLength, int numWord,  String source, 
			String output, boolean debug) throws FileNotFoundException, BadArgumentDataException {
		
		ArrayList<String> textArray = new ArrayList<String>();
		File inFile = new File(source);
		Scanner fileScanner = new Scanner(inFile);
		while(fileScanner.hasNext()){
			textArray.add(fileScanner.next());
		}
		
		if(textArray.size() <= prefixLength) {
				throw new BadArgumentDataException("ERROR: prefixLength >= number of words " +
						"in sourceFile");
		}
		
		RandomTextGenerator newText = new RandomTextGenerator(textArray, prefixLength, debug);
		
		newText.generator(numWord);
		String finalText = newText.toString();
		try {
			File outFile = new File(output);
			PrintWriter outScanner = new PrintWriter(outFile);
			outScanner.print(finalText);
			outScanner.close();
		}
		catch(FileNotFoundException exception) {
			System.out.println("ERROR: can't write to output file");
		}
		
	}

}
