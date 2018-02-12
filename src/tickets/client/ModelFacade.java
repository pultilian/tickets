package tickets.client;

import java.util.ArrayList;
import java.util.List;

import tickets.common.Game;
import tickets.common.IClient;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.UserData;
import tickets.common.response.*;
import tickets.client.model.ClientObservable;
import tickets.client.model.LobbyManager;

public class ModelFacade implements IClient {
	//Singleton structure
	private static ModelFacade INSTANCE = null;
	public static ModelFacade getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ModelFacade();
		return INSTANCE;
	}
	private ModelFacade() {
		observable = new ClientObservable();
		lobbyManager = new LobbyManager();
		localPlayers = new ArrayList<>();
	}
	
	//variables
	private ClientObservable observable;
	private LobbyManager lobbyManager;
	private UserData userData;
	private Game currentGame;
	private List<Player> localPlayers;

	//methods
	public boolean register(UserData userData) throws Exception {
		LoginResponse result = ServerProxy.getInstance().register(userData);
		this.userData = userData;

		if (result.getException() == null) {
			userData.setAuthenticationToken(result.getAuthToken());
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean login(UserData userData) throws Exception {
		LoginResponse result = ServerProxy.getInstance().login(userData);
		this.userData = userData;
    
		if (result.getException() == null) {
			userData.setAuthenticationToken(result.getAuthToken());
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean joinLobby(String lobbyId) throws Exception {
		String token = userData.getAuthenticationToken();
		JoinLobbyResponse result = ServerProxy.getInstance().joinLobby(lobbyId, token);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean createLobby(Lobby lobby) throws Exception {
		String token = userData.getAuthenticationToken();
		JoinLobbyResponse result = ServerProxy.getInstance().createLobby(lobby, token);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean logout() throws Exception {
		String token = userData.getAuthenticationToken();
		LogoutResponse result = ServerProxy.getInstance().logout(token);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean startGame(String lobbyId) throws Exception {
		String token = userData.getAuthenticationToken();
		StartGameResponse result = ServerProxy.getInstance().startGame(lobbyId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean leaveLobby(String lobbyId) throws Exception {
		String token = userData.getAuthenticationToken();
		LeaveLobbyResponse result = ServerProxy.getInstance().leaveLobby(lobbyId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean addGuest(String lobbyId) throws Exception {
		String token = userData.getAuthenticationToken();
		AddGuestResponse result = ServerProxy.getInstance().addGuest(lobbyId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean takeTurn(String playerId) throws Exception {
		String token = userData.getAuthenticationToken();
		PlayerTurnResponse result = ServerProxy.getInstance().takeTurn(playerId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}
	
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
		// TODO Auto-generated method stub
		
	}
	
	public void endCurrentTurn() {
		// TODO Auto-generated method stub
		
	}
	
	public String authenticate() {
		return userData.getAuthenticationToken();
	}
	
}