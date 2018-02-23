package tickets.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tickets.common.Game;
import tickets.common.IClient;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.UserData;
import tickets.common.response.*;
import tickets.common.IObserver;
import tickets.common.IMessage;

import tickets.client.async.*;
import tickets.client.model.ClientObservable;
import tickets.client.model.LobbyManager;


public class ModelFacade implements IClient {
//----------------------------------------------------------------------------
//	Singleton structure

	private static ModelFacade INSTANCE = null;
	public static ModelFacade getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ModelFacade();
		return INSTANCE;
	}
	private ModelFacade() {
		asyncManager = new AsyncManager(this);

		observable = new ClientObservable();
		lobbyManager = new LobbyManager();
		localPlayers = new ArrayList<>();
	}
	
//----------------------------------------------------------------------------
//	member variables

	private AsyncManager asyncManager;
	private ClientObservable observable;
	private LobbyManager lobbyManager;
	private Lobby currentLobby;
	private UserData userData;
	private Game currentGame;
	private ServerPoller serverPoller = null;
	private List<Player> localPlayers;

//----------------------------------------------------------------------------
//	methods
	public boolean startServerPoller(){
	    if (serverPoller == null){
            serverPoller = new ServerPoller();
        }
		return serverPoller.startPolling();
	}
//-------------------------------------------------
//	model interface methods

//Observer pattern
	public void updateObservable(IMessage state) {
		observable.notify(state);
		return;
	}

//Observer pattern
	public void linkObserver(IObserver observer) {
		observable.linkObserver(observer);
		return;
	}

//User Data access
	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public void addAuthToken(String token) {
		userData.setAuthenticationToken(token);
	}

	public String getAuthToken() {
		return userData.getAuthenticationToken();
	}

//Lobby Data access
	public void updateLobbyList(List<Lobby> lobbyList) {
		lobbyManager.updateLobbyList(lobbyList);
	}

	public List<Lobby> getLobbyList() {
		return lobbyManager.getLobbyList();
	}

	public void setCurrentLobby(Lobby lobby) {
		currentLobby = lobby;
		return;
	}

	public Lobby getLobby() {
		return currentLobby;
	}

  public Lobby getLobby(String lobbyID) {
  	return lobbyManager.getLobby(lobbyID);
  }

	
//Game data access
	public void addGame(Game game) {
		currentGame = game;
		return;
	}
	
	public Game getGame() {
		return currentGame;
	}

//-------------------------------------------------
//		server interface methods
//
//mirror the server interface to the presenters
//calls are made on the ServerProxy via AsyncTask objects

	public void register(UserData userData) {
		setUserData(userData);
		System.out.println("ModelFacade calling AsyncManager");
		asyncManager.register(userData);
		return;
	}

	public void login(UserData userData) {
		setUserData(userData);
		asyncManager.login(userData);
		return;
	}

	public void joinLobby(String lobbyID) {
		asyncManager.joinLobby(lobbyID, getAuthToken());
		return;
	}

	public void createLobby(Lobby lobby) {
		asyncManager.createLobby(lobby, getAuthToken());
		return;
	}

	public void logout() {
		asyncManager.logout(getAuthToken());
		return;
	}

	public void startGame(String lobbyID) {
		asyncManager.startGame(lobbyID, getAuthToken());
		return;
	}

	public void leaveLobby(String lobbyID) {
		asyncManager.leaveLobby(lobbyID, getAuthToken());
		return;
	}

	public void addGuest(String lobbyID) {
		asyncManager.addGuest(lobbyID, getAuthToken());
		return;
	}

	public void takeTurn(String playerID) {
		asyncManager.takeTurn(playerID, getAuthToken());
		return;
	}

	public void addToChat(String message) {
		asyncManager.addToChat(message, getAuthToken());
	}

//-------------------------------------------------
//		IClient interface methods
//
//Represents the client to the server.
//These methods are called when commands are retrieved
//	by the poller from the Server.
	public void addLobbyToList(Lobby lobby) {
		lobbyManager.addLobby(lobby);
	}
	public void removeLobbyFromList(Lobby lobby) {
		lobbyManager.removeLobby(lobby);
	}
	public void addPlayerToLobbyInList(Lobby lobby, Player playerToAdd) {
		lobbyManager.addPlayer(lobby, playerToAdd);
	}
	public void removePlayerFromLobbyInList(Lobby lobby, Player player) {
		lobbyManager.removePlayer(lobby, player);
	}
	public void addPlayer(Player player) {
		localPlayers.add(player);
	}
	public void removePlayer(Player player) {
		localPlayers.remove(player);
	}
	public void startGame() {
		return;
	}
	public void endCurrentTurn() {
		return;
	}
	public void addChatMessage(String message) { currentGame.addToChat(message); }

}