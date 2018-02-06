package client;

import common.Lobby;
import common.UserData;
import common.response.*;
import client.model.ClientModelRoot;
import client.model.observable.IStateChange;
import client.proxy.ServerProxy;

public class ModelFacade {
  //Singleton structure
	public static ModelFacade SINGLETON = new ModelFacade();
	private ModelFacade() {
		modelRoot = new ClientModelRoot();
	}
	
  //variables
  ClientModelRoot modelRoot;

  //methods
  public boolean register(UserData userData) throws Exception {
    LoginResponse result = ServerProxy.SINGLETON.register(userData);

    if (result.getException() == null) {
      modelRoot.setUserData(userData);
      modelRoot.addAuthenticationToken(result.getAuthToken());
      //TODO: Figure out how to use StateChange to notify presenters of login
      //  modelRoot.updateObserable( ... )
      return true;
    }
    else {
      throw result.getException();
    }
  }

  public boolean login(UserData userData) throws Exception {
	LoginResponse result = ServerProxy.SINGLETON.login(userData);
    
	if (result.getException() == null) {
	  modelRoot.setUserData(userData);
	  modelRoot.addAuthenticationToken(result.getAuthToken());
      return true;
    }
    else {
      throw result.getException();
    }
  }

  public boolean joinLobby(String id) {
	  JoinLobbyResponse result = ServerProxy.SINGLETON.joinLobby(id);
	    
		if (result.getException() == null) {
	      return true;
	    }
	    else {
	      return false;
	    }
  }

  public boolean createLobby(Lobby lobby) {
	  JoinLobbyResponse result = ServerProxy.SINGLETON.createLobby(lobby);
	    
		if (result.getException() == null) {
	      return true;
	    }
	    else {
	      return false;
	    }
  }

  public boolean logout() {
	  LogoutResponse result = ServerProxy.SINGLETON.logout();
	    
		if (result.getException() == null) {
	      return true;
	    }
	    else {
	      return false;
	    }
  }

  public boolean startGame(Lobby id) {
    return false;
  }

  public boolean leaveLobby(UserData user) {
    return false;
  }

  public boolean addGuest(Lobby id) {
    return false;
  }

  public boolean takeTurn(String playerId) {
    return false;
  }
}
