package tickets.client.commandline;

import tickets.client.gui.presenters.LobbyPresenter;
import tickets.common.Player;

public class CL_LobbyView extends CommandlineView {
	private LobbyPresenter presenter = new LobbyPresenter();

	@Override
	void printMenu() {
		System.out.println("\n*** You are in lobby: " + presenter.getLobby().getName() +" ***\n");
		System.out.println("  Select an option: ");
		System.out.println("    1. Print lobby member list");
		System.out.println("    2. Print lobby history");
		System.out.println("    3. Enter game");
		System.out.println("    4. Leave lobby");
		System.out.println();
	}

	@Override
	boolean handleUserInput(int in) {
		switch(in) {
			case 1:
				printMemberList();
				return false;
			case 2:
				printHistory();
				return false;
			case 3:
				return startGame();
			case 4:
				presenter.leaveLobby(presenter.getLobby().getId());
				returnValue = new CL_LobbyListView();
				return true;
			default:
				System.out.println("Input choice not recognized.");
		}
		return false;
	}

	private boolean startGame() {
	    // If lobby = null, that means someone else has already started the game
	    if (presenter.getLobby() != null)
            presenter.startGame(presenter.getLobby().getId());
        returnValue = new CL_GameView();
		return true;
	}

	private void printMemberList() {
		int i = 1;
		System.out.println("  Players in lobby:");
		for (Player member : presenter.getLobby().getPlayers()) {
			System.out.println(Integer.toString(i) + ". " + member.getPlayerFaction().getName() +
					" (" + member.getPlayerFaction().getColor().toString() + ")");
			i++;
		}
	}
	
	private void printHistory() {
		System.out.println(" Lobby history:");
		for (String message : presenter.getLobby().getHistory()) {
			System.out.println("- " + message);
		}
	}

}
