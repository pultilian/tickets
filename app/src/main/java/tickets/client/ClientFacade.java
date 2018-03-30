package tickets.client;

import java.util.List;
import java.util.Map;

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
import tickets.common.PlayerColor;
import tickets.common.PlayerSummary;
import tickets.common.Route;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
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
	private String currentLobby;
	private UserData userData;
	private Game currentGame;
	private ServerPoller serverPoller = null;
	private Player localPlayer;
	private List<PlayerSummary> gameSummary;

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

	public void setCurrentLobby(String lobbyId) {
		currentLobby = lobbyId;
	}

	public Lobby getLobby() {
		if (currentLobby == null)
			return null;
		return lobbyManager.getLobby(currentLobby);
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

//-------------------------------------------------
//		IClient interface methods
//
//Represents the client to the server.
//These methods are called when commands are retrieved by the poller from the Server.
	public void addLobbyToList(Lobby lobby) {
		lobbyManager.addLobby(lobby);
		ClientModelUpdate update = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.lobbyListUpdated);
        updateObservable(update);
	}

	public void removeLobbyFromList(Lobby lobby) {
		lobbyManager.removeLobby(lobby);
        ClientModelUpdate update = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.lobbyListUpdated);
        updateObservable(update);
	}

	public void addPlayerToLobbyInList(Lobby lobby, Player playerToAdd) {
		lobbyManager.addPlayer(lobby, playerToAdd);
        ClientModelUpdate update = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.lobbyListUpdated);
        updateObservable(update);
	}

	public void removePlayerFromLobbyInList(Lobby lobby, Player player) {
		lobbyManager.removePlayer(lobby, player);
        ClientModelUpdate update = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.lobbyListUpdated);
        updateObservable(update);
	}

	public void removePlayer(Player player) {
		localPlayer = null;
	}

	public void startGame(Game game, HandTrainCard playerHand, ChoiceDestinationCards destCardOptions) {
		game.initializeMap();
		addGame(game);
		currentLobby = null;
		localPlayer.setTrainCardHand(playerHand);
		localPlayer.setDestinationCardOptions(destCardOptions);

		ClientStateChange.ClientState stateVal;
		stateVal = ClientStateChange.ClientState.game;
		ClientStateChange state = new ClientStateChange(stateVal);

		updateObservable(state);
	}

	public List<PlayerSummary> getGameSummary(){
	    return gameSummary;
    }

//-------------------------------------------------
// Update public info
	public void addPlayerTrainCard() {
		currentGame.getActivePlayerInfo().addTrainCard();
        ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerInfoUpdated);
        updateObservable(message);
	}

	public void addClaimedRoute(Route route, RouteColors routeColor, PlayerColor player) {
        currentGame.claimRoute(route, routeColor, player);
	}

	public void addPlayerDestinationCards(int numCards) {
		for (int i = 0; i < numCards; i++) {
			currentGame.getActivePlayerInfo().addDestinationCard();
		}
		ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerInfoUpdated);
		updateObservable(message);
	}

	public void removePlayerDestinationCard() {
		currentGame.getActivePlayerInfo().removeDestinationCard();
		ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerInfoUpdated);
		updateObservable(message);
	}

    public void replaceFaceUpCard(Integer position, TrainCard card) {
        currentGame.replaceFaceUpCard(position, card);
        ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.faceUpCardUpdated);
        updateObservable(message);
    }

	public void endCurrentTurn() {
		currentGame.nextTurn();
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

    public void displayEndGame(List<PlayerSummary> playerSummaries) {
        gameSummary = playerSummaries;
        ClientStateChange state = new ClientStateChange(ClientStateChange.ClientState.summary);
        observable.notify(state);
    }

	public List<TrainCard> getCardsForRoute(Route route) {
        return localPlayer.getCardsForRoute(route);
	}

    public void removeUsedCardsFromPlayerHand(Map<RouteColors, Integer> removeCards) {
	    localPlayer.removeUsedTrainCards(removeCards);
    }

	public List<Route> getClaimedRoutes() {
		return currentGame.getClaimedRoutes();
	}

	public List<Route> getAllRoutes() {
	    return currentGame.getAllRoutes();
	}
}