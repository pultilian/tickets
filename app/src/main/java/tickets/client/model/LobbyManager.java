package tickets.client.model;

import java.util.ArrayList;
import java.util.List;

import tickets.common.Game;
import tickets.common.Lobby;
import tickets.common.Player;

public class LobbyManager {
	private List<Lobby> lobbyList;
	private List<Lobby> currentLobbies;
	private List<Game> currentGames;

	public List<Lobby> getLobbyList() {
		return lobbyList;
	}
	
	public LobbyManager() {
		lobbyList = new ArrayList<>();
		currentLobbies = new ArrayList<>();
		currentGames = new ArrayList<>();
	}
	
	public void updateLobbyList(List<Lobby> lobbyList) {
		this.lobbyList = lobbyList;
	}
	
	public Lobby getLobby(String id) {
		for (Lobby l : lobbyList) {
			if (l.getId().equals(id)) {
				return l;
			}
		}
		throw new RuntimeException("invalid lobby id");
	}

	public void addLobby(Lobby lobby) {
		lobbyList.add(lobby);
	}

	public void removeLobby(Lobby lobby) {
		lobbyList.remove(lobby.getId());
	}

	public void addPlayer(Lobby lobby, Player player) {
		getLobby(lobby.getId()).addPlayer(player);
		return;
	}

	public void removePlayer(Lobby lobby, Player player) {
		getLobby(lobby.getId()).removePlayer(player);
		return;
	}

    public void updateCurrentLobbies(List<Lobby> currentLobbies) {
		this.currentLobbies = currentLobbies;
    }

	public void updateCurrentGames(List<Game> currentGames) {
		this.currentGames = currentGames;
	}

	public List<Lobby> getCurrentLobbies() {
		return currentLobbies;
	}

	public List<Game> getCurrentGames() {
		return currentGames;
	}
}
