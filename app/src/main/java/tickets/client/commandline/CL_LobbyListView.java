package tickets.client.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tickets.client.gui.presenters.LobbyListPresenter;
import tickets.common.Lobby;

public class CL_LobbyListView extends CommandlineView {
	private LobbyListPresenter presenter = new LobbyListPresenter();
	
	@Override
	void printMenu() {
		System.out.println("\n***Lobby List View***\n");
		System.out.println("  Select an option: ");
		System.out.println("    1. Create a lobby");
		System.out.println("    2. Join existing lobby");
		System.out.println("    3. Logout");
		System.out.println();
	}

	@Override
	boolean handleUserInput(int in) {
		switch(in) {
			case 1:
				return createLobby();
			case 2:
				return joinLobby();
			case 3:
				presenter.logout();
				this.returnValue = new CL_LoginView();
				return true;
			default:
				System.out.println("Input choice not recognized.");
		}
		return false;
	}
	
	private boolean createLobby() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Enter lobby title: ");
			String name = input.readLine();
			System.out.print("Enter number of players for game: ");
			int players = Integer.parseInt(input.readLine());
			if (players < 2 || players > 5) {
				System.out.println("Games must have 2-5 players");
				return false;
			}
			Lobby lobby = new Lobby(name, players);
			presenter.createLobby(lobby);
			returnValue = new CL_LobbyView();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.println("Number of players should be numeric.");
		}
		return false;
	}
	
	private boolean joinLobby() {
		System.out.println("  Available Lobbies:");
		int i = 1;
		for (Lobby lobby : presenter.getLobbyList()) {
			System.out.println(Integer.toString(i) + ". " + lobby.getName() + "  "
					+ Integer.toString(lobby.getCurrentMembers()) + "/" + 
					Integer.toString(lobby.getMaxMembers()));
			i++;
		}
		System.out.println("x. Cancel");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Enter number of lobby you wish to enter: ");
			String option = input.readLine();
			if (option.equals("x")) {
				return false;
			}
			int lobbyNum = Integer.parseInt(option) - 1;
			if (lobbyNum > presenter.getLobbyList().size()) {
				System.out.println("Option exceeds available lobbies");
				return false;
			}
			Lobby lobby = presenter.getLobbyList().get(lobbyNum);
			presenter.joinLobby(lobby.getId());
			returnValue = new CL_LobbyView();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

}
