package tickets.client.commandline;

public class CL_GameView extends CommandlineView {
	

	@Override
	void printMenu() {
		System.out.println("\n*** You are in a game ***\n");
		System.out.println("  Select an option: ");
		System.out.println("    1. Leave game");
		System.out.println();
	}

	@Override
	boolean handleUserInput(int in) {
		switch(in) {
			case 1:
				this.returnValue = new CL_LobbyListView();
				return true;
			default:
				System.out.println("Input choice not recognized.");
		}
		return false;
	}

}
