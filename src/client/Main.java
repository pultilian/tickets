package client;

import client.gui.presenters.LoginPresenter;
import common.UserData;

public class Main {

	public static void main(String[] args) {
		String operation = null;
		String input1 = null;
		String input2 = null;
		try {
			operation = args[0];
			input1 = args[1];
			input2 = args[2];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("ERROR: incorrect program usage");
			printUsage();
			printOperations();
		}
		switch(operation) {
			case "register":
				LoginPresenter presenter = new LoginPresenter();
				UserData data = new UserData(input1, input2);
				presenter.register(data);
				System.out.println("User registered");
				break;
			default:
				System.err.println("ERROR: operation not recognized");
				printOperations();
				return;
		}
	}

	private static void printUsage() {
		System.out.println("general usage: java client.Main [operation] [inputs]");
	}

	private static void printOperations() {
		System.out.println("operations ");
		System.out.println("  register: if possible, converts input string to integer");
		System.out.println("    (demonstrated by adding 5 to input)");
		System.out.println("    usage: java client.Main register {username} {password}");
	}

}
