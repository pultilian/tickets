package tickets.client.model;

import java.util.Map;

import tickets.common.Game;
import tickets.common.Lobby;
import tickets.common.UserData;

import tickets.client.model.observable.*;

public class ClientModelRoot {
	private ClientObservable observable;
	private LobbyManager lobbyManager;
	private UserData userData;

	private Lobby currentLobby;
	private Game currentGame;

	public ClientModelRoot() {
		observable = new ClientObservable();
		lobbyManager = new LobbyManager();
	}
	
	//Observer pattern methods
	public void updateObservable(IMessage state) {
		observable.notify(state);
		return;
	}

	public void linkObserver(IObserver observer) {
		observable.linkObserver(observer);
		return;
	}
	
	//Data model methods
	public Map<String, Lobby> getLobbyList() {
		return lobbyManager.getLobbyList();
	}

	public void updateLobbyList(Map<String, Lobby> lobbyList) {
		lobbyManager.updateLobbyList(lobbyList);
	}
	
	public void addAuthenticationToken(String token) {
		userData.setAuthenticationToken(token);
	}

	public String getAuthenticationToken() {
		return userData.getAuthenticationToken();
	}
	
	public void setCurrentLobby(Lobby current) {
		currentLobby = current;
		return;
	}

	public Lobby getCurrentLobby() {
		return currentLobby;
	}

	public Lobby getLobby(String lobbyId) {
		return lobbyManager.getLobby(lobbyId);
	}

	public void addGame(Game game) {
		currentGame = game;
		return;
	}
	
	public Game getGame() {
		return currentGame;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
}
