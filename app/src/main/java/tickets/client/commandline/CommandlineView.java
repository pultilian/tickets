package tickets.client.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class CommandlineView {
	protected CommandlineView returnValue = null;
	
	abstract void printMenu();
	abstract boolean handleUserInput(int in);
	
	public CommandlineView display(){
		boolean exit = false;
		while(!exit){
			printMenu();
			int in = getUserInput();
			exit = handleUserInput(in);
		}
		return returnValue;
	}
	
	private int getUserInput() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Your choice: ");
		
		try {
			String s = input.readLine();
			int in = Integer.parseInt(s);
			return in;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e){
			System.out.println("Only numeric options are valid.");
		}
		
		return 0;
	}
	
}
