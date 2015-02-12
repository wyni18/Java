// Name: Yanni Wang	
// USC loginid: yanniwan 
// CS 455 PA3
// Spring 2013
import java.util.ArrayList;
import java.util.Scanner;

/**
PolyProg     
    command-based program for Poly class.
    Prompts cmd> to remind users to type in command
	Allowed command:
	create 0
		0 is the the 0th polynomial, it can also be 1, 2, ..., 9
	print 0
		print the 0th polynomial
	add 2 1 2
		the 2nd polynomial equals 1st polynomial add to 2nd polynomial
	eval 2.2
	 	evaluate the value of polynomial when x is 2.2
	 quit
	 	stop the program
	 other commands are forbidden
	
*/

public class PolyProg {
	private static Poly[] polyGroup;
	private static Scanner in;
	private static Scanner lineScanner;
	private static ArrayList<Integer> argu;
	
/**
    Test results for Poly class.
    @param args not used
 */
	public static void main(String[] args) { 
		polyGroup = new Poly[NUM];
		for(int i = 0; i < NUM; i++) { // initialize the 10 polynomials
			polyGroup[i] = new Poly();
		}
		
		boolean done = true;
		while(done) {
			try {
				done = PolyProgRun();
			}
			catch(InvalidCommandException exception) {
				System.out.println("ERROR: " + exception.getMessage());
			}
			catch(BadArgumentException exception) {
				System.out.println("ERROR: " + exception.getMessage());
			} 
			
		}
	}
	
	/**
	 * Get the command and execute them
	 * @return true or false to determine the end of the loop
	 */
	private static boolean PolyProgRun() 
			throws InvalidCommandException, BadArgumentException {
		System.out.print("cmd> ");
		in = new Scanner(System.in);
		String line = in.nextLine().toLowerCase();
		lineScanner = new Scanner(line);
		String command = lineScanner.next();
		
		if(!(command.equals("create") || command.equals("print") || command.equals("eval") ||
				command.equals("add") || command.equals("addin") || command.equals("copy") ||
				command.equals("mult") || command.equals("help") || command.equals("quit"))) {
			throw new InvalidCommandException("Illegal command. For more information type 'help'");
		}
		
		argu = getArgument(command);
		if(command.equals("quit")) {
			return false;
		}
		doCommand(command);
		return true;
	}
	
	/**
	 * Distinguish between different commands and execute them separately
	 */
	private static void doCommand(String command) {
		if(command.equals("create")) {
			CreateCmd();
		}
		else if(command.equals("copy")) {
			CopyCmd();
		}
		else if(command.equals("eval")) {
			EvalCmd();
		}
		else if(command.equals("print")) {
			PrintCmd();
		}
		else if(command.equals("add")) {
			AddCmd();
		}
		else if(command.equals("addin")) {
			AddInCmd();
		}
		else if(command.equals("mult")) {
			MultCmd();
		}
		else if(command.equals("help")) {
			Help();
		}
	}
	
	/**
	 * Achieve the command argument
	 * @param command the command to instruct what to do
	 * @return a ArrayList which indicates the command argument
	 */
	private static ArrayList<Integer> getArgument(String command) 
			throws BadArgumentException {
		ArrayList<Integer> argu = new ArrayList<Integer>();
		int numArgument = 0;
		while(lineScanner.hasNextInt()) {
			int Argument = lineScanner.nextInt();
			if(Argument < ZERO || Argument > NUM-1) {
				throw new BadArgumentException("illegal index for a poly.  must be between 0 and 9, inclusive");
			}			
			argu.add(Argument);
		}
		if(lineScanner.hasNext()) {
			throw new BadArgumentException("wrong type of input. index should be integer");
		}
		if(command.equals("create") || command.equals("print") || command.equals("eval")) {
			numArgument = ARGU;
		}
		if(command.equals("addin") || command.equals("copy")) {
			numArgument = ARGU1;
		}
		if(command.equals("add") || command.equals("mult")) {
			numArgument = ARGU2;
		}
		
		if(argu.size() < numArgument) {
			throw new BadArgumentException("too few arguments. Expecting " + numArgument + ".\n" + 
					"For more information type 'help'");
		}
		else if(argu.size() > numArgument) {
			throw new BadArgumentException("too many arguments. Expecting " + numArgument + ".\n" + 
						"For more information type 'help'");
		}
		
		return argu;		
	}
	
