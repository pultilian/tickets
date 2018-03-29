package tickets.client.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tickets.client.ClientCommunicator;
import tickets.client.ClientFacade;
import tickets.client.gui.presenters.LoginPresenter;
import tickets.common.UserData;

public class CL_LoginView extends CommandlineView {
	private LoginPresenter presenter = new LoginPresenter();

	@Override
	void printMenu() {
		System.out.println("*** Welcome to Ticket to Ride ***\n");
		System.out.println("  Select an option: ");
		System.out.println("    1. Register new user");
		System.out.println("    2. Login existing user");
		System.out.println("    3. Exit");
		System.out.println();
	}

	@Override
	boolean handleUserInput(int in) {
		switch(in){
			case 1:
				return register();
			case 2:
				return login();
			case 3:
				this.returnValue = null;
				return true;
			default:
				System.out.println("Input choice not recognized.");
		}
		return false;
	}
	
	private boolean register() {
		boolean success = presenter.register(getUserData());
		if (success)
			this.returnValue = new CL_LobbyListView();
		return success;
	}
	
	private boolean login() {
		boolean success = presenter.login(getUserData());
		if (success)
			this.returnValue = new CL_LobbyListView();
		return success;
	}
	
	private UserData getUserData() { 
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Enter IP: ");
			ClientCommunicator.getInstance().setIP(input.readLine());
			System.out.print("Enter username: ");
			String username = input.readLine();
			System.out.print("Enter password: ");
			String password = input.readLine();
			return new UserData(username, password);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
