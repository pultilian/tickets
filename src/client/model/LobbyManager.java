package client.model;

import java.util.Map;

import common.Lobby;
import common.LobbyId;

public class LobbyManager {
	private Map<LobbyId, Lobby> lobbyList;
	
	public void updateLobbyList(Map<LobbyId, Lobby> lobbyList) {
		this.lobbyList = lobbyList;
	}
	
	public Lobby getLobby(LobbyId id) {
		return lobbyList.get(id);
	}
}