	/**
	 * Do creating process
	 * 		create polynomials accroding to given term coefficients and exponents
	 * 		example: cmd> create 0
	 * 				Enter a space-separated sequence of coeff-power pairs terminated by <nl>
	 * 				1 100 3 0
	 * 			creates the polynomial x^2 + 2.0x + 1.0
	 * @param num indicates which polynomial to execute
	 */
	private static void CreateCmd() {
		ArrayList<Term> term = new ArrayList<Term>();
		polyGroup[argu.get(0)] = new Poly();
		boolean done = false;
		while(!done) {
			System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by <nl>");
			try {
				term = getTerm();
				done = true;
			}
			catch(BadDataException exception) {
				System.out.println("ERROR: " + exception.getMessage());
			}
		}
		for(int i = 0; i < term.size(); i++) {
			polyGroup[argu.get(0)].addIn(new Poly(term.get(i)));
		}
	}
	
	/**
	 * Achieve the create terms
	 * @return a ArrayList which indicates the terms for create command
	 */
	private static ArrayList<Term> getTerm()
			throws BadDataException {
		ArrayList<Term> term = new ArrayList<Term>();
		String line = in.nextLine();
		lineScanner = new Scanner(line);
		if(!lineScanner.hasNext()) {
			throw new BadDataException("empty input. Please input polynomial data");
		}
		while(lineScanner.hasNext()) {
			if(!lineScanner.hasNextDouble()) {
				throw new BadDataException("wrong input type. A term is a coefficient (double) "
						+ "followed by an exponent (int)");
			}
			double coef = lineScanner.nextDouble();
			if(!(lineScanner.hasNext())) {
				System.out.println("WARNING: missing the last exponent.");
			}
			else if(!lineScanner.hasNextInt()) {
				throw new BadDataException("wrong input type. A term is a coefficient (double) "
						+ "followed by an exponent (int)");
			}
			else {
				int expon = lineScanner.nextInt();
				if(expon < 0) { // check whether the exponent is negative
					throw new BadDataException("negative exponent. An exponent cannot be negative");
				}
				term.add(new Term(coef, expon));
			}
		}
		return term;	
	}
	
	/**
	 *  Do copy process 
	 */
	private static void CopyCmd() {
		polyGroup[argu.get(ZERO)] = new Poly(polyGroup[argu.get(SECOND)]);
	}
	
	/**
	 *  Do print process 
	 */
	private static void PrintCmd() {
		System.out.println(polyGroup[argu.get(ZERO)].toFormattedString());
	}	
		
		
	/**Do evaluation for given float value of x
	 */
	public static void EvalCmd() {
		System.out.print("Enter a floating point value for x: ");
		String line = in.nextLine();
		lineScanner = new Scanner(line);	
		System.out.println(polyGroup[argu.get(ZERO)].eval(lineScanner.nextDouble()));
	}

	/**Do adding process
	 * 		example: cmd> add 2 1 0
	 * 			(poly 2 = poly 0 + poly 1)
	 */
	private static void AddCmd() {
		polyGroup[argu.get(ZERO)] = polyGroup[argu.get(SECOND)].add(polyGroup[argu.get(THIRD)]);
	}
	
	/**Do AddIn process
	 * 		example: cmd> add 2 1 
	 * 			(poly 2 += poly 1)
	 */
	private static void AddInCmd() {
		polyGroup[argu.get(ZERO)].addIn(polyGroup[argu.get(SECOND)]);
	}
	
	/**
	 *  Do mult process 
	 */
	private static void MultCmd() {
		polyGroup[argu.get(ZERO)] = polyGroup[argu.get(SECOND)].mult(polyGroup[argu.get(THIRD)]);
	}
	
	/**
	   Print help 
	 */
	private static void Help() {
		System.out.println("Command Introduction:");
		System.out.println("Commands other than 'create', 'print', 'eval', 'add', 'addIn', 'copy' " +
				"'quit' and 'help' are illegal");
		System.out.println("The number of arguments to the commands 'create', 'eval', 'print' is 1," +
				"that to the commands 'quit', 'help' is 0, that to the commands 'add' is 3, the rest one is 2");
		System.out.println("All indexes should be between 0 to 9");
		System.out.println("create t: Create a new polynomial with index t");
		System.out.println("The polynomial data for the create command should be with right type:" + 
				"doubles are supposed to alternate with int");
		System.out.println("print t: Print the polynomial with index t");
		System.out.println("eval t: Returns the value of the polynomial with index i when x is a given value");
		System.out.println("add t i j: Add polynomials with index i and j, sume i in polynomial with index t");
		System.out.println("addIn t i: Add polynomials with index i to polynomial with index i");
		System.out.println("copy t i: Copy polynomials with index i to polynomial index t");
		System.out.println("quit: Quit the program");
		System.out.println("help: Get help for command");
	}

		
	
	// **************************************************************
    //  PRIVATE INSTANCE VARIABLE(S)
    private static final int NUM = 10;
    private static final int ZERO = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int ARGU = 1;
    private static final int ARGU1 = 2;
    private static final int ARGU2 = 3;

}
