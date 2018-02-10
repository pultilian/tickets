package tickets.client;

import tickets.common.Lobby;
import tickets.common.UserData;
import tickets.common.response.*;
import tickets.client.model.ClientModelRoot;

public class ModelFacade {
	//Singleton structure
	private static ModelFacade INSTANCE = null;
	public static ModelFacade getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ModelFacade();
		return INSTANCE;
	}
	private ModelFacade() {
		model = new ClientModelRoot();
	}
	
	//variables
	private ClientModelRoot model;

	//methods
	public boolean register(UserData userData) throws Exception {
		LoginResponse result = ServerProxy.getInstance().register(userData);

		if (result.getException() == null) {
			System.out.println(result.getAuthToken());
			model.setUserData(userData);
			model.addAuthenticationToken(result.getAuthToken());
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean login(UserData userData) throws Exception {
		LoginResponse result = ServerProxy.getInstance().login(userData);
    
		if (result.getException() == null) {
			model.setUserData(userData);
			model.addAuthenticationToken(result.getAuthToken());
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean joinLobby(String lobbyId) throws Exception {
		String token = model.getAuthenticationToken();
		JoinLobbyResponse result = ServerProxy.getInstance().joinLobby(lobbyId, token);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean createLobby(Lobby lobby) throws Exception {
		String token = model.getAuthenticationToken();
		JoinLobbyResponse result = ServerProxy.getInstance().createLobby(lobby, token);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean logout() throws Exception {
		String token = model.getAuthenticationToken();
		LogoutResponse result = ServerProxy.getInstance().logout(token);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean startGame(String lobbyId) throws Exception {
		String token = model.getAuthenticationToken();
		StartGameResponse result = ServerProxy.getInstance().startGame(lobbyId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean leaveLobby(String lobbyId) throws Exception {
		String token = model.getAuthenticationToken();
		LeaveLobbyResponse result = ServerProxy.getInstance().leaveLobby(lobbyId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean addGuest(String lobbyId) throws Exception {
		String token = model.getAuthenticationToken();
		AddGuestResponse result = ServerProxy.getInstance().addGuest(lobbyId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean takeTurn(String playerId) throws Exception {
		String token = model.getAuthenticationToken();
		PlayerTurnResponse result = ServerProxy.getInstance().takeTurn(playerId, token);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}
}
