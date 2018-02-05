package android.phase1_android_client.model;

import android.phase1_android_client.model.observable.ClientObservable;

import java.util.Map;

import common.Lobby;
import common.LobbyId;
import common.UserData;

public class ClientModelRoot {
	private ClientObservable observable;
	private LobbyManager lobbyManager;
	private UserData userData;
	private Game currentGame;
	
	public void updateObservable() {
		observable.notify();
	}
	
	public void updateLobbyList(Map<LobbyId, Lobby> lobbyList) {
		lobbyManager.updateLobbyList(lobbyList);
	}
	
	public void addAuthenticationToken(String token) {
		userData.setAuthenticationToken(token);
	}
	
	public void addGame(Game game) {
		currentGame = game;
	}
	
	public Game getGame() {
		return currentGame;
	}
}
