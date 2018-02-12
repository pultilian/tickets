
package tickets.client;

import tickets.common.UserData;

import tickets.client.gui.presenters.LoginPresenter;
import tickets.client.gui.presenters.IHolderActivity;


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
		LoginPresenter presenter = new LoginPresenter(new StubHolder());
		UserData data = new UserData(input1, input2);
		switch(operation) {
			case "register":
				presenter.register(data);
				System.out.println("User registered");
				break;
			case "login":
				presenter.login(data);
				System.out.println("Logged in");
				break;
			case "--help":
				printOperations();
				return;
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
		System.out.println("  register: register a new user to server, get back authentication token");
		System.out.println("    usage: java client.Main register {username} {password}");
		System.out.println("  login: login a user that has alredy been registered, get back authentication token");
		System.out.println("    usage: java client.Main login {username} {password}");
	}



	public static class StubHolder implements IHolderActivity {
		public void makeTransition(Transition toActivity) {return;}
    public void toastMessage(String message)  {return;}
		public void toastException(Exception e)  {return;}
	}
}
