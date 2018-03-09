package tickets.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tickets.common.Lobby;
import tickets.common.Player;

public class LobbyManager {
	private List<Lobby> lobbyList;

	public List<Lobby> getLobbyList() {
		return lobbyList;
	}
	
	public LobbyManager() {
		lobbyList = new ArrayList<>();
	}
	
	public void updateLobbyList(List<Lobby> lobbyList) {
		this.lobbyList = lobbyList;
	}
	
	public Lobby getLobby(String id) {
		for (Lobby l : lobbyList) {
			System.out.println(l.getId());
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
}
