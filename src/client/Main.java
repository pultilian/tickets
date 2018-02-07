package client;

public class Main {

	public static void main(String[] args) {
		String operation = null;
		String input = null;
		try {
			operation = args[0];
			input = args[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("ERROR: incorrect program usage");
			printUsage();
			printOperations();
		}
		switch(operation) {
			case "toLowerCase":
				String lowerResult = ServerProxy.getInstance().toLowerCase(input);
				System.out.println("Result: " + lowerResult);
				break;
			case "trim":
				String trimResult = ServerProxy.getInstance().trim(input);
				System.out.println("Result: \\" + trimResult + "\\");
				break;
			case "parseInteger":
				try {
					int result = ServerProxy.getInstance().parseInteger(input);
					result += 5;
					System.out.println("Result plus five: " + Integer.toString(result));
				} catch (NumberFormatException e) {
					System.err.println("ERROR: please enter a valid integer to be parsed when using this operation");
					System.out.println("  examples: \'5\', \'2.56\', \'-10\'");
				}
				break;
			default:
				System.err.println("ERROR: operation not recognized");
				printOperations();
				return;
		}
	}

	private static void printUsage() {
		System.out.println("usage: [program] [operation] [input]");
	}

	private static void printOperations() {
		System.out.println("operations ");
		System.out.println("  toLowerCase: converts all letters in input string to lower case");
		System.out.println("  trim: removes whitespace at both ends of input string");
		System.out.println("  parseInteger: if possible, converts input string to integer");
		System.out.println("    (demonstrated by adding 5 to input)");
	}

}
