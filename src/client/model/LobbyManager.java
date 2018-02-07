package client.model;

import java.util.Map;

import common.Lobby;

public class LobbyManager {
	private Map<String, Lobby> lobbyList;
	
	public void updateLobbyList(Map<String, Lobby> lobbyList) {
		this.lobbyList = lobbyList;
	}
	
	public Lobby getLobby(String id) {
		return lobbyList.get(id);
	}
}
