package tickets.client.commandline;

import tickets.client.gui.presenters.LoginPresenter;

public class LoginView extends CommandlineView {

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
		LoginPresenter presenter = new LoginPresenter();
		presenter.register(getUserData());
	}
	
	private boolean login() {
		LoginPresenter presenter = new LoginPresenter();
		presenter.login(getUserData());
		return false;
	}
	
}
