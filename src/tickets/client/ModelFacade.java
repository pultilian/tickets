package tickets.client;

import tickets.common.Lobby;
import tickets.common.UserData;
import tickets.common.response.*;
import tickets.client.model.ClientModelRoot;

public class ModelFacade {
	//Singleton structure
	public static ModelFacade INSTANCE = null;
	public static ModelFacade getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ModelFacade();
		return INSTANCE;
	}
	
	//variables
	private ClientModelRoot modelRoot = new ClientModelRoot();

	//methods
	public boolean register(UserData userData) throws Exception {
		LoginResponse result = ServerProxy.getInstance().register(userData);

		if (result.getException() == null) {
			System.out.println(result.getAuthToken());
			modelRoot.setUserData(userData);
			modelRoot.addAuthenticationToken(result.getAuthToken());
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean login(UserData userData) throws Exception {
		LoginResponse result = ServerProxy.getInstance().login(userData);
    
		if (result.getException() == null) {
			modelRoot.setUserData(userData);
			modelRoot.addAuthenticationToken(result.getAuthToken());
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean joinLobby(String id) throws Exception {
		JoinLobbyResponse result = ServerProxy.getInstance().joinLobby(id);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean createLobby(Lobby lobby) throws Exception {
		JoinLobbyResponse result = ServerProxy.getInstance().createLobby(lobby);
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean logout() throws Exception {
		LogoutResponse result = ServerProxy.getInstance().logout();
	    
		if (result.getException() == null) {
	      return true;
	    } else {
	      throw result.getException();
	    }
	}

	public boolean startGame(String lobbyId) throws Exception {
		StartGameResponse result = ServerProxy.getInstance().startGame(lobbyId);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean leaveLobby(String lobbyId) throws Exception {
		LeaveLobbyResponse result = ServerProxy.getInstance().leaveLobby(lobbyId);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean addGuest(String lobbyId) throws Exception {
		AddGuestResponse result = ServerProxy.getInstance().addGuest(lobbyId);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}

	public boolean takeTurn(String playerId) throws Exception {
		PlayerTurnResponse result = ServerProxy.getInstance().takeTurn(playerId);
		if (result.getException() == null) {
			return true;
		} else {
			throw result.getException();
		}
	}
}
