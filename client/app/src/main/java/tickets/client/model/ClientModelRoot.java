package tickets.client.model;

import java.util.Map;

import tickets.common.Lobby;
import tickets.common.UserData;
import tickets.client.model.observable.ClientObservable;
import tickets.client.model.observable.IStateChange;

public class ClientModelRoot {
	private ClientObservable observable;
	private LobbyManager lobbyManager;
	private UserData userData;
	private Game currentGame;
	
	public void updateObservable(IStateChange change) {
		//
	}
	
	public void updateLobbyList(Map<String, Lobby> lobbyList) {
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

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
}
