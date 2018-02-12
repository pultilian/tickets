package tickets.client.model;

import java.util.HashMap;
import java.util.Map;

import tickets.common.Lobby;
import tickets.common.Player;

public class LobbyManager {
	private Map<String, Lobby> lobbyList;
	
	public LobbyManager() {
		lobbyList = new HashMap<>();
	}
	
	public void updateLobbyList(Map<String, Lobby> lobbyList) {
		this.lobbyList = lobbyList;
	}
	
	public Lobby getLobby(String id) {
		return lobbyList.get(id);
	}

	public void addLobby(Lobby lobby) {
		lobbyList.put(lobby.getId(), lobby);
	}

	public void removeLobby(Lobby lobby) {
		lobbyList.remove(lobby.getId());
	}

	public void addPlayer(Lobby lobby, Player player) {
		lobbyList.get(lobby.getId()).addPlayer(player);
	}

	public void removePlayer(Lobby lobby, Player player) {
		lobbyList.get(lobby.getId()).removePlayer(player);
	}
}
