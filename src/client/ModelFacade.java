package client;

import common.Lobby;
import common.UserData;
import common.response.*;
import client.model.ClientModelRoot;
import client.model.observable.IStateChange;

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
      //TODO: Figure out how to use StateChange to notify presenters of login
      //  modelRoot.updateObserable( ... )
      return true;
    }
    else {
      throw result.getException();
    }
  }

  public boolean login(UserData userData) throws Exception {
	LoginResponse result = ServerProxy.getInstance().login(userData);
    
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
	  JoinLobbyResponse result = ServerProxy.getInstance().joinLobby(id);
	    
		if (result.getException() == null) {
	      return true;
	    }
	    else {
	      return false;
	    }
  }

  public boolean createLobby(Lobby lobby) {
	  JoinLobbyResponse result = ServerProxy.getInstance().createLobby(lobby);
	    
		if (result.getException() == null) {
	      return true;
	    }
	    else {
	      return false;
	    }
  }

  public boolean logout() {
	  LogoutResponse result = ServerProxy.getInstance().logout();
	    
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
