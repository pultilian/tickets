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
  public boolean register(UserData userData, ILoginPresenter callback) throws Exception {
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
	  JoinLobbyResponse result = ServerProxy.getInstance().joinLobby(id, null);
	    
		if (result.getException() == null) {
	      return true;
	    }
	    else {
	      return false;
	    }
  }

  public boolean createLobby(Lobby lobby) {
	  JoinLobbyResponse result = ServerProxy.getInstance().createLobby(lobby, null);
	    
		if (result.getException() == null) {
	      return true;
	    }
	    else {
	      return false;
	    }
  }

  public boolean logout() {
	  LogoutResponse result = ServerProxy.getInstance().logout(null);
	    
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
