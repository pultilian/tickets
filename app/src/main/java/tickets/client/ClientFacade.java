package tickets.client;

import java.util.List;

import tickets.client.model.ClientObservable;
import tickets.client.model.LobbyManager;
import tickets.common.ChoiceDestinationCards;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.Game;
import tickets.common.HandTrainCard;
import tickets.common.IClient;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.UserData;


public class ClientFacade implements IClient {
//----------------------------------------------------------------------------
//	Singleton structure
	private static ClientFacade INSTANCE = null;
	public static ClientFacade getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ClientFacade();
		return INSTANCE;
	}
	private ClientFacade() {
		observable = new ClientObservable();
		lobbyManager = new LobbyManager();
	}
	
//----------------------------------------------------------------------------
//	member variables
	private ClientObservable observable;
	private LobbyManager lobbyManager;
	private Lobby currentLobby;
	private UserData userData;
	private Game currentGame;
	private ServerPoller serverPoller = null;
	private Player localPlayer;

//----------------------------------------------------------------------------
//	methods
	public boolean startServerPoller(){
	    if (serverPoller == null){
            serverPoller = new ServerPoller();
        }
		return serverPoller.startPolling();
	}

	public void stopServerPoller(){
		serverPoller.stopPolling();
		serverPoller = null;
	}

//-------------------------------------------------
//	model interface methods

//Observer pattern
	public void updateObservable(IMessage state) {
		observable.notify(state);
	}

//Observer pattern
	public void linkObserver(IObserver observer) {
		observable.linkObserver(observer);
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
	}

	public Lobby getLobby() {
		return currentLobby;
	}

    public Lobby getLobby(String lobbyID) {
  	return lobbyManager.getLobby(lobbyID);
  }

    public Player getLocalPlayer() {
        return localPlayer;
    }
	
//Game data access
	public void addGame(Game game) {
		currentGame = game;
	}
	
	public Game getGame() {
		return currentGame;
	}

	public void setPlayer(Player player) {
	    localPlayer = player;
	}

    public void endCurrentTurn() {
        return;
    }

    public void addChatMessage(String message) {
        currentGame.addToChat(message);
        ClientModelUpdate update = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.chatUpdated);
        updateObservable(update);
    }

    public void addToGameHistory(String message) {
        currentGame.addToHistory(message);
        ClientModelUpdate update = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.gameHistoryUpdated);
        updateObservable(update);
    }

//-------------------------------------------------
//		IClient interface methods
//
//Represents the client to the server.
//These methods are called when commands are retrieved
//	by the poller from the Server.
	public void addLobbyToList(Lobby lobby) {
		if (lobbyManager.getLobbyList().contains(lobby))
			return;
		lobbyManager.addLobby(lobby);
		ClientModelUpdate update = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.lobbyAdded);
        updateObservable(update);
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

	public void removePlayer(Player player) {
		localPlayer = null;
	}

	public void startGame(Game game, HandTrainCard playerHand, ChoiceDestinationCards destCardOptions) {
		currentGame = game;
		currentLobby = null;
		localPlayer.setTrainCardHand(playerHand);
		localPlayer.setDestinationCardOptions(destCardOptions);

		ClientStateChange.ClientState stateVal;
		stateVal = ClientStateChange.ClientState.game;
		ClientStateChange state = new ClientStateChange(stateVal);

		updateObservable(state);
	}

//-------------------------------------------------
// Update public info
	public void addPlayerTrainCard() {
		currentGame.getActivePlayerInfo().addTrainCard();
        ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerInfoUpdated);
        updateObservable(message);
	}

    public void addPlayerPoints(int points) {
        if (localPlayer.getPlayerFaction().getName().equals(currentGame.getActivePlayerInfo().getFaction().getName())) {
            localPlayer.getInfo().addToScore(points);
        }
	    currentGame.getActivePlayerInfo().addToScore(points);
        ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerInfoUpdated);
        updateObservable(message);
    }

    public void removePlayerShips(int numShips) {
	    if (localPlayer.getPlayerFaction().getName().equals(currentGame.getActivePlayerInfo().getFaction().getName())) {
	        localPlayer.getInfo().useShips(numShips);
        }
	    currentGame.getActivePlayerInfo().useShips(numShips);
	    ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerInfoUpdated);
	    updateObservable(message);
    }

    public void addPlayerDestinationCard(){
        currentGame.getActivePlayerInfo().addDestinationCard();
        ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerInfoUpdated);
        updateObservable(message);
    }
}